package org.led20.frontend;

import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.led20.backend.CampaignPresets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class AddEditCampaignPreset extends JPanel {

    private CampaignPresets campaignPresets;

    private JPanel presetPanel;
    private JComboBox<String> campaignDropdown;

    public AddEditCampaignPreset() {

        campaignPresets = new CampaignPresets(); // initialize backend class
        setLayout(new BorderLayout());  // Set layout manager
        initializeUI();
    }
    
    private void initializeUI() {
        JLabel title = new JLabel("Manage Campaign Presets", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        add(title, BorderLayout.NORTH);  // Add title label at the top
    
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Dropdown Panel
        JPanel dropdownPanel = new JPanel(new FlowLayout());
        campaignDropdown = new JComboBox<>();
        campaignDropdown.setPreferredSize(new Dimension(300, 25));
        campaignDropdown.addItem("Create New Campaign Preset"); // this is the default option and used for creating new presets
        
        // add campaign names to dropdown
        List<String> campaignNames = campaignPresets.getCampaignNames();
        for (String campaignName : campaignNames) {
            campaignDropdown.addItem(campaignName);
        }

        dropdownPanel.add(campaignDropdown); // Add dropdown to dropdownPanel
        centerPanel.add(dropdownPanel, BorderLayout.NORTH); // Add dropdownPanel to the top of centerPanel

        // Preset Panel
        presetPanel = new JPanel(new BorderLayout()); // BorderLayout for presetPanel
        centerPanel.add(presetPanel, BorderLayout.CENTER); // Add presetPanel to the center of centerPanel

        // show campaign preset info for selected campaign
        campaignDropdown.addActionListener(campaignDropdownActionListener -> {
            String selectedCampaign = (String) campaignDropdown.getSelectedItem();
            presetPanel.removeAll();  // Clear previous content
    
            // if the selection is a preset
            if (!selectedCampaign.equals("Create New Campaign Preset")) {

                // get a panel containing the info for the preset
                JSONObject selectedPresetData = campaignPresets.getPresetData(selectedCampaign); // load JSON of selected preset

                if (selectedPresetData != null) {
                    JPanel currentPresetPanel = createPresetInfoPanel(selectedPresetData);
    


                }
            } else {
                // TODO: Add fields to create new campaign preset

            }
    
            presetPanel.revalidate();  // Refresh the panel
            presetPanel.repaint();
        });
        
        add(centerPanel, BorderLayout.CENTER);  // Add center panel
        
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

    // function to create campaign preset info panel updates upon selection change in dropdown
    private JPanel createPresetInfoPanel (JSONObject presetData) {

        // Campaign Name Panel
        JPanel campaignNamePanel = new JPanel(new FlowLayout()); // FlowLayout for campaignNamePanel
        JLabel campaignNameLabel = new JLabel("Campaign Name: ");
        campaignNamePanel.add(campaignNameLabel);
        String campaignName = (String) presetData.get("campaignName");
        JTextField campaignNameField = new JTextField();
        campaignNameField.setText(campaignName);
        campaignNamePanel.add(campaignNameField);
        presetPanel.add(campaignNamePanel, BorderLayout.NORTH); // Add campaignNamePanel to the top of presetPanel

        // Show player data in columns
        JSONArray playersArray = (JSONArray) presetData.get("players");
        JSONArray healthBarIndicesArray = (JSONArray) presetData.get("healthBarIndices");
        int[] healthBarIndices = new int[healthBarIndicesArray.size()];
        // loop through players in preset
        for (int i = 0; i < healthBarIndicesArray.size(); i++) {
            healthBarIndices[i] = ((Long) healthBarIndicesArray.get(i)).intValue();
        }

        JPanel playerDataPanel = new JPanel(new FlowLayout()); // FlowLayout for playerDataPanel

        // go through each of the player objects in the array and make a JPanel for each
        for(Object playerObj : playersArray) {
            JSONObject player = (JSONObject) playerObj; // cast the player object to a JSONObject
            JPanel playerPanel = new JPanel(new FlowLayout()); // create a new JPanel for each player
            presetPanel.add(playerPanel); // add the playerPanel to the presetPanel

            // Player Name: <player name>
            JLabel playerNameLabel = new JLabel("Player Name: ");
            playerPanel.add(playerNameLabel);
            JTextField playerNameField = new JTextField();
            String playerName = (String) player.get("name");
            playerNameField.setText(playerName);
            playerPanel.add(playerNameField);
            
            // Player ID: <player ID>
            JLabel playerIDLabel = new JLabel("Player ID: ");
            playerPanel.add(playerIDLabel);
            JTextField playerIDField = new JTextField();
            String playerID = (String) player.get("id");
            playerIDField.setText(playerID);
            playerPanel.add(playerIDField);
            
            // Health Bar Indices: <start index> - <end index>
            // for player index, get the start and end indices and put them in a text field
            JLabel healthBarIndicesLabel = new JLabel("Health Bar Indices: ");
            playerPanel.add(healthBarIndicesLabel);
            // get index of current playerObj in playersArray
            int playerIndex = playersArray.indexOf(playerObj);
            // get the start and end indices for the health bar
            int startIndex = healthBarIndices[playerIndex * 2];
            int endIndex = healthBarIndices[playerIndex * 2 + 1];
            JTextField healthBarIndicesField = new JTextField();
            healthBarIndicesField.setText(startIndex + " - " + endIndex);
            playerPanel.add(healthBarIndicesField);

            // add the playerPanel to the playerDataPanel
            playerDataPanel.add(playerPanel);
        }
                    
        // add the playerDataPanel to the presetPanel
        presetPanel.add(playerDataPanel, BorderLayout.CENTER);
        
        return presetPanel;
    }

    // TODO: add "Preview Health Bars" button to show health bars based on current user input
    // preview button should only appear after the user has made a change to one of the healthbar fields
    private void previewHealthBars() {

    }

    // TODO: add "Save Changes" button to save current user input as new preset
    // button should appear only after the user has made a change to one or more of the fields
    private void savePreset() {

    }
    
    public static void show(JFrame frame) {
        AddEditCampaignPreset newContent = new AddEditCampaignPreset();
        frame.setContentPane(newContent);
        frame.revalidate();
        frame.repaint();
    }
    
}
