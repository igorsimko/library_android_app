package com.example.bcapp.app;

import android.app.Activity;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.os.Handler;
import com.example.bcapp.alg.Calculation;
import com.example.bcapp.services.MapManager;
import com.example.bcapp.services.WifiService;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private static final String TAG = "MainActivity";

    public int LIBRARY_ID = 0;

    private Fragment fragment;

    private static final int STATUS_UPDATE = 45;

    private CharSequence mTitle;

    private WifiService mWifiService;
    private WifiManager wifiManager;

    private Calculation calculation;

    private MapManager mapManager;

    public boolean isScanning = false;

    /**
     * Screen
     */
    private DisplayMetrics dm = new DisplayMetrics();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mWifiService.getBroadcastReceiver());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (mWifiService == null) {
            if (wifiManager == null) {
                wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                mWifiService = new WifiService(wifiManager, mHandler, isScanning);

                IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                registerReceiver(mWifiService.getBroadcastReceiver(), filter); // Don't forget to unregister during onDestroy
            }
        }

        if (mapManager == null) {
            mapManager = new MapManager(mHandler);

        }


        login();
    }

    public void login() {
        setMap(LIBRARY_ID);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        fragment = new MainFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                fragment = new MainFragment();
                fragmentTransaction.add(fragment, "MainFragment");

                break;
            case 1:
                fragment = new MapFragment();
                fragmentTransaction.add(fragment, "MapFragment");
                break;
        }

        fragmentManager.beginTransaction().replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
    }

    public void setMap(int library_id) {
        if (mapManager != null) {
            mapManager.setLIBRARY_ID(library_id);
            mapManager.setMap(R.drawable.room, 500, 550, 1);

            if (mWifiService != null) {
                mapManager.setWifiPoints(mWifiService.getWifiPoints());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATUS_UPDATE:
                    if (fragment.getTag().contains("MainFragment") && fragment.isVisible()) {
                        MainFragment fragment_obj = (MainFragment) getSupportFragmentManager().
                                findFragmentByTag("MainFragment");
                        fragment_obj.updateStatus(mWifiService.getmStringBuilder().toString());
                    }
                    break;
            }
        }
    };

    public WifiService getmWifiService() {
        return mWifiService;
    }

    public WifiManager getWifiManager() {
        return wifiManager;
    }

    public void setIsScanning(boolean isScanning) {
        this.isScanning = isScanning;
    }

    public DisplayMetrics getDm() {
        return dm;
    }

    public MapManager getMapManager() {
        return mapManager;
    }
}
