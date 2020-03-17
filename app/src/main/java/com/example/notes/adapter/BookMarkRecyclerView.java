package com.example.notes.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.model.Bookmark;

import java.util.ArrayList;
import java.util.List;

public class BookMarkRecyclerView extends RecyclerView.Adapter<BookMarkRecyclerView.BookMarkViewHolder> {
    private List<Bookmark> bookmarkList = new ArrayList<>();


    public BookMarkRecyclerView(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;

    }

    @NonNull
    @Override
    //go here 2nd
    public BookMarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_list, parent, false);
        return new BookMarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkViewHolder holder, int position) {
        Bookmark bookmark = bookmarkList.get(position);
        holder.bindBookMark(bookmark);
    }


    //go here 1st
    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class BookMarkViewHolder extends RecyclerView.ViewHolder {
        private Bookmark mBookmark;
        TextView mTitle, mContent;
        ImageButton mSpeakerButton;

        public BookMarkViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.recycler_item_title);
            mContent = itemView.findViewById(R.id.recycler_item_content);
            mSpeakerButton = itemView.findViewById(R.id.recycler_item_speaker);
        }

        public void bindBookMark(Bookmark bookmark) {
            mBookmark = bookmark;
            mTitle.setText(mBookmark.getmTitle());
            mContent.setText(mBookmark.getmContent());

        }
    }


}
