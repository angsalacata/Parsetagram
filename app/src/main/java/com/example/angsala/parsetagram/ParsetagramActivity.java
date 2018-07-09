package com.example.angsala.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ParsetagramActivity extends AppCompatActivity {

   final String TAG = "ParsetagramActivity";
    EditText loginUsername;
    EditText loginPassword;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsetagram);

        //inflate
        loginUsername = (EditText) findViewById(R.id.loginUsername);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String passedusername = loginUsername.getText().toString();
                final String passedpassword = loginPassword.getText().toString();
                login_helper(passedusername,passedpassword);
            }
        });
    }

    //helper function to login in from click listener
    private void login_helper(String username, String password){
        //call the log in methods and pass in username and password. LogIn call back will let us know when network request comes back!
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null){
                    Log.d(TAG, "Login successful");
                    //TODO- have a valid user
                } else{
                    Log.e(TAG, "Failed to login");
                    e.printStackTrace();
                }
            }
        });

    }

}
