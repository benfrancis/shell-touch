package org.webian.shelltouch;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

/**
 * Webian Shell Touch.
 */
public class HomeActivity extends Activity {

    private View mContentView;
    private WebView webview;
    private static final String HOME_PAGE = "http://localhost:8080/home/";
    private ShellServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContentView = findViewById(R.id.home_content);
        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setInitialScale(100);
        webview.loadUrl(HOME_PAGE);
    }

    public void onResume() {
        AssetManager assetManager = getAssets();
        super.onResume();
        try {
            server = new ShellServer(assetManager);
        } catch (IOException e) {
            System.out.println("Failed to instantiate ShellServer");
        }
        try {
            server.start();
        } catch (IOException e) {
            System.out.println("ShellServer failed to start");
            e.printStackTrace();
        }
    }

    public void onPause() {
        super.onPause();
        if(server != null) {
            server.stop();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if(server != null) {
            server.stop();
        }
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
