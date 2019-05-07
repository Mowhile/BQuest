package com.example.b_quest;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ImagesActivity extends AppCompatActivity {

    private ImageView questImageView2;

    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    StorageReference ref;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("treasure_hunt");
    private TreasureHunt hunt;

    Quest quest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_images);


        questImageView2 = findViewById(R.id.image_view_hero1);

        showImage();


    }


    private void showImage() {

        ref = mStorageRef.child(getIntent().getStringExtra("thID") + "/" + getIntent().getStringExtra("questName"));


        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                String url = uri.toString();
                if (Picasso.get().load(url) != null) {
                    Picasso.get().load(url).into(questImageView2);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                alertMessage("The hero has not uploaded an image for this quest");
                Toast.makeText(ImagesActivity.this, "NO IMAGE", Toast.LENGTH_SHORT).show();
            }
        });
    }


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                if (getIntent().getStringExtra("tag").equals("activity_lord")) {
                    //Intent intent = new Intent(ShowImageActivity.this, LordScreenActivity.class);
                    //intent.putExtra("thID", getIntent().getStringExtra("thID"));
                    finish();
                    // startActivity(intent);
                } else if (getIntent().getStringExtra("tag").equals("chief")) {
                    //Intent intent = new Intent(ShowImageActivity.this, ChiefDisplayActivity.class);
                    // intent.putExtra("thID", getIntent().getStringExtra("thID"));
                    finish();
                    //startActivity(intent);
                }
            }
        }
    };


    public void alertMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Hey!")
                .setMessage(message)
                .setPositiveButton(R.string.ok, dialogClickListener)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
