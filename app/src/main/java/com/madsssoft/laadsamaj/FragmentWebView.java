package com.madsssoft.laadsamaj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class FragmentWebView extends Fragment {

    private WebView webView;

    public FragmentWebView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        // Assuming you have a WebView element in your layout with the id "webView"
        webView = view.findViewById(R.id.webView);

        // Enable JavaScript (optional)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Retrieve the URL from the bundle
        Bundle args = getArguments();
        if (args != null && args.containsKey("url")) {
            String url = args.getString("url");

            // Assuming the URL is the filename in the assets folder (without "file:///android_asset/")
            String htmlFilePath = "file:///android_asset/" + url;

            // Load the URL into the WebView
            webView.loadUrl(htmlFilePath);

            // Set a WebViewClient to handle redirects and page loading
            webView.setWebViewClient(new WebViewClient());
        } else {
            // Handle the case where the URL is not provided
            // You may show an error message or take appropriate action
        }

        return view;
    }
}
