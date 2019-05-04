package robot;

import robot.motor.EasyDriver;
import robot.motor.Stepper;

public class Elevator {
    double height;
    double heading;
    EasyDriver rotationStepper;
    EasyDriver screwStepper;

    public Elevator() {
        rotationStepper = new EasyDriver(Global.stepperStepPin1, Global.stepperDirPin1);
        screwStepper = new EasyDriver(Global.stepperStepPin2, Global.stepperDirPin2);
        //TODO debug
        System.out.println("Set height");
        setHeight(1);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        //TODO debug
        try {
            rotationStepper.move(1,100);

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.height = height;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }
}
