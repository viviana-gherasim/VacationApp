package com.example.vacationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity implements View.OnClickListener {

    private Button logout,addDestination;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        addDestination=(Button) findViewById(R.id.buttonAddDestination);
        addDestination.setOnClickListener(this);

        logout = (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
              case R.id.buttonAddDestination:
                  startActivity(new Intent(Admin.this,AddDestination.class));
                  break;
//
//            case R.id.buttonEditDestination:
//                startActivity(new Intent(Admin.this, ));
//                break;
//
//            case R.id.buttonFlights:
//                startActivity(new Intent(Admin.this, ));
//                break;
//
//            case R.id.buttonHotels:
//                startActivity(new Intent(Admin.this, ));
//                break;

            case R.id.logoutButton:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Admin.this, MainActivity.class));
                break;
        }
    }
}
