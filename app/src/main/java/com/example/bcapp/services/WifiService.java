package com.example.bcapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import com.example.bcapp.alg.Calculation;
import model.WifiPoint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Igor on 06.09.2015.
 */
public class WifiService {

    private static final String TAG = "WifiService";

    private static final int STATUS_UPDATE = 45;

    private WifiManager wifiManager;
    private Handler mHandler;
    private boolean isScanning;

    private List<ScanResult> results;

    private List<WifiPoint> wifiPoints;

    private StringBuilder mStringBuilder;

    public WifiService(WifiManager wifiManager, Handler mHandler, boolean isScanning) {
        this.wifiManager = wifiManager;
        this.mHandler = mHandler;
        this.isScanning = isScanning;

        wifiPoints = new ArrayList();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context c, Intent intent) {
            mStringBuilder = new StringBuilder();
            results = wifiManager.getScanResults();
            for (ScanResult s : results) {
                DecimalFormat df = new DecimalFormat("#.##");
                Log.d(TAG, "frequency: " + s.frequency + " | " + s.SSID + " | " + s.BSSID + ": " + ", d: " +
                        df.format(Calculation.calculateDistance((double) s.level, s.frequency)) + "m");


                // Add or update wifi points in List
                WifiPoint point = new WifiPoint(s.SSID, s.BSSID, s.level, s.frequency);

                if (!wifiPoints.contains(point) && isInLib(point)) {
                    wifiPoints.add(point);
                    positionByMac(point);
                } else {
                    int index = wifiPoints.indexOf(point);

                    if (index >= 0) {
                        wifiPoints.get(index).setFreqInMHz(s.frequency);
                        wifiPoints.get(index).setFreqInMHz(s.level);
                    }
                }

                mStringBuilder.append(isScanning + "| " + s.frequency + " | " + s.SSID + " | " + ": " + ", d: " +
                        df.format(Calculation.calculateDistance((double) s.level, s.frequency)) + "m\n");
            }

            // Update status text
            mHandler.sendEmptyMessage(STATUS_UPDATE);

            if (isScanning)
                wifiManager.startScan();
        }
    };

    public boolean isInLib(WifiPoint point) {
        //Query find in database

        if (point.getMac().contains("80:1f:02:12:fa:f2")) {
            return true;
        }
        return false;
    }

    public void positionByMac(WifiPoint point) {
        // Query for find mac
        // ...
        // get x,y coordinates
        //...


        /* TEMP */
        if (point.getName().contains("Cely")) {
            point.setX(480);
            point.setY(320);
        } else if (point.getName().contains("klatisko")) {
/*            point.setX();
            point.setY();*/
        } else if (point.getName().contains("Fox")) {
            point.setX(200);
            point.setY(40);
        }

    }

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public StringBuilder getmStringBuilder() {
        return mStringBuilder;
    }

    public List<WifiPoint> getWifiPoints() {
        return wifiPoints;
    }
}
