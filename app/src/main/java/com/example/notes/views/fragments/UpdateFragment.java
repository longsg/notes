package com.example.notes.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.model.Bookmark;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends DialogFragment {
    private static final String REQUIRES = "requires";
    private static final String TAG = "UpdateFragment";
    public static final int REQUEST_CODE_FRAGMENT = 911;
    private static final String UPDATE_FRAGMENT = UpdateFragment.class.toString();
    private Bookmark mBookmark;
    private Button mUpdateButton, mCancel, mDateTimeButton;
    private TextInputLayout mTitleWrapper, mContentWrapper;
    private TextInputEditText mInputTitle, mInputContent;
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("notes");

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Material_Dialog_Alert;
        mBookmark = new Bookmark();
        setStyle(style, theme);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_update, null);
        initUI(view);
        //getArguments go here 1st, when Fragment creating
        assert getArguments() != null;
        mBookmark = (Bookmark) getArguments().getParcelable(UPDATE_FRAGMENT);
        assert mBookmark != null;
        String title = mBookmark.getmTitle();
        String content = mBookmark.getmContent();
        Date date = mBookmark.getmDate();


//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        String dateConverter = dateFormat.format(date);
//        mDateTimeButton.setText(dateConverter);

        /*
         * [Start-Methods]
         * */
        setUpData(title, content);
        cancelUpdate(mCancel);
        updateAction(mUpdateButton);
        dateTimePick(mDateTimeButton);
        /*
         * [END-Of-Methods]
         * */
        return view;
    }

    private void dateTimePick(Button dateTimeButton) {
        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DateTimePickerFragment fragment = DateTimePickerFragment.newInstance(mBookmark.getmDate());
                fragment.setTargetFragment(UpdateFragment.this, REQUEST_CODE_FRAGMENT);
                fragment.show(fragmentManager, "Just Pick");
            }
        });
    }

    private void setUpData(String title, String content) {
        mInputTitle.setText(title);
        mInputContent.setText(content);
    }

    private void showToastFragment(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    private void updateAction(Button updateButton) {

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleAfter = mInputTitle.getText().toString();
                showToastFragment("Updating.....");
                if (titleAfter.isEmpty()) {
                    mTitleWrapper.setError(REQUIRES);
                    mTitleWrapper.requestFocus();
                } else {
                    mTitleWrapper.setError(null);
                }
                String contentAfter = mInputContent.getText().toString();
                if (contentAfter.isEmpty()) {
                    mContentWrapper.setError(REQUIRES);
                    mContentWrapper.requestFocus();
                } else {
                    mContentWrapper.setError(null);
                }
                doUpdate(mReference, titleAfter, contentAfter);
                doStuffMove();
            }
        });
    }


    private void doUpdate(DatabaseReference reference, String titleAfter, String contentAfter) {
        mBookmark.setmTitle(titleAfter);
        mBookmark.setmContent(contentAfter);

        reference.child(mBookmark.getmBookmarkId()).setValue(mBookmark).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showToastFragment("Correct? check again!! ");
            }
        });
    }

    private void doStuffMove() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                getDialog().dismiss();
            }
        }, 3000);
    }


    private void cancelUpdate(Button cancel) {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void initUI(View view) {
        //button
        mUpdateButton = view.findViewById(R.id.button_update_update);
        mCancel = view.findViewById(R.id.button_update_cancel);
        mDateTimeButton = view.findViewById(R.id.button_update_datetime);
        //layoutInput
        mTitleWrapper = view.findViewById(R.id.inputText_update_titleWrapper);
        mContentWrapper = view.findViewById(R.id.inputText_update_contentWrapper);
        //inputEdittext
        mInputTitle = view.findViewById(R.id.inputText_update_title);
        mInputContent = view.findViewById(R.id.inputText_update_content);
    }

    public static UpdateFragment newInstance(Bookmark bookmark) {
        Bundle bundle = new Bundle();
        UpdateFragment fragment = new UpdateFragment();
        bundle.putParcelable(UPDATE_FRAGMENT, bookmark);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_FRAGMENT) {
            Date date = (Date) data.getSerializableExtra(DateTimePickerFragment.EXTRA_DATE);
            // convertDateTime(date);
            mBookmark.setmDate(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateConverted = simpleDateFormat.format(date);
            mDateTimeButton.setText(dateConverted);
        }
    }
}
