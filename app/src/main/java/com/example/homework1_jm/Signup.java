package com.example.homework1_jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    private Button signupButton;
   // HashMap<String,String> hashMap= new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



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

                if(!emailchecker(Email))
                {
                  Toast.makeText(getApplicationContext(),"email incorrect", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public void opensignin()
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    boolean emailchecker(CharSequence Email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }
}
