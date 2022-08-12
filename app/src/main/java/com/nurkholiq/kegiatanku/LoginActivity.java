package com.nurkholiq.kegiatanku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView gotoRegister = findViewById(R.id.goto_register);
        gotoRegister.setOnClickListener(view -> {
            Intent toRegister = new Intent(getApplicationContext(), com.nurkholiq.kegiatanku.RegisterActivity.class);
            startActivity(toRegister);
        });
    }
}