package org.led20.backend;

import java.util.List;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.fazecast.jSerialComm.*;

public class ArduinoManager {
    
    private List<ArduinoBoard> connectedBoards = new ArrayList<>();

    public void connectToBoard(String portDescription) {
        SerialPort comPort = SerialPort.getCommPort(portDescription);
        comPort.setBaudRate(9600);
        if (comPort.openPort()) {
            connectedBoards.add(new ArduinoBoard(comPort));
        } else {
            System.err.println("Failed to connect to board at port " + portDescription + ".");
        }
    }

    public void WriteToPort(String portDescription, boolean devMode, String portWriteString) {
        if (!devMode) {
            // write to port
            byte[] bytes = portWriteString.getBytes(StandardCharsets.UTF_8);
            
            SerialPort comPort = SerialPort.getCommPort(portDescription);
            OutputStream out = comPort.getOutputStream();
            try {
                out.write(bytes);
                out.close();
                System.out.print("NO ERRORS\t");
            } catch (Exception e) {
                e.printStackTrace();
            }
            comPort.flushIOBuffers();
        } else {
            System.out.println("DEV MODE: " + portWriteString);
        }
        System.out.println("Done writing.\n");
    }

}
