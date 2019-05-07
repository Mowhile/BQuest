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

//CODE BY JUAN MARTIN

public class LordScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //getting instances of the database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("events");

    //declaring menu items
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    //declaring an instance of the TreasureHuntClass
    private TreasureHunt hunt;
    private TextView thName;

    //declaring the views that will hold the name ogf the quest
    private TextView questOneTextPreview;
    private TextView questTwoTextPreview;
    private TextView questThreeTextPreview;
    private TextView questFourTextPreview;
    private TextView questFiveTextPreview;

    //declaring the views that will hold the status of the quest
    private TextView statusQuestOne;
    private TextView statusQuestTwo;
    private TextView statusQuestThree;
    private TextView statusQuestFour;
    private TextView statusQuestFive;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lord);

        //this button will take the user to the chat room
        Button chat = findViewById(R.id.lord_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LordScreenActivity.this, ChatActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                startActivity(intent);
            }
        });

        thName = findViewById(R.id.quest_name_preview);

        //menu
        Toolbar toolbar = findViewById(R.id.chief_nav_action);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //attaching the views with their corresponding elements on the layout
        questOneTextPreview = findViewById(R.id.quest_one_text_preview);
        questTwoTextPreview = findViewById(R.id.quest_two_text_preview);
        questThreeTextPreview = findViewById(R.id.quest_three_text_preview);
        questFourTextPreview = findViewById(R.id.quest_four_text_preview);
        questFiveTextPreview = findViewById(R.id.quest_five_text_preview);

        statusQuestOne = findViewById(R.id.status1);
        statusQuestTwo = findViewById(R.id.status2);
        statusQuestThree = findViewById(R.id.status3);
        statusQuestFour = findViewById(R.id.status4);
        statusQuestFive = findViewById(R.id.status5);

        //hiding the elements (they will be shown if necessary depending on the number of quests)
        questFourTextPreview.setVisibility(View.GONE);
        questFiveTextPreview.setVisibility(View.GONE);
        statusQuestFour.setVisibility(View.GONE);
        statusQuestFive.setVisibility(View.GONE);

        //the following are OnCheckedChangeListeners that have been attached to the switches
        //this will define what quests will be marked as approved and also how much the progress bar will be fill
        //depending on the number of quests on the treasure hunt and the number of quest to be approved.
        questOneTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LordScreenActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "activity_lord");
                intent.putExtra("questName", questOneTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questTwoTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LordScreenActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "activity_lord");
                intent.putExtra("questName", questTwoTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questThreeTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LordScreenActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "activity_lord");
                intent.putExtra("questName", questThreeTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questFourTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LordScreenActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "activity_lord");
                intent.putExtra("questName", questFourTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questFiveTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LordScreenActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "activity_lord");
                intent.putExtra("questName", questFiveTextPreview.getText().toString());
                startActivity(intent);
            }
        });

        getTreasureHunt();

    }

    //the getTreasureHunt method checks the database and retrieves the treasure hunt which matches the "thID" value that was passed
    //from the RecyclerView class via intent.
    //also checks the number of quests of the treasure hunt by counting the elements in the questMapList inside the hunt object
    //and check which quests have been completed, is a quest is completed the switch corresponding to that quest will be "on" and blocked.
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
                            questOneTextPreview.setText(quest1.getQuestName());
                            if (quest1.getStatus().equals("complete")) {
                                statusQuestOne.setTextColor(getColor(R.color.quest_completed));
                                statusQuestOne.setText(getString(R.string.completed));
                            }
                            Quest quest2 = hunt.getQuestMapList().get("quest2");
                            questTwoTextPreview.setText(quest2.getQuestName());
                            if (quest2.getStatus().equals("complete")) {
                                statusQuestTwo.setTextColor(getColor(R.color.quest_completed));
                                statusQuestTwo.setText(getString(R.string.completed));
                            }
                            Quest quest3 = hunt.getQuestMapList().get("quest3");
                            questThreeTextPreview.setText(quest3.getQuestName());
                            if (quest3.getStatus().equals("complete")) {
                                statusQuestThree.setTextColor(getColor(R.color.quest_completed));
                                statusQuestThree.setText(getString(R.string.completed));
                            }

                            if (hunt.getQuestMapList().size() == 4) {
                                Quest quest4 = hunt.getQuestMapList().get("quest4");
                                statusQuestFour.setVisibility(View.VISIBLE);
                                questFourTextPreview.setVisibility(View.VISIBLE);
                                questFourTextPreview.setText(quest4.getQuestName());

                                if (quest4.getStatus().equals("complete")) {
                                    statusQuestFour.setTextColor(getColor(R.color.quest_completed));
                                    statusQuestFour.setText(getString(R.string.completed));
                                }
                            }
                            if (hunt.getQuestMapList().size() == 5) {
                                Quest quest4 = hunt.getQuestMapList().get("quest4");
                                statusQuestFour.setVisibility(View.VISIBLE);
                                questFourTextPreview.setVisibility(View.VISIBLE);
                                questFourTextPreview.setText(quest4.getQuestName());

                                Quest quest5 = hunt.getQuestMapList().get("quest5");
                                statusQuestFive.setVisibility(View.VISIBLE);
                                questFiveTextPreview.setVisibility(View.VISIBLE);
                                questFiveTextPreview.setText(quest5.getQuestName());

                                if (quest5.getStatus().equals("complete")) {
                                    statusQuestFive.setTextColor(getColor(R.color.quest_completed));
                                    statusQuestFive.setText(getString(R.string.completed));
                                }
                            }
                        }
                    }
                }
            });

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
                break;

            case R.id.nav_profile:
                Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_LONG).show();
                break;

            case R.id.settings:
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

}
