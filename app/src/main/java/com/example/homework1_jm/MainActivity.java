package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;
    private Button signUpBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.editText_UserName);
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
        });

    }


    public void opensignup() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);

    }
}