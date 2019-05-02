import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Global {
    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    public static final GpioController gpio = GpioFactory.getInstance();
}
