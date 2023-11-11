package org.led20.frontend;

import com.formdev.flatlaf.FlatDarkLaf;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Startup {

    private static JFrame frame;

    final int NUM_LEDS = 299;
    int[][] playerHP = { { 100, 100 }, { 100, 100 }, { 100, 100 }, { 100, 100 }, { 100, 100 }, { 100, 100 },
            { 100, 100 }, { 100, 100 } }; // playerNumber, {current, max}
    int[] playerHPPercentage = { 100, 100, 100, 100, 100, 100, 100, 100 };
    int[][] deathSaves = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
    String separatorString = "0,0,0,44,45,89,90,134,185,218,219,252,253,286,287,320";
    int[] separatorIndices = {0,0,0,44,45,89,90,134,185,218,219,252,253,286,287,320}; // NEW FOR TABLE V3 (NO END HEALTH BAR)

    public static void main(String[] args) {
        // Set Look and Feel
        FlatDarkLaf.setup();
        show();
    }

    Color backgroundColor = new Color(50,50,50);
    Color buttonColor = new Color(102,102,102);
    Color textColor = new Color(210,210,210);
    Color highlightColor = new Color(170,170,170);
    Color pressedColor = new Color(60,60,60);

    public static void show() {
        // Ensure UI updates are made on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (frame == null) {
                    frame = new JFrame("LED20 Control Panel");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800, 500);  // Width, Height
                    frame.setLocationRelativeTo(null);  // Center frame on screen
                }
                Startup startup = new Startup();
                frame.setContentPane(startup.createContentPane());
                URL url = getClass().getResource("/imgs/BlueD20.jpg");
                if (url != null) {
                    ImageIcon icon = new ImageIcon(url);
                    frame.setIconImage(icon.getImage());

                } else {
                    System.out.println("Error loading icon");
                }
                frame.revalidate();
                frame.repaint();
                frame.setVisible(true);
            }
        });
    }

    private JPanel createContentPane() {
        // Load campaigns from JSON file
        String[] campaigns = loadCampaignNames();
        
        // Create combo box for campaign selection
        JComboBox<String> campaignSelector = new JComboBox<>(campaigns);
        campaignSelector.setFont(new Font("Arial", Font.PLAIN, 16));  // Set font size
        
        // Create buttons
        JButton editPresetButton = new JButton("Create/Edit Campaign Preset");
        editPresetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditCampaignPreset.show(frame);
            }
        });

        JButton configArduinoButton = new JButton("Add/Edit Arduino Connections");
        configArduinoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArduinoConnections.show(frame);
            }
        });

        JButton toggleEffectsButton = new JButton("Toggle Effects");
        toggleEffectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectEffects.show(frame);
            }
        });

        // Set button font size
        editPresetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        configArduinoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        toggleEffectsButton.setFont(new Font("Arial", Font.PLAIN, 32));

        // Create panel and add components
        JPanel panel = new JPanel();
        panel.add(campaignSelector);
        panel.add(editPresetButton);
        panel.add(configArduinoButton);
        panel.add(toggleEffectsButton);

        return panel;
    }

    private String[] loadCampaignNames() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("campaignPresets.json")) {
            if (is == null) {
                throw new IOException("Campaign presets file not found");
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            
            // Convert the string content to a JSONObject
            JSONObject rootObject = new JSONObject(content);
            // Access the campaignPresets array inside the root object
            JSONArray jsonArray = rootObject.getJSONArray("campaignPresets");
            
            String[] campaignNames = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                campaignNames[i] = jsonObject.getString("campaignName");
            }
            return campaignNames;
        } catch (IOException e) {  
            e.printStackTrace();
            return new String[] {"Default Campaign"};  // Return default campaign if file reading fails
        } catch (JSONException e) {
            e.printStackTrace();
            return new String[] {"Error loading campaigns"};  // Return error message if JSON parsing fails
        }
    }

}
