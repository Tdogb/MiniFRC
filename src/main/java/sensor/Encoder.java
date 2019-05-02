import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Encoder {
    int pinA;
    int pinB;

    public Encoder(int pinA, int pinB) {
        this.pinA = pinA;
        this.pinB = pinB;
        final GpioPinDigitalInput A = Global.gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.OFF);
        A.setShutdownOptions(true);
        A.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                System.out.println("A tick");
            }
        });
        final GpioPinDigitalInput B = Global.gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.OFF);
        B.setShutdownOptions(true);
        B.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                System.out.println("B tick");
            }
        });
    }

    public void readEncoder() throws InterruptedException {


    }

}
