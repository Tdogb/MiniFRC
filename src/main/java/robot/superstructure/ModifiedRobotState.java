package robot.superstructure;

public class ModifiedRobotState {
    private int elevatorHeightStateChange;
    private int ballIntakeThetaStateChange;
    private int hatchPanelThetaStateChange;

    public int getElevatorHeightStateChange() {
        return elevatorHeightStateChange;
    }

    public void setElevatorHeightStateChange(int elevatorHeightStateChange) {
        this.elevatorHeightStateChange = elevatorHeightStateChange;
    }

    public int getBallIntakeThetaStateChange() {
        return ballIntakeThetaStateChange;
    }

    public void setBallIntakeThetaStateChange(int ballIntakeThetaStateChange) {
        this.ballIntakeThetaStateChange = ballIntakeThetaStateChange;
    }

    public int getHatchPanelThetaStateChange() {
        return hatchPanelThetaStateChange;
    }

    public void setHatchPanelThetaStateChange(int hatchPanelThetaStateChange) {
        this.hatchPanelThetaStateChange = hatchPanelThetaStateChange;
    }
}
