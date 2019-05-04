package robot;

import robot.autonomous.*;
import robot.motor.*;
import robot.sensor.*;

public class Drivetrain {
    Brushed rMotor;
    Brushed lMotor;
    static Encoder rightEncoder;

    public Drivetrain() {
        rightEncoder = new Encoder(Global.encoderPinA, Global.encoderPinB);
    }

    public boolean followProfile(MotionProfile mp) {

        return true;
    }

}
