package com.example.homework1_jm;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;


public class BlankFragment extends Fragment {

    //text, text2, and text3 get the url and then get the vehicle make and model
    private static final String TEXT = "text";
    private static final String TEXT2= "text2";
    private static final String TEXT3= "text3";

    //String that will hold the final api call and the vehicle make and model
    private String finalApiCall;
    private String mm;

    //Hashmap that will store the values of information
    private HashMap<String,String> vehicleDISPLAY=new HashMap<>();

   //Text views that will show information to the user
    private TextView makeAndModelDisplay;
    private TextView price;
    private TextView location;
    private ImageView image;
    private TextView update;

    public BlankFragment() {}

    //Grabs the arguments and places them into the temporary text fields
    public static BlankFragment newInstance(String text,String text2, String text3) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(TEXT, text);
        args.putString(TEXT2,text2);
        args.putString(TEXT3,text3);
        fragment.setArguments(args);
        return fragment;
    }

    //Grab the information and place it in variables that will display the information
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            finalApiCall = getArguments().getString(TEXT);
            mm=getArguments().getString(TEXT2)+ " " +getArguments().getString(TEXT3);
        }
    }

    //Connect all the views and call the async method to grab the information
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        price = view.findViewById(R.id.priceInfo);
        location = view.findViewById(R.id.vehicleInfo);
        image = view.findViewById(R.id.vehiclePicture);
        update = view.findViewById(R.id.lastUpdateInfo);
        makeAndModelDisplay = view.findViewById(R.id.makeModel);
        new GetInfo().execute();

        return view;
    }

    //Where the information will be grabbed from the api call
    private class GetInfo extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {}

        @Override
        protected Void doInBackground (Void... arg0)
        {
            //To open up the api calls
            HttpHandler sh=new HttpHandler();

            //Get the strings for the api call
            String jsonStr=sh.makeServiceCall(finalApiCall);

            if(jsonStr != null)
            {
                try{

                    //Where the information will be placed
                    JSONArray vehicleInfo = new JSONArray(jsonStr);

                    for(int i=0; i < vehicleInfo.length();i++)
                    {
                        //Where the api info will show up
                        JSONObject c = vehicleInfo.getJSONObject(i);

                        //Fetching all the information
                        String vehiclePRICE =c.getString("price");
                        String vehicleDES=c.getString("veh_description");
                        String vehicleIMAGE = c.getString("image_url");
                        String vehicleUPDATED = c.getString("updated_at");

                        vehicleDISPLAY.put("price", vehiclePRICE);
                        vehicleDISPLAY.put("veh_description", vehicleDES);
                        vehicleDISPLAY.put("image_url", vehicleIMAGE);
                        vehicleDISPLAY.put("updated_at", vehicleUPDATED);
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

            makeAndModelDisplay.setText(mm);

            String url = vehicleDISPLAY.get("image_url"); //Gets the url and sets it to string to avoid errors

            price.setText("Price: $" + vehicleDISPLAY.get("price")); //Set the price with money signs

            location.setText(vehicleDISPLAY.get("veh_description"));// Place the location

            Glide.with(BlankFragment.this).load(url).into(image); //Dispaly the image

            update.setText("Last Update: " + vehicleDISPLAY.get("updated_at")); //Display when it was last displayed
        }
    }
}
