package org.webian.shelltouch;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Task Manager.
 *
 * A special window for managing other windows.
 */
public class TaskManagerWindow extends Fragment {


    public TaskManagerWindow() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_manager_window, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}