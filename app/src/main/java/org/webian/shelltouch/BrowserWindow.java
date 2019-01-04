package org.webian.shelltouch;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;


/**
* Browser Window.
*
* A window with a URL bar and GeckoView.
*/
public class BrowserWindow extends Fragment {

    private GeckoView geckoview;
    private GeckoSession session;
    private static GeckoRuntime runtime;
    private static final String NEW_TAB_PAGE = "http://webian.org";

    public BrowserWindow(GeckoRuntime geckoRuntime) {
        this.runtime = geckoRuntime;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browser_window, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        // Find the GeckoView in our layout
        geckoview = view.findViewById(R.id.browser_geckoview);
        session = new GeckoSession();
        session.open(runtime);
        //geckoview.releaseSession();
        geckoview.setSession(session);
        session.loadUri(NEW_TAB_PAGE);
    }

    /**
     * Navigate back in session history.
     */
    public void goBack() {
        session.goBack();
    }

    /**
     * Reload the current page.
     */
    public void reload() {
        session.reload();
    }
}