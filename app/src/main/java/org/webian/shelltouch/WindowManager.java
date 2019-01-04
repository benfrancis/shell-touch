package org.webian.shelltouch;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * Window Manager.
 *
 * Manages open app and browser windows.
 */
public class WindowManager extends Fragment {
    public WindowManager() {
        // Required empty public constructor
    }

    private ArrayList windows;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_window_manager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
