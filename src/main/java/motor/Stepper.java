import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

import java.util.Timer;
import java.util.TimerTask;

public class Stepper {
    private final double stepsRev = 1.8;
    //private GpioPinDigitalOutput stepPin;
    private GpioPinDigitalOutput dirPin;
    private long millisecondsPerStep = 200;
    Timer stepTimer;
    TimerTask pwm;

    public Stepper(Pin _stepPin, Pin _dirPin) {
        final GpioPinDigitalOutput stepPin = Global.gpio.provisionDigitalOutputPin(_stepPin, PinState.LOW);
        this.dirPin = Global.gpio.provisionDigitalOutputPin(_dirPin, PinState.LOW);
        stepPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        dirPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        stepTimer = new Timer("steptimer");
        pwm = new TimerTask() {
            @Override
            public void run() {
                stepPin.setState(!stepPin.getState().isHigh());
            }
        };
    }

    public void moveDegrees(int degrees, boolean dir) {
        for(int i = 0; i < degrees/stepsRev; i++) {
            pwm(dir);
        }
    }
    /*
    This needs to be changed to non-continuous stepping
     */
    public void pwm(boolean dir) {
        if(dir) {
            dirPin.setState(PinState.LOW);
        }
        else {
            dirPin.setState(PinState.HIGH);
        }
        stepTimer.cancel();
        stepTimer.purge();
        stepTimer.schedule(pwm, 0, millisecondsPerStep/2);
    }
}
