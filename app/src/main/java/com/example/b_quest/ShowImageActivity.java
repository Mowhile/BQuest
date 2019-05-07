package com.example.b_quest;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

//CODE BY JUAN MARTIN


public class ShowImageActivity extends AppCompatActivity {

    //this class allow the user to see an image that has been uploaded by the "Hero" as a proof of completion of a quest
    //also will give the user the ability to save the image in its devise if want it.
    private ImageView questImageView;
    Button download;
    Button goBack;

    //this is the reference for the Firebase storage where images are been stored
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    StorageReference ref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        questImageView = findViewById(R.id.quest_image_view);

        //this button calls the saveImage() method and save the image in the device
        download = findViewById(R.id.download_button);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questImageView.setImageResource(0);
                saveImage();
            }
        });

        goBack = findViewById(R.id.go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questImageView.setImageResource(0);
                if (getIntent().getStringExtra("tag").equals("activity_lord")) {
                    finish();
                } else if (getIntent().getStringExtra("tag").equals("chief")) {
                    finish();
                }
            }
        });
        //calling the showImage method()
        showImage();
    }


    //saveImage() brings the image form the storage and calls the downloadImage() to then save the image
    public void saveImage() {
        ref = mStorageRef.child(getIntent().getStringExtra("thID") + "/" + getIntent().getStringExtra("questName") + ".png");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadImage(ShowImageActivity.this, getIntent().getStringExtra("questName"), ".png", Environment.DIRECTORY_DOWNLOADS, url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    //this method saves the image in the phone
    public void downloadImage(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);

    }

    //showImage() brings the image form the storage and display the image on screen
    private void showImage() {
        ref = mStorageRef.child(getIntent().getStringExtra("thID") + "/" + getIntent().getStringExtra("questName") );
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                if (Picasso.get().load(url) != null) {
                    Picasso.get().load(url).into(questImageView);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                alertMessage("The hero has not uploaded an image for this quest");
                Toast.makeText(ShowImageActivity.this, "NO IMAGE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                if (getIntent().getStringExtra("tag").equals("activity_lord")) {
                    finish();
                } else if (getIntent().getStringExtra("tag").equals("chief")) {
                    finish();
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
