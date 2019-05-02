import autonomous.MotionProfile;

public class Robot {
    Drivetrain d;
    MotionProfile mp;

    public Robot() {
        init();
        autoInit();
        autoPeriodic();
        teleopInit();
        teleopPeriodic();
    }

    public void init() {
        d = new Drivetrain();
        mp = new MotionProfile(PathPlan.getPlan());
    }
    public void autoInit() {
        d.followProfile(mp);
    }

    public void autoPeriodic() {

    }

    public void teleopInit() {

    }

    public void teleopPeriodic() {

    }
}
