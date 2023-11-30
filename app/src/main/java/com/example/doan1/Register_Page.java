package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register_Page extends AppCompatActivity {
    TextView returnlink;
    private EditText usernameEdt,passwordEdt,emailEdt;
    private Button registerbtn;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        onCreate();
        setEvent();
    }
    private void onCreate(){
        returnlink =(TextView) findViewById(R.id.return_link);
        usernameEdt = findViewById(R.id.name);
        passwordEdt = findViewById(R.id.RegPassword);
        registerbtn = findViewById(R.id.RegisterBtn);
        emailEdt = findViewById(R.id.email);
        databaseHelper = new DatabaseHelper(this);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String email = emailEdt.getText().toString();
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(Register_Page.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean registrationSuccessful = databaseHelper.addUser(username, password, email);
                    if (registrationSuccessful) {
                        Toast.makeText(Register_Page.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register_Page.this, Login_Page.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register_Page.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void setEvent(){
        returnlink.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Register_Page.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: This finishes the current activity to prevent going back to it using the back button
        }
        });
    }
}