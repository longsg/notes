package com.example.notes.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


import android.os.Handler;
import android.text.TextUtils;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class AddNewNote extends DialogFragment {
    private static final String TAG = "AddNewNote";
    private static final String REQUIRED = "Required";
    private static final int REQUEST_DATE = 0;
    public static final int ID_FRAGMENT = 1;
    private TextInputLayout mInputTitleWrapper, mInputContentWrapper;
    private TextInputEditText mInputTitle, mInputContent;
    private MaterialButton mCreateDialogButton, mCancelDialogButton;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public static DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("notes");
    private Button mDatetimeButton;
    private Bookmark bookmark;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Material_Dialog_Alert;
        setStyle(style, theme);
        bookmark = new Bookmark();
    }

    public AddNewNote() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_add_note, container, false);
        initUI(inflate);
        getDialog().setTitle("New Note");
        datetimePicker(mDatetimeButton);
        createNote(mCreateDialogButton);
        cancelNote(mCancelDialogButton);
        return inflate;
    }

    private void datetimePicker(Button mDatetimeButton) {
        mDatetimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DateTimePickerFragment fragment = DateTimePickerFragment.newInstance(bookmark.getmDate());
                //setTargetFragment set connect AddNewNote with DateTimePickerFrag/
                fragment.setTargetFragment(AddNewNote.this, REQUEST_DATE);
                fragment.show(manager, "Test");
            }
        });
    }

    private void cancelNote(MaterialButton mCancelDialogButton) {
        mCancelDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void createNote(MaterialButton mCreateDialogButton) {
        mCreateDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mInputTitle.getText().toString();
                String content = mInputContent.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    mInputTitleWrapper.setError(REQUIRED);
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    mInputContentWrapper.setError(REQUIRED);
                    return;
                }
                showToast("Creating....");
                int id = new Random().nextInt(100);
//                Bookmark bookmark = new Bookmark();
                bookmark.setmBookmarkId("AF" + id);
                bookmark.setmTitle(title);
                bookmark.setmContent(content);
                bookmark.setmUserId(currentUser.getEmail());

                mDatabaseReference.child(bookmark.getmBookmarkId()).setValue(bookmark)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showToast("Saved !");
                                    showToast(bookmark.getmBookmarkId());
                                    closeDialog();
                                } else {
                                    Log.w(TAG, "onComplete: ", task.getException());
                                }
                            }
                        });
            }
        });


    }

    private void closeDialog() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDialog().dismiss();
            }
        }, 3000);

    }

    private void initUI(View inflate) {
        //title
        mInputTitleWrapper = inflate.findViewById(R.id.inputText_dialog_titleWrapper);
        mInputTitle = inflate.findViewById(R.id.inputText_dialog_title);
        //content
        mInputContentWrapper = inflate.findViewById(R.id.inputText_dialog_contentWrapper);
        mInputContent = inflate.findViewById(R.id.inputText_dialog_content);
        //buttons
        mCreateDialogButton = inflate.findViewById(R.id.button_dialog_create);
        mCancelDialogButton = inflate.findViewById(R.id.button_dialog_cancel);
        mDatetimeButton = inflate.findViewById(R.id.button_fragment_datetime);

    }

    private void showToast(String string) {
        Toast.makeText(getContext()
                , string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DateTimePickerFragment.EXTRA_DATE);
            // convertDateTime(date);
            showToast(date.toString());
            bookmark.setmDate(date);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateConverted = simpleDateFormat.format(date);
            mDatetimeButton.setText(dateConverted);
        }
    }

}
