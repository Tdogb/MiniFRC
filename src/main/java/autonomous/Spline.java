package autonomous;

public class Spline {
    Vector p0, p1, t0, t1, a, b, c, d;

    public double arcLength = 0;

    public Spline(Waypoint start, Waypoint end) {
//        this.p0 = new Vector(0,0);
//        this.p1 = new Vector(2,4);
//        this.t0 = new Vector(1,0);
//        this.t1 = new Vector(1,0);
        this.p0 = start.getPosition();
        this.p1 = end.getPosition();
        this.t0 = start.getTangent();
        this.t1 = end.getTangent();
        generateSpline();
    }

    private void generateSpline() {
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

        computeArcLength();
    }

    /**
     * Uses quotient rule to find derrivative of the slope y/slope x
     * @param _t Converted t
     * @return Angular velocity
     */
    public double deltaTheta(double t) {
        double dtheta = ((3*a.x()*Math.pow(t,2)+2*b.x()*t+c.x())*(6*a.y()*t+2*b.y())-((3*a.y()*Math.pow(t,2)+2*b.y()*t+c.y()*1)*(6*a.x()*t+2*b.x())))/(Math.pow(3*a.x()*Math.pow(t,2)+2*b.x()*t+c.x(),2));
        return dtheta/GlobalVars.timeStep;
    }

    private void computeArcLength() {
        double totalTime = 0;
        for(double t = 0; t <=1; t+=GlobalVars.timeStep) { //These are also in terms of this "fake" time (proportional to total time)
            double dx = 3*a.x()*Math.pow(t,2)+2*b.x()*t+c.x();
            double dy = 3*a.y()*Math.pow(t,2)+2*b.y()*t+c.y();
            arcLength += Math.sqrt(1+(Math.pow(dx,2) + Math.pow(dy,2)))*GlobalVars.timeStep;
        }
    }

    public double getArcLength() {
        return arcLength;
    }

    public void debug(double tconversionFactor, double pathTime) {
        for(double time = GlobalVars.timeStep; time <= pathTime; time+=GlobalVars.timeStep) {
            double t = time * tconversionFactor;
            System.out.println("");
            System.out.print(time);
            System.out.print(",");
            System.out.print(a.x()*Math.pow(t,3)+b.x()*Math.pow(t,2)+c.x()*t+d.x());
            System.out.print(",");
            System.out.print(a.y()*Math.pow(t,3)+b.y()*Math.pow(t,2)+c.y()*t+d.y());
            System.out.print(",");
            System.out.print(",");
            System.out.print(deltaTheta(time));
            System.out.print(",");
        }
//        System.out.println("");
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
//        System.out.println(d);
//
//        System.out.println("Values ");
//        System.out.println(a.x());
//        System.out.println(b.x());
//        System.out.println(c.x());
//        System.out.println(d.x());
//        System.out.println(a.y());
//        System.out.println(b.y());
//        System.out.println(c.y());
//        System.out.println(d.y());
//        for (double time = GlobalVars.timeStep; time <= pathTime; time += GlobalVars.timeStep) {
//            System.out.println(deltaTheta(time));
//        }
    }
}
