package org.led20.backend;

import com.fazecast.jSerialComm.*;
import java.nio.charset.StandardCharsets;

public class ArduinoBoard {
    
    private SerialPort comPort;

    public ArduinoBoard(SerialPort comPort) {
        this.comPort = comPort;
    }

    public void sendData(String data) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        comPort.writeBytes(dataBytes, dataBytes.length);
    }
}
