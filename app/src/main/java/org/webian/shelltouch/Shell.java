package org.webian.shelltouch;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * Webian Shell Touch.
 */
public class Shell extends Activity {

    private ShellDatabase database;
    private View mContentView;
    private HomeScreenWindow homeScreenWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shell);
        database = new ShellDatabase(getApplicationContext());
        ArrayList apps = database.getApps();
        System.out.println("Apps: " + apps);
        mContentView = findViewById(R.id.home_content);
        homeScreenWindow = (HomeScreenWindow) getFragmentManager().findFragmentById(R.id.homescreen_window);
    }

    /**
     * Back button clicked.
     *
     * @param view
     */
    public void handleBackButtonClick(View view) {
        homeScreenWindow.goBack();
    }

    /**
     * Home button clicked.
     *
     * @param view
     */
    public void handleHomeButtonClick(View view) {
      homeScreenWindow.goHome();
    }

    /**
     * Windows button clicked.
     *
     * @param view
     */
    public void handleWindowsButtonClick(View view) {
      showTaskManager();
    }

    public void showTaskManager() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Create new fragment and transaction
        Fragment taskManager = new TaskManagerWindow();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.homescreen_window, taskManager);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


    /**
     * Reload the current page.
     *
     * @param view
     */
    /*public void reload(View view) {
        homeScreenWindow.reload();
    }*/

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
