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

    private static final String TEXT = "text";
    private static final String TEXT2= "text2";
    private static final String TEXT3= "text3";

    private String finalApiCall;
    private HashMap<String,String> vehicleDISPLAY=new HashMap<>();

    //Information to display
    private TextView makeAndModelDisplay;
    private TextView price;
    private TextView location;
    private ImageView image;
    private TextView update;
    private String mm;


    public BlankFragment() {}



                                                           //String param2
    public static BlankFragment newInstance(String text,String text2, String text3) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(TEXT, text);
        args.putString(TEXT2,text2);
        args.putString(TEXT3,text3);
        fragment.setArguments(args);
        return fragment;
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);


        price = view.findViewById(R.id.priceInfo);
        location = view.findViewById(R.id.vehicleInfo);
        image = view.findViewById(R.id.vehiclePicture);
        update = view.findViewById(R.id.lastUpdateInfo);
        makeAndModelDisplay = view.findViewById(R.id.makeModel)
        new GetInfo().execute();

//        thisIsATest = view.findViewById(R.id.makeInfo);
//
//        thisIsATest.setText(mText);


        return view;
    }

    private class GetInfo extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected Void doInBackground (Void... arg0)
        {
            HttpHandler sh=new HttpHandler();

            String jsonStr=sh.makeServiceCall(finalApiCall);

            if(jsonStr != null)
            {
                try{

                    JSONArray cars = new JSONArray(jsonStr);

                    for(int i=0;i<cars.length();i++)
                    {
                        JSONObject c =cars.getJSONObject(i);

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

            String url = vehicleDISPLAY.get("image_url");

            price.setText("Price: $" + vehicleDISPLAY.get("price"));
            location.setText(vehicleDISPLAY.get("veh_description"));
            Glide.with(BlankFragment.this).load(url).into(image);
            update.setText("Last Update: " + vehicleDISPLAY.get("updated_at"));
        }
    }
}
