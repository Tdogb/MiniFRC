package robot.subsystems;

import robot.Global;
import robot.motor.EasyDriver;
import robot.motor.Stepper;

public class Elevator {
    enum ElevatorGamepeice {
        REST,
        CARGO,
        HATCH;
    }
    enum ElevatorLevel {
        CARGOSHIP,
        ROCKET_1,
        ROCKET_2,
        ROCKET_3;
    }
    private int height;
    private double heading;
    private Stepper rotationStepper;
    private Stepper screwStepper;
    private final double gearRatio = 20/52;
    //TODO debug
    private int previousButtonState = 0;
    public Elevator() {
        rotationStepper = new Stepper(Global.stepperStepPin2, Global.stepperDirPin2, (int)(200*gearRatio));
        screwStepper = new Stepper(Global.stepperStepPin1, Global.stepperDirPin1, 200);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        //System.out.println(previousButtonState);
        if(previousButtonState > 20000 && Global.controller.getTriangleButton()) {
            screwStepper.setElevatorHeight(2, Math.signum(height) > 0);
            System.out.println("Elevator + ");
            previousButtonState = 0;
        }
        else if(previousButtonState > 20000 && Global.controller.getXButton()) {
            screwStepper.setElevatorHeight(4, false);
            System.out.println("Elevator -");
            previousButtonState = 0;
        }
        else {
            previousButtonState++;
        }
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }
}
