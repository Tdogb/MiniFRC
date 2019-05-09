package robot.motor;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import com.pi4j.component.motor.*;
import com.pi4j.wiringpi.SoftPwm;
import robot.Global;
import robot.util.PWMTask;

import java.util.Timer;
import java.util.TimerTask;

public class Stepper {
    private GpioStepperMotorComponent motor;
    private int currentHeading = 0;
    private double height = 0;
    private PWMTask dirPWM;
    private PWMTask stepPWM;
    private int stepsPerRev = 200;

    public Stepper(Pin dir, Pin step, int stepsPerRev) {
        this.stepsPerRev = stepsPerRev;
        dirPWM = new PWMTask(dir);
        stepPWM = new PWMTask(step);
        dirPWM.setUseMilliseconds(true);
        stepPWM.setUseMilliseconds(true);
    }

    public void step(int steps, double revPerSecond) { //rev/sec
        stepPWM.setEnabled(true);
//        stepPWM.setPeriodMilliseconds();
    }
}