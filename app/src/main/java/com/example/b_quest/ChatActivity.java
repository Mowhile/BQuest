package com.example.b_quest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//CODE BY JUAN MARTIN

public class ChatActivity extends AppCompatActivity {

    //declaring variable to hold the username to be passed as part of a message
    private String userName;

    //this will hold the body of the message
    private EditText inputText;

    //this button will trigger the sendMessage method, sending the massage to the database and starting the chat recycler view
    private FloatingActionButton sendButton;

    //this arrays will hold the information to be passed to the adapter
    private ArrayList<Message> messages;

    //getting instances of the firebase real time database and firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //attaching a listener to the input text so the user can send the message by pressing the enter button on the soft keyboard
        //clicking the enter button will trigger the sendMessage method
        inputText = findViewById(R.id.chat_input);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });

        //same as before adding a listener, this time for the send button
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        myRef = database.getReference().child(getIntent().getStringExtra("thID"));

        //calling methods
        getMessages();
    }

    //the getUser method query the database and brings the user information to the app and create an user object
    //then we can use whatever we need from that user
    private void getUser() {
        CollectionReference collectionReference = db.collection("users");
        Query query = collectionReference.whereEqualTo("user_auth_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        userName = user.getUsername();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Query failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //the sendMessage method gets information from the inputText and the getUser method and with that creates a Message object.
    //after that sends the message to the real time database and clears the inputText field so its ready for the next message
    public void sendMessage() {
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            Message chat = new Message(userName, input);
            myRef.push().setValue(chat);
            inputText.setText("");
        }
    }

    //this method brigs the messages back from the database and store that information in arrayLists that then will be passed to the adapter
    //also call the startChatRecyclerView method and displays a message in case something goes wrong
    public void getMessages() {
        getUser();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Message message = dataSnapshot1.getValue(Message.class);
                    messages.add(message);
                }
                startChatRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //this method creates an instance of the RecyclerView class and binds it to the recycler view layout where the messages will be displayed
    //then creates an instance of theChatRecyclerViewAdapter class named adapter and attach that adapter to the recycler view
    //the information to be displayed is passed to the adapter via constructor.
    private void startChatRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.chat_recycler_view);
        ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(messages, userName, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

    }
}
