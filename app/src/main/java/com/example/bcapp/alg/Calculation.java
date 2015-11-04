package com.example.bcapp.alg;

import model.Position;
import model.WifiPoint;

import java.util.Collections;
import java.util.List;

/**
 * Created by Igor on 27.10.2015.
 */
public class Calculation {

    /**
     * Calculate distance from wifi router
     *
     * @param levelInDb
     * @param freqInMHz
     * @return
     */
    public static double calculateDistance(double levelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(levelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    public static Position trilateration(List<WifiPoint> wifiPoints) {
        //Collections.so

        double x, y, z;
        double r1, r2, r3;

        int d; // coordinate of point P2
        int i;
        int j;

        r1 = calculateDistance(wifiPoints.get(0).getLevelInDb(),wifiPoints.get(0).getFreqInMHz());
        r2 = calculateDistance(wifiPoints.get(1).getLevelInDb(),wifiPoints.get(1).getFreqInMHz());
        r3 = calculateDistance(wifiPoints.get(2).getLevelInDb(),wifiPoints.get(2).getFreqInMHz());

        d = wifiPoints.get(1).getX() - wifiPoints.get(0).getX();

        i = wifiPoints.get(2).getX();
        j = wifiPoints.get(2).getY();

        x = (Math.pow(r1,2)-Math.pow(r2,2)+Math.pow(d,2))/(2*d);
        y = ((Math.pow(r1,2)-Math.pow(r3,2)+Math.pow(i,2)+Math.pow(j,2))/(2*j))-((i/j)*x);


        return new Position((int)x,(int)y);
    }
}
