package org.led20.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectEffects extends JPanel {

    String effects[] = {
        "Sakura", "Morning", "Fire", "Flashbang", "Thunder",
        "Upside Down", "Day", "Water", "UwU", "Snow",
        "Big Storm", "Evening", "Candles", "Cyber", "Rain",
        "Winter", "Night", "Forest", "Divine", "Typhoon",
        "Off",
    };
        
    String effectChars[] = {
            "h","m","F","f","U",
            "?","d","w","u","s",
            "A","e","c","b","r",
            "W","n","t","V","T",
            "R", "1"
    };

    char effect = 'w'; // default is 'w' for water

    private boolean isCombatModeEnabled = false;
    private JButton combatModeButton;

    public static void show(JFrame frame) {
        SelectEffects newContent = new SelectEffects();
        frame.setContentPane(newContent);
        frame.revalidate();
        frame.repaint();
    }
    
    public SelectEffects() {
        setLayout(new BorderLayout());  // Set layout manager
        
        JLabel title = new JLabel("Effects", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        add(title, BorderLayout.NORTH);  // Add title label at the top

        JPanel effectButtonPanel = new JPanel(new GridLayout(4, 5));
        // create and add effect buttons
        for (int i = 0; i < effects.length; i++) {
            JButton effect = createBtn(effects[i]);
            effectButtonPanel.add(effect);
        }

        add(effectButtonPanel, BorderLayout.CENTER);  // Add effect buttons in the center

        // Initialize combat mode button with listener
        combatModeButton = new JButton("Combat Mode: OFF");
        combatModeButton.setFont(new Font("Arial", Font.BOLD, 16));
        combatModeButton.setBackground(new Color(139, 0, 0)); // Dark red for "off"
        combatModeButton.setForeground(Color.WHITE);
        combatModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle combat mode state
                isCombatModeEnabled = !isCombatModeEnabled;
                updateCombatModeButtonAppearance();
            }
        });
        
        // Example button to go back to the main screen
        JButton backButton = new JButton("Back to Startup");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Startup.show();  // Go back to the main screen
            }
        });

        // Create panel for buttons on bottom
        // combat button on top, back button on bottom. Both buttons spanning full width
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        // add combat button
        bottomPanel.add(combatModeButton);
        // add back button
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);  // Add bottom panel
    }

    // [HELPER] creates a button with parameter text and adds a listener to it
    private JButton createBtn(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(buttonText);
        button.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // action listener should set the class effect char to the button's effect char
                    for(int i = 0; i < effects.length; i++) {
                        if(e.getActionCommand().equals(effects[i])) {
                            effect = effectChars[i].charAt(0);
                            System.out.println("Effect: " + effect);
                        }
                    }
                }
            }
        );
        return button;
    }

    // [HELPER] Method to update the appearance of the combat mode button
    private void updateCombatModeButtonAppearance() {
        if (isCombatModeEnabled) {
            combatModeButton.setBackground(new Color(255, 0, 0)); // Bright red for "on"
            combatModeButton.setText("Combat Mode: ON");
        } else {
            combatModeButton.setBackground(new Color(139, 0, 0));
            combatModeButton.setText("Combat Mode: OFF");
        }
    }

}
