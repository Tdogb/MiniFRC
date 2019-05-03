package robot;

import robot.autonomous.MotionProfile;

import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

public class Robot {
    Drivetrain d;
    MotionProfile mp;
    //Elevator elevator;

    public Robot() {
        init();
        autoInit();
        teleopInit();
    }

    public final void init() {
        d = new Drivetrain();
        mp = new MotionProfile(PathPlan.getPlan());
        //elevator = new Elevator();
    }
    public final void autoInit() {
        d.followProfile(mp);
    }

    public final void autoPeriodic() {

    }

    public final void teleopInit() {

    }

    public final void teleopPeriodic() {

    }

    final Runnable autoInitRunnable = new Runnable() {
        public void run() {
            autoInit();
        }
    };
    final Runnable autoPeriodicRunnable = new Runnable() {
        public void run() {
            autoPeriodic();
        }
    };
    final Runnable teleopInitRunnable = new Runnable() {
        public void run() {
            teleopInit();
        }
    };
    final Runnable teleopPeriodicRunnable = new Runnable() {
        public void run() {
            teleopPeriodic();
        }
    };
    final ScheduledFuture<?> autoInit = Global.scheduler.schedule(autoInitRunnable, 0, MILLISECONDS);
    final ScheduledFuture<?> autoPeriodic = Global.scheduler.scheduleAtFixedRate(autoPeriodicRunnable, 1, 50, MILLISECONDS);
    final ScheduledFuture<?> teleopInit = Global.scheduler.schedule(teleopInitRunnable, 15000, MILLISECONDS);
    final ScheduledFuture<?> teleopPeriodic = Global.scheduler.scheduleAtFixedRate(teleopPeriodicRunnable, 15000, 50, MILLISECONDS);
}
