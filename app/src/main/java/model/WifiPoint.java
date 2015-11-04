package model;

/**
 * Created by Igor on 27.10.2015.
 */
public class WifiPoint {

    private int x;
    private int y;

    private String name;
    private String mac;

    private double levelInDb;
    private double freqInMHz;

    public WifiPoint(String name, String mac, double levelInDb, double freqInMHz) {
        this.name = name;
        this.mac = mac;
        this.levelInDb = levelInDb;
        this.freqInMHz = freqInMHz;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLevelInDb(double levelInDb) {
        this.levelInDb = levelInDb;
    }

    public void setFreqInMHz(double freqInMHz) {
        this.freqInMHz = freqInMHz;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    public double getLevelInDb() {
        return levelInDb;
    }

    public double getFreqInMHz() {
        return freqInMHz;
    }
}
