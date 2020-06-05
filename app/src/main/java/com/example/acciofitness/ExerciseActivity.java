package com.example.acciofitness;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ExerciseActivity extends NavigationActivity {
    ImageView iv;
    boolean success;
    AsyncExercise as=new AsyncExercise();
    JSONObject json=new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_exercise, null, false);
        frameLayout.addView(activityView);
        iv=findViewById(R.id.image);
    }
    public void displayImg(View view)
    {
        int btnId=view.getId();
        Button btn=view.findViewById(btnId);
        String path=as.execute(btn.getText().toString());
        //String path=as.resFinal;
        String imageUrl = "http://e71629af8ad8.ngrok.io/project_api/exercise/exercise_image/"+path.substring(1,path.length()-1);
        Log.e("OPUrl",imageUrl);
        Picasso.get().load(imageUrl).into(iv);
    }
    class AsyncExercise
    {
        public String execute(String type)
        {
            String url_activity = "http://e71629af8ad8.ngrok.io/project_api/exercise/type.php";
            try {
                json.put("exType", type);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            InputStream inputStream;
            HttpURLConnection httpURLConnection;
            StringBuilder result=new StringBuilder();
            try {
                URL url = new URL(url_activity);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("User-Agent", "GYUserAgentAndroid");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setUseCaches(false);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(json.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                    success=true;
                }
                else
                {
                    inputStream = httpURLConnection.getErrorStream();
                    success=false;
                }
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) result.append(line);
                    bufferedReader.close();
                    inputStream.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                httpURLConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("OP",result.toString());
            return result.toString();
        }
    }
}
