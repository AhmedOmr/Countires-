package com.mecodroid.myfirebaseauthgoogle;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;

public class Begin extends AppCompatActivity {
    GoogleSignInOptions inOptions;
    GoogleSignInClient client;
    int Rec_SignIn = 0;
    FirebaseAuth mAuth;
    String token = "801485653415-aht3r2hioib7546scs4bql19359q1bh5.apps.googleusercontent.com";
    CountryCodePicker ccop;
    Button verifyNumber, cancelVerify;
    EditText editnumber;
    Button canclogin, log_in;
    Button resetPass, canclreset;
    private Dialog builder, builder2, builder3;
    private AppCompatEditText emailLogin, passLogin;
    private TextView forgetPass;
    private AppCompatEditText emailresetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        mAuth = FirebaseAuth.getInstance();
        inOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail().build();
        client = GoogleSignIn.getClient(this, inOptions);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Rec_SignIn) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                FirebaseWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

    private void FirebaseWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user2 = mAuth.getCurrentUser();
                    Intent n = new Intent(Begin.this, Home.class);
                    n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(n);
                } else {
                    Toast.makeText(getApplicationContext(), "connection is failed", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Begin.this, "Failed To login" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void sign_withGoogle(View view) {
        if (mAuth.getCurrentUser() == null) {
            Intent signIntent = client.getSignInIntent();
            startActivityForResult(signIntent, Rec_SignIn);
        } else {
            Intent n = new Intent(Begin.this, Home.class);
            n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(n);
        }
    }

    public void Verify_phone(View view) {
        setDailogphone();
        builder.show();
        verifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editnumber.getText().toString().trim();
                String numberWithPlus = ccop.getFullNumberWithPlus();
                if (mobile.isEmpty() || mobile.length() < 13) {
                    editnumber.setError("Enter a valid mobile");
                    editnumber.requestFocus();
                    return;
                }

                Intent intent = new Intent(Begin.this, VerficationCode.class);
                intent.putExtra("mobile", numberWithPlus);
                startActivity(intent);
                builder.dismiss();
            }

        });
        cancelVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }

        });

    }

    public void sign_up(View view) {
        Intent n = new Intent(Begin.this, Registration.class);
        startActivity(n);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent n = new Intent(Begin.this, Home.class);
            n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(n);
        }
    }


    public void LogIn_withemailandPass(View view) {
        setDailogLogin();
        builder2.show();
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Remail = emailLogin.getText().toString().trim();
                String Rpass = passLogin.getText().toString().trim();
                if (!TextUtils.isEmpty(Remail) && !TextUtils.isEmpty(Rpass)) {
                    mAuth.signInWithEmailAndPassword(Remail, Rpass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent n = new Intent(Begin.this, Home.class);
                                    n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(n);
                                    builder2.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Begin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            emailLogin.setText("");
                            passLogin.setText("");
                        }
                    }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Begin.this, "Welcome back ", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Begin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        canclogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder2.dismiss();
            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder2.dismiss();
                setDailogReset();
                builder3.show();
                resetPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String reset = emailresetPass.getText().toString().trim();
                        mAuth.sendPasswordResetEmail(reset)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            builder3.dismiss();
                                        } else {
                                            Toast.makeText(Begin.this, "Operation Failed, Try again ..!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                });
                canclreset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder3.dismiss();
                    }
                });

            }
        });

    }

    public void setDailogphone() {
        builder = new Dialog(this);
        View v1 = LayoutInflater.from(this).inflate(R.layout.dialogeditphone, (ViewGroup) findViewById(R.id.cardviewzkr));
        editnumber = v1.findViewById(R.id.editTextMobile);
        ccop = v1.findViewById(R.id.ccp);
        ccop.registerCarrierNumberEditText(editnumber);
        verifyNumber = v1.findViewById(R.id.gotoMark);
        cancelVerify = v1.findViewById(R.id.cancelshop);
        builder.setContentView(v1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.setCancelable(false);
        builder.show();
    }

    public void setDailogLogin() {
        builder2 = new Dialog(this);
        View v2 = LayoutInflater.from(this).inflate(R.layout.dialoglogin, (ViewGroup) findViewById(R.id.cardviewzkr));
        emailLogin = v2.findViewById(R.id.editlogemail);
        passLogin = v2.findViewById(R.id.editlogpass);
        log_in = v2.findViewById(R.id.loginnow);
        forgetPass = v2.findViewById(R.id.textforget);

        canclogin = v2.findViewById(R.id.cancelogin);
        builder2.setContentView(v2, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder2.setCancelable(false);
        builder2.show();
    }

    public void setDailogReset() {
        builder3 = new Dialog(this);
        View v3 = LayoutInflater.from(this).inflate(R.layout.dialogrestpass, (ViewGroup) findViewById(R.id.cardviewzkr));
        emailresetPass = v3.findViewById(R.id.editrestemail);
        resetPass = v3.findViewById(R.id.restpas);
        canclreset = v3.findViewById(R.id.cancelrest);
        builder3.setContentView(v3, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder3.setCancelable(false);
        builder3.show();
    }
}
