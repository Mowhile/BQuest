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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//CODE BY JUAN MARTIN

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables for the menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    //fireStore variable
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView mUserNameTag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getUser();

        //this button takes the user to the CreateTreasureHuntActivity where a treasure hunt can be created
        Button createTh = findViewById(R.id.create_th_button);
        createTh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CreateTreasureHuntActivity.class);
                startActivity(intent);
            }
        });

        //this button takes the user to the MyTreasureHuntActivity where all treasure hunt involving the user will be displayed
        Button myTreasureHunt = findViewById(R.id.list_treasure_button);
        myTreasureHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MyTreasureHuntActivity.class);
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

    //this method gets the user from the database and displays its username on the dashboard screen
    private void getUser() {
        CollectionReference collectionReference = db.collection("users");
        Query query = collectionReference.whereEqualTo("user_auth_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        mUserNameTag.setText(user.getUsername());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Query failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                Intent intentProfile = new Intent(DashboardActivity.this, MyProfile2.class);
                startActivity(intentProfile);

                break;

            case R.id.settings:
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                Intent intentSettings = new Intent(DashboardActivity.this, Settings.class);
                startActivity(intentSettings);
                break;

            case R.id.logout:
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }
}
