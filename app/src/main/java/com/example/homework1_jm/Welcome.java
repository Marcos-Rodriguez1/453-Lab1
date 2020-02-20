package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
//Creates the welcome screen and gets the information from the main activity
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        name=(TextView) findViewById(R.id.UN);
        name.setText(getIntent().getStringExtra("Username"));



    }
}
