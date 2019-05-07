package com.example.b_quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    //variables for the menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private TextView help;
    private TextView aboutUs;

    private TextView mUserNameTag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        help = findViewById(R.id.help_settings);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Help.class);
                startActivity(intent);

            }
        });


        aboutUs = findViewById(R.id.aboutus_settings);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, AboutUs.class);
                startActivity(intent);
            }
        });


        //menu
        mUserNameTag = findViewById(R.id.tag);
        Toolbar toolbar = findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }





    //menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.home:
                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_profile:
                Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Settings.this, MyProfile2.class);
                startActivity(intent1);
                break;

            case R.id.settings:
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(Settings.this, Settings.class);
                startActivity(intent2);

                break;

            case R.id.logout:
                Intent intent = new Intent(Settings.this, MainActivity.class);
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }
}



