package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private EditText username; //Added private buttons
    private EditText password; //that will be placeholders
    private Button signUpBtn;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //To show main activity layout

        //Im pretty sure this is how you setup to receive the hash tables
        Intent intent=getIntent();
        final HashMap<String,String> hashMap=(HashMap<String,String>)intent.getSerializableExtra("send");



        username = findViewById(R.id.editText_UserName); //Connect it to text fields
        password = findViewById(R.id.editText_Password);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);

        //Let me in
        signUpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                opensignup();

            }
        }); //even listener for button

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // loginUser(hashMap); //
            }
        });

    }


    public void opensignup() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);

    }

    public void loginUser(HashMap<String, String> hashMap, String user, String pw)
    {
        String
    }
}