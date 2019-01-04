package org.webian.shelltouch;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;


/**
 * Webian Shell Touch.
 */
public class Shell extends Activity {

    private ShellDatabase database;
    private View shellContent;
    private HomeScreenWindow homeScreenWindow;
    private TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        shellContent = findViewById(R.id.shell_content);

        // Start database
        database = new ShellDatabase(getApplicationContext());
        ArrayList apps = database.getApps();
        System.out.println("Apps: " + apps);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Create home screen window.
        homeScreenWindow = new HomeScreenWindow();
        transaction.add(R.id.windows, homeScreenWindow);

        // Create and hide task manager.
        taskManager = new TaskManager();
        transaction.add(R.id.windows, taskManager);
        transaction.hide(taskManager);

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
        transaction.hide(taskManager);
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
     * Show the task manager.
     *
     * @param view
     */
    public void showTaskManager(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(homeScreenWindow);
        transaction.show(taskManager);
        transaction.commit();

        // Hide windows button and show add window button
        ImageButton windowsButton = (ImageButton) findViewById(R.id.windows_button);
        ImageButton addWindowButton = (ImageButton) findViewById(R.id.add_window_button);
        windowsButton.setVisibility(View.GONE);
        addWindowButton.setVisibility(View.VISIBLE);
    }

    /**
     * Reload the current page.
     *
     * @param view
     */
    public void reload(View view) {
        homeScreenWindow.reload();
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
