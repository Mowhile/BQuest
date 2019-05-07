package com.example.b_quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Help extends AppCompatActivity {

   private Button btnGoBack;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        btnGoBack = findViewById(R.id.go_back_help);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Help.this, Settings.class);
                startActivity(intent);
            }
        });
    }




}
