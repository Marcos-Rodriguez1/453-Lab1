package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

/////////////////
        TextView uname,pword;
        uname=(TextView) findViewById(R.id.editText_UserName);
        uname.setText(getIntent().getStringExtra("uname"));
        pword=(TextView) findViewById(R.id.editText_Password);
        pword.setText(getIntent().getStringExtra("password"));
///////////////
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
               loginUser(hashMap, username,password); //
            }
        });

    }


    public void opensignup() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);

    }

    public void loginUser(HashMap<String, String> hashMap, EditText user, EditText pw)
    {
        //Get the values from the user input
        String tUser = user.getText().toString().trim();
        String tPW = pw.getText().toString().trim();

        //If there is nothing in the hash, says it's empty
        if(hashMap == null)
        {
            Toast.makeText(getApplicationContext(), "EMPTY DATABASE", Toast.LENGTH_LONG).show();
        }
        //If user leaves fields empty
        else if(tUser.isEmpty() || tPW.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Username or password are empty", Toast.LENGTH_LONG).show();
        }
        //If the username matches check if the password does too
        else if(hashMap.containsKey(tUser))
        {
            //Once you get the password go to the welcome page
            if(hashMap.containsValue(tPW))
            {
                String Username=username.getText().toString();
                Intent intent = new Intent(this, Welcome.class);
                intent.putExtra("Username",Username);
                startActivity(intent);
            }
            //If wrong password
            else
            {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                password.setText(null);
            }
        }

        //If everything fails make a error message
        else {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            password.setText(" ");
        }
    }
}