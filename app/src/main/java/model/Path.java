package model;

import java.util.ArrayList;

/**
 * Created by Igor on 22.10.2015.
 */
public class Path {

    private ArrayList<Position> path = new ArrayList<Position>();

    public void addPosition(Position pos){
        path.add(pos);
    }

    public ArrayList<Position> getPath() {
        return path;
    }

}
