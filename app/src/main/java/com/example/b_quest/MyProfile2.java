package com.example.b_quest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class MyProfile2 extends AppCompatActivity implements View.OnClickListener {


    ImageView imageView2;
    static final int SELECT_IMAGE = 1000;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private TextView mFirstName;
    private TextView mSecondName;
    private TextView mNickname;
    private TextView mEmail;
    private TextView mPhoneNumber;


    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    private StorageTask uploadTask;
    public Uri imgUri;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile2);


        getUser();

        mFirstName =findViewById(R.id.hero_name);
        mSecondName = findViewById(R.id.hero_surname);
        mNickname = findViewById(R.id.hero_nickname);
        mEmail = findViewById(R.id.hero_email);
        mPhoneNumber = findViewById(R.id.hero_phoneNumber);


        final Button btnSelectImage = findViewById(R.id.button_profile2);
        imageView2 = findViewById(R.id.profile_image2);

        btnSelectImage.setOnClickListener( this);

        handlePermission();

        Button goBack = findViewById(R.id.go_back_hero);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile2.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        Button upload = findViewById(R.id.uploadPicProfile);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FileUploader();
            }
        });


    }




    private void getUser() {
        CollectionReference collectionReference = db.collection("users");
        Query query = collectionReference.whereEqualTo("user_auth_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        mFirstName.setText(user.getFirstName());
                        mSecondName.setText(user.getLastName());
                        mNickname.setText(user.getUsername());
                        mEmail.setText(user.getEmail());
                        mPhoneNumber.setText(user.getPhoneNumber());


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Query failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void handlePermission(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            return;
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_IMAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_IMAGE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //  Show your own message here
                        } else {
                            showSettingsAlert();
                        }

                    }
                }
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DON'T ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings();
                    }
                });
        alertDialog.show();
    }

    private void openAppSettings(){
        Intent i = new Intent();
        i.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(i);
    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (resultCode == RESULT_OK) {
                    if (requestCode == SELECT_IMAGE) {
                        // Get the url from data
                        final Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            imageView2.post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView2.setImageURI(selectedImageUri);
                                }
                            });


                        }
                    }
                }
            }
        }).start();
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        openImageChooser();
    }



//    private void FileUploader(){
//
//
//        StorageReference filePath  = mStorageRef.child("ImagesProfile").child(imgUri.getEncodedPath() + "thId");
//
//        uploadTask = filePath.putFile(imgUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(MyProfile2.this, "Picture was successfully uploaded", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                        Toast.makeText(MyProfile2.this, "Picture wasn't successfully uploaded, Please try again", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//    }



}
