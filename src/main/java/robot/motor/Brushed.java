package robot.motor;

import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.*;

import java.util.Map;

public class Brushed {
    GpioPinDigitalOutput forwardPin;
    GpioPinDigitalOutput reversePin;
    MotorState currentMotorState;

    public Brushed(Pin fwdPin, Pin rvsPin) {
        forwardPin = robot.Global.gpio.provisionDigitalOutputPin(fwdPin, PinState.LOW);
        reversePin = robot.Global.gpio.provisionDigitalOutputPin(rvsPin, PinState.LOW);
        forwardPin.setShutdownOptions(true);
        reversePin.setShutdownOptions(true);
    }

    public void forward() {
        setState(MotorState.FORWARD);
    }

    public void reverse() {
        setState(MotorState.REVERSE);
    }

    public void stop() {
        setState(MotorState.STOP);
    }

    public MotorState getState() {
        return currentMotorState;
    }

    public void setState(MotorState motorState) {
        if(motorState.equals(MotorState.FORWARD)) {
            reversePin.low();
            forwardPin.high();
        }
        else if(motorState.equals(MotorState.REVERSE)) {
            forwardPin.low();
            reversePin.high();
        }
        else { //MotorState = stopped
            forwardPin.low();
            reversePin.low();
        }
        currentMotorState = motorState;
    }

    public boolean isState(MotorState motorState) {
        return motorState.equals(currentMotorState);
    }

    public boolean isStopped() {
        return currentMotorState.equals(MotorState.STOP);
    }
}
