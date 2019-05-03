package robot;

import robot.motor.EasyDriver;
import robot.motor.Stepper;

public class Elevator {
    double height;
    double heading;
    EasyDriver rotationStepper;
    EasyDriver screwStepper;

    public Elevator() {
        rotationStepper = new EasyDriver(0, Global.stepperStepPin1, Global.stepperDirPin1, null,null,null,null, null);
        screwStepper = new EasyDriver(0, Global.stepperStepPin2, Global.stepperDirPin2, null,null,null,null, null);
        //TODO debug
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
