package com.mecodroid.myfirebaseauthgoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Registration extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    private AppCompatEditText email, password, confPass, uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editemail);
        password = findViewById(R.id.editpassword);
        confPass = findViewById(R.id.editconpassword);
        uName = findViewById(R.id.editname);


    }

    public void register_it(View view) {
        String Remail = email.getText().toString().trim();
        String Rpass = password.getText().toString().trim();
        String Rconpass = confPass.getText().toString().trim();
        final String rname = uName.getText().toString().trim();
        if (!TextUtils.isEmpty(Remail) && !TextUtils.isEmpty(Rpass)
                && !TextUtils.isEmpty(Rconpass) && TextUtils.equals(Rpass, Rconpass)) {
            mAuth.createUserWithEmailAndPassword(Remail, Rpass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent n = new Intent(Registration.this, Home.class);
                            n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(n);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    uName.setText("");
                                                    email.setText("");
                                                    password.setText("");
                                                    confPass.setText("");
                                                }
                                            }
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        user = task.getResult().getUser();
                        UserProfileChangeRequest userProfileChangeRequest =
                                new UserProfileChangeRequest.Builder()
                                        .setDisplayName(rname)
                                        .build();
                        user.updateProfile(userProfileChangeRequest);

                        Toast.makeText(Registration.this, "Registration Success", Toast.LENGTH_SHORT).show();

                    } else {
                        // if email already registerd
                        if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
                            Toast.makeText(Registration.this, "هذا الايميل مستخدم من قبل", Toast.LENGTH_SHORT).show();

                        }
                        Toast.makeText(Registration.this, "Registration failed ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
