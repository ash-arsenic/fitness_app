package com.miniproject.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserEmail;
    private EditText mUserPassword;
    private Button mLogIn;
    private Button mSignUp;
    private Button mForgotPassword;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseUser != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            if (user.getUid().equals("4Mrn7fwH42Tf8GabTg4ZFdskL1n2")) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Log.d("LoginActivity", user.getDisplayName());
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userId", "4Mrn7fwH42Tf8GabTg4ZFdskL1n2");
                intent.putExtra("userName", "Chat With Us");
                startActivity(intent);
            }
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserEmail = findViewById(R.id.login_email);
        mUserPassword = findViewById(R.id.login_password);
        mLogIn = findViewById(R.id.login_button);
        mSignUp = findViewById(R.id.need_register);
        mForgotPassword = findViewById(R.id.forgot_password);

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Reset Password")
                        .setMessage("Enter your Email:")
                        .setView(resetMail)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (TextUtils.isEmpty(resetMail.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseAuth.getInstance().sendPasswordResetEmail(resetMail.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(LoginActivity.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Password reset mail has been sent to you.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                builder.create().show();
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String txt_email = mUserEmail.getText().toString();
                String txt_password = mUserPassword.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "All field required", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthInvalidUserException ex) {
                                            Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                        } catch (FirebaseAuthInvalidCredentialsException ex) {
                                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                        } catch (Exception ex) {
                                            Toast.makeText(LoginActivity.this, "Some Error occurred. Try Again", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
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
                                            intent.putExtra("userName", "Chat With us");
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