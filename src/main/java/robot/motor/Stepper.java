package robot.motor;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.*;
import com.pi4j.wiringpi.SoftPwm;
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
        motor.setStepsPerRevolution((int) (gearRatio * 1.8));
    }

    public void moveToHeading(int headingSetpoint) {
        for (int i = 0; i < Math.abs(currentHeading - headingSetpoint); i++) {
            motor.rotate(1 / 360);
        }
    }
}