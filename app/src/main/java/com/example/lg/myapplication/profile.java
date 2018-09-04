package com.example.lg.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView username, phone, email;

        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String getemail = user.getEmail();

            username = findViewById(R.id.username);
            username.setText(name);
        }

        email.setText("13599983");

    }
}
