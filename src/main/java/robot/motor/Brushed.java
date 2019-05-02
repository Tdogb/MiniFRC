package robot.motor;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

public class Brushed {
    GpioPinDigitalOutput forwardPin;
    GpioPinDigitalOutput reversePin;

    public Brushed(Pin fwdPin, Pin rvsPin) {
        forwardPin = robot.Global.gpio.provisionDigitalOutputPin(fwdPin, PinState.LOW);
        reversePin = robot.Global.gpio.provisionDigitalOutputPin(rvsPin, PinState.LOW);
        forwardPin.setShutdownOptions(true);
        reversePin.setShutdownOptions(true);

    }

    public void drive(boolean dir, int voltage) {

    }
}
