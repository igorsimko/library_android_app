package com.example.bcapp.services;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import model.Pedestrian;
import model.WifiPoint;

import java.util.List;

/**
 * Created by Igor on 22.10.2015.
 */
public class MapManager {

    public int LIBRARY_ID = 0;

    private Handler mHandler;

    private int width;
    private int height;

    private int offset_x;
    private int offset_y;

    private int ratio;

    private Pedestrian pedestrian;
    private List<WifiPoint> wifiPoints;

    private int mapImage;

    public MapManager(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void setMap(int mapImage, int width, int height, int ratio) {
        this.mapImage = mapImage;
        this.width = width;
        this.height = height;
        this.ratio = ratio;
    }

    public void setLIBRARY_ID(int LIBRARY_ID) {
        this.LIBRARY_ID = LIBRARY_ID;
    }

    public void setWifiPoints(List<WifiPoint> wifiPoints) {
        this.wifiPoints = wifiPoints;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
