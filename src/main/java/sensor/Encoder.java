import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.*;

public class Encoder {
    final long samplePeriod = 1;

    GpioPinDigitalInput A;
    GpioPinDigitalInput B;
    Timer encoderTimer ;
    boolean aPreviousPinState = false;
    boolean bPreviousPinState = false;
    boolean aCurrentPinState = false;
    boolean bCurrentPinState = false;

    double velocity = 0;
    long previousTime = 0;

    public Encoder(Pin pinA, Pin pinB) {
        encoderTimer = new Timer("encodertimer");
        A = Global.gpio.provisionDigitalInputPin(pinA, PinPullResistance.OFF);
        A.setShutdownOptions(true);
        A.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                System.out.println("A tick");
                aCurrentPinState = !aCurrentPinState;
            }
        });
        B = Global.gpio.provisionDigitalInputPin(pinB, PinPullResistance.OFF);
        B.setShutdownOptions(true);
        B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                System.out.println("B tick");
            }
        });
        TimerTask encoderReadTask = new TimerTask() {
            @Override
            public void run() {
                if(aCurrentPinState != aPreviousPinState) {
                    velocity = (double)((System.currentTimeMillis() - previousTime)/samplePeriod)/1000;
                }
            }
        };
        encoderTimer.schedule(encoderReadTask, 0, samplePeriod);
    }
}
