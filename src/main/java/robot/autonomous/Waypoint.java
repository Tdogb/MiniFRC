package robot.autonomous;

public class Waypoint {
    private Vector position;
    private Vector tangent;

    public Waypoint(Vector position, Vector tangent) {
        this.position = position;
        this.tangent = tangent;
    }

    public Waypoint(double x, double y, double tx, double ty) {
        position = new Vector(x,y);
        tangent = new Vector(tx, ty);
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getTangent() {
        return tangent;
    }
}
