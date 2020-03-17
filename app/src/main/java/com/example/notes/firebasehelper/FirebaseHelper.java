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

public class FirebaseHelper {
    public static final class FirebaseInit {
        public static final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        public static final DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference("notes");

    }

}
