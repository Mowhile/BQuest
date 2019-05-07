package com.example.b_quest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//CODE BY JUAN MARTIN

public class QuestActivity extends AppCompatActivity implements View.OnClickListener {
    //database instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //declaring the two layouts attached to this class
    private ConstraintLayout categoryLayout;
    private ConstraintLayout questListLayout;

    //variables
    int cont = 0;
    RadioGroup rg;
    RadioButton selected;

    //List for the names of the quest
    ArrayList<String> questNames = new ArrayList<>();

    Map<String, Quest> questMapList = new HashMap<>();

    Quest quest = new Quest();

    Button done;

    //textViews from the questCategory layout
    private TextView questTitle1;
    private TextView questTitle2;
    private TextView questTitle3;
    private TextView questTitle4;
    private TextView questTitle5;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        //populating the questNames ArrayList
        questNames.add("Quest1");
        questNames.add("Quest2");
        questNames.add("Quest3");
        questNames.add("Quest4");
        questNames.add("Quest5");

        //declaring the questDone button and setting an onClickListener
        //and setting messages to show the user depending on the amount of quest that has been selected
        final Button questDone = findViewById(R.id.quest_done_button);
        questDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont == 0 || cont < 3) {
                    minimumQuestMessage("You need at least 3 quests!");
                } else if (cont >= 3 && cont < 5) {
                    alertMessage("You have added only " + cont + " quests to your treasure hunt!");
                } else {
                    passingQuestMap();
                }
            }
        });

        //finding the radio button group
        rg = findViewById(R.id.radioGroup);

        //finding the layouts
        categoryLayout = findViewById(R.id.category_layout);
        questListLayout = findViewById(R.id.quest_list_layout);

        //finding the textViews that will hold the quests names
        questTitle1 = findViewById(R.id.quest_1_text_view);
        questTitle2 = findViewById(R.id.quest_2_text_view);
        questTitle3 = findViewById(R.id.quest_3_text_view);
        questTitle4 = findViewById(R.id.quest_4_text_view);
        questTitle5 = findViewById(R.id.quest_5_text_view);

        //declaring and finding all radioButtons and setting the global onClickListener
        RadioButton quest1 = findViewById(R.id.quest_1);
        quest1.setOnClickListener(this);
        RadioButton quest2 = findViewById(R.id.quest_2);
        quest2.setOnClickListener(this);
        RadioButton quest3 = findViewById(R.id.quest_3);
        quest3.setOnClickListener(this);
        RadioButton quest4 = findViewById(R.id.quest_4);
        quest4.setOnClickListener(this);
        RadioButton quest5 = findViewById(R.id.quest_5);
        quest5.setOnClickListener(this);
        RadioButton quest6 = findViewById(R.id.quest_6);
        quest6.setOnClickListener(this);
        RadioButton quest7 = findViewById(R.id.quest_7);
        quest7.setOnClickListener(this);
        RadioButton quest8 = findViewById(R.id.quest_8);
        quest8.setOnClickListener(this);
        RadioButton quest9 = findViewById(R.id.quest_9);
        quest9.setOnClickListener(this);
        RadioButton quest10 = findViewById(R.id.quest_10);
        quest10.setOnClickListener(this);
        RadioButton quest11 = findViewById(R.id.quest_11);
        quest11.setOnClickListener(this);
        RadioButton quest12 = findViewById(R.id.quest_12);
        quest12.setOnClickListener(this);
        RadioButton quest13 = findViewById(R.id.quest_13);
        quest13.setOnClickListener(this);
        RadioButton quest14 = findViewById(R.id.quest_14);
        quest14.setOnClickListener(this);
        RadioButton quest15 = findViewById(R.id.quest_15);
        quest15.setOnClickListener(this);
        RadioButton quest16 = findViewById(R.id.quest_16);
        quest16.setOnClickListener(this);
        RadioButton quest17 = findViewById(R.id.quest_17);
        quest17.setOnClickListener(this);
        RadioButton quest18 = findViewById(R.id.quest_18);
        quest18.setOnClickListener(this);
        RadioButton quest19 = findViewById(R.id.quest_19);
        quest19.setOnClickListener(this);
        RadioButton quest20 = findViewById(R.id.quest_20);
        quest20.setOnClickListener(this);
        RadioButton quest21 = findViewById(R.id.quest_21);
        quest21.setOnClickListener(this);
        RadioButton quest22 = findViewById(R.id.quest_22);
        quest22.setOnClickListener(this);
        RadioButton quest23 = findViewById(R.id.quest_23);
        quest23.setOnClickListener(this);
        RadioButton quest24 = findViewById(R.id.quest_24);
        quest24.setOnClickListener(this);
        RadioButton quest25 = findViewById(R.id.quest_25);
        quest25.setOnClickListener(this);

        final Button button1 = findViewById(R.id.button_quest_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.GONE);
                questListLayout.setVisibility(View.VISIBLE);
            }
        });
        final Button button2 = findViewById(R.id.button_quest_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.GONE);
                questListLayout.setVisibility(View.VISIBLE);
            }
        });
        final Button button3 = findViewById(R.id.button_quest_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.GONE);
                questListLayout.setVisibility(View.VISIBLE);
            }
        });
        final Button button4 = findViewById(R.id.button_quest_4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.GONE);
                questListLayout.setVisibility(View.VISIBLE);
            }
        });
        final Button button5 = findViewById(R.id.button_quest_5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.GONE);
                questListLayout.setVisibility(View.VISIBLE);
            }
        });

        //finding and adding onClickListener to the done button
        //this button set the quest selected and passes the name to the arrayList
        //also activates and deactivate the "choose your quest" Buttons in a specific order
        done = findViewById(R.id.option_done_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont++;
                done.setEnabled(false);
                questMapList.put("quest" + cont, quest);
                rg.clearCheck();
                if (cont == 1) {
                    questTitle1.setText(questNames.get(0));
                    button1.setEnabled(false);
                    button2.setEnabled(true);
                }

                if (cont == 2) {
                    questTitle2.setText(questNames.get(1));
                    button2.setEnabled(false);
                    button3.setEnabled(true);

                }
                if (cont == 3) {
                    questTitle3.setText(questNames.get(2));
                    button3.setEnabled(false);
                    button4.setEnabled(true);

                }
                if (cont == 4) {
                    questTitle4.setText(questNames.get(3));
                    button4.setEnabled(false);
                    button5.setEnabled(true);

                }
                if (cont == 5) {
                    questTitle5.setText(questNames.get(4));
                    button5.setEnabled(false);
                }
                questListLayout.setVisibility(View.GONE);
                categoryLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    //Global onClick listener for all radio buttons
    public void onClick(View v) {
        //getting the id of the radioButton that has been clicked
        int selectedId = rg.getCheckedRadioButtonId();
        //passing the a id to a variable (radioButton)
        selected = findViewById(selectedId);
        //setting the text related to the radioButton to be passed with the onClick listener
        String passing = selected.getText().toString();
        switch (v.getId()) {

            case R.id.quest_1:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_2:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_3:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_4:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_5:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_6:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_7:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_8:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_9:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_10:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_11:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_12:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_13:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_14:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_15:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_16:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_17:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_18:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_19:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_20:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_21:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_22:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_23:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_24:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
            case R.id.quest_25:
                questNames.set(cont, passing);
                done.setEnabled(true);
                gettingQuest(passing);
                break;
        }
    }

    //getting the quest from the database
    //the quest will be gotten by its name, the name is the
    //"passing" value we got above
    public void gettingQuest(String passing) {
        CollectionReference ref = db.collection("quest");
        Query query = ref.whereEqualTo("questName", passing);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        quest = document.toObject(Quest.class);
                        String message = quest.getQuestDescription();
                        Toast.makeText(QuestActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //passing the quests to the CreateTreasureHuntActivity to later be send to the database when the creation of the treasure hunt is completed
    public void passingQuestMap() {

        Intent intent = new Intent(QuestActivity.this, CreateTreasureHuntActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("quests", (Serializable) questMapList);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);

    }

    //this message is displayed if the user have no selected 5 quest, the user is given the option to select more quests
    //or to keep going with the amount selected, if the positive option is selected then it calls the passingQuestMap() method
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                passingQuestMap();
            }
        }
    };

    //messages to be displayed to the user

    public void alertMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Hey!")
                .setMessage(message)
                .setPositiveButton(R.string.ok_option, dialogClickListener)
                .setNegativeButton(R.string.add_more_option, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void minimumQuestMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Hey!")
                .setMessage(message)
                .setNegativeButton(R.string.add_more_option, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //this create a new CreateTreasureHuntActivity activity when we press the Android back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuestActivity.this, CreateTreasureHuntActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}





