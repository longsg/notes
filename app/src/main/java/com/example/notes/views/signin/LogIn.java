package com.example.notes.views.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.dao.VerifyAccount;
import com.example.notes.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LogIn extends AppCompatActivity {
    private static final String TAG = "LogIn";
    //[FIREBASE ]
    private FirebaseAuth mFirebaseAuth;
    //[END_FIREBASE_]
    private TextInputLayout mInput_emailWrapper, mInput_passWrapper;
    private TextInputEditText mInput_email, mInput_pass;
    private MaterialButton mSignInButton, mRegisterButton;

    private VerifyAccount mVerifyAccount = new VerifyAccount();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mHandler = new Handler();
        iniUI();
        signInAction(mSignInButton);
        registerAction(mRegisterButton);
    }

    private void signInAction(MaterialButton mSignInButton) {
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mInput_email.getText().toString();
                String passWord = mInput_pass.getText().toString();
                if (!mVerifyAccount.verifyEmail(email)) {
                    //showToastDisplay("Email wrong");
                    mInput_emailWrapper.setError("Please enter your email !!");
                    mInput_emailWrapper.requestFocus();
                    return;
                }

               if (!mVerifyAccount.verifyPass(passWord)) {
                    mInput_passWrapper.setError("Password must be at least 6 characters !!");
                    mInput_passWrapper.requestFocus();
                    return;
                }

                signInEmailAndPassword(email, passWord);
            }
        });
    }

    private void showToastDisplay(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    //using FirebaseAuth
    private void signInEmailAndPassword(String email, String passWord) {
        mFirebaseAuth.signInWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            }
                            // if user enters wrong email.
                            catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Log.d(TAG, "onComplete: weak_password");

                                // TODO: take your actions!
                            }
                            // if user enters wrong password.
                            catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Log.d(TAG, "onComplete: malformed_email");
                               showToastDisplay("Wrong password or mail !");
//                                showToast("Malformed email");
                                // TODO: Take your action
                                mInput_emailWrapper.setError("Malformed  email ");
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Log.d(TAG, "onComplete: exist_email");
//                                showToast("Email exist");
                                // TODO: Take your action
                                mInput_emailWrapper.setError("Email exist");
                             }catch (FirebaseAuthInvalidUserException e){
                                showToastDisplay("Email maybe blocked or not register");
                            }
                            catch (Exception e) {
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
                        } else if (task.isSuccessful()) {
                            showToastDisplay("Wait a few seconds");
                            moveScreen();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
    }

    private void moveScreen() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LogIn.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

    private void registerAction(MaterialButton mRegisterButton) {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, Register.class));
            }
        });
    }

    //[START_INIT_UI]
    private void iniUI() {
        mInput_emailWrapper = findViewById(R.id.inputText_login_emailWrapper);
        mInput_email = findViewById(R.id.inputText_login_emailInput);

        mInput_passWrapper = findViewById(R.id.inputText_login_passWrapper);

        mInput_pass = findViewById(R.id.inputText_login_passInput);


        mSignInButton = findViewById(R.id.button_login_login);
        mRegisterButton = findViewById(R.id.button_login_register);
    }
    //[END_INIT_UI]
}
