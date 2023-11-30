package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan1.model.StaticManga;

public class Login_Page extends AppCompatActivity {

    private EditText edtextusername, edttextpassword;
    private Button buttonlogin;
    private DatabaseHelper databaseHelper;
    TextView returnlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        onCreate();
        setEvent();
    }

    private void onCreate() {
        returnlink = findViewById(R.id.return_link);
        edtextusername = findViewById(R.id.username);
        edttextpassword = findViewById(R.id.password);
        buttonlogin = findViewById(R.id.LoginBtn);
        databaseHelper = new DatabaseHelper(this);
    }

    public void setEvent() {
        returnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtextusername.getText().toString().trim();
                String password = edttextpassword.getText().toString().trim();
                Log.d("LoginActivity", "Attempting login with username: " + username + ", password: " + password);
                // Check if the username and password match
                boolean loginSuccessful = databaseHelper.checkCredentials(username, password);
                if (loginSuccessful) {
                    Toast.makeText(Login_Page.this, "Login successful", Toast.LENGTH_SHORT).show();
                    StaticManga.setLogin(true);
                    StaticManga.setUsername(username);
                    Intent intent = new Intent(Login_Page.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login_Page.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
