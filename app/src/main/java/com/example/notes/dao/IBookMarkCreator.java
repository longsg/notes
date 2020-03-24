package com.example.notes.dao;

import com.example.notes.model.Bookmark;

public interface IBookMarkCreator {
    void writeNote(String title, String content);

    void updateNote(Bookmark bookmark);

    void deleteNote(Bookmark bookmark);

    void selectedBookMark(Bookmark bookmark);
}
