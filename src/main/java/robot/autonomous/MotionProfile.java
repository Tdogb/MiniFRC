package robot.autonomous;

import java.util.ArrayList;

public class MotionProfile {
    double cruiseStart = 100;
    double cruiseEnd = 0;
    double cruiseTime = 0;
    double pathTime = 0;
    boolean instantaneousCruise = false;
    boolean velocityProfileGenerated = false;
    double tconversionFactor = 1;
    double totalTime = 0;

    ArrayList<Waypoint> wps;
    ArrayList<Spline> splines;

    double totalArcLength = 0;

    public MotionProfile(ArrayList<Waypoint> wps) {
        this.wps = wps;
        splines = new ArrayList<Spline>();
        initMP();
    }

    private void initMP() {
        for(int i = 1; i < wps.size(); i++) {
            splines.add(new Spline(wps.get(i-1), wps.get(i)));
        }
        for(int i = 0; i < splines.size(); i++) {
            totalArcLength += splines.get(i).getArcLength();
        }
        generateVelocityProfile();
        debug();
    }

    public double velocityProfile(double t) {
        if(velocityProfileGenerated) {
            if(t < cruiseStart) {
                return robot.Global.maxAccel*t;
            }
            else if(t>= cruiseStart && t<= cruiseEnd) {
                return robot.Global.maxVel;
            }
            else {
                return robot.Global.maxVel - robot.Global.maxAccel*(t-cruiseEnd);
            }
        }
        else {
            System.err.println("Velocity profile not generated");
        }
        return 0;
    }

    private void generateVelocityProfile() {
        //When integral of velocity = total path length
        //If half up the velocity profile = half of path length

        //Points:
        //cruise, end cruise
        double v = 0;
        double distance = 0;
        double t = robot.Global.timeStep;
        //Acceleration IN REAL TIME UNITS
        while(v < robot.Global.maxVel && !instantaneousCruise) {
            double vi = v;
            v = v + robot.Global.maxAccel * robot.Global.timeStep;
            distance += (v-vi)* robot.Global.timeStep;
            if(distance > totalArcLength/2) {
                cruiseStart = t;
                cruiseEnd = t;
                instantaneousCruise = true;
            }
            t+= robot.Global.timeStep;
            totalTime += robot.Global.timeStep;
            if(v >= robot.Global.maxVel) {
                v = robot.Global.maxVel;
                cruiseStart = t;
                instantaneousCruise = false;
            }
        }
        //Deceleration
        if(!instantaneousCruise) {
            cruiseEnd = cruiseStart;
            t = 0;
            distance = 0;
            v = 0;
            while(v < robot.Global.maxVel) {
                double vi = v;
                v = v + robot.Global.maxAccel* robot.Global.timeStep;
                distance += (v-vi)* robot.Global.timeStep;
                if(v >= robot.Global.maxVel) {
                    cruiseTime = (totalArcLength - 2 * distance)/ robot.Global.maxVel;
                    cruiseEnd = cruiseStart + cruiseTime;
                }
                t += robot.Global.timeStep;
                totalTime += robot.Global.timeStep;
            }
            //totalTime = (1 - (cruiseEnd-cruiseStart)); in terms of ratios.
            //T total time is the 2 triangles in the trapezoid, and the cruise is the block in the middle
            //Divided by total time because it is a conversion
//            tconversionFactor = (1-(cruiseEnd-cruiseStart))/(totalTime);
        }
        else {
            totalTime *= 2;
//            tconversionFactor = totalTime;
        }
        pathTime = cruiseEnd + cruiseStart; //Because cruisestart should be one of the triangles and cruiseend is one triangle + middle
        tconversionFactor = 1/pathTime;
        velocityProfileGenerated = true;
    }

    private void debug() {
        for (Spline s : splines) {
            //s.debug(tconversionFactor, pathTime);
        }
    }
}
