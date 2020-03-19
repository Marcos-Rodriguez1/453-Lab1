package com.example.homework1_jm;

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

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TEXT = "text";
    //private static final String ARG_PARAM2 = "param2";

    private TextView thisIsATest;

    // TODO: Rename and change types of parameters
    private String finalApiCall;
    private HashMap<String,String> vehicleDISPLAY=new HashMap<>();

    //Information to display
    private TextView makeANDmodel;
    private TextView price;
    private TextView location;
    private ImageView image;
    private TextView update;
    //private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }



                                                           //String param2
    public static BlankFragment newInstance(String text) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(TEXT, text);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            finalApiCall = getArguments().getString(TEXT);
            //mParam2 = getArguments().getString(ARG_PARAM2);
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

        new GetInfo().execute();

//        thisIsATest = view.findViewById(R.id.makeInfo);
//
//        thisIsATest.setText(mText);


        return view;
    }

    private class GetInfo extends AsyncTask<Void,Void,Void>
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

            String jsonStr=sh.makeServiceCall(finalApiCall);

            //Log.e(TAG,"response from url: "+jsonStr );

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

                        //HashMap<String,String> vehicleDISPLAY=new HashMap<>();

                        vehicleDISPLAY.put("price", vehiclePRICE);
                        vehicleDISPLAY.put("veh_description", vehicleDES);
                        vehicleDISPLAY.put("image_url", vehicleIMAGE);
                        vehicleDISPLAY.put("updated_at", vehicleUPDATED);

                        //carList.add(car);
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

            String url = vehicleDISPLAY.get("image_url");

            price.setText(vehicleDISPLAY.get("price"));
            location.setText(vehicleDISPLAY.get("veh_description"));
            Glide.with(BlankFragment.this).load(url).into(image);
            update.setText(vehicleDISPLAY.get("updated_at"));

//            SpinnerAdapter adapter= new SimpleAdapter(BlankFragment.this, vehicleDISPLAY,
//                    R.layout.fragment_blank,new String[]{"id","vehicle_make"},
//                    new int []{R.id.id,R.id.vehicle_make});
//            spinner.setAdapter(adapter);

        }
    }
}
