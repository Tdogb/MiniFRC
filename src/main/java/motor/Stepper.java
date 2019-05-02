import com.pi4j.io.gpio.*;

public class Stepper {
    private final double stepsRev = 1.8;
    private GpioPinDigitalOutput stepPin;
    private GpioPinDigitalOutput dirPin;

    public Stepper(Pin stepPin, Pin dirPin) {
        this.stepPin = Global.gpio.provisionDigitalOutputPin(stepPin, PinState.LOW);
        this.dirPin = Global.gpio.provisionDigitalOutputPin(dirPin, PinState.LOW);
        this.stepPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        this.dirPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    }

    public void moveDegrees(int degrees, boolean dir) {
        for(int i = 0; i < degrees/stepsRev; i++) {
            step(dir);
        }
    }

    public void step(boolean dir) {
        if(dir) {

        }
        else {

        }
    }
}
