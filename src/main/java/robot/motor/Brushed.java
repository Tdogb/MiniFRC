package robot.motor;

import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.*;
import com.pi4j.wiringpi.SoftPwm;

import java.util.Map;

public class Brushed {
    GpioPinDigitalOutput forwardPin;
    GpioPinDigitalOutput reversePin;
    Pin fwdPin;
    Pin rvsPin;
    MotorState currentMotorState;

    public Brushed(Pin fwdPin, Pin rvsPin) {
        this.fwdPin = fwdPin;
        this.rvsPin = rvsPin;
        forwardPin = robot.Global.gpio.provisionDigitalOutputPin(fwdPin, PinState.LOW);
        reversePin = robot.Global.gpio.provisionDigitalOutputPin(rvsPin, PinState.LOW);
        forwardPin.setShutdownOptions(true);
        reversePin.setShutdownOptions(true);
        SoftPwm.softPwmCreate(fwdPin.getAddress(),0, 100);
        SoftPwm.softPwmCreate(rvsPin.getAddress(),0, 100);
    }

    public void rotate(double percentVoltage) {
        System.out.println(percentVoltage);
        if(percentVoltage == 0) {
            SoftPwm.softPwmStop(fwdPin.getAddress());
            SoftPwm.softPwmStop(rvsPin.getAddress());
        }
        else {
            SoftPwm.softPwmStop(percentVoltage <= 0 ? fwdPin.getAddress() : rvsPin.getAddress());
            SoftPwm.softPwmWrite((percentVoltage > 0 ? fwdPin.getAddress() : rvsPin.getAddress()), (int)(percentVoltage));
        }
    }

    public void stop() {
        rotate(0);
    }
}
