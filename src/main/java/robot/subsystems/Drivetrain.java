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
        int throttle = -(int)(100*(((double)Global.controller.getAnalogStickLeftY())/128));
        int turn = -(int)(100*(((double)Global.controller.getAnalogStickRightX())/128));
        //Max magnitude is 0, no wheel reverse while turning
//        System.out.println("Turn " + turn);
        int throttleSign = Integer.signum(throttle);
        if(throttle < 10) {
            lMotor.rotate(Math.min(Math.max(throttle-turn, -100),100));
            rMotor.rotate(-Math.min(Math.max(throttle+turn, -100),100));
            System.out.println("Left: " + Math.min(Math.max(throttle-turn, -100),100) + " Right: " + Math.min(Math.max(throttle+turn, -100),100));
        }
        else {
            if(turn >= 0) {
                lMotor.rotate(Math.min(Math.max(throttleSign*(Math.abs(throttle)-Math.abs(turn)), 0),100));
                rMotor.rotate(-Math.min(Math.max(throttle, -100),100));
                System.out.println( "Left: " + (throttle-Math.abs(turn)) + " Right: " + (throttle));

            }
            else {
                lMotor.rotate(Math.min(Math.max(throttle, -100),100));
                rMotor.rotate(-Math.min(Math.max(throttleSign*(Math.abs(throttle) - Math.abs(turn)), 0),100));
                System.out.println( "Left: " + (throttle) + " Right: " + (throttle-Math.abs(turn)));

            }
        }
    }

    public boolean followProfile(MotionProfile mp) {
        return true;
    }

}
