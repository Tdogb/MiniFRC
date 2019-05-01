package autonomous;

public class Spline {
    Vector p0, p1, t0, t1, a, b, c, d;
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

    public Spline() {
        p0 = new Vector(0,0);
        p1 = new Vector(2,4);
        t0 = new Vector(1,0);
        t1 = new Vector(1,0);
        generateSpline();
    }

    /**
     * Uses quotient rule to find derrivative of the slope y/slope x
     * @param _t
     * @return Angular velocity
     */
    public double deltaTheta(double _t) {
        double t = _t * tconversionFactor;
        double dtheta = ((3*a.x()*Math.pow(t,2)+2*b.x()*t+c.x())*(6*a.y()*t+2*b.y())-((3*a.y()*Math.pow(t,2)+2*b.y()*t+c.y()*1)*(6*a.x()*t+2*b.x())))/(Math.pow(3*a.x()*Math.pow(t,2)+2*b.x()*t+c.x(),2));
        return dtheta/timeStep;
    }

    /**
     * velocityProfile
     * @return Base velocity setpoint at time
     */
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

    public void generateVelocityProfile() {
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

    public void generateSpline() {
        //First run on x then on y
        double dx = p0.x(); //x0
        double dy = p0.y(); //y0
        double cx = t0.x();
        double cy = t0.y();
        double bx = -3*dx+3*p1.x()-2*cx-t1.x();
        double by = -3*dy+3*p1.y()-2*cy-t1.y();
        double ax = 2*dx-2*p1.x()+cx+t1.x();
        double ay = 2*dy-2*p1.y()+cy+t1.y();

        a = new Vector(ax,ay);
        b = new Vector(bx,by);
        c = new Vector(cx,cy);
        d = new Vector(dx,dy);
        generateVelocityProfile();
        debug();
        for(double time = timeStep; time <= pathTime; time+=timeStep) {
            double t = time * tconversionFactor;
            System.out.println("");
            System.out.print(time);
            System.out.print(",");
            System.out.print(a.x()*Math.pow(t,3)+b.x()*Math.pow(t,2)+c.x()*t+d.x());
            System.out.print(",");
            System.out.print(a.y()*Math.pow(t,3)+b.y()*Math.pow(t,2)+c.y()*t+d.y());
            System.out.print(",");
            System.out.print(velocityProfile(time));
            System.out.print(",");
            System.out.print(deltaTheta(time));
            System.out.print(",");
        }
        System.out.println("");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);

        System.out.println(cruiseStart);
        System.out.println(cruiseEnd);
        System.out.println(instantaneousCruise);
        System.out.println("Values ");
        System.out.println(a.x());
        System.out.println(b.x());
        System.out.println(c.x());
        System.out.println(d.x());
        System.out.println(a.y());
        System.out.println(b.y());
        System.out.println(c.y());
        System.out.println(d.y());
    }

    public void debug() {
        for (double time = timeStep; time <= pathTime; time += timeStep) {
            System.out.println(deltaTheta(time));
        }
    }
}
