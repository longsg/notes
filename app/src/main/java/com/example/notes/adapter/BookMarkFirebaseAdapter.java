package com.example.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.model.Bookmark;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;

import java.util.List;

//required object and custom ViewHolder in agru
public class BookMarkFirebaseAdapter extends FirebaseRecyclerAdapter<Bookmark, BookMarkFirebaseAdapter.BookMarkViewHolder> {
    private List<Bookmark> bookmarkList;

    public BookMarkFirebaseAdapter(@NonNull FirebaseRecyclerOptions<Bookmark> options, List<Bookmark> bookmarkList) {
        super(options);
        this.bookmarkList = bookmarkList;
    }


    @Override
    protected void onBindViewHolder(@NonNull BookMarkViewHolder holder, int position, @NonNull Bookmark model) {
        model = bookmarkList.get(position);
        holder.bindBookMark(model);
    }

    @NonNull
    @Override
    public BookMarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_list, parent, false);
        return new BookMarkViewHolder(view);
    }

    public class BookMarkViewHolder extends RecyclerView.ViewHolder {

        private Bookmark mBookmark;
        TextView mTitle, mContent;
        Button mSpeakerButton;

        public BookMarkViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.recycler_item_title);
            mContent = itemView.findViewById(R.id.recycler_item_content);

        }

        public void bindBookMark(Bookmark bookmark) {
            mBookmark = bookmark;
            mTitle.setText(mBookmark.getmTitle());
            mContent.setText(mBookmark.getmContent());

        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();

    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
    }
}
