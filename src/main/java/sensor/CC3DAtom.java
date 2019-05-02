package sensor;

import com.pi4j.io.serial.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;

import java.io.IOException;

/*
I AM DOING THIS ON THE ARDUINO through the serial plug
 */


public class CC3DAtom {
    byte[] data;
    public void connect() {
        final Serial serial = SerialFactory.createInstance();
        serial.addListener(new SerialDataEventListener() {
            public void dataReceived(SerialDataEvent serialDataEvent) {
                try {
                    serialDataEvent.getByteBuffer();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            SerialConfig config = new SerialConfig();
            config.device(SerialPort.getDefaultPort())
                    .baud(Baud._57600)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);
            serial.open(config);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
