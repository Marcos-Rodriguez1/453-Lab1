package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Welcome extends AppCompatActivity {
    private String TAG=Welcome.class.getSimpleName();
    TextView test;
    TextView test2;
    TextView idmodel;
    Spinner spinner;
    Spinner spinner2;
    String VeModel;
    String Carid;
    String Modelid;
    String Vmakeid;
    String modelweb;
    String availableweb;


    String VMake;
    private ListView lv;

    private FrameLayout fragmentContainer;

    Button mButton;
    Button mButton2;
    private static String urlcars=("https://thawing-beach-68207.herokuapp.com/carmakes");
    private static String urlmodles =("https://thawing-beach-68207.herokuapp.com/carmodelmakes/");
    private static String urlavailable=("https://thawing-beach-68207.herokuapp.com/cars/");
    private static String urldetails=("https://thawing-beach-68207.herokuapp.com/cars/");

    ArrayList<HashMap<String,String>> carList;
    ArrayList<HashMap<String,String>> modelList;
    ArrayList<HashMap<String,String>> newIdList = new ArrayList<>();

    String VeMake[]= new String []{"Jaguar","Tesla","Lamborghini","Ferrari","Porsche","Bugatti","Maserati","BMW","Aston Martin","Bently"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        carList=new ArrayList<>();
        modelList=new ArrayList<>();
        newIdList = new ArrayList<>();

        spinner=(Spinner) findViewById(R.id.spin);
        spinner2=(Spinner) findViewById(R.id.spin2);
        mButton=(Button)findViewById(R.id.button);
        mButton2=(Button)findViewById(R.id.button2);
        //test=(TextView)findViewById(R.id.test);
       // test2=(TextView)findViewById(R.id.test2);
        idmodel=(TextView)findViewById(R.id.idmodel);
        lv = findViewById(R.id.list);

        //For the fragment
        fragmentContainer = findViewById(R.id.fragment_carInfo);


        //This setonclick listener gets theh selected from the spinner then gets the car id and model as a string the parses it so we can
        // put it into the url so we can get the next api
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Carid= spinner.getSelectedItem().toString();
                Carid = Carid.replaceAll("[^-?0-9]+", "");

                modelweb=(urlmodles+Carid);

                int cid=Integer.parseInt(Carid);

                VMake=VeMake[cid-2];

                new GetModel().execute();

            }
        });

        //This method gets the information as a string from the spinner parses it so we can add it to the url and recieve the information for the next api
        mButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Modelid=spinner2.getSelectedItem().toString();

                String [] twoStringArray= Modelid.split(",",3);

                Modelid=twoStringArray[1];
                Modelid = Modelid.replaceAll("[^-?0-9]+", "");
                Vmakeid=twoStringArray[2];
                Vmakeid = Vmakeid.replaceAll("[^-?0-9]+", "");
                availableweb=(urlavailable+Vmakeid+"/"+Modelid+"/"+"92603");
                VeModel=twoStringArray[0];
                String target=VeModel.copyValueOf("{model=".toCharArray());
                VeModel=VeModel.replace(target,"");
                new getNewVehicle().execute();
            }
        });

        //This gets the item from teh listview and we parse to get the final url and then pass it through the fragment
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               String value = lv.getItemAtPosition(i).toString();
               value = value.replaceAll("[^-?0-9]+", "");


               String temp = urldetails + value; //Give the final url


               //Open the fragment
               openFragment(temp,VeModel,VMake);


           }
       });


        new GetCars().execute();
    }

    public void openFragment(String test1,String test2, String test3)
    {
        BlankFragment fragment = BlankFragment.newInstance(test1,test2,test3);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Only close fragment
        transaction.addToBackStack(null);

        transaction.replace(R.id.fragment_carInfo, fragment, "BLANK_FRAGMENT").commit();
    }

    //creates an async tasks
    private class GetCars extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected Void doInBackground (Void... arg0)
        {
            HttpHandler sh=new HttpHandler();

            String jsonStr=sh.makeServiceCall(urlcars);

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

    private class getNewVehicle extends AsyncTask<Void,Void,Void>
    {
        //so you put loading bar and shit here
        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected Void doInBackground (Void... arg0)
        {
            newIdList.clear();

            HttpHandler sh=new HttpHandler();

            String jsonStr=sh.makeServiceCall(availableweb);

            Log.e(TAG,"response from url: "+jsonStr );

            if(jsonStr != null)
            {
                try{


                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray newVehicleId = jsonObj.getJSONArray("lists");


                    for(int i=0;i< newVehicleId.length();i++)
                    {
                        JSONObject c = newVehicleId.getJSONObject(i);

                        String idNew=c.getString("id");

                        HashMap<String,String> vehicleInformation =new HashMap<>();

                        vehicleInformation.put("id",idNew);


                        newIdList.add(vehicleInformation);
                    }

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
            ListAdapter adapter= new SimpleAdapter(Welcome.this,newIdList,
                    R.layout.list3_item, new String[]{"id"},
                    new int []{R.id.newVehicle_id});


            lv.setAdapter(adapter);

        }
    }

}
