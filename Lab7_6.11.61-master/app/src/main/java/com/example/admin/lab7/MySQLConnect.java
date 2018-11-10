package com.example.admin.lab7;

import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnect {

    private final Activity main;
    private List<String> list;
    private String URL ="http;//192.168.1.11/" ,
            GET_URL ="loginapp/get_post.php" ,
            SENT_URL = "loginapp/sent_post.php",
            DEL_URL = "loginapp/del_post.php",
            UPD_URL = "loginapp/upd_post.php";

    public String std_id ="";
    public String std_name ="";
    public String std_tel ="";
    public String std_email ="";
    public String string ="";


    public MySQLConnect() { main = null;}

    public MySQLConnect(Activity mainA){
        main = mainA;
        list = new ArrayList<String>();
    }

    public List<String> getData() {

        String url = URL + GET_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);

                if(list.isEmpty()){
                    Toast.makeText(main, "ไม่มีข้อมูล", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(main, "ข้อมูลในฐานข้อมูล", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(main, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(main.getApplicationContext());
        requestQueue.add(stringRequest);

        return list;
    }

    public void showJSON(String response) {
        std_id="";
        std_name="";
        std_tel="";
        std_email="";
        string="";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject collectData = result.getJSONObject(i);
                std_id = collectData.getString("std_id");
                std_name = collectData.getString("std_name");
                std_tel = collectData.getString("std_tel");
                std_email = collectData.getString("std_email");
                string = std_id+"\n"+ std_name+"\n" +std_tel+"\n" +std_email;
                list.add(string);

            }

        }catch (JSONException ex){ ex.printStackTrace(); }
    }


    public void sentData(String StdId, String StdName, String StdTel, String StdEmail) {
        StrictMode.enableDefaults();
        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try{
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(  "isAdd",  "true"));
            nameValuePairs.add(new BasicNameValuePair( "stdid", StdId));
            nameValuePairs.add(new BasicNameValuePair( "stdname", StdName));
            nameValuePairs.add(new BasicNameValuePair( "stdtel", StdTel));
            nameValuePairs.add(new BasicNameValuePair( "stdemail", StdEmail));
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL + SENT_URL);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpClient.execute(httpPost);

            Toast.makeText(main,  "Completed.", Toast.LENGTH_LONG).show();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        public void upd_data(final String dataStdId,final String dataStdName,final String dataStdTel,final String dataStdEmail) {
            StrictMode.enableDefaults();
            if(Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            try{
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(  "isAdd",  "true"));
                nameValuePairs.add(new BasicNameValuePair( "stdid", StdId));
                nameValuePairs.add(new BasicNameValuePair( "stdname", StdName));
                nameValuePairs.add(new BasicNameValuePair( "stdtel", StdTel));
                nameValuePairs.add(new BasicNameValuePair( "stdemail", StdEmail));
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL + UPD_URL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                httpClient.execute(httpPost);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


