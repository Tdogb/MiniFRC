package robot.subsystems;

import robot.Global;
import robot.autonomous.*;
import robot.motor.*;
import robot.sensor.*;

public class Drivetrain {
    Brushed rMotor;
    Brushed lMotor;
    Encoder rightEncoder;

    public Drivetrain() {
        rightEncoder = new Encoder(Global.encoderPinA, Global.encoderPinB);
        rMotor = new Brushed(Global.rightMotorForwardsPin, Global.rightMotorReversePin);
        lMotor = new Brushed(Global.leftMotorForwardsPin, Global.leftMotorReversePin);
    }

    public void drive() {
        //System.out.println(Global.controller.getAnalogStickLeftY());
        int throttle = (int)(100*(((double)Global.controller.getAnalogStickLeftY())/128));
        int turn = (int)(100*(((double)Global.controller.getAnalogStickRightX())/128));
        //System.out.println("Throttle: " + throttle);
        //System.out.println("Turn " + turn);
        rMotor.rotate(Math.min(Math.max(throttle+turn, -100),100));
        lMotor.rotate(Math.min(Math.max(throttle-turn, -100),100));
    }

    public boolean followProfile(MotionProfile mp) {
        return true;
    }

}
