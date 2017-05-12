package com.codemagos.policeapp;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LawActivity extends AppCompatActivity {
WebView mWebview;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        mWebview = (WebView) findViewById(R.id.webview);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" +"http://ecourts.gov.in/sites/default/files/ipc%20sections%20ecourts%20pta_1.pdf");
        progressDialog = ProgressDialog.show(LawActivity.this, "",
                "Loading. Please wait...", true);
        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(LawActivity.this, description, Toast.LENGTH_SHORT).show();
            }
            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressDialog.hide();
            }
        });


    }
}
