package com.example.notes.dao;

import android.text.TextUtils;
import android.util.Patterns;

public class VerifyAccount {
    public boolean verifyEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean verifyPass(String passWord) {
        return passWord.length() > 5;
    }


}
