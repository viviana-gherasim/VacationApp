package com.example.vacationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout, profileButton, offerButton, spotButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        logout=(Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(this);

        profileButton=(Button) findViewById(R.id.buttonProfileMenu);
        profileButton.setOnClickListener(this);

        offerButton=(Button) findViewById(R.id.buttonViewOffer);
        offerButton.setOnClickListener(this);

        spotButton = (Button) findViewById(R.id.buttonFindBestSpot);
        spotButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutButton:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainMenuActivity.this, MainActivity.class));
                break;

//            case R.id.buttonProfileMenu:
//                startActivity(new Intent(MainMenuActivity.this, ));
//                break;
//
//            case R.id.buttonViewOffer:
//                startActivity(new Intent(MainMenuActivity.this, ));
//                break;
//
            case R.id.buttonFindBestSpot:
                startActivity(new Intent(MainMenuActivity.this, Form.class));
                break;
        }
    }

}

