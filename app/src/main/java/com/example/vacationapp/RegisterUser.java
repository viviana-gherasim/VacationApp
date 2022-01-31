package com.example.vacationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private TextView back;
    private TextView sign;
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword, daySpinner, monthSpinner, yearSpinner;
    private Spinner activityLevel;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        back=(TextView)findViewById(R.id.buttonBackSignUp);
        back.setOnClickListener(this);

        sign=(TextView)findViewById(R.id.buttonSignUp);
        sign.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        daySpinner=(EditText) findViewById(R.id.spinnerDOBDay);
        monthSpinner=(EditText) findViewById(R.id.spinnerDOBMonth);
        yearSpinner=(EditText) findViewById(R.id.spinnerDOBYear);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonBackSignUp:
                startActivity(new Intent(this,MainActivity.class ));
                break;
            case R.id.buttonSignUp:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email =editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String day=daySpinner.getText().toString().trim();
        String month=monthSpinner.getText().toString().trim();
        String year=yearSpinner.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Email has a wrong format!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            editTextPassword.setError("Password is to short");
            editTextPassword.requestFocus();
            return;
        }

        if(day.isEmpty()){
            daySpinner.setError("Day is required!");
            daySpinner.requestFocus();
            return;
        }

        if(month.isEmpty()){
            monthSpinner.setError("Month is required!");
            monthSpinner.requestFocus();
            return;
        }

        if(year.isEmpty()){
            yearSpinner.setError("Year is required!");
            yearSpinner.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user=new User(email, password, day, month, year);

                            FirebaseDatabase.getInstance("https://vacationapp-ae530-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast toast=Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                    else
                                    {
                                        Toast toast=Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG);;
                                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast toast=Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG);;
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }
                });
    }
}
