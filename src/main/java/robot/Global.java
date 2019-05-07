package robot;

import com.pi4j.io.gpio.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Global {
    public final static int updatePeriod = 10;
    public final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    public final static GpioController gpio = GpioFactory.getInstance();
    public final static double timeStep = 0.1; //Timstep should be greater than the pwm freqency for brushed motors and steppers
    public final static double maxVel = 0.2; //m/s
    public final static double maxAccel = 0.1; //m/s^2
    public final static double robotWheelBase = 0.18; //Diameter
    public final static double robotWheelDiameter = 0.051;

    public final static Pin encoderPinA = RaspiPin.GPIO_01;
    public final static Pin encoderPinB = RaspiPin.GPIO_25;

    public final static Pin stepperDirPin1 = RaspiPin.GPIO_09;
    public final static Pin stepperStepPin1 = RaspiPin.GPIO_08;

    public final static Pin stepperDirPin2 = RaspiPin.GPIO_04;
    public final static Pin stepperStepPin2 = RaspiPin.GPIO_06;

    public final static Pin leftMotorForwardsPin = RaspiPin.GPIO_07;
    public final static Pin leftMotorReversePin = RaspiPin.GPIO_00;
    public final static Pin rightMotorForwardsPin = RaspiPin.GPIO_02;
    public final static Pin rightMotorReversePin = RaspiPin.GPIO_03;

    public static SerialRecieve serial;
    public static InputController controller;
}
