package robot;

import robot.autonomous.MotionProfile;
import robot.subsystems.Drivetrain;
import robot.subsystems.Elevator;

public class Robot {
    Drivetrain drivetrain;
    MotionProfile mp;
    Elevator elevator;
    InputController controller;

    public Robot() {
        drivetrain = new Drivetrain();
        mp = new MotionProfile(PathPlan.getPlan());
        elevator = new Elevator();
        controller = new InputController();
        init();
        autoInit();
        teleopInit();
    }

    public final void init() {
        //TODO debug
        autoPeriodic();
    }
    public final void autoInit() {
        drivetrain.followProfile(mp);
    }

    public final void autoPeriodic() {
        while(true) {
            elevator.setHeight(1);
            drivetrain.drive();
        }
    }

    public final void teleopInit() {
    }

    public final void teleopPeriodic() {
        //System.out.println(d.rightEncoder.getVelocity());
        elevator.setHeight(1);

    }

    final Runnable autoInitRunnable = new Runnable() {
        public void run() {
            try {
                autoInit();
            }
            catch (Exception e) {

            }
        }
    };
    final Runnable autoPeriodicRunnable = new Runnable() {
        public void run() {
            try {
                autoPeriodic();
            }
            catch (Exception e) {
                e.printStackTrace();
                //System.out.println("catch");
            }
        }
    };
    final Runnable teleopInitRunnable = new Runnable() {
        public void run() {
            try {
                teleopInit();
            }
            catch (Exception e) {

            }
        }
    };
    final Runnable teleopPeriodicRunnable = new Runnable() {
        public void run() {
            try {
                teleopPeriodic();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    //final ScheduledFuture<?> autoInit = Global.scheduler.schedule(autoInitRunnable, 0, MILLISECONDS);
    //final ScheduledFuture<?> autoPeriodic = Global.scheduler.scheduleAtFixedRate(autoPeriodicRunnable, 1, Global.updatePeriod, MILLISECONDS);
    //final ScheduledFuture<?> teleopInit = Global.scheduler.schedule(teleopInitRunnable, 15000, MILLISECONDS);
    //final ScheduledFuture<?> teleopPeriodic = Global.scheduler.scheduleAtFixedRate(teleopPeriodicRunnable, 15000, Global.updatePeriod, MILLISECONDS);
}
