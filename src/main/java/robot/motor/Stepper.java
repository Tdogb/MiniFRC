package robot.motor;

import com.pi4j.io.gpio.*;
import robot.Global;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
//INPUTS
    /*
    Rotation:
        - Angle
        - Speed

        - Revolutions stepper per revolutions elevator
    Linear:
        - Height
        - Speed

        - Revolutions per mm
     */
public class Stepper implements Runnable {
    private Thread worker;
    private enum StepperState {
        OFF,
        ROTATION,
        LINEAR;
    }
    private AtomicInteger setpoint; //Degrees steps of 1
    private AtomicReference<StepperState> desiredStepperState;
    private AtomicReference<Double> speed;
    private AtomicBoolean direction;
    private final int leadscrewPitch = 5; //mm/rev
    private AtomicBoolean hasZeroPoint;

    //OUTPUTS
    private AtomicInteger absoluteScaledStepperPosition;
    private AtomicInteger absoluteRevolutions;
    private int currentHeading = 0;
    private double height = 0;
    private int stepsPerRev = 200;
    private GpioPinDigitalOutput stepPin;
    private GpioPinDigitalOutput dirPin;

    private int period = 5; //Technically half of the period

    public Stepper(Pin dir, Pin step, int stepsPerRev) {
        this.stepPin = Global.gpio.provisionDigitalOutputPin(step);
        this.dirPin = Global.gpio.provisionDigitalOutputPin(dir);
        this.stepsPerRev = stepsPerRev;
        absoluteScaledStepperPosition = new AtomicInteger(0);
        absoluteRevolutions = new AtomicInteger(0);
        desiredStepperState = new AtomicReference<StepperState>(StepperState.OFF);
        speed = new AtomicReference<Double>(new Double(0));
        direction = new AtomicBoolean(true);
        setpoint = new AtomicInteger(0);
        hasZeroPoint = new AtomicBoolean(false);
        worker = new Thread(this);
        worker.start();
    }

    public void setSpeed(double revolutionsPerSecond) {
        this.speed.set(revolutionsPerSecond);
    }

    private void setDirection(boolean forwards) {
        direction.set(forwards);
        if(forwards) {
            dirPin.high();
        }
        else {
            dirPin.low();
        }
    }

    private void setStepSetpoint(int scaledSetpoint) {
        this.setpoint.set(scaledSetpoint);
    }

    private void setDeltaAngleSetpoint(int setpoint) {
        this.setpoint.set((setpoint/360)*stepsPerRev);
    }

    public int getHeight() {
        return 0;
    }

    @Override
    public void run() {
        while(true) {
            if(desiredStepperState.get() != StepperState.OFF) {
                if(absoluteScaledStepperPosition.get() + 360*absoluteRevolutions.get() != setpoint.get()) {
                    System.out.println(absoluteScaledStepperPosition.get() + " " + setpoint.get());
                    stepPin.low();
                    try {
                        Thread.sleep(period);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stepPin.high();
                    try {
                        Thread.sleep(period);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (absoluteScaledStepperPosition.get() == 359 && direction.get()) {
                        absoluteScaledStepperPosition.set(0);
                        absoluteRevolutions.set(absoluteRevolutions.get() + 1);
                    } else if (absoluteScaledStepperPosition.get() == 0 && !direction.get()) {
                        if(hasZeroPoint.get()) {
                            if(!(absoluteRevolutions.get() == 0)) {
                               absoluteScaledStepperPosition.set(359);
                                absoluteRevolutions.set(absoluteRevolutions.get() - 1);
                            }
                            else {
                                System.err.println("Elevator already at 0");
                            }
                        }
                        else {
                            absoluteRevolutions.set(absoluteRevolutions.get() - 1);
                        }
                    }
                    else {
                        absoluteScaledStepperPosition.set(absoluteScaledStepperPosition.get() + (direction.get() ? 1 : -1));
                    }
                }
            }
        }
    }

    //ROTATION SPECIFIC
    public void setTurretAngle(int angleScaled) {
        hasZeroPoint.set(false);
        int angleDifference = absoluteScaledStepperPosition.get() - angleScaled;
        if(angleDifference <= 180) {
            setDirection(true);
            setDeltaAngleSetpoint(angleDifference);
        }
        else {
            setDirection(false);
            setDeltaAngleSetpoint((angleDifference-180));
        }
        desiredStepperState.set(StepperState.ROTATION);
    }

    //LINEAR SPECIFIC
    public void setElevatorHeight(int mm, boolean positive) {
        hasZeroPoint.set(true);
        double revolutions = leadscrewPitch/mm;
        setDirection(positive);
        setStepSetpoint((int)(revolutions*(double)stepsPerRev));
        System.out.println("Setpoint: " + (int)(revolutions*(double)stepsPerRev));
        desiredStepperState.set(StepperState.LINEAR);
    }
}