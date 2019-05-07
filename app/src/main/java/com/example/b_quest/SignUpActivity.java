package com.example.b_quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

//CODE BY JUAN MARTIN

public class SignUpActivity extends AppCompatActivity {

    private AutoCompleteTextView mFirstName;
    private AutoCompleteTextView mLastName;
    private AutoCompleteTextView mUsername;
    private AutoCompleteTextView mEmail;
    private EditText mPhoneNumber;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private CheckBox termsAndConditions;
    private Button signUp;

    //FirebaseAut variable
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //Firestore variable
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirstName = findViewById(R.id.name);
        mLastName = findViewById(R.id.lastName);
        mUsername = findViewById(R.id.userName);
        mEmail = findViewById(R.id.email);
        mPhoneNumber = findViewById(R.id.phone);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirmPassword);

        // Executed when Sign Up button is pressed.
        signUp = findViewById(R.id.submitButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!termsAndConditions.isChecked()) {
                    signUp.setEnabled(false);
                    Toast.makeText(SignUpActivity.this, "YOU MUST READ AND ACCEPT TERMS AND CONDITIONS", Toast.LENGTH_SHORT).show();
                } else {
                    attemptRegistration();
                }

            }
        });

        //this method enables the signUp only if the terms and conditions box is checked
        termsAndConditions = findViewById(R.id.terms_checkBox);
        termsAndConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signUp.setEnabled(true);
            }
        });
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            createFirebaseUser();
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    //checks that the email has a "@"
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    //this checks that the password match with the password in the authentication database
    private boolean isPasswordValid(String password) {

        String confirmPassword = mConfirmPassword.getText().toString();

        if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "Password does not match the confirmation", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 character long", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    //creating new firebaseAuth credentials and user
    private void createFirebaseUser() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getApplicationContext(), "Auth created", Toast.LENGTH_SHORT).show();

                String userAuthId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DocumentReference ref = db.collection("users").document();

                String firstName = mFirstName.getText().toString();
                String lastName = mLastName.getText().toString();
                String username = mUsername.getText().toString();
                String email = mEmail.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();

                User user = new User();

                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setUsername(username);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setUserID(ref.getId());
                user.setUser_auth_id(userAuthId);

                ref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "New user created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
