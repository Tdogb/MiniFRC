package robot;

import robot.autonomous.MotionProfile;

import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

public class Robot {
    //Drivetrain d;
    //MotionProfile mp;
    //Elevator elevator;

    public Robot() {
        init();
        autoInit();
        teleopInit();
    }

    public final void init() {
        //d = new Drivetrain();
        //mp = new MotionProfile(PathPlan.getPlan());
        //elevator = new Elevator();
    }
    public final static void autoInit() {
        Drivetrain.followProfile(new MotionProfile(PathPlan.getPlan()));
    }

    public final static void autoPeriodic() {
        System.out.println(Drivetrain.rightEncoder.getVelocity());
    }

    public final static void teleopInit() {
    }

    public final static void teleopPeriodic() {
        System.out.println(Drivetrain.rightEncoder.getVelocity());

    }

    final static Runnable autoInitRunnable = new Runnable() {
        public void run() {
            try {
                autoInit();
            }
            catch (Exception e) {

            }
        }
    };
    final static Runnable autoPeriodicRunnable = new Runnable() {
        public void run() {
            try {
                autoPeriodic();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    final static Runnable teleopInitRunnable = new Runnable() {
        public void run() {
            try {
                teleopInit();
            }
            catch (Exception e) {

            }
        }
    };
    final static Runnable teleopPeriodicRunnable = new Runnable() {
        public void run() {
            try {
                teleopPeriodic();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    final static ScheduledFuture<?> autoInit = Global.scheduler.schedule(autoInitRunnable, 0, MILLISECONDS);
    final static ScheduledFuture<?> autoPeriodic = Global.scheduler.scheduleAtFixedRate(autoPeriodicRunnable, 1, Global.updatePeriod, MILLISECONDS);
    final static ScheduledFuture<?> teleopInit = Global.scheduler.schedule(teleopInitRunnable, 15000, MILLISECONDS);
    final static ScheduledFuture<?> teleopPeriodic = Global.scheduler.scheduleAtFixedRate(teleopPeriodicRunnable, 15000, Global.updatePeriod, MILLISECONDS);
}
