package com.example.b_quest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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

public class Hero extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //getting instances of the database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("events");


    //declaring menu items
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;


    //declaring the views that will hold the name ogf the quest
    private TextView questOne;
    private TextView questTwo;
    private TextView questThree;
    private TextView questFour;
    private TextView questFive;


    private TreasureHunt hunt;
    private TextView thName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);

        thName = findViewById(R.id.quest_name_preview);



        //menu
        Toolbar toolbar = findViewById(R.id.hero_nav_action);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);







        questOne = findViewById(R.id.hero_quest1);
        questTwo = findViewById(R.id.hero_quest2);
        questThree = findViewById(R.id.hero_quest3);
        questFour = findViewById(R.id.hero_quest4);
        questFive = findViewById(R.id.hero_quest5);


        questOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Hero.this, ImagesActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "Hero");
                intent.putExtra("questName", questOne.getText().toString());
                startActivity(intent);

            }
        });

        questTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hero.this, ImagesActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "Hero");
                intent.putExtra("questName", questTwo.getText().toString());
                startActivity(intent);
            }
        });

        questThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hero.this, ImagesActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "Hero");
                intent.putExtra("questName", questThree.getText().toString());
                startActivity(intent);
            }
        });

        questFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hero.this, ImagesActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "Hero");
                intent.putExtra("questName", questFour.getText().toString());
                startActivity(intent);
            }
        });

        questFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hero.this, ImagesActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "Hero");
                intent.putExtra("questName", questFive.getText().toString());
                startActivity(intent);
            }
        });




        Button buttonChat =(Button) findViewById(R.id.hero_button_chat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChat = new Intent(Hero.this, ChatActivity.class);
                intentChat.putExtra("thID", hunt.getTreasureHuntID());
                finish();
                startActivity(intentChat);

            }
        });




        Button button1 =(Button) findViewById(R.id.hero_button_upload1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent22 = new Intent(Hero.this, HeroUpload1.class);
                intent22.putExtra("thID", hunt.getTreasureHuntID());
                finish();
                startActivity(intent22);

            }
        });

        Button button2 =(Button) findViewById(R.id.hero_button_upload2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Hero.this, HeroUpload2.class);
                intent2.putExtra("thID", hunt.getTreasureHuntID());
                finish();
                startActivity(intent2);

            }
        });

        Button button3 =(Button) findViewById(R.id.hero_button_upload3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent22 = new Intent(Hero.this, HeroUpload3.class);
                intent22.putExtra("thID", hunt.getTreasureHuntID());
                finish();
                startActivity(intent22);

            }
        });

        Button button4 =(Button) findViewById(R.id.hero_button_upload4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent22 = new Intent(Hero.this, HeroUpload4.class);
                intent22.putExtra("thID", hunt.getTreasureHuntID());
                finish();
                startActivity(intent22);

            }
        });

        Button button5 =(Button) findViewById(R.id.hero_button_upload5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent22 = new Intent(Hero.this, HeroUpload5.class);
                intent22.putExtra("thID", hunt.getTreasureHuntID());
                finish();
                startActivity(intent22);

            }
        });



        Button cancel= (Button) findViewById(R.id.hero_cancelo);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(Hero.this, MyTreasureHuntActivity.class);
                startActivity(intent11);
            }
        });
        getTreasureHunt();

    }


    private void getTreasureHunt() {
        if (getIntent().hasExtra("thID")) {
            Query query = collectionReference.whereEqualTo("treasureHuntID", getIntent().getStringExtra("thID"));
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            hunt = document.toObject(TreasureHunt.class);
                            thName.setText(hunt.getTreasureHunt());
                            Quest quest1 = hunt.getQuestMapList().get("quest1");
                            questOne.setText(quest1.getQuestName());
                            if (quest1.getStatus().equals("complete")){
                                //statusQuestOne.setTextColor(getColor(R.color.quest_completed));
                                //statusQuestOne.setText(getString(R.string.completed));
                            }
                            Quest quest2 = hunt.getQuestMapList().get("quest2");
                            questTwo.setText(quest2.getQuestName());
                            if (quest2.getStatus().equals("complete")){
                               // statusQuestTwo.setTextColor(getColor(R.color.quest_completed));
                                //statusQuestTwo.setText(getString(R.string.completed));
                            }
                            Quest quest3 = hunt.getQuestMapList().get("quest3");
                            questThree.setText(quest3.getQuestName());
                            if (quest3.getStatus().equals("complete")){
                                //statusQuestThree.setTextColor(getColor(R.color.quest_completed));
                                //statusQuestThree.setText(getString(R.string.completed));
                            }

                            if (hunt.getQuestMapList().size() == 4) {
                                Quest quest4 = hunt.getQuestMapList().get("quest4");
                                //statusQuestFour.setVisibility(View.VISIBLE);
                                questFour.setVisibility(View.VISIBLE);
                                questFour.setText(quest4.getQuestName());


                            }
                            if (hunt.getQuestMapList().size() == 5) {
                                Quest quest4 = hunt.getQuestMapList().get("quest4");
                                questFour.setVisibility(View.VISIBLE);
                                questFour.setText(quest4.getQuestName());

                                Quest quest5 = hunt.getQuestMapList().get("quest5");
                                //statusQuestFive.setVisibility(View.VISIBLE);
                                questFive.setVisibility(View.VISIBLE);
                                questFive.setText(quest5.getQuestName());

                                if (quest5.getStatus().equals("complete")){
                                    //statusQuestFive.setTextColor(getColor(R.color.quest_completed));
                                    //statusQuestFive.setText(getString(R.string.completed));
                                }
                            }
                        }
                    }
                }
            });

        }
        else{
            Toast.makeText(this, "asdasd", Toast.LENGTH_LONG).show();
        }


    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.home:
                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
                Intent intent0 = new Intent(Hero.this, DashboardActivity.class);
                startActivity(intent0);
                break;

            case R.id.nav_profile:
                Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Hero.this, MyProfile2.class);
                startActivity(intent1);
                break;

            case R.id.settings:
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(Hero.this, Settings.class);
                startActivity(intent2);

                break;

            case R.id.logout:

                Intent intent = new Intent(Hero.this, MainActivity.class);
                finish();
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();

                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }




    ////
    private void openImagesActivity(){
        Intent intent33 = new Intent(Hero.this, ImagesActivity.class);
        startActivity(intent33);
    }


    ////


    //The sendEmail method builds the emails with a subject (sub), a body (mail), and the email address taken from
    //the text "bHeroName" field.
    //then creates instances of the SendingEmail class and send them with the .execute() command (in thi case the email is for the hero).
    // when an email is being sent a progress bar will be displayed
    private void sendEmail(){
        User user = null;
        String sub = "BQuest trial";
        String mail = "Hello" + user.getFirstName() + " the Hero has finished all the quest!!! " +
                "\n" +  FirebaseAuth.getInstance().getCurrentUser().getEmail() + " " +
                "\n" + hunt.getTreasureHunt() + " BQuest Treasure hunt " +
                "\n THIS EMAIL HAVE BEEN SENT FROM THE BQUEST MOBILE APP";
        SendingEmail send = new SendingEmail(this, user.getFirstName(), sub, mail);

        send.execute();
    }


}