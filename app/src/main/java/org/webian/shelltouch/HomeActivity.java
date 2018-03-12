package org.webian.shelltouch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Webian Shell Touch.
 */
public class HomeActivity extends Activity {

    private ShellDatabase database;
    private View mContentView;
    private WebView webview;
    private static final String HOME_PAGE = "http://home.tola.me.uk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = new ShellDatabase(getApplicationContext());
        ArrayList apps = database.getApps();
        System.out.println("Apps: " + apps);
        mContentView = findViewById(R.id.home_content);
        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setInitialScale(100);
        webview.loadUrl(HOME_PAGE);
    }

    /**
     * Navigate to the home page.
     *
     * @param view
     */
    public void home(View view) {
        webview.loadUrl(HOME_PAGE);
    }

    /**
     * Navigate back in session history.
     *
     * @param view
     */
    public void back(View view) {
        webview.goBack();
    }

    /**
     * Reload the current page.
     *
     * @param view
     */
    public void reload(View view) {
        webview.reload();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mContentView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
