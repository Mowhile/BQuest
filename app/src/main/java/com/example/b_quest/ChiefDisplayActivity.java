package com.example.b_quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.ActionBarDrawerToggle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//CODE BY JUAN MARTIN

public class ChiefDisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables for the menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    //declaring variables, buttons, textViews and switches
    private ProgressBar progressBar;
    private TreasureHunt hunt;
    private TextView thName;

    private Switch switchQuest1;
    private Switch switchQuest2;
    private Switch switchQuest3;
    private Switch switchQuest4;
    private Switch switchQuest5;

    private TextView questOneTextPreview;
    private TextView questTwoTextPreview;
    private TextView questThreeTextPreview;
    private TextView questFourTextPreview;
    private TextView questFiveTextPreview;

    //getting Firestore instances
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("events");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief_screen);

        thName = findViewById(R.id.quest_name_preview);

        //this takes the user to the AddLordActivity where th user can invite lords to the treasure hunt
        //is passing  the treasure hunt id and name, these will be used at the time of making the invitations
        Button share = findViewById(R.id.share_button);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiefDisplayActivity.this, AddLordActivity.class);
                finish();
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("thName", thName.getText().toString());
                startActivity(intent);
            }
        });

        //this button calls the updateQuestStatus method that chane the status of a quest when the chief consider the quest has been completed
        Button approve = findViewById(R.id.accept_approval);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestStatus();
                finish();
            }
        });

        //this takes the user to the chat room
        Button chat = findViewById(R.id.chief_chat_button);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiefDisplayActivity.this, ChatActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                startActivity(intent);
            }
        });

        //attaching all views, switches and the progress bar to the elements on the layout
        questOneTextPreview = findViewById(R.id.quest_one_text_preview);
        questTwoTextPreview = findViewById(R.id.quest_two_text_preview);
        questThreeTextPreview = findViewById(R.id.quest_three_text_preview);
        questFourTextPreview = findViewById(R.id.quest_four_text_preview);
        questFiveTextPreview = findViewById(R.id.quest_five_text_preview);

        switchQuest1 = findViewById(R.id.switch_quest1);
        switchQuest2 = findViewById(R.id.switch_quest2);
        switchQuest3 = findViewById(R.id.switch_quest3);
        switchQuest4 = findViewById(R.id.switch_quest4);
        switchQuest5 = findViewById(R.id.switch_quest5);

        questFourTextPreview.setVisibility(View.GONE);
        questFiveTextPreview.setVisibility(View.GONE);
        switchQuest4.setVisibility(View.GONE);
        switchQuest5.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);

        //here we attach all the elements for the menu
        Toolbar toolbar = findViewById(R.id.chief_nav_action);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //the following are onClick listeners that have been added to the textViews that hold the name of each quest on the treasure hunt
        //when clicked will take the user to the ShowImageActivity where the picture or media file that has been uploaded as a proof of the
        //completion of a quest will be shown
        questOneTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiefDisplayActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "chief");
                intent.putExtra("questName", questOneTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questTwoTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiefDisplayActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "chief");
                intent.putExtra("questName", questTwoTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questThreeTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiefDisplayActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "chief");
                intent.putExtra("questName", questThreeTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questFourTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiefDisplayActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "chief");
                intent.putExtra("questName", questFourTextPreview.getText().toString());
                startActivity(intent);
            }
        });
        questFiveTextPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiefDisplayActivity.this, ShowImageActivity.class);
                intent.putExtra("thID", hunt.getTreasureHuntID());
                intent.putExtra("tag", "chief");
                intent.putExtra("questName", questFiveTextPreview.getText().toString());
                startActivity(intent);
            }
        });

        //the following are OnCheckedChangeListeners that have been attached to the switches
        //this will define what quests will be marked as approved and also how much the progress bar will be fill
        //depending on the number of quests on the treasure hunt and the number of quest to be approved.
        switchQuest1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hunt.getQuestMapList().size() == 3) {
                    arrayListThree();
                } else if (hunt.getQuestMapList().size() == 4) {
                    arrayListFour();
                } else {
                    arrayListFive();
                }
            }
        });
        switchQuest2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hunt.getQuestMapList().size() == 3) {
                    arrayListThree();
                } else if (hunt.getQuestMapList().size() == 4) {
                    arrayListFour();
                } else {
                    arrayListFive();
                }
            }
        });
        switchQuest3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hunt.getQuestMapList().size() == 3) {
                    arrayListThree();
                } else if (hunt.getQuestMapList().size() == 4) {
                    arrayListFour();
                } else {
                    arrayListFive();
                }
            }

        });
        switchQuest4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hunt.getQuestMapList().size() == 4) {
                    arrayListFour();
                } else if (hunt.getQuestMapList().size() == 5) {
                    arrayListFive();
                }

            }

        });
        switchQuest5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hunt.getQuestMapList().size() == 5) {
                    arrayListFive();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTreasureHunt();

    }

    //the updateQuestStatus will update the status of a quest in the database
    private void updateQuestStatus() {
        DocumentReference doc = collectionReference.document(getIntent().getStringExtra("thID"));

        if (switchQuest1.isChecked()) {
            doc.update("questMapList.quest1.status", "complete");
        }
        if (switchQuest2.isChecked()) {
            doc.update("questMapList.quest2.status", "complete");
        }
        if (switchQuest3.isChecked()) {
            doc.update("questMapList.quest3.status", "complete");
        }
        if (switchQuest4.isChecked()) {
            doc.update("questMapList.quest4.status", "complete");
        }
        if (switchQuest5.isChecked()) {
            doc.update("questMapList.quest5.status", "complete");
        }
    }

    //the getTreasureHunt method checks the database and retrieves the treasure hunt which matches the "thID" value that was passed
    //from the RecyclerView class via intent.
    //also checks the number of quests of the treasure hunt by counting the elements in the questMapList inside the hunt object
    //and check which quests have been completed, is a quest is completed the switch corresponding to that quest will be "on" and blocked.
    private void getTreasureHunt() {
        if (getIntent().hasExtra("thID")) {
            Query query = collectionReference.whereEqualTo("treasureHuntID", getIntent().getStringExtra("thID"));
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            hunt = document.toObject(TreasureHunt.class);
                            thName.setText(hunt.getTreasureHunt());
                            Quest quest1 = hunt.getQuestMapList().get("quest1");
                            questOneTextPreview.setText(quest1.getQuestName());
                            if (quest1.getStatus().equals("complete")) {
                                switchQuest1.setChecked(true);
                                switchQuest1.setEnabled(false);
                            }
                            Quest quest2 = hunt.getQuestMapList().get("quest2");
                            questTwoTextPreview.setText(quest2.getQuestName());
                            if (quest2.getStatus().equals("complete")) {
                                switchQuest2.setChecked(true);
                                switchQuest2.setEnabled(false);
                            }
                            Quest quest3 = hunt.getQuestMapList().get("quest3");
                            questThreeTextPreview.setText(quest3.getQuestName());
                            if (quest3.getStatus().equals("complete")) {
                                switchQuest3.setChecked(true);
                                switchQuest3.setEnabled(false);
                            }

                            if (hunt.getQuestMapList().size() == 4) {
                                Quest quest4 = hunt.getQuestMapList().get("quest4");
                                switchQuest4.setVisibility(View.VISIBLE);
                                questFourTextPreview.setVisibility(View.VISIBLE);
                                questFourTextPreview.setText(quest4.getQuestName());

                                if (quest4.getStatus().equals("complete")) {
                                    switchQuest4.setChecked(true);
                                    switchQuest4.setEnabled(false);
                                }
                            }
                            if (hunt.getQuestMapList().size() == 5) {
                                Quest quest4 = hunt.getQuestMapList().get("quest4");
                                switchQuest4.setVisibility(View.VISIBLE);
                                questFourTextPreview.setVisibility(View.VISIBLE);
                                questFourTextPreview.setText(quest4.getQuestName());
                                Quest quest5 = hunt.getQuestMapList().get("quest5");
                                switchQuest5.setVisibility(View.VISIBLE);
                                questFiveTextPreview.setVisibility(View.VISIBLE);
                                questFiveTextPreview.setText(quest5.getQuestName());
                                if (quest4.getStatus().equals("complete")) {
                                    switchQuest4.setChecked(true);
                                    switchQuest4.setEnabled(false);
                                }
                                if (quest5.getStatus().equals("complete")) {
                                    switchQuest5.setChecked(true);
                                    switchQuest5.setEnabled(false);
                                }
                            }
                        }
                    }
                }
            });

        }
    }

    //this gives functionality to the menu and perform actions according to the item that have been clicked
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
                break;

            case R.id.settings:
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.logout:
                Intent intent = new Intent(ChiefDisplayActivity.this, MainActivity.class);
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

    //these methods manage how the progress bar will be filled up based on if the number of quest the treasure hunt has and how many quest
    //are marked as complete
    //three quest combinations
    public void arrayListThree() {
        if (switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked()) {
            progressBar.setProgress(100);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked()) {
            progressBar.setProgress(33);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked()) {
            progressBar.setProgress(33);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked()) {
            progressBar.setProgress(66);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked()) {
            progressBar.setProgress(33);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked()) {
            progressBar.setProgress(66);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked()) {
            progressBar.setProgress(66);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked()) {
            progressBar.setProgress(66);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked()) {
            progressBar.setProgress(0);
        }
    }

    //four quest combinations
    public void arrayListFour() {
        if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(0);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(25);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(25);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(50);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(25);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(50);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(50);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(75);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(25);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(50);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(50);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(75);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(50);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(75);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked()) {
            progressBar.setProgress(75);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked()) {
            progressBar.setProgress(100);
        }
    }

    //five quest combinations
    public void arrayListFive() {
        if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(0);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(20);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(20);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(20);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(20);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(20);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(40);
        } else if (!switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(60);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && !switchQuest5.isChecked()) {
            progressBar.setProgress(80);
        } else if (!switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(80);
        } else if (switchQuest1.isChecked() && !switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(80);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && !switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(80);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && !switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(80);
        } else if (switchQuest1.isChecked() && switchQuest2.isChecked() && switchQuest3.isChecked() && switchQuest4.isChecked() && switchQuest5.isChecked()) {
            progressBar.setProgress(100);
        }
    }
}

