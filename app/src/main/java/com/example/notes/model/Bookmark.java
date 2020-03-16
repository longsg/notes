package com.example.notes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Bookmark implements Parcelable {
    private String mTitle;
    private String mContent;

    private String mBookmarkId;
    private String mUserId;

    public Bookmark() {
    }

    public Bookmark(String mTitle, String mContent, String mBookmarkId, String mUserId) {
        this.mTitle = mTitle;
        this.mContent = mContent;

        this.mBookmarkId = mBookmarkId;
        this.mUserId = mUserId;
    }

    protected Bookmark(Parcel in) {
        mTitle = in.readString();
        mContent = in.readString();
        mBookmarkId = in.readString();
        mUserId = in.readString();
    }

    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        @Override
        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }


    public String getmBookmarkId() {
        return mBookmarkId;
    }

    public void setmBookmarkId(String mBookmarkId) {
        this.mBookmarkId = mBookmarkId;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mContent);
        dest.writeString(mBookmarkId);
        dest.writeString(mUserId);
    }
}
