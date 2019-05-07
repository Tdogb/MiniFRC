package robot.util;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import robot.Global;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PWMTask implements Runnable {
    private Thread worker;
    private AtomicBoolean enabled = new AtomicBoolean(false);
    private AtomicInteger dutyCycle = new AtomicInteger(0);
    private Pin pin;
    private GpioPinDigitalOutput accessPin;

    public PWMTask(Pin pin) {
        this.pin = pin;
        accessPin = Global.gpio.provisionDigitalOutputPin(this.pin, PinState.LOW);
        accessPin.setShutdownOptions(true);
        worker = new Thread(this);
        worker.start();
    }

    public void setDutyCycle(double percent) {
        this.dutyCycle.set((int)(percent*100));
    }

    public void setEnabled(boolean enabled) {
        //System.out.println("enabled");
        this.enabled.set(enabled);
    }

    @Override
    public void run() {
        while(true) {
            if(enabled.get()) {
                accessPin.high();
                try {
                    Thread.sleep(0,dutyCycle.get()); //Can divide by 100 to cancel out the *100
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                accessPin.low();
                try {
                    Thread.sleep(0,10000-dutyCycle.get());
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}
