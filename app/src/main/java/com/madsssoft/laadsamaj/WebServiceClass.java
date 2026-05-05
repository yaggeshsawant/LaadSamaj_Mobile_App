package com.madsssoft.laadsamaj;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class WebServiceClass {

    private static final int CALL_PHONE_PERMISSION_REQUEST = 123; // You can use any integer value



    private Context context;

    public WebServiceClass(Context context) {
        this.context = context;
    }

    public void makeCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // If the permission is granted, initiate the phone call
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+phoneNumber));
            context.startActivity(callIntent);
        } else {
            // If the permission is not granted, request it from the user
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST);
        }
        }

    public void sendWhatsAppMessage(String phoneNumber) {
        openUrl("https://api.whatsapp.com/send?phone=" + phoneNumber);
    }

    public void showLiveMapLocation(String url) {
        openUrl(url);
    }

    public void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            showErrorMessage("Error opening URL");
            e.printStackTrace();
        }
    }


    private void showErrorMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



}

