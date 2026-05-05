package com.madsssoft.laadsamaj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageAvailabilityChecker {
    // image validity checker
    public static boolean isImageAvailable(Context context, String imageUrl) {
        try {
            return new CheckImageAvailabilityTask().execute(imageUrl).get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class CheckImageAvailabilityTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String imageUrl = params[0];
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

// url validity checker and open
    public static void openLinkInBrowser(Context context, String link) {
        if (isValidUrl(link)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            context.startActivity(browserIntent);
        } else {
            Toast.makeText(context, "Invalid link", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

