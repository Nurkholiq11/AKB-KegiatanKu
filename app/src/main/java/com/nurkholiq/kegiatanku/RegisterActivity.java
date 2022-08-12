package com.nurkholiq.kegiatanku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView gotoLogin = findViewById(R.id.goto_login);
        gotoLogin.setOnClickListener(view -> {
            Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(toLogin);
        });
    }
}