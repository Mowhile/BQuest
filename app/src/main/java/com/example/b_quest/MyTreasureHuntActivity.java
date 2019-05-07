package com.example.b_quest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//CODE BY JUAN MARTIN

public class MyTreasureHuntActivity extends AppCompatActivity {
    //Firebase instance to connect and retrieve the treasure hunt
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<String> huntNames = new ArrayList<>();
    private ArrayList<String> heroNames = new ArrayList<>();
    private ArrayList<String> huntID = new ArrayList<>();
    private ArrayList<String> role = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_treasure_hunt);

        //this takes the user back to the dashboard screen
        Button goBackButton = findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getTreasureHunt();
    }

    //"CollectionReference" below specify the database collection we want to check
    //"Tasks" are the different conditions we want to check to determine what role is the user taking in the treasure hunt.
    //When one or more of the tasks are successful, data is taken from that treasure hunt and passed to the recycler view adapter to be displayed
    private void getTreasureHunt() {
        CollectionReference collectionReference = db.collection("events");

        Task<QuerySnapshot> task1 = collectionReference.whereEqualTo("treasureHuntChief", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();
        Task<QuerySnapshot> task2 = collectionReference.whereEqualTo("heroEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();
        Task<QuerySnapshot> task3 = collectionReference.whereEqualTo("invMap.inv1", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();
        Task<QuerySnapshot> task4 = collectionReference.whereEqualTo("invMap.inv2", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();
        Task<QuerySnapshot> task5 = collectionReference.whereEqualTo("invMap.inv3", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();
        Task<QuerySnapshot> task6 = collectionReference.whereEqualTo("lordMap.inv1", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();
        Task<QuerySnapshot> task7 = collectionReference.whereEqualTo("lordMap.inv2", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();
        Task<QuerySnapshot> task8 = collectionReference.whereEqualTo("lordMap.inv3", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get();

        Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(task1, task2, task3, task4, task5, task6, task7, task8);
        allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {

                for (QuerySnapshot queryDocumentSnapshot : querySnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshot) {
                        TreasureHunt hunt = documentSnapshot.toObject(TreasureHunt.class);
                        huntNames.add(hunt.getTreasureHunt());
                        heroNames.add(hunt.getHeroName());
                        huntID.add(hunt.getTreasureHuntID());
                        if (hunt.getTreasureHuntChief().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            role.add("Chief");
                        } else if (hunt.getHeroEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            role.add("Hero");
                        } else if (hunt.getInvMap().containsValue(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            role.add("Inv");
                        } else if (hunt.getLordMap().containsValue(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            role.add("Lord");
                        }
                    }
                    startRecyclerView();
                }
            }
        });
    }

    //Creating a RecyclerView and attaching te our component to it ("recyclerView") is the id for the component in the layout
    //After that we create an instance of the RecyclerViewAdapter class and pass the information gathered on the "getTreasureHunt()" method
    //and set that instance as the adapter for the recyclerView.
    //finally we set a layout to the recyclerView.
    private void startRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(huntID, huntNames, heroNames, role, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
