package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private Button signupButton;
    //Was just called Map before
    HashMap<String,String> profile= new HashMap< >(); //Create the hashmap\\



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //adds profiles to hashmap
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); //Show the layout for it
        profile.put("John", "123");
        profile.put("James","456");


       //sets values to everything
        signupButton=(Button)findViewById(R.id.signmeup);
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText UsernameID=findViewById(R.id.UserName);
                EditText PasswordID=findViewById(R.id.Password);
                EditText CPasswordID=findViewById(R.id.ConfirmPassword);
                EditText EmailID=findViewById(R.id.Email);
                EditText PhoneID=findViewById(R.id.Phone);
                String Name=UsernameID.getText().toString();
                String Pass=PasswordID.getText().toString();
                String Cpass=CPasswordID.getText().toString();
                String Email=EmailID.getText().toString();
                String Phone=PhoneID.getText().toString();
                boolean correct;
                correct=true;


        //checks if User meets all requirements

                if(!emailchecker(Email))
                {
                  Toast.makeText(getApplicationContext(),"email incorrect", Toast.LENGTH_SHORT).show();
                  EmailID.setText(null);
                  correct=false;
                }
                if(!phoneChecker(Phone))
                {
                    Toast.makeText(getApplicationContext(),"phone is incorrect",Toast.LENGTH_SHORT).show();
                    PhoneID.setText(null);
                    correct=false;
                }
                if(!Pass.equals(Cpass))
                {
                    Toast.makeText(getApplicationContext(),"Passwords dont match",Toast.LENGTH_SHORT).show();
                    PasswordID.setText(null);
                    correct=false;
                }
                if(Name.matches("")||Pass.matches("")||Cpass.matches("")||Email.matches("")||Phone.matches(""))
                {
                    Toast.makeText(getApplicationContext(),"fill out all fields",Toast.LENGTH_SHORT).show();
                    correct=false;
                }

                if(correct==true)
                {
                    opensignin(Name,Pass);
                }

            }
        });
    }


//this method makes sure teh name isnt already taken and passes the information to main activity if everything is correct
    public void opensignin(String Name,String Pass)
    {
        if(profile.containsKey(Name))
        {
            Toast.makeText(getApplicationContext(), "Username is already in database", Toast.LENGTH_SHORT).show();
        }
        else {
            profile.put(Name, Pass);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("send", (Serializable) profile);
            intent.putExtra("uname",Name);
            intent.putExtra("password",Pass);
            startActivity(intent);
        }
    }


    //These two methods makes sure the email and phone are in the correct format using patterns.
    boolean emailchecker(CharSequence Email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    boolean phoneChecker(CharSequence Phone)
    {

        return Patterns.PHONE.matcher(Phone).matches();
    }
}
