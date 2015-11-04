package com.example.bcapp.app;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;


/**
 * Created by Igor on 06.09.2015.
 */
public class MainFragment extends Fragment {

    private WifiManager wifiManager;

    private TextView status_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (((MainActivity) getActivity()).getWifiManager() != null) {
            wifiManager = ((MainActivity) getActivity()).getWifiManager();
        }


        Button display = (Button) getView().findViewById(R.id.button_status);
        status_text = (TextView) getView().findViewById(R.id.textView_wifiStatus);
        final Switch wifiOnOff = (Switch) getView().findViewById(R.id.switch_wifi);

        wifiOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifiManager != null) {
                    if (wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(true);
                    } else {
                        wifiManager.setWifiEnabled(false);
                    }
                }
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("MainFragment", "Click");


                if (!((MainActivity) getActivity()).isScanning) {
                    ((MainActivity) getActivity()).getWifiManager().startScan();
                    ((MainActivity) getActivity()).setIsScanning(true);
                }
                else {
                    ((MainActivity) getActivity()).setIsScanning(false);
                }
            }
        });
    }

    public void updateStatus(String text){
        if (status_text != null)
            if (status_text.getVisibility() == View.VISIBLE){
                status_text.setText(text);
            }
    }
}
