package robot.motor;

import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.*;
import com.pi4j.wiringpi.Gpio;
import robot.util.PWMTask;

public class Brushed {
    GpioPinDigitalOutput forwardPin;
    GpioPinDigitalOutput reversePin;
    Pin fwdPin;
    Pin rvsPin;
    MotorState currentMotorState;
    PWMTask pwmForwards;
    PWMTask pwmReverse;
    public Brushed(Pin fwdPin, Pin rvsPin) {
        Gpio.wiringPiSetup();
        this.fwdPin = fwdPin;
        this.rvsPin = rvsPin;
        //forwardPin = robot.Global.gpio.provisionDigitalOutputPin(fwdPin, PinState.LOW);
        //reversePin = robot.Global.gpio.provisionDigitalOutputPin(rvsPin, PinState.LOW);
        //forwardPin.setShutdownOptions(true);
        //reversePin.setShutdownOptions(true);
        pwmForwards = new PWMTask(fwdPin);
        pwmReverse = new PWMTask(rvsPin);
        //SoftPwm.softPwmCreate(fwdPin.getAddress(),0, 100);
        //SoftPwm.softPwmCreate(rvsPin.getAddress(),0, 100);
    }

    public void rotate(double percentVoltage) {
        if(percentVoltage == 0) {
            pwmForwards.setEnabled(false);
            pwmReverse.setEnabled(false);
        }
        else if(percentVoltage > 0){
            if(percentVoltage > 100) {
                percentVoltage = 100;
                System.err.print("Motor value over 100");
            }
            pwmReverse.setEnabled(false);
            pwmForwards.setEnabled(true);
            pwmForwards.setDutyCycle(Math.abs(percentVoltage));
        }
        else {
            if(percentVoltage < -100) {
                percentVoltage = -100;
                System.err.print("Motor value under -100");
            }
            pwmForwards.setEnabled(false);
            pwmReverse.setEnabled(true);
            pwmReverse.setDutyCycle(Math.abs(percentVoltage));
        }
    }

//    public void rotate(double percentVoltage) {
//        //System.out.println(percentVoltage);
//        System.out.println(percentVoltage <= 0 ? fwdPin.getAddress() : rvsPin.getAddress());
//        if(percentVoltage == 0) {
//            SoftPwm.softPwmStop(fwdPin.getAddress());
//            SoftPwm.softPwmStop(rvsPin.getAddress());
//        }
//        else {
//            SoftPwm.softPwmStop(percentVoltage <= 0 ? fwdPin.getAddress() : rvsPin.getAddress());
//            SoftPwm.softPwmWrite((percentVoltage > 0 ? fwdPin.getAddress() : rvsPin.getAddress()), (int)(Math.abs(percentVoltage)));
//        }
//    }

//    public void stop() {
//        rotate(0);
//    }
}
