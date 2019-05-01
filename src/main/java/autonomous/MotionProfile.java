package autonomous;

import java.util.ArrayList;

public class MotionProfile {
    final double timeStep = 0.01;
    final double maxVel = 0.2; //m/s
    final double maxAccel = 0.1; //m/s^2
    final double robotWheelBase = 0.18; //Diameter
    final double robotWheelDiameter = 0.051;
    double cruiseStart = 100;
    double cruiseEnd = 0;
    double cruiseTime = 0;
    double pathTime = 0;
    boolean instantaneousCruise = false;
    boolean velocityProfileGenerated = false;
    double tconversionFactor = 1;

    ArrayList<Waypoint> wp = new ArrayList<Waypoint>();

    public double velocityProfile(double t) {
        if(velocityProfileGenerated) {
//            t = _t * tconversionFactor;
            if(t < cruiseStart) {
//                return 0.5*maxAccel*Math.pow(t,2);
                return maxAccel*t;
            }
            else if(t>= cruiseStart && t<= cruiseEnd) {
                return maxVel;
            }
            else {
//                return maxVel - 0.5*maxAccel*Math.pow((t-cruiseEnd), 2);
                return maxVel - maxAccel*(t-cruiseEnd);
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
        double totalTime = 0;
        double arcLength = 0;
        for(double t = 0; t <=1; t+=timeStep) { //These are also in terms of this "fake" time (proportional to total time)
            double dx = 3*a.x()*Math.pow(t,2)+2*b.x()*t+c.x();
            double dy = 3*a.y()*Math.pow(t,2)+2*b.y()*t+c.y();
            arcLength += Math.sqrt(1+(Math.pow(dx,2) + Math.pow(dy,2)))*timeStep;
        }
        double v = 0;
        double distance = 0;
        double t = timeStep;
        //Acceleration IN REAL TIME UNITS
        while(v < maxVel && !instantaneousCruise) {
            double vi = v;
            v = v + maxAccel * timeStep;
            distance += (v-vi)*timeStep;
            if(distance > arcLength/2) {
                cruiseStart = t;
                cruiseEnd = t;
                instantaneousCruise = true;
            }
            t+=timeStep;
            totalTime += timeStep;
//            System.out.println("");
//            System.out.print(distance);
//            System.out.print("  " + v);
            if(v >= maxVel) {
                v = maxVel;
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
            while(v < maxVel) {
                double vi = v;
                v = v + maxAccel*timeStep;
                distance += (v-vi)*timeStep;
                if(v >= maxVel) {
                    cruiseTime = (arcLength - 2 * distance)/maxVel;
                    cruiseEnd = cruiseStart + cruiseTime;
                }
                t += timeStep;
                totalTime += timeStep;
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
}
