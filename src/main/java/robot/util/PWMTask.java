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
    private AtomicInteger period;
    private AtomicInteger periodMilliseconds;
    private AtomicBoolean useMilliseconds;
    private Pin pin;
    private GpioPinDigitalOutput accessPin;

    public PWMTask(Pin pin) {
        this.pin = pin;
        accessPin = Global.gpio.provisionDigitalOutputPin(this.pin, PinState.LOW);
        accessPin.setShutdownOptions(true);
        period = new AtomicInteger(10000);
        periodMilliseconds = new AtomicInteger(0);
        useMilliseconds = new AtomicBoolean(false);
        worker = new Thread(this);
        worker.start();
    }

    public void setDutyCycle(double percent) {
        this.dutyCycle.set((int)(percent*((useMilliseconds.get() ? periodMilliseconds.get() : period.get())/100)));
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public void setPeriod(int nanoseconds) {
        this.period.set(nanoseconds);
    }

    public void setPeriodMilliseconds(int milliseconds) {
        this.periodMilliseconds.set(milliseconds);
    }

    public void setUseMilliseconds(boolean value) {
        this.useMilliseconds.set(value);
    }

    @Override
    public void run() {
        while(true) {
            if(enabled.get()) {
                accessPin.high();
                try {
                    if(useMilliseconds.get()) {
                        Thread.sleep(dutyCycle.get(), 0);
                    }
                    else {
                        Thread.sleep(0, dutyCycle.get()); //Can divide by 100 to cancel out the *100
                    }
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                accessPin.low();
                int sleepTime = period.get()-dutyCycle.get();
                if(sleepTime != 0) {
                    try {
                        if(useMilliseconds.get()) {
                            Thread.sleep(periodMilliseconds.get() - dutyCycle.get(), 0);
                        }
                        else {
                            Thread.sleep(0, period.get() - dutyCycle.get());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
