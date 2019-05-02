package autonomous;

public class AutonomousVars {
    final static double timeStep = 0.1; //Timstep should be greater than the pwm freqency for brushed motors and steppers
    final static double maxVel = 0.2; //m/s
    final static double maxAccel = 0.1; //m/s^2
    final static double robotWheelBase = 0.18; //Diameter
    final static double robotWheelDiameter = 0.051;
}