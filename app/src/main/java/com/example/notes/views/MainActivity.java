package com.example.notes.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;


    private FloatingActionButton mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFireBase();

        initUI();
    }

    private void initUI() {
        mFloatingButton = findViewById(R.id.floatingActionButton);
    }

    private void initFireBase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mCurrentUser = mFirebaseAuth.getCurrentUser();
    }
}
