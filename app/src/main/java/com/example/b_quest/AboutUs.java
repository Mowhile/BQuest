package com.example.b_quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AboutUs extends AppCompatActivity {

    private Button btnGoBack;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        btnGoBack = findViewById(R.id.go_back_aboutUs);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUs.this, Settings.class);
                startActivity(intent);
            }
        });


    }
}
