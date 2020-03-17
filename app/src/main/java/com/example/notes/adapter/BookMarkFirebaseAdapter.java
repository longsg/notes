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

import java.util.List;

public class BookMarkFirebaseAdapter extends FirebaseRecyclerAdapter<Bookmark, BookMarkFirebaseAdapter.BookMarkViewHolder> {
    private List<Bookmark> bookmarkList;

    public BookMarkFirebaseAdapter(@NonNull FirebaseRecyclerOptions<Bookmark> options, List<Bookmark> bookmarkList) {
        super(options);
        this.bookmarkList = bookmarkList;
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BookMarkFirebaseAdapter(@NonNull FirebaseRecyclerOptions<Bookmark> options) {
        super(options);
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
            mSpeakerButton = itemView.findViewById(R.id.recycler_item_speaker);
        }

        public void bindBookMark(Bookmark bookmark) {
            mBookmark = bookmark;
            mTitle.setText(mBookmark.getmTitle());
            mContent.setText(mBookmark.getmContent());

        }
    }


}
