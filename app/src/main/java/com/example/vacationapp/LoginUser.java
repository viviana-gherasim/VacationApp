package com.example.vacationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity implements View.OnClickListener {

    private TextView login;
    private TextView back;
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login=(TextView)findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        back=(TextView)findViewById(R.id.backButtonLogin);
        back.setOnClickListener(this);

        editTextEmail=(EditText) findViewById(R.id.editTextEmailLogin);

        editTextPassword=(EditText) findViewById(R.id.editTextPasswordLogin);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginButton:
                userLogin();
                break;

            case R.id.backButtonLogin:
                startActivity(new Intent(this,MainActivity.class ));
                break;
        }
    }

    private void userLogin() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Invalid Email format!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("A password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            editTextPassword.setError("Password is to short!(Min.6 chars)");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String inputMail = editTextEmail.getText().toString();
                String admin = "admin@gmail.com";
                if(task.isSuccessful()){
                    if(inputMail.equals(admin)) {
                        startActivity(new Intent(LoginUser.this,Admin.class));
                    } else {
                        startActivity(new Intent(LoginUser.this, MainMenuActivity.class));
                    }
                }
                else
                {
                    Toast.makeText(LoginUser.this, "Failed to login! Please check your credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
