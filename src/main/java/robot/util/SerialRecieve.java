package robot.util;

import com.pi4j.io.serial.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import robot.Global;

import java.io.IOException;
import java.util.Date;

/**
 * This example code demonstrates how to perform serial communications using the Raspberry Pi.
 *
 * @author Robert Savage
 */
public class SerialRecieve {

    public SerialRecieve() {
        try {
            main();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This example program supports the following optional command arguments/options:
     *   "--device (device-path)"                   [DEFAULT: /dev/ttyAMA0]
     *   "--baud (baud-rate)"                       [DEFAULT: 38400]
     *   "--data-bits (5|6|7|8)"                    [DEFAULT: 8]
     *   "--parity (none|odd|even)"                 [DEFAULT: none]
     *   "--stop-bits (1|2)"                        [DEFAULT: 1]
     *   "--flow-control (none|hardware|software)"  [DEFAULT: none]
     *
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */

    public void main() throws InterruptedException, IOException {

        // !! ATTENTION !!
        // By default, the serial port is configured as a console port
        // for interacting with the Linux OS shell.  If you want to use
        // the serial port in a software program, you must disable the
        // OS from using this port.
        //
        // Please see this blog article for instructions on how to disable
        // the OS console for this port:
        // https://www.cube-controls.com/2015/11/02/disable-serial-port-terminal-output-on-raspbian/

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

        // create and register the serial data listener
        serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                // NOTE! - It is extremely important to read the data received from the
                // serial port.  If it does not get read from the receive buffer, the
                // buffer will continue to grow and consume memory.

                // print out the data received to the console
                try {
                    //console.println("[HEX DATA]   " + event.getHexByteString());
                    byte[] data = event.getBytes();
                    if(data.length > 0 && data.length == 5) {
                        Global.controller.setAnalogStickLeftX(data[0]);
                        Global.controller.setAnalogStickLeftY(data[1]);
                        Global.controller.setAnalogStickRightY(data[2]);
                        Global.controller.setAnalogStickRightX(data[3]);
                        Global.controller.setSquareButton(((data[4] >> 0) & 1) != 0);
                        Global.controller.setXButton(((data[4] >> 1) & 1) != 0);
                        Global.controller.setOButton(((data[4] >> 2) & 1) != 0);
                        Global.controller.setTriangleButton(((data[4] >> 3) & 1) != 0);
                        //System.out.println(Global.controller.getTriangleButton() + " " + Global.controller.getOButton() + " " + Global.controller.getSquareButton() + " " + Global.controller.getXButton());
                    }
                    //System.out.println(event.getAsciiString());
                    //console.println("[ASCII DATA] " + event.getAsciiString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // create serial config object
            SerialConfig config = new SerialConfig();

            // set default serial settings (device, baud rate, flow control, etc)
            //
            // by default, use the DEFAULT com port on the Raspberry Pi (exposed on GPIO header)
            // NOTE: this utility method will determine the default serial port for the
            //       detected platform and board/model.  For all Raspberry Pi models
            //       except the 3B, it will return "/dev/ttyAMA0".  For Raspberry Pi
            //       model 3B may return "/dev/ttyS0" or "/dev/ttyAMA0" depending on
            //       environment configuration.
            config.device(SerialPort.getDefaultPort())
                    .baud(Baud._38400)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);

            // parse optional command argument options to override the default serial settings.
//            if(args.length > 0){
//                config = CommandArgumentParser.getSerialConfig(config, args);
//            }
//            System.out.println("Serial connecting");
//            // display connection details
//            console.box(" Connecting to: " + config.toString(),
//                    " We are sending ASCII data on the serial port every 1 second.",
//                    " Data received on serial port will be displayed below.");


            // open the default serial device/port with the configuration settings
            serial.open(config);
            System.out.println("Serial connected");

            // continuous loop to keep the program running until the user terminates the program
//            while(console.isRunning()) {
//                try {
////                    // write a formatted string to the serial transmit buffer
////                    serial.write("CURRENT TIME: " + new Date().toString());
////
////                    // write a individual bytes to the serial transmit buffer
////                    serial.write((byte) 13);
////                    serial.write((byte) 10);
////
////                    // write a simple string to the serial transmit buffer
////                    serial.write("Second Line");
////
////                    // write a individual characters to the serial transmit buffer
////                    serial.write('\r');
////                    serial.write('\n');
////
////                    // write a string terminating with CR+LF to the serial transmit buffer
////                    serial.writeln("Third Line");
//                }
//                catch(IllegalStateException ex){
//                    ex.printStackTrace();
//                }
//
//                // wait 1 second before continuing
//                Thread.sleep(1000);
//            }

        }
        catch(IOException ex) {
            console.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }
    }
}
