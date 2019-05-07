package com.example.b_quest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class HeroUpload1 extends AppCompatActivity {

    //Variables for buttons
    private Button choosePic;
    private Button uploadPic;
    private Button goBack;


    ImageView image;


    //Firebase instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    private StorageTask uploadTask;

    //Firebase Collection instance
    private CollectionReference collectionReference = db.collection("events");

    public Uri imgUri;
    private TreasureHunt hunt;
    private Quest quest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_uploads);


        image = (ImageView) findViewById(R.id.imageUpload44);

        getTreasureHunt();


        //By clicking this button the user can gain access to the images files
        choosePic = (Button) findViewById(R.id.choose_pic_hero);
        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FileChooser();
            }
        });


        uploadPic = (Button) findViewById(R.id.upload_pic_hero);
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(HeroUpload1.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    FileUploader();
                }
            }
        });


    }


    // This method allow the user to upload the picture that was selected with the method "FileChooser" and
    // upload the picture to firebase with the Id of the user
    private void FileUploader() {
        StorageReference Ref = mStorageRef.child(getIntent().getStringExtra("thID")).child("/" + quest.getQuestName());
        uploadTask = Ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(HeroUpload1.this, "Picture was successfully uploaded", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handleling unsuccessful uploads
                        // ...
                        Intent intent0 = new Intent(HeroUpload1.this, Hero.class);
                        startActivity(intent0);
                        Toast.makeText(HeroUpload1.this, "Picture wasn't successfully uploaded, Please try again", Toast.LENGTH_LONG).show();
                    }
                });
    }


    //with this method we are accessing to the phone's images
    //allowing us to choose a picture
    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            Picasso.get().load(imgUri).into(image);
        }
    }


    private void getTreasureHunt() {
        if (getIntent().hasExtra("thID")) {
            Query query = collectionReference.whereEqualTo("treasureHuntID", getIntent().getStringExtra("thID"));
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            hunt = document.toObject(TreasureHunt.class);
                            quest = hunt.getQuestMapList().get("quest1");
                        }
                    }
                }

            });
        }
    }


}







