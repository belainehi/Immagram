package com.codepath.belainehi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

//This code is from episode 4

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnsignup;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_login);

        if (ParseUser.getCurrentUser()!= null){
            goMainActivity();
        }

        etUsername = findViewById (R.id.etUsername);
        etPassword = findViewById (R.id.etPassword);
        btnLogin = findViewById (R.id.btnLogin);
        btnsignup = findViewById(R.id.btnsignup);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }

        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signupUser(username, password);
            }
        });
    }

    private void loginUser (String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e != null) {
                    Log.e(TAG, "Issue with Login", e);
                    return;
                }
                //TODO: Navigate to the main activity if the user has signed in properly.
                goMainActivity();
                Toast.makeText(LoginActivity.this,"Success!",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void signupUser (final String username, final String password){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    loginUser(username, password);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG,"Sign Up failed.");
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }


    }


