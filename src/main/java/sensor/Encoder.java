package sensor;

public class Encoder {
    int pinA;
    int pinB;
    public Encoder(int pinA, int pinB) {
        this.pinA = pinA;
        this.pinB = pinB;
    }
    public double getVelocity() {
        return 0;
    }
}
