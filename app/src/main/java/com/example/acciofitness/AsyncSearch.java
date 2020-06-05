package com.example.acciofitness;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsyncSearch extends AsyncTask<String,Void,String> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    boolean success;
    InputStream inputStream;
    HttpURLConnection httpURLConnection;
    StringBuilder resultTmp=new StringBuilder();
    String line=null;
    String type;
    JSONObject json=new JSONObject();
    final List<String> list = new ArrayList();
    AsyncSearch(NavigationActivity cxt) {
        context=cxt;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            String url_str;
            type=params[0];
            if(type.equals("Symptom"))
                url_str="http://e71629af8ad8.ngrok.io/project_api/disease/display.php";
            else if(type.equals("Recipe"))
                url_str="http://e71629af8ad8.ngrok.io/project_api/recipe/display.php";
            else if(type.equals("Food"))
                url_str="http://e71629af8ad8.ngrok.io/project_api/diet/food.php";
            else
                url_str="http://e71629af8ad8.ngrok.io/project_api/diet/display.php";
            URL url = new URL(url_str);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("User-Agent", "GYUserAgentAndroid");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setUseCaches(false);
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                success = true;
            } else {
                inputStream = httpURLConnection.getErrorStream();
                success = false;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            String line;
            while ((line = bufferedReader.readLine()) != null) resultTmp.append(line);
            bufferedReader.close();
            inputStream.close();
        }
        catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }
        httpURLConnection.disconnect();
        return resultTmp.toString();
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
        try
        {
            JSONArray JA=new JSONArray(result);
            JSONObject json= null;
            String column;
            final String[] str1 = new String[JA.length()];
            if(type.equals("Symptom"))
                column="symptom_name";
            else if(type.equals("Recipe"))
                column="recipe_name";
            else if(type.equals("Food"))
                column="food_name";
            else
                column="disease_name";
            for(int i=0;i<JA.length();i++)
            {
                json=JA.getJSONObject(i);
                str1[i]=json.getString(column);
            }

            for(int i=0;i<str1.length;i++)
            {
                list.add(str1[i]);
            }
            Collections.sort(list);
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }
}
