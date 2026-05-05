package com.madsssoft.laadsamaj;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class InputValidate {
    private Context context;

    public void InputValidator(Context context) {
        this.context = context;
    }

    // Regular expression for password validation (at least 8 characters, 1 letter, 1 number)
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-zA-Z])" +      // any letter
                    ".{8,}" +               // at least 8 characters
                    "$");

    // Validate name (non-empty)
    public boolean validateName(String name) {
         if(!TextUtils.isEmpty(name)){
            return true;
        }
        else{
//             Toast.makeText(context, "name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
// validate message
    public boolean validateMessage(String message) {
        if(!TextUtils.isEmpty(message)){
            return true;
        }
        else{
//             Toast.makeText(context, "name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Validate mobile number (matches phone number pattern)
    public boolean validateMobile(String mobile) {
         if(!TextUtils.isEmpty(mobile) && Patterns.PHONE.matcher(mobile).matches())
         {
             return true;
         }
         else{
//             Toast.makeText(context, "enter correct number", Toast.LENGTH_SHORT).show();
             return false;
         }
    }

    // Validate email (matches email pattern)
    public boolean validateEmail(String email) {
        if(TextUtils.isEmpty(email) || Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        else{
//            Toast.makeText(context, "enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Validate password (matches password pattern)
    public boolean validatePassword(String password) {
//        if(!TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches())
//
        if(!TextUtils.isEmpty(password)){
            return true;
        }
        else{
//            Toast.makeText(context, "enter a valid password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Validate URL (matches URL pattern)
    public boolean validateURL(String url) {
        if(TextUtils.isEmpty(url) || Patterns.WEB_URL.matcher(url).matches()){
            return true;
        }
        else{
//            Toast.makeText(context, "enter a valid URl", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

//    // Validate necessary field (non-empty)
//    public boolean validateNecessaryField(String field) {
//        return !TextUtils.isEmpty(field);
//    }
//
//    // Method to validate all fields at once and return a boolean
//    public boolean validateAllFields(String name, String mobile, String email, String password, String url, String necessaryField) {
//        return validateName(name) &&
//                validateMobile(mobile) &&
//                validateEmail(email) &&
//                validatePassword(password) &&
//                validateURL(url) &&
//                validateNecessaryField(necessaryField);
//    }
}
