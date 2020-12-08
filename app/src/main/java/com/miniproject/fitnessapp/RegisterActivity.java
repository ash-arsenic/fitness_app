package com.miniproject.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mRegisterName;
    private EditText mRegisterEmail;
    private EditText mRegisterPassword;
    private Button mRegisterButton;

    FirebaseAuth mAuth;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegisterButton = findViewById(R.id.login_button);
        mRegisterEmail = findViewById(R.id.login_email);
        mRegisterName = findViewById(R.id.register_name);
        mRegisterPassword = findViewById(R.id.register_pswd);

        mAuth = FirebaseAuth.getInstance();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mRegisterEmail.getText().toString()) || TextUtils.isEmpty(mRegisterPassword.getText().toString()) || TextUtils.isEmpty(mRegisterName.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "All Field Required", Toast.LENGTH_SHORT).show();
                } else {

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    register(mRegisterName.getText().toString(), mRegisterEmail.getText().toString(), mRegisterPassword.getText().toString());
                }
            }
        });

    }

    private void register(final String username, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();

                            } catch (FirebaseAuthInvalidCredentialsException ex) {
                                Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException ex) {
                                Toast.makeText(RegisterActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
                            } catch (Exception ex) {
                                Toast.makeText(RegisterActivity.this, "Some error occurred. Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            final FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            firebaseUser.sendEmailVerification();

                            String userId = firebaseUser.getUid();
                            if (firebaseUser != null) {
                                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(mRegisterName.getText().toString())
                                        .setPhotoUri(null)
                                        .build();
                                firebaseUser.updateProfile(profile)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            mReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

                            mReference.setValue(new User(username, userId)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        assert user != null;
                                        if (user.getUid().equals("4Mrn7fwH42Tf8GabTg4ZFdskL1n2")) {
                                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            Log.d("LoginActivity", user.getUid());
                                            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("userId", "4Mrn7fwH42Tf8GabTg4ZFdskL1n2");
                                            intent.putExtra("userName", "Chat with us");
                                            startActivity(intent);
                                        }
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                });
    }
}