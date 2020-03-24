package com.example.notes.firebasehelper.dao;

import com.example.notes.model.Bookmark;

import java.util.List;

public interface IFirebaseRepository {
    void DataIsLoader(Bookmark bookmark);

    void DataIsInserted(String title, String content);

    void DataIsUpdated(Bookmark bookmark);

    void DataIsDeleted(Bookmark bookmark);
}
