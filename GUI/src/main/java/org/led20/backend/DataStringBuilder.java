package org.led20.backend;

public class DataStringBuilder {
    // writes combat mode status, effect, player HP percentages, player death saves, and separator indices to table strip com port
    

    public void writeToPort(boolean combatMode, char effect, byte numPlayers, byte[] playerHPPercentage, byte[][] deathSaves, byte[] separatorIndices, byte[] playerConditions, String separatorString, boolean devMode, String portName) {
        String portWriteString = "";

        portWriteString += "S";
        portWriteString += "*";

        // combat mode?
        if (combatMode) {
            portWriteString += "C" + "t" + "*";
        } else {
            portWriteString += "C" + "f" + "*";
        }

        // current effect
        portWriteString += "E" + effect + "*";

        portWriteString += "N";
        portWriteString += numPlayers;
        portWriteString += "*";

        // player hp percentages
        portWriteString += "P";
        for (int i = 0; i <= numPlayers; i++) {
            portWriteString += playerHPPercentage[i] + ",";
        }
        portWriteString += "*";

        // death saving throws
        portWriteString += "D";
        for (int i = 0; i <= numPlayers; i++) {
            portWriteString += deathSaves[i][0];
            portWriteString += deathSaves[i][1];
            portWriteString += ",";
        }
        portWriteString += "*";

        // white separator led indices
        portWriteString += "I";
        // is numPlayers+1 to include future DM health bar
        // for (int i = 0; i < 2 * (numPlayers + 1); i++) {
        //     portWriteString += separatorIndices[i];
        //     portWriteString += ",";
        // }
        portWriteString += separatorString;
        portWriteString += "*";

        // player conditions
        portWriteString += "T0,";
        for (int i = 1; i <= numPlayers; i++) {
            portWriteString += playerConditions[i] + ",";
        }

        portWriteString += "*";

        // delimiters
        portWriteString += "#";
        portWriteString += '\0';
        // print and write to port
        System.out.print("DATA:\t");
        System.out.println(portWriteString);
        System.out.println("Writing to port...");
        

    }
}
