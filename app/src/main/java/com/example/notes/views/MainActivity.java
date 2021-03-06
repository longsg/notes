package com.example.notes.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.example.notes.dao.SwiperControll;
import com.example.notes.model.Bookmark;
import com.example.notes.views.fragments.AddNewNote;
import com.example.notes.views.fragments.UpdateFragment;
import com.example.notes.views.signin.LogIn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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
    public static String BUNDLE_EXTRA = MainActivity.class.toString();
    private FirebaseAuth mFirebaseAuth;
    private FloatingActionButton mFloatingButton;
    private Handler mHandler;
    private RecyclerView mRecyclerViewContainer;
    private List<Bookmark> bookmarkList;
    private BookMarkRecyclerView mBookMarkAdapter;
    private DatabaseReference mDatabaseReference;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private Bookmark mBookmark;
    private ItemTouchHelper mItemTouchHelper;
    private SwiperControll mSwiperControll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBaseStuff();
        initUI();
        mBookmark = new Bookmark();
        doFloatingButton(mFloatingButton);

        bookmarkList = new ArrayList<>();
        mBookMarkAdapter = new BookMarkRecyclerView(bookmarkList);
        mRecyclerViewContainer.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewContainer.setHasFixedSize(true);

        Log.d(TAG, "onCreate: " + bookmarkList.size());


        requestData();

        //recyclerView onItem click
        recyclerViewItemClick(mBookMarkAdapter);
        mItemTouchHelper.attachToRecyclerView(mRecyclerViewContainer);
        mBookMarkAdapter.notifyDataSetChanged();
    }

    private void recyclerViewItemClick(BookMarkRecyclerView bookMarkAdapter) {
        bookMarkAdapter.setClickListener(new BookMarkRecyclerView.ICustomClickListener() {
            @Override
            public void customClick(int position, View view) {
                FragmentManager manager = getSupportFragmentManager();
                UpdateFragment fragment = UpdateFragment.newInstance(bookmarkList.get(position));
                fragment.show(manager, "OK?????");
            }
        });
    }

    private void requestData() {
        ValueEventListener bookmarkEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookmarkList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    mBookmark = data.getValue(Bookmark.class);
                    if (currentUser.equals(mBookmark.getmUserId()))
                        bookmarkList.add(mBookmark);
//                    if (data.getKey().equals(mBookmark.getmBookmarkId())) {
//                        showToastDisplay("Correct !");
//                    }
                    Log.d(TAG, "receiveDataFirebase: " + mBookmark.getmUserId());
                }

                mRecyclerViewContainer.setAdapter(mBookMarkAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        this.mDatabaseReference.addValueEventListener(bookmarkEventListener);
    }

    private void initUI() {
        mFloatingButton = findViewById(R.id.floatingActionButton);
        mRecyclerViewContainer = findViewById(R.id.recyclerView_container);
    }

    private void initBaseStuff() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("notes");
        mHandler = new Handler();
        mSwiperControll = new SwiperControll();
        mItemTouchHelper = new ItemTouchHelper(mSwiperControll);
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
    }

    @Override
    protected void onStop() {
        super.onStop();

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
