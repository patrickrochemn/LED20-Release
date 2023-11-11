package org.led20.frontend;

import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArduinoConnections extends JPanel {
    
    private final JComboBox<String> comPortList;
    private final DefaultComboBoxModel<String> comPortModel;
    private final JButton refreshButton;
    private final JButton connectButton;
    private final JTextArea connectionStatus;

    public ArduinoConnections() {
        setLayout(new BorderLayout());  // Set layout manager

        JLabel title = new JLabel("Add/Edit Arduino Connections", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        add(title, BorderLayout.NORTH);  // Add title label at the top

        // Dropdown for COM port selection
        comPortModel = new DefaultComboBoxModel<>();
        comPortList = new JComboBox<>(comPortModel);
        comPortList.setFont(new Font("Arial", Font.PLAIN, 16));
        updateCOMPortList();

        // Button to refresh COM  port list
        refreshButton = new JButton("Refresh COM Ports");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 16));
        refreshButton.addActionListener(e -> updateCOMPortList());

        // Text area for connection status
        connectionStatus = new JTextArea(5, 20);
        connectionStatus.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(connectionStatus);

        // Button to connect to selected COM port
        connectButton = new JButton("Connect to Selected Port");
        connectButton.setFont(new Font("Arial", Font.PLAIN, 16));
        connectButton.addActionListener(e -> connectToSelectedPort());

        // layout the components
        JPanel topPanel = new JPanel();
        topPanel.add(comPortList);
        topPanel.add(refreshButton);
        topPanel.add(connectButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        
        // Example button to go back to the main screen
        JButton backButton = new JButton("Back to Main");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Startup.show();  // Go back to the main screen
            }
        });
        add(backButton, BorderLayout.SOUTH);  // Add back button at the bottom
    
    }

    private void updateCOMPortList() {
        comPortModel.removeAllElements();
        SerialPort[] COMPorts = SerialPort.getCommPorts();
        for (SerialPort port : COMPorts) {
            comPortModel.addElement(port.getSystemPortName());
        }
    }

    private void connectToSelectedPort() {
        String selectedPortName = (String) comPortList.getSelectedItem();
        // if the user has selected a COM port and the list of ports is not empty
        if (selectedPortName != null && !selectedPortName.isEmpty()) {
            new Thread(() -> {
                try {
                    SerialPort selectedPort = SerialPort.getCommPort(selectedPortName);
                    selectedPort.setBaudRate(9600); // maybe make adjustable later - ideally automatically detected based on board type

                    if (selectedPort.openPort()) {
                        connectionStatus.append("Connected to " + selectedPortName + "\n");
                        // Store the connected port reference if needed for later use
                    } else {
                        connectionStatus.append("Failed to connect to " + selectedPortName + "\n");
                    }
                } catch (Exception e) {
                    connectionStatus.append("Error: " + e.getMessage() + "\n");
                }
            }).start();
        } else {
            connectionStatus.append("No COM port selected.\n");
        }
    }

    public static void show(JFrame frame) {
        ArduinoConnections newContent = new ArduinoConnections();
        frame.setContentPane(newContent);
        frame.revalidate();
        frame.repaint();
    }
}
