package robot;

import robot.autonomous.MotionProfile;

import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

public class Robot {
    Drivetrain d;
    MotionProfile mp;
    Elevator elevator;

    public Robot() {
        init();
        autoInit();
        teleopInit();
    }

    public final void init() {
        d = new Drivetrain();
        mp = new MotionProfile(PathPlan.getPlan());
        elevator = new Elevator();
    }
    public final void autoInit() {
        d.followProfile(mp);
    }

    public final void autoPeriodic() {
        elevator.setHeight(1);
    }

    public final void teleopInit() {
        elevator.setHeight(1);
    }

    public final void teleopPeriodic() {

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
    final ScheduledFuture<?> autoInit = Global.scheduler.schedule(autoInitRunnable, 0, MILLISECONDS);
    final ScheduledFuture<?> autoPeriodic = Global.scheduler.scheduleAtFixedRate(autoPeriodicRunnable, 1, Global.updatePeriod, MILLISECONDS);
    final ScheduledFuture<?> teleopInit = Global.scheduler.schedule(teleopInitRunnable, 15000, MILLISECONDS);
    final ScheduledFuture<?> teleopPeriodic = Global.scheduler.scheduleAtFixedRate(teleopPeriodicRunnable, 15000, Global.updatePeriod, MILLISECONDS);
}
