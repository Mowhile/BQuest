package com.example.b_quest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

//CODE BY JUAN MARTIN

public class AddLordActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("events");

    //declaring a hash map to store the emails of the lords to be invited
    Map<String, String> invMap = new HashMap<>();

    //declaring the text fields for the email addresses
    EditText lordOne;
    EditText lordTwo;
    EditText lordThree;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lord);

        //linking the button object to its matching ID on the layout
        Button addLordsButton = findViewById(R.id.add_lords_button);
        addLordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                addLords();
                sendEmail();
            }
        });

        //Linking the text fields with the element on the layout
        lordOne = findViewById(R.id.lord_one);
        lordTwo = findViewById(R.id.lord_two);
        lordThree = findViewById(R.id.lord_three);
    }

    //The addLords method takes the emails entered by the user and store them in invMap map that is after sent to the database
    public void addLords() {
        DocumentReference doc = collectionReference.document(getIntent().getStringExtra("thID"));
        invMap.put("inv1", lordOne.getText().toString());
        invMap.put("inv2", lordTwo.getText().toString());
        invMap.put("inv3", lordThree.getText().toString());
        doc.update("invMap", invMap);

    }

    //The sendEmail method builds the emails with a subject (sub), a body (mail), and the email addresses taken captured
    //the text fields above.
    //then creates instances of the SendingEmail class and send them with the .execute() command.
    private void sendEmail() {
        String sub = "BQuest trial";
        String mail = "Hello! you have been chosen!!! " +
                "\n" + FirebaseAuth.getInstance().getCurrentUser().getEmail() + " has invited you to " +
                "\n" + getIntent().getStringExtra("thName") + " BQuest Treasure hunt " +
                "\n THIS EMAIL HAVE BEEN SENT FROM THE BQUEST MOBILE APP";
        SendingEmail send = new SendingEmail(this, lordOne.getText().toString(), sub, mail);
        SendingEmail send2 = new SendingEmail(this, lordTwo.getText().toString(), sub, mail);
        SendingEmail send3 = new SendingEmail(this, lordThree.getText().toString(), sub, mail);

        send.execute();
        send2.execute();
        send3.execute();
    }
}
