package robot.util;

public class InputController {
    boolean OButton;
    boolean XButton;
    boolean TriangleButton;
    boolean SquareButton;
    byte analogStickLeftX;
    byte analogStickLeftY;
    byte analogStickRightY;
    byte analogStickRightX;
    int dPadState; //off=0,up=1, down=2, right=3, left=4
    boolean foundController = false;
    SerialRecieve serial;

    public InputController() {
    }

    public void setOButton(boolean OButton) {
        this.OButton = OButton;
    }

    public void setXButton(boolean XButton) {
        this.XButton = XButton;
    }

    public void setTriangleButton(boolean triangleButton) {
        TriangleButton = triangleButton;
    }

    public void setSquareButton(boolean squareButton) {
        SquareButton = squareButton;
    }

    public void setAnalogStickLeftX(byte analogStickLeftX) {
        this.analogStickLeftX = analogStickLeftX;
    }

    public void setAnalogStickLeftY(byte analogStickLeftY) {
        this.analogStickLeftY = analogStickLeftY;
    }

    public void setAnalogStickRightY(byte analogStickRightY) {
        this.analogStickRightY = analogStickRightY;
    }

    public void setAnalogStickRightX(byte analogStickRightX) {
        this.analogStickRightX = analogStickRightX;
    }

    public void setdPadState(int dPadState) {
        this.dPadState = dPadState;
    }

    public void setFoundController(boolean foundController) {
        this.foundController = foundController;
    }

    public boolean getOButton() {
        return OButton;
    }

    public boolean getXButton() {
        return XButton;
    }

    public boolean getTriangleButton() {
        return TriangleButton;
    }

    public boolean getSquareButton() {
        return SquareButton;
    }

    public byte getAnalogStickLeftX() {
        return analogStickLeftX;
    }

    public byte getAnalogStickLeftY() {
        return analogStickLeftY;
    }

    public byte getAnalogStickRightY() {
        return analogStickRightY;
    }

    public byte getAnalogStickRightX() {
        return analogStickRightX;
    }

    public int getdPadState() {
        return dPadState;
    }
}