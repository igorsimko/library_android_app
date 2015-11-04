package model;

/**
 * Created by Igor on 22.10.2015.
 */
public class Regal {

    private int width;
    private int height;

    private Position position;

    public Regal(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
