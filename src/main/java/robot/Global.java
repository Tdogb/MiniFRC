package robot;

import com.pi4j.io.gpio.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Global {
    public final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    public final static GpioController gpio = GpioFactory.getInstance();
    public final static double timeStep = 0.1; //Timstep should be greater than the pwm freqency for brushed motors and steppers
    public final static double maxVel = 0.2; //m/s
    public final static double maxAccel = 0.1; //m/s^2
    public final static double robotWheelBase = 0.18; //Diameter
    public final static double robotWheelDiameter = 0.051;
}
