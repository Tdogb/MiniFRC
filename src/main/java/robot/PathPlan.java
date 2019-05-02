package robot;

import robot.autonomous.Waypoint;

import java.util.ArrayList;

public class PathPlan {
    public static ArrayList<Waypoint> getPlan() {
        ArrayList<Waypoint> wps = new ArrayList<Waypoint>();
        wps.add(new Waypoint(0,0,1,0));
        wps.add(new Waypoint(2,5,0.5,0.5));
        wps.add(new Waypoint(2,9,1,0));
        return wps;
    }
}
