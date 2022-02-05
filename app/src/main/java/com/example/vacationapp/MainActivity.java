package com.example.vacationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private TextView login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView) findViewById(R.id.registerButton);
        register.setOnClickListener(this);

        login=(TextView) findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        /*mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null)
        {

        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.loginButton:
                startActivity(new Intent(this, LoginUser.class));
                break;
        }
    }
}