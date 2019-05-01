package autonomous;

public class Waypoint {
    Vector position;
    Vector tangent;

    public Waypoint(Vector position, Vector tangent) {
        this.position = position;
        this.tangent = tangent;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getTangent() {
        return tangent;
    }
}
