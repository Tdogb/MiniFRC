package robot.sensor;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import robot.Global;
import java.util.concurrent.*;


import java.util.Timer;
import java.util.TimerTask;
import java.lang.*;

public class Encoder {
    final long samplePeriod = 2;

    GpioPinDigitalInput A;
    GpioPinDigitalInput B;
    Timer encoderTimer ;
    boolean aPreviousPinState = false;
    boolean bPreviousPinState = false;
    boolean aCurrentPinState = false;
    boolean bCurrentPinState = false;

    double velocity = 0;
    long previousTime = 0;
    long encoderTickCount = 0;

    public Encoder(Pin pinA, Pin pinB) {
        System.out.println("Encoder initialized");
        encoderTimer = new Timer("encodertimer");
        A = Global.gpio.provisionDigitalInputPin(pinA, PinPullResistance.OFF);
        A.setShutdownOptions(true);
        A.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                //System.out.println("A tick");
                aCurrentPinState = !aCurrentPinState;
                velocity =
            }
        });
        B = Global.gpio.provisionDigitalInputPin(pinB, PinPullResistance.OFF);
        B.setShutdownOptions(true);
        B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                //System.out.println("B tick");
            }
        });
        TimerTask encoderReadTask = new TimerTask() {
            @Override
            public void run() {
                if(aCurrentPinState != aPreviousPinState) {
                    long currentTimeMillis = System.currentTimeMillis();
                    velocity = (double)((currentTimeMillis - previousTime)/samplePeriod)/1000;
                    previousTime = currentTimeMillis;
                }
            }
        };
        encoderTimer.schedule(encoderReadTask, 0, samplePeriod, TimeUnit.MICROSECONDS);
    }
    public double getVelocity() {
        return velocity;
    }
}
