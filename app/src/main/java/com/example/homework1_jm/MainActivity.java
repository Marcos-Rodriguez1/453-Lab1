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


    private EditText username;
    private EditText password;
    private Button signUpBtn;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Im pretty sure this is how you setup to recieve the hash tables
        Intent intent=getIntent();
        HashMap<String,String> hashMap=(HashMap<String,String>)intent.getSerializableExtra("send");



        username = findViewById(R.id.editText_UserName);
        password = findViewById(R.id.editText_Password);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);


        signUpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                opensignup();

            }
        });

    }


    public void opensignup() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);

    }
}