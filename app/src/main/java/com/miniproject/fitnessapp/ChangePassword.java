package com.miniproject.fitnessapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private EditText mChangeEmail, mNewPassword, mCurrentPassword;
    private Button mChangePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChangeEmail = findViewById(R.id.change_email);
        mCurrentPassword = findViewById(R.id.current_pswd);
        mNewPassword = findViewById(R.id.new_password);
        mChangePasswordButton = findViewById(R.id.change_password_button);

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mChangeEmail.getText().toString()) || TextUtils.isEmpty(mCurrentPassword.getText().toString()) || TextUtils.isEmpty(mNewPassword.getText().toString())) {
                    Toast.makeText(ChangePassword.this, "All Field are required", Toast.LENGTH_SHORT).show();
                } else {

                    if (mCurrentPassword.getText().toString().equals(mNewPassword.getText().toString())) {
                        Toast.makeText(ChangePassword.this, "Both the passwords are same", Toast.LENGTH_SHORT).show();
                    } else {

                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(mChangeEmail.getText().toString(), mCurrentPassword.getText().toString());
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()) {
                                            try {
                                                throw task.getException();
                                            } catch (FirebaseAuthInvalidUserException ex) {
                                                Toast.makeText(ChangePassword.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                            } catch (FirebaseAuthInvalidCredentialsException ex) {
                                                Toast.makeText(ChangePassword.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                                            } catch (Exception ex) {
                                                Toast.makeText(ChangePassword.this, "Some Error occurred. Try Again", Toast.LENGTH_SHORT).show();
                                            }
//                                        Toast.makeText(ChangePassword.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                                        } else {
                                            user.updatePassword(mNewPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (!task.isSuccessful()) {
                                                                Toast.makeText(ChangePassword.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(ChangePassword.this, "Done", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
}