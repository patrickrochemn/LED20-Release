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

    // Send info when not in combat
    public void sendRegularUpdate(char effect) {
        /**
         * Regular update includes:
         * Current Effect
         */

        String regularUpdateString = "";

        // Indicator that packet is a regular update
        regularUpdateString += "R";

        // Current effect
        regularUpdateString += effect;

        sendData(regularUpdateString);
    }

    // Send info when in combat
    public void sendCombatUpdate() {
        /**
         * Combat update includes:
         * Number of players
         * Player HP percentages
         * Player death saves
         * Player conditions
         * White separator led indices
         */

        String combatUpdateString = "";

        // Indicator that packet is a combat update
        combatUpdateString += "C";

        // Number of players

        // Player HP percentages

        // Player death saves

        // Player conditions

        // White separator led indices

        sendData(combatUpdateString);
    }

}
