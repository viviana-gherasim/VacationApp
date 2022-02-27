package com.example.vacationapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;

public class AddDestination extends AppCompatActivity implements View.OnClickListener {
    private Button back, chooseImg, addDest;
    private EditText countryName, cityName, description, act1, act2, act3, act4, act5;
    private ImageView imageView;
    private DatabaseReference databaseReference;

    //constant to compare the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_destinations_to_firebase);

        back=(Button) findViewById(R.id.backToAdminPage);
        back.setOnClickListener(this);

        chooseImg = findViewById(R.id.buttonChooseImage);
        imageView = findViewById(R.id.image_view);

        countryName=(EditText) findViewById(R.id.editTextCountry);
        cityName=(EditText) findViewById(R.id.editTextCity);
        description=(EditText) findViewById(R.id.editTextDescription);
        act1=(EditText) findViewById(R.id.editTextActivity1);
        act2=(EditText) findViewById(R.id.editTextActivity2);
        act3=(EditText) findViewById(R.id.editTextActivity3);
        act4=(EditText) findViewById(R.id.editTextActivity4);
        act5=(EditText) findViewById(R.id.editTextActivity5);

        //handle the choose img button to trigger
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    void imageChooser() {
        //create an instance of the intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        //pass the constant to compare it with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    //this function is triggered when user selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if(null != selectedImageUri) {
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backToAdminPage:
                startActivity(new Intent(AddDestination.this, Admin.class));
        }
    }
}
