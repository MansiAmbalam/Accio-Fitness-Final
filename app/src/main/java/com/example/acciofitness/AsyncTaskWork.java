package com.example.acciofitness;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
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

public class AsyncTaskWork extends AsyncTask<String,Void,String> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    boolean success=false;
    TextView v1;
    TextView v2;
    TextView v3;
    public AlertDialog.Builder alertDialog;
    JSONObject json=new JSONObject();
    String url_activity,type;
    AsyncTaskWork(RegistrationActivity cxt) {
        context = cxt;
        alertDialog= new AlertDialog.Builder(context);
    }

    AsyncTaskWork(LoginActivity cxt) {
        context=cxt;
        alertDialog = new AlertDialog.Builder(context);
    }
    AsyncTaskWork(UpdateDiseaseActivity cxt) {
        context=cxt;
        alertDialog = new AlertDialog.Builder(context);
    }
    AsyncTaskWork(UpdateDietActivity cxt) {
        context=cxt;
        alertDialog = new AlertDialog.Builder(context);
    }
    AsyncTaskWork(NavigationActivity cxt,TextView tv1) {
        context=cxt;
        v1=tv1;
    }
    AsyncTaskWork(NavigationActivity cxt, TextView tv1, TextView tv2, TextView tv3) {
        context=cxt;
        v1=tv1;
        v2=tv2;
        v3=tv3;
    }

    @Override
    protected String doInBackground(String... params) {
        type=params[0];
        if(params[0].equals("Register")) {
            // url_activity="http://192.168.137.223/project_api/user/create.php";
            url_activity="http://e71629af8ad8.ngrok.io/project_api/user/create.php";
            try {
                json.put("name", params[1]);
                json.put("email", params[2]);
                json.put("pwd", params[3]);
                json.put("contact", params[4]);
                json.put("bdate", params[5]);
                json.put("cpwd", params[6]);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else if(params[0].equals("Login"))
        {
            // url_activity="http://192.168.137.223/project_api/user/login.php";
            url_activity="http://e71629af8ad8.ngrok.io/project_api/user/login.php";
            try {
                json.put("email", params[1]);
                json.put("pwd", params[2]);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else if(params[0].equals("UpdateDisease"))
        {
            // url_activity="http://192.168.137.223/project_api/user/login.php";
            url_activity="http://e71629af8ad8.ngrok.io/project_api/disease/update.php";
            try {
                json.put("dname", params[1]);
                json.put("symName", params[2]);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else if(params[0].equals("UpdateDiet"))
        {
            // url_activity="http://192.168.137.223/project_api/user/login.php";
            url_activity="http://e71629af8ad8.ngrok.io/project_api/diet/update.php";
            try {
                json.put("dname", params[1]);
                json.put("bf", params[2]);
                json.put("lu", params[3]);
                json.put("di", params[4]);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else if(type.equals("Diet"))
        {
            // url_activity="http://192.168.137.223/project_api/diet/read.php";
            url_activity="http://e71629af8ad8.ngrok.io/project_api/diet/read.php";
            try {
                json.put("name", params[1]);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else if(type.equals("Recipe"))
        {
            // url_activity="http://192.168.137.223/project_api/diet/read.php";
            url_activity="http://e71629af8ad8.ngrok.io/project_api/recipe/read.php";
            try {
                json.put("name", params[1]);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // url_activity="http://192.168.137.223/project_api/disease/read.php";
            url_activity="http://e71629af8ad8.ngrok.io/project_api/disease/read.php";
            try {
                json.put("name", params[1]);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
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
        return result.toString();
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
        if(type.equals("Register") || type.equals("Login")|| type.equals("UpdateDisease")|| type.equals("UpdateDiet"))
        {
            alertDialog.create();
            alertDialog.setTitle("Status");
            if (success && (type.equals("Register") || type.equals("Login"))) {
                Intent intent = new Intent(context, SymptomActivity.class);
                context.startActivity(intent);
            }
            else if(type.equals("UpdateDisease"))
            {
                Intent targetIntent = new Intent(context,UpdateDiseaseActivity.class);
                context.startActivity(targetIntent);
                ((Activity) context).finish();
            }
            else if(type.equals("UpdateDiet"))
            {
                Intent targetIntent = new Intent(context,UpdateDietActivity.class);
                context.startActivity(targetIntent);
                ((Activity) context).finish();
            }
            StringBuilder finalRes=new StringBuilder(result);
            finalRes.deleteCharAt(0);
            finalRes.deleteCharAt(finalRes.length()-1);
            alertDialog.setMessage(finalRes);
            alertDialog.show();
        }
        else if(type.equals("Diet"))
        {   int bfIndex=result.indexOf("Breakfast");
            int luIndex=result.indexOf("Lunch");
            int diIndex=result.indexOf("Dinner");
            StringBuilder finResBf=new StringBuilder(result.substring(bfIndex,luIndex));
            StringBuilder finResLu=new StringBuilder(result.substring(luIndex,diIndex));
            StringBuilder finResDi=new StringBuilder(result.substring(diIndex));
            int index;
            while((index=finResBf.indexOf("\""))!=-1)
                finResBf.deleteCharAt(index);
            while((index=finResLu.indexOf("\""))!=-1)
                finResLu.deleteCharAt(index);
            while((index=finResDi.indexOf("\""))!=-1)
                finResDi.deleteCharAt(index);
            index=finResBf.indexOf("[");
            finResBf.deleteCharAt(index);
            index=finResBf.indexOf("]");
            finResBf.deleteCharAt(index);
            index=finResLu.indexOf("[");
            finResLu.deleteCharAt(index);
            index=finResLu.indexOf("]");
            finResLu.deleteCharAt(index);
            index=finResDi.indexOf("[");
            finResDi.deleteCharAt(index);
            index=finResDi.indexOf("]");
            finResDi.deleteCharAt(index);
            finResBf.deleteCharAt(finResBf.length()-1);
            finResLu.deleteCharAt(finResLu.length()-1);
            finResDi.deleteCharAt(finResDi.length()-1);
            v1.setText(finResBf);
            v2.setText(finResLu);
            v3.setText(finResDi);
        }
        else if(type.equals("Recipe"))
        {
            int nmIndex=result.indexOf("Name");
            int deIndex=result.indexOf("Description");
            int inIndex=result.indexOf("Ingredients");
            StringBuilder finResNm=new StringBuilder(result.substring(nmIndex,deIndex));
            StringBuilder finResDe=new StringBuilder(result.substring(deIndex,inIndex));
            StringBuilder finResIn=new StringBuilder(result.substring(inIndex));
            int index;
            while((index=finResNm.indexOf("\""))!=-1)
                finResNm.deleteCharAt(index);
            while((index=finResDe.indexOf("\""))!=-1)
                finResDe.deleteCharAt(index);
            while((index=finResIn.indexOf("\""))!=-1)
                finResIn.deleteCharAt(index);
            index=finResIn.indexOf("{");
            finResIn.deleteCharAt(index);
            index=finResIn.indexOf("}");
            finResIn.deleteCharAt(index);
            finResNm.deleteCharAt(finResNm.length()-1);
            finResIn.deleteCharAt(finResIn.length()-1);
            finResDe.deleteCharAt(finResDe.length()-1);
            v1.setText(finResNm);
            v2.setText(finResIn);
            v3.setText(finResDe);
        }
        else
        {
            String[] disName=result.split("\"");
            StringBuffer finDis=new StringBuffer();
            for(int i=1;i<disName.length;i=i+2)
                finDis.append(disName[i]+"\n");
            v1.setText(finDis);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }
}
