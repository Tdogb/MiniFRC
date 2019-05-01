package motor;

public class Stepper {
    private final double stepsRev = 1.8;
    private int stepPin;
    private int dirPin;

    public Stepper(int stepPin, int dirPin) {
        this.stepPin = stepPin;
        this.dirPin = dirPin;
    }

    public void moveDegrees(int degrees) {
        for(int i = 0; i < degrees/stepsRev; i++) {
            step();
        }
    }

    public void step() {

    }
}
