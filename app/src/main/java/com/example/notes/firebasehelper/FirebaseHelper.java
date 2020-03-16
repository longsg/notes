package com.example.notes.firebasehelper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.notes.firebasehelper.dao.IFirebaseRepository;
import com.example.notes.model.Bookmark;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseHelper implements IFirebaseRepository {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private List<Bookmark> bookmarkList = new ArrayList<>();

    public FirebaseHelper() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("notes");
    }


    @Override
    public void DataIsLoader(Bookmark bookmark) {

    }

    @Override
    public void DataIsInserted(String title, String content) {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("content", content);

        mDatabaseReference.push().setValue(dataMap);
    }

    @Override
    public void DataIsUpdated(Bookmark bookmark) {

    }

    @Override
    public void DataIsDeleted(Bookmark bookmark) {

    }
}
