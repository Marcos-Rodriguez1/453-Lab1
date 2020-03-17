package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
    TextView test;

    Spinner spinner;
    Spinner spinner2;
    String Carid;
    String modelweb;

    Button mButton;
    private static String urlcars=("https://thawing-beach-68207.herokuapp.com/carmakes");
    private static String urlmodles =("https://thawing-beach-68207.herokuapp.com/carmodelmakes/");
    private static String urlavailable=("https://thawing-beach-68207.herokuapp.com/cars/10/20/92603");
    private static String urldetails=("https://thawing-beach-68207.herokuapp.com/cars/%3ccarid");

    ArrayList<HashMap<String,String>> carList;
    ArrayList<HashMap<String,String>> modelList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        carList=new ArrayList<>();
        modelList=new ArrayList<>();
        spinner=(Spinner) findViewById(R.id.spin);
        spinner2=(Spinner) findViewById(R.id.spin2);
        mButton=(Button)findViewById(R.id.button);
        test=(TextView)findViewById(R.id.test);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Carid= spinner.getSelectedItem().toString();
                Carid = Carid.replaceAll("[^-?0-9]+", "");
                test.setText(urlmodles+Carid);
                modelweb=(urlmodles+Carid);
                new GetModel().execute();
            }
        });

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

                    JSONArray cars = new JSONArray(jsonStr);

                    for(int i=0;i<cars.length();i++)
                    {
                        JSONObject c =cars.getJSONObject(i);

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

            SpinnerAdapter adapter= new SimpleAdapter(Welcome.this,carList,
                    R.layout.list_item,new String[]{"id","vehicle_make"},
                    new int []{R.id.id,R.id.vehicle_make});
            spinner.setAdapter(adapter);

        }
    }

    private class GetModel extends AsyncTask<Void,Void,Void>
    {
        //so you put loading bar and shit here
        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected Void doInBackground (Void... arg0)
        {
            modelList.clear();

            HttpHandler sh=new HttpHandler();

            String jsonStr=sh.makeServiceCall(modelweb);

            Log.e(TAG,"response from url: "+jsonStr );

            if(jsonStr != null)
            {
                try{

                    JSONArray models = new JSONArray(jsonStr);

                    for(int i=0;i<models.length();i++)
                    {
                        JSONObject c =models.getJSONObject(i);

                        String id=c.getString("id");
                        String model=c.getString("model");
                        String vehicle_make_id=c.getString("vehicle_make_id");

                        HashMap<String,String> modelcar=new HashMap<>();

                        modelcar.put("id",id);
                        modelcar.put("model",model);
                        modelcar.put("vehicle_make_id",vehicle_make_id);

                        modelList.add(modelcar);
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
            SpinnerAdapter adapter= new SimpleAdapter(Welcome.this,modelList,
                    R.layout.list2_item,new String[]{"id","model","vehicle_make_id"},
                    new int []{R.id.idmodel,R.id.model,R.id.vehicle_makeid});
            spinner2.setAdapter(adapter);

        }
    }








}
