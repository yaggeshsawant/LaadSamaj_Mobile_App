package com.madsssoft.laadsamaj;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {
        Context context;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        private final String PREF_FILE_NAME = "member_info";
        private final int PRIVATE_MODE = 0;

        private final String KEY_MEMBER_ID = "key_member_id";
        private final String KEY_MEMBER_NAME = "key_member_name";
        private final String KEY_IF_LOGGED_IN = "key_session_if_logged_in";

        public void createSession(PojoLogin list) {

            editor = sharedPreferences.edit();
            editor.putInt(KEY_MEMBER_ID, list.getId());
            editor.putString(KEY_MEMBER_NAME, list.getName());
            editor.putBoolean(KEY_IF_LOGGED_IN, true);
            editor.apply();
        }

        public SessionManager(Context context) {
            this.context = context;
            sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, PRIVATE_MODE);
            editor = sharedPreferences.edit();
        }


    public boolean checkSession() {
        int sessionId = sharedPreferences.getInt(KEY_MEMBER_ID, 0);
        return sessionId > 0;
    }

    public PojoLogin getStudentDetails() {
        PojoLogin list = new PojoLogin();
            list.setId(sharedPreferences.getInt(KEY_MEMBER_ID, 0));
            list.setName(sharedPreferences.getString(KEY_MEMBER_NAME, null));

        return list;
    }



    public int getRegId() {
        return sharedPreferences.getInt(KEY_MEMBER_ID, 0);
    }

    public String getName() {
        return sharedPreferences.getString(KEY_MEMBER_NAME, "");
    }



    public void logoutSession() {
        editor.clear();
        editor.apply();

        Intent intent = new Intent(context, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Optional: If you want to finish the current activity
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }



}
