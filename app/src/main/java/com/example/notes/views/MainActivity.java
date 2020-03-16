package com.example.notes.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.adapter.BookMarkRecyclerView;
import com.example.notes.firebasehelper.FirebaseHelper;
import com.example.notes.model.Bookmark;
import com.example.notes.views.fragments.AddNewNote;
import com.example.notes.views.signin.LogIn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String REQUIRED = "Required";
    private FirebaseAuth mFirebaseAuth;
    private FloatingActionButton mFloatingButton;
    private Handler mHandler;
    private RecyclerView mRecyclerViewContainer;
    private List<Bookmark> bookmarkList = new ArrayList<>();
    private BookMarkRecyclerView mBookMarkAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBaseStuff();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        initUI();
        doFloatingButton(mFloatingButton);
        showData(mDatabaseReference);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewContainer.setLayoutManager(linearLayoutManager);
        mRecyclerViewContainer.setHasFixedSize(true);
        mBookMarkAdapter = new BookMarkRecyclerView(bookmarkList);
        mRecyclerViewContainer.setAdapter(mBookMarkAdapter);

    }

    private void showData(DatabaseReference mDatabaseReference) {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Bookmark bookmark = data.getValue(Bookmark.class);
                    bookmarkList.add(bookmark);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToastDisplay(databaseError.toException() + "");
            }
        });
    }


    private void initUI() {
        mFloatingButton = findViewById(R.id.floatingActionButton);
        mRecyclerViewContainer = findViewById(R.id.recyclerView_container);
    }

    private void initBaseStuff() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("notes");
        mHandler = new Handler();
    }

    private void doFloatingButton(FloatingActionButton mFloatingButton) {
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNote addNewNote = new AddNewNote();
                addNewNote.show(getSupportFragmentManager(), getString(R.string.new_note));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mCurrentUser = mFirebaseAuth.getCurrentUser();
        if (mCurrentUser != currentUser) {
            startActivity(new Intent(this, LogIn.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser mCurrentUser = mFirebaseAuth.getCurrentUser();
        if (mCurrentUser != currentUser) {
            startActivity(new Intent(this, LogIn.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logOut: {
                doLogOut();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void doLogOut() {
        mFirebaseAuth.signOut();
        showToastDisplay("Sign Out");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showToastDisplay("Re-Login again !");
                startActivity(new Intent(MainActivity.this, LogIn.class));
                finish();
            }
        }, 4000);
    }

    private void showToastDisplay(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
