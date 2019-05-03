package robot.motor;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.*;
import robot.Global;

import java.util.Timer;
import java.util.TimerTask;

public class Stepper {
    GpioStepperMotorComponent motor;
    int currentHeading = 0;

    public Stepper(Pin dir, Pin step, int gearRatio) {
        final GpioPinDigitalOutput[] pins = {
                Global.gpio.provisionDigitalOutputPin(dir, PinState.LOW),
                Global.gpio.provisionDigitalOutputPin(step, PinState.LOW)
        };
        Global.gpio.setShutdownOptions(true, PinState.LOW, pins);
        motor = new GpioStepperMotorComponent(pins);
        motor.setStepsPerRevolution((int)(gearRatio * 1.8));
    }

    public void moveToHeading(int headingSetpoint) {
        for(int i = 0; i < Math.abs(currentHeading-headingSetpoint); i++) {
            motor.rotate(1/360);
        }
    }

//    private final double stepsRev = 1.8;
//    //private GpioPinDigitalOutput stepPin;
//    private GpioPinDigitalOutput dirPin;
//    private long millisecondsPerStep = 200;
//    Timer stepTimer;
//    TimerTask pwm;
//
//    public Stepper(Pin _stepPin, Pin _dirPin) {
//        StepperMotor s = new St
//
//        final GpioPinDigitalOutput stepPin = robot.Global.gpio.provisionDigitalOutputPin(_stepPin, PinState.LOW);
//        this.dirPin = robot.Global.gpio.provisionDigitalOutputPin(_dirPin, PinState.LOW);
//        stepPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
//        dirPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
//        stepTimer = new Timer("steptimer");
//        pwm = new TimerTask() {
//            @Override
//            public void run() {
//                stepPin.setState(!stepPin.getState().isHigh());
//            }
//        };
//    }
//
//    public void moveDegrees(int degrees, boolean dir, double acceleration) {
//        for(int i = 0; i < degrees/stepsRev; i++) {
//            pwm(dir); //One step
//        }
//    }
//    /*
//    This needs to be changed to non-continuous stepping
//     */
//    public void pwm(boolean dir) {
//        if(dir) {
//            dirPin.setState(PinState.LOW);
//        }
//        else {
//            dirPin.setState(PinState.HIGH);
//        }
//        stepTimer.cancel();
//        stepTimer.purge();
//        stepTimer.schedule(pwm, 0, millisecondsPerStep/2);
//    }

}
