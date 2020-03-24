package com.example.notes.views.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.dao.VerifyAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    private static final int REQUEST_CODE = 999;
    private static final String EMAIL = "email";
    private static final String PASS = "pass";
    private FirebaseAuth mFirebaseAuth;

    private TextInputLayout mInput_emailWrapper, mInput_passWrapper;
    private TextInputEditText mInput_email, mInput_pass;
    private MaterialButton mRegisterButton;
    private VerifyAccount mVerifyAccount = new VerifyAccount();

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mHandler = new Handler();
        initUI();

        registerAction(mRegisterButton);

    }

    private void registerAction(MaterialButton mRegisterButton) {

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mInput_email.getText().toString();
                String pass = mInput_pass.getText().toString();
                if (!mVerifyAccount.verifyEmail(email)) {
                    String str = "Wrong email or already exist";
                    mInput_emailWrapper.setError(str);
                    mInput_emailWrapper.requestFocus();
                    return;
                }

                if (!mVerifyAccount.verifyPass(pass)) {
                    String str = "Password must be at least 6 characters";
                    mInput_passWrapper.setError(str);
                    mInput_passWrapper.requestFocus();
                    return;
                }
                Log.d(TAG, "onClick: Email" + email);

                createUserWithFirebase(email, pass);


            }
        });

    }


    private void createUserWithFirebase(String email, String pass) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
//                                showToast("Malformed email");
                                // TODO: Take your action
                                mInput_emailWrapper.setError("Malformed  email ");
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Log.d(TAG, "onComplete: exist_email");
//                                showToast("Email exist");
                                // TODO: Take your action
                                mInput_emailWrapper.setError("Email exist");
                            } catch (Exception e) {
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
                        } else if (task.isSuccessful()) {
                            showToast("Successfully, Please relogin");
                            //move LoginActivity
                            moveActivity();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Something wrong, check again !!");
                    }
                });
    }

    private void moveActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doStuff();
            }
        }, 3000);
    }

    private void doStuff() {
        Intent intent = new Intent(Register.this, LogIn.class);
        startActivity(intent);
        finish();
    }

    private void initUI() {
        mInput_emailWrapper = findViewById(R.id.inputText_register_emailWrapper);
        mInput_passWrapper = findViewById(R.id.inputText_register_passWrapper);
        mInput_email = findViewById(R.id.inputText_register_emailInput);
        mInput_pass = findViewById(R.id.inputText_register_passInput);
        mRegisterButton = findViewById(R.id.button_register_register);
    }

    public void setmVerifyAccount(VerifyAccount mVerifyAccount) {
        this.mVerifyAccount = mVerifyAccount;
    }

    //show Toast on Display {@Toast}
    private void showToast(String str) {
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
    }


}
