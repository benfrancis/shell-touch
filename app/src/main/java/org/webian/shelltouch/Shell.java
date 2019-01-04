package org.webian.shelltouch;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoRuntimeSettings;

import java.util.ArrayList;


/**
 * Webian Shell Touch.
 */
public class Shell extends Activity {

    private ShellDatabase database;
    private View shellContent;
    private HomeScreenWindow homeScreenWindow;
    private WindowManager windowManager;
    private static GeckoRuntime runtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        shellContent = findViewById(R.id.shell_content);

        // Start Gecko runtime
        final GeckoRuntimeSettings.Builder runtimeSettingsBuilder =
                new GeckoRuntimeSettings.Builder();
        // Manually set display density for Raspberry Pi display
        runtimeSettingsBuilder.displayDensityOverride(1);
        runtime = GeckoRuntime.create(getApplicationContext(), runtimeSettingsBuilder.build());

        // Start database
        database = new ShellDatabase(getApplicationContext());
        ArrayList apps = database.getApps();
        System.out.println("Apps: " + apps);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Create home screen window.
        homeScreenWindow = new HomeScreenWindow(runtime);
        transaction.add(R.id.windows, homeScreenWindow);

        // Create and hide window manager.
        windowManager = new WindowManager();
        transaction.add(R.id.windows, windowManager);
        transaction.hide(windowManager);

        transaction.commit();


    }

    /**
     * Show the home screen.
     *
     * @param view
     */
    public void home(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(windowManager);
        transaction.show(homeScreenWindow);
        transaction.commit();

        // Hide add window button and show windows button
        ImageButton windowsButton = (ImageButton) findViewById(R.id.windows_button);
        ImageButton addWindowButton = (ImageButton) findViewById(R.id.add_window_button);
        windowsButton.setVisibility(View.VISIBLE);
        addWindowButton.setVisibility(View.GONE);
    }

    /**
     * Navigate back in session history.
     *
     * @param view
     */
    public void back(View view) {
        homeScreenWindow.goBack();
    }

    /**
     * Show the window manager.
     *
     * @param view
     */
    public void showWindowManager(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(homeScreenWindow);
        transaction.show(windowManager);
        transaction.commit();

        // Hide windows button and show add window button
        ImageButton windowsButton = (ImageButton) findViewById(R.id.windows_button);
        ImageButton addWindowButton = (ImageButton) findViewById(R.id.add_window_button);
        windowsButton.setVisibility(View.GONE);
        addWindowButton.setVisibility(View.VISIBLE);
    }

    public void addWindow(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Create new browser window.
        BrowserWindow browserWindow = new BrowserWindow(runtime);
        transaction.add(R.id.windows, browserWindow);
        transaction.hide(windowManager);
        transaction.commit();

        // Hide add window button and show windows button
        ImageButton windowsButton = (ImageButton) findViewById(R.id.windows_button);
        ImageButton addWindowButton = (ImageButton) findViewById(R.id.add_window_button);
        windowsButton.setVisibility(View.VISIBLE);
        addWindowButton.setVisibility(View.GONE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            shellContent.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
