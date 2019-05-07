package robot;

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

//package robot;
//
//import net.java.games.input.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class InputController {
//    Event event;
//    Component component;
//    EventQueue eventQueue;
//    Controller gamepad;
//    boolean OButton;
//    boolean XButton;
//    boolean TriangleButton;
//    boolean SquareButton;
//    float analogStickLeftX;
//    float analogStickLeftY;
//    float analogStickRightY;
//    float analogStickRightX;
//    int dPadState; //off=0,up=1, down=2, right=3, left=4
//    boolean foundController = false;
//
//    public InputController() {
//        List<Controller> gamepads = Arrays.stream(ControllerEnvironment.getDefaultEnvironment().getControllers()).filter(controller -> controller.getType().equals(Controller.Type.GAMEPAD)).collect(Collectors.toList());
//        gamepad = gamepads.get(0);
//        System.out.println(gamepad.getName());
//        foundController = !gamepad.equals(null);
//        eventQueue = gamepad.getEventQueue();
//        event = new Event();
//    }
//
//    public void updateController() {
//        if(foundController) {
//            OButton = false;
//            XButton = false;
//            TriangleButton = false;
//            SquareButton = false;
//            analogStickRightY = 0;
//            analogStickLeftX = 0;
//            analogStickLeftY = 0;
//            analogStickRightX = 0;
//            dPadState = 0;
//            gamepad.poll();
//            while (eventQueue.getNextEvent(event)) {
//                component = event.getComponent();
//                String componentName = component.getIdentifier().getName();
//                float value = event.getValue();
//
//                if (component.isAnalog()) {
//                    switch (componentName) {
//                        case "x":
//                            // left stick - RIGHT
//                            analogStickLeftX = value;
//                            break;
//                        case "y":
//                            // left stick - DOWN
//                            analogStickLeftY = value;
//                            break;
//                        case "rx":
//                            // right stick - RIGHT
//                            analogStickLeftX = value;
//
//                            break;
//                        case "ry":
//                            // right stick - DOWN
//                            analogStickRightY = value;
//                            break;
//                    }
//                }
//                else {
//                    if(value == 1) {
//                        switch (componentName) {
//                            case "0":
//                                // A-Button
//                                XButton = true;
//                                break;
//                            case "1":
//                                // B-Button
//                                OButton = true;
//                                break;
//                            case "2":
//                                // X-Button
//                                TriangleButton = true;
//                                break;
//                            case "3":
//                                // Y-Button
//                                SquareButton = true;
//                                break;
//                            case "4":
//                                // LB-Button
//
//                                break;
//                            case "5":
//                                // RB-Button
//
//                                break;
//                            case "6":
//                                // Back-Button
//
//                                break;
//                            case "7":
//                                // Start-Button
//
//                                break;
//                            case "8":
//                                // Left Stick Push
//
//                                break;
//                            case "9":
//                                // Right Stick Push
//                            case "pov":
//                                dPadState = 4;
//                                break;
//                        }
//                    }
//                    else if(value == 0.25) {
//                        //dpad up
//                        dPadState = 1;
//                    }
//                    else if(value == 0.75) {
//                        //dpad down
//                        dPadState = 2;
//                    }
//                    else if(value == 0.5) {
//                        //dpad right
//                        dPadState = 3;
//                    }
//                }
//            }
//        }
//        else {
//            System.out.println("No controller found");
//        }
//    }
//
//    public boolean isOButton() {
//        return OButton;
//    }
//
//    public boolean isXButton() {
//        return XButton;
//    }
//
//    public boolean isTriangleButton() {
//        return TriangleButton;
//    }
//
//    public boolean isSquareButton() {
//        return SquareButton;
//    }
//
//    public float getAnalogStickLeftX() {
//        return analogStickLeftX;
//    }
//
//    public float getAnalogStickLeftY() {
//        return analogStickLeftY;
//    }
//
//    public float getAnalogStickRightY() {
//        return analogStickRightY;
//    }
//
//    public float getAnalogStickRightX() {
//        return analogStickRightX;
//    }
//
//    public int getdPadState() {
//        return dPadState;
//    }
//}
