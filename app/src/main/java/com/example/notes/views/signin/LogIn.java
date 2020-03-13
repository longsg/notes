package com.example.notes.views.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.notes.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    //[FIREBASE ]
    private FirebaseAuth mFirebaseAuth;
    private TextInputLayout mInput_emailWrapper, mInput_passWrapper;
    private TextInputEditText mInput_email, mInput_pass;
    private MaterialButton mSignInButton, mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        iniUI();
        signInAction(mSignInButton);
        registerAction(mRegisterButton);
    }

    private void signInAction(MaterialButton mSignInButton) {
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

        mInput_passWrapper = findViewById(R.id.inputText_login_emailWrapper);
        mInput_pass = findViewById(R.id.inputText_login_passInput);


        mSignInButton = findViewById(R.id.button_login_login);
        mRegisterButton = findViewById(R.id.button_login_register);
    }
    //[END_INIT_UI]
}
