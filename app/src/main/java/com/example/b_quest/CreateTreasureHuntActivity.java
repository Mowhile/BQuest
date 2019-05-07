package com.example.b_quest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//CODE BY JUAN MARTIN

public class CreateTreasureHuntActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //declaring variables, text fields
    private EditText treasureHuntName;
    private EditText bHeroName;
    private EditText bHeroEmail;

    private String text1;
    private String text2;
    private String text3;

    //creating shared preferences constants, shared preferences help to keep information on screen
    public static final String SHARED_PREF = "sharedPref";
    public static final String TREASURE_HUNT_NAME = "thName";
    public static final String TREASURE_HUNT_HERO_NAME = "thHero";
    public static final String TREASURE_HUNT_HERO_EMAIL = "thHeroEmail";

    //this is the map that will hold the quests
    private Map<String, Quest> settingQuests = new HashMap<>();

    //database reference
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //variables for the spinner (drop down menu) to select how much the contribution would be
    private String contributionSelected;
    private int tribute;

    //menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_th);

        //this is getting the Map containing the list of quests that was passed via intend from the QuestActivity class
        Bundle bundleQuest = getIntent().getExtras();
        if (bundleQuest != null) {
            settingQuests = (Map<String, Quest>) bundleQuest.getSerializable("quests");
        }

        treasureHuntName = findViewById(R.id.th_name);
        bHeroName = findViewById(R.id.choose_hero);
        bHeroEmail = findViewById(R.id.hero_email);

        //this takes the user to the QuestActivity where the user will be able to select the quest to be added to the treasure hunt
        final Button questCategory = findViewById(R.id.quest_categories);
        questCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                finish();
                Intent intent = new Intent(CreateTreasureHuntActivity.this, QuestActivity.class);
                startActivity(intent);

            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //this declares the spinner that holds the amounts to be selected as a contribution
        //then attach the "contribution" array and to the layouts that will be passed to an adapter in order to display the drop down menu.
        //finally a listener is set so we can see what item was selected, the selected string then is transformed into an integer to be
        //passed to the treasure hunt object later
        final Spinner contribution = findViewById(R.id.minimum_contribution);
        ArrayAdapter<CharSequence> contributionAdapter = ArrayAdapter.createFromResource(this, R.array.contribution, R.layout.spinner_item);
        contributionAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        contribution.setAdapter(contributionAdapter);
        contribution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                contributionSelected = contribution.getSelectedItem().toString();
                try {
                    tribute = Integer.parseInt(contributionSelected);
                } catch (NumberFormatException nfe) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "no item selected", Toast.LENGTH_SHORT).show();
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //menu
        Toolbar toolbar = findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //this button close the current window (create treasure hunt window) and takes the user back to the dashboard
        //also clears the information from the shared preferences.
        Button goBackButton = findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                clearingData();
            }
        });

        //by clicking this button the user is creating the treasure hunt by calling the validations method
        //also clears the information from the shared preferences.
        Button createTreasureHunt = findViewById(R.id.create_treasure_hun_button);
        createTreasureHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (settingQuests.isEmpty()) {
                    errorMessage("You must add at least three quest to your hunt");
                } else {
                    clearingData();
                    validations();
                }
            }
        });
        //methods for the shared preferences
        loadData();
        updateView();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                break;


            case R.id.settings:
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.logout:
                Intent intent = new Intent(CreateTreasureHuntActivity.this, MainActivity.class);
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

    //this method does some validations to tell the user that some fields are mandatory
    //call the createTreasureHunt method and send the user back to the dashboard
    private void validations() {

        treasureHuntName.setError(null);
        bHeroName.setError(null);
        bHeroEmail.setError(null);
        int minimumContribution = tribute;

        Spinner contribution = findViewById(R.id.minimum_contribution);
        String treasureHunt = treasureHuntName.getText().toString();
        String heroName = bHeroName.getText().toString();
        String heroEmail = bHeroEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(treasureHunt)) {
            treasureHuntName.setError(getString(R.string.treasure_hunt_name_error));
            focusView = treasureHuntName;
            cancel = true;
        }

        if (TextUtils.isEmpty(heroName)) {
            bHeroName.setError(getString(R.string.hero_name_error));
            focusView = bHeroName;
            cancel = true;
        }

        if (TextUtils.isEmpty(heroEmail)) {
            bHeroEmail.setError(getString(R.string.hero_email_error));
            focusView = bHeroEmail;
            cancel = true;
        } else if (!isEmailValid(heroEmail)) {
            bHeroEmail.setError(getString(R.string.error_invalid_email));
            focusView = bHeroEmail;
            cancel = true;
        }

        if (minimumContribution == 0) {
            Toast.makeText(this, "Pick a contribution", Toast.LENGTH_SHORT).show();
            focusView = contribution;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            createTreasureHunt();
            sendEmail();
            Intent intent = new Intent(CreateTreasureHuntActivity.this, DashboardActivity.class);
            finish();
            startActivity(intent);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    //check that the email contains an @ symbol
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    //this keeps a hold of the information that are added to the shared preferences and attach an editor (acts as a holder)
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TREASURE_HUNT_NAME, treasureHuntName.getText().toString());
        editor.putString(TREASURE_HUNT_HERO_NAME, bHeroName.getText().toString());
        editor.putString(TREASURE_HUNT_HERO_EMAIL, bHeroEmail.getText().toString());
        editor.apply();
    }

    //saves the information from the shared preferences into variables
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        text1 = sharedPreferences.getString(TREASURE_HUNT_NAME, "");
        text2 = sharedPreferences.getString(TREASURE_HUNT_HERO_NAME, "");
        text3 = sharedPreferences.getString(TREASURE_HUNT_HERO_EMAIL, "");
    }

    //set the text views with information from the treasure hunt
    public void updateView() {
        treasureHuntName.setText(text1);
        bHeroName.setText(text2);
        bHeroEmail.setText(text3);
    }

    //method to destroy the information stored on the share preferences
    public void clearingData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //gathers information and creates a TreasureHunt object the is later saved in the database.
    private void createTreasureHunt() {

        DocumentReference ref = db.collection("events").document();

        String userAuthEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        String treasureHunt = treasureHuntName.getText().toString();
        int minimumContribution = tribute;
        String heroName = bHeroName.getText().toString();
        String heroEmail = bHeroEmail.getText().toString();
        Map<String, Quest> questMap = settingQuests;
        Map<String, String> lordMap = new HashMap<>();
        lordMap.put("inv1", "");
        lordMap.put("inv2", "");
        lordMap.put("inv3", "");
        Calendar date = Calendar.getInstance();

        TreasureHunt hunt = new TreasureHunt();
        hunt.setTreasureHuntID(ref.getId());
        hunt.setTreasureHunt(treasureHunt);
        hunt.setContribution(minimumContribution);
        hunt.setTreasureHuntChief(userAuthEmail);
        hunt.setHeroName(heroName);
        hunt.setHeroEmail(heroEmail);
        hunt.setQuestMapList(questMap);
        hunt.setLordMap(lordMap);
        hunt.setDate(DateFormat.getDateInstance().format(date.getTime()));

        ref.set(hunt).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "New hunt created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //The sendEmail method builds the emails with a subject (sub), a body (mail), and the email address taken from
    //the text "bHeroName" field.
    //then creates instances of the SendingEmail class and send them with the .execute() command (in thi case the email is for the hero).
    // when an email is being sent a progress bar will be displayed
    private void sendEmail() {

        String sub = "BQuest trial";
        String mail = "Hello " + bHeroName.getText().toString() + " you are the HERO!!! " +
                "\n" + FirebaseAuth.getInstance().getCurrentUser().getEmail() + " has invited you to " +
                "\n" + treasureHuntName.getText().toString() + " BQuest Treasure hunt " +
                "\n THIS EMAIL HAVE BEEN SENT FROM THE BQUEST MOBILE APP";
        SendingEmail send = new SendingEmail(this, bHeroEmail.getText().toString(), sub, mail);

        send.execute();
    }

    //this gets rid of the progress bar that comes from the SendingEmail class
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendingEmail.dismissProgressDialog();
    }

    //this is an alert message that is displayed to the user in different occasions, the message to be shown is passed as an argument
    //when the method is called.
    public void errorMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Hey!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onBackPressed() {
        clearingData();
        super.onBackPressed();
    }
}
