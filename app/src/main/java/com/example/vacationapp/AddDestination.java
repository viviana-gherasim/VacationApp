package com.example.vacationapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddDestination extends AppCompatActivity implements View.OnClickListener {

    private Button back, chooseImg, addDest;
    private EditText countryName, cityName, description, act1, act2, act3;
    private ImageView imageView;
    private RadioButton hot, cold;
    private Uri filePath;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;

    //constant to compare the activity result code
    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_destinations_to_firebase);

        addDest = (Button) findViewById(R.id.buttonAddDestToDatabase);
        addDest.setOnClickListener(this);

        back=(Button) findViewById(R.id.backToAdminPage);
        back.setOnClickListener(this);

        databaseReference= FirebaseDatabase.getInstance("https://vacationapp-ae530-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Destinations");

        chooseImg = findViewById(R.id.buttonChooseImage);
        imageView = findViewById(R.id.image_view);

        countryName=(EditText) findViewById(R.id.editTextCountry);
        cityName=(EditText) findViewById(R.id.editTextCity);
        description=(EditText) findViewById(R.id.editTextDescription);
        act1=(EditText) findViewById(R.id.editTextActivity1);
        act2=(EditText) findViewById(R.id.editTextActivity2);
        act3=(EditText) findViewById(R.id.editTextActivity3);

        hot = (RadioButton) findViewById(R.id.radioButtonHot);
        cold = (RadioButton) findViewById(R.id.radioButtonCold);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        //handle the choose img button to trigger
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

//        addDest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadDestination();
//            }
//        });
    }

    //select image method
    private void imageChooser() {
        //create an instance of the intent of the type image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //pass the constant to compare it with the returned requestCode
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    //this function is triggered when user selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadDestination() {
        if(filePath != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AddDestination.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddDestination.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddDestToDatabase:
                addDestinationToDatabase();
                uploadDestination();
        }

        switch (v.getId()){
            case R.id.backToAdminPage:
                startActivity(new Intent(AddDestination.this, Admin.class));
        }
    }

    private void addDestinationToDatabase() {
        String country = countryName.getText().toString().trim();
        String city = cityName.getText().toString().trim();
        String desc = description.getText().toString().trim();
        String activity1 = act1.getText().toString().trim();
        String activity2 = act2.getText().toString().trim();
        String activity3 = act3.getText().toString().trim();

        if(country.isEmpty())
        {
            countryName.setError("Country is required!");
            countryName.requestFocus();
            return;
        }

        if(city.isEmpty())
        {
            cityName.setError("City is required!");
            cityName.requestFocus();
            return;
        }

        if(desc.isEmpty())
        {
            description.setError("Description is required!");
            description.requestFocus();
            return;
        }

        if(activity1.isEmpty())
        {
            act1.setError("Activity1 is required!");
            act1.requestFocus();
            return;
        }

        if(activity2.isEmpty())
        {
            act2.setError("Activity2 is required!");
            act2.requestFocus();
            return;
        }

        if(activity3.isEmpty())
        {
            act3.setError("Activity3 is required!");
            act3.requestFocus();
            return;
        }

        Destination destinations = new Destination(country, city, desc, activity1, activity2, activity3);
        databaseReference.push().setValue(destinations).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddDestination.this, "Destination inserted successfully into the database!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Admin.class));
                }
                else {
                    Toast.makeText(AddDestination.this, "Destination failed insertion into the database!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}