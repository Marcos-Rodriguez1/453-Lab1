package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Welcome extends AppCompatActivity {
//Creates the welcome screen and gets the information from the main activity
    private String TAG=Welcome.class.getSimpleName();
    TextView name;
    ListView lv;

    private static String urlcars=("https://thawing-beach-68207.herokuapp.com/carmakes");
    private static String urlmodles =("https://thawing-beach-68207.herokuapp.com/carmodelmakes/2");
    private static String urlavailable=("https://thawing-beach-68207.herokuapp.com/carmodelmakes/2");
    private static String urldetails=("https://thawing-beach-68207.herokuapp.com/cars/%3ccarid");

    ArrayList<HashMap<String,String>> carList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        name=(TextView) findViewById(R.id.UN);
        name.setText(getIntent().getStringExtra("Username"));

        carList=new ArrayList<>();
        lv=(ListView) findViewById(R.id.list);
        new GetCars().execute();
    }

    //creates an async task
    private class GetCars extends AsyncTask<Void,Void,Void>
    {
        //so you put loading bar and shit here
        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected Void doInBackground (Void... arg0)
        {
            HttpHandler sh=new HttpHandler();

            String jsonStr=sh.makeServiceCall(urlcars);

            Log.e(TAG,"response from url: "+jsonStr );

            if(jsonStr != null)
            {
                try{

                    JSONArray json= new JSONArray(jsonStr);

                    for(int i=0;i<json.length();i++)
                    {
                        JSONObject c =json.getJSONObject(i);

                        String id=c.getString("id");
                        String vehicle_make=c.getString("vehicle_make");

                        HashMap<String,String> car=new HashMap<>();

                        car.put("id",id);
                        car.put("vehicle_make",vehicle_make);

                        carList.add(car);
                    }

                    //add the exception here if that shit doesnt work
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            ListAdapter adapter=new SimpleAdapter
                    (Welcome.this,carList,
                            R.layout.list_item,new String[]{"id","vehicle_make"},
                            new int []{R.id.id,R.id.vehicle_make});
            lv.setAdapter(adapter);
        }
    }
}
