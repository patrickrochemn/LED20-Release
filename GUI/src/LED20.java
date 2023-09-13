import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

// begin auto hp imports

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.HashMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// end auto hp imports

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fazecast.jSerialComm.SerialPort;
import com.formdev.flatlaf.FlatDarkLaf;

public class LED20 implements Runnable {

    public JFrame frame;
    public JFrame startupFrame;
    public JFrame campaignFrame;
    public JFrame editCampaignFrame;
    public JFrame enableEffectsFrame;
    public JFrame manualCombatFrame;
    JPanel panel, effectPanel;

    boolean combatMode = false;
    boolean devMode = false;
    boolean accentHigh = false;
    boolean accentLow = false;
    boolean tableStrip = false;

    boolean webScraper = false; // THIS BOOLEAN IS FOR ACTIVATING THE WEB SCRAPING FEATURE

    boolean customBackground = false;

    String currentCampaign = "DEFAULT";


    // TODO: FINISH MANUAL HP CONTROLS
    boolean manualHP = false; // needs to be true for manual controls to show

    ImageIcon iconBackground;
    JLabel jlBackgroundImage;
    Image background;

    int numPlayers = 7;
    final int NUM_LEDS = 299;
    int[][] playerHP = { { 100, 100 }, { 100, 100 }, { 100, 100 }, { 100, 100 }, { 100, 100 }, { 100, 100 },
            { 100, 100 }, { 100, 100 } }; // playerNumber, {current, max}
    int[] playerHPPercentage = { 100, 100, 100, 100, 100, 100, 100, 100 };
    int[][] deathSaves = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
    // String separatorString = "0,0,0,33,34,67,68,102,103,135,185,235,236,278,279,320";
    // front right corner is 0
    // back right corner is 135
    // back left corner is 185
    // front left corner is 320
    String separatorString = "0,0,0,44,45,89,90,134,185,218,219,252,253,286,287,320";
    // int[] separatorIndices = {0, 0, 0, 33, 34, 67, 68, 102, 103, 135, 185, 235, 236, 278, 279, 320}; // NEW FOR TABLE V3 (NO END HEALTH BAR)
    int[] separatorIndices = {0,0,0,44,45,89,90,134,185,218,219,252,253,286,287,320}; // NEW FOR TABLE V3 (NO END HEALTH BAR)
    // int[] separatorIndices = {DMS, DME, P1S, P1E, P2S, P2E, P3S, P3E, P4S, P4E, P5S, P5E, P6S, P6E, P7S, P7E}; //NEW

    String[] playerConditions = { "", "", "", "", "", "", "", "" };

    String[] playerHPCurs = {"titties","C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P1HPCur.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P2HPCur.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P3HPCur.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P4HPCur.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P5HPCur.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P6HPCur.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P7HPCur.txt"};
    String[] playerHPMaxs = {"oppai","C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P1HPMax.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P2HPMax.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P3HPMax.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P4HPMax.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P5HPMax.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P6HPMax.txt",
                "C:\\Users\\Table\\Documents\\DnDBeyond Scraper\\ScrapeTXTs\\P7HPMax.txt"};

    String imageName = "GUI\\src\\imgs\\parchment.jpg";

    char effect = 'w';
    char effectAccentOne = 'w';
    char effectAccentTwo = 'w';
    char preset = 'f';
    String effects[] = {
            "Sakura", "Morning", "Fire", "Flashbang", "Thunder",
            "Upside Down", "Day", "Water", "UwU", "Snow",
            "Big Storm", "Evening", "Candles", "Cyber", "Rain",
            "Winter", "Night", "Forest", "Divine", "Typhoon",
            "Off",
            };
            // "Ruby", "Alden", "Nadine's Gift"
    String effectChars[] = {
            "h","m","F","f","U",
            "?","d","w","u","s",
            "A","e","c","b","r",
            "W","n","t","V","T",
            "R", "1"
    };
    String campaigns[] = {};

    Integer campaignColors[][] = { { 255, 0, 0 }, { 255, 150, 255 } };

    SerialPort comPort = SerialPort.getCommPort("COM3"); // ARTEMIS
    // SerialPort comPort = SerialPort.getCommPort("COM5"); // PRO MICRO

    // [MAIN] main function, it calls run()
    public static void main(String[] args) {
        // FlatDarkLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new LED20());
    }

    Color backgroundColor = new Color(50,50,50);
    Color buttonColor = new Color(102,102,102);
    Color textColor = new Color(210,210,210);
    Color highlightColor = new Color(170,170,170);
    Color pressedColor = new Color(60,60,60);

    // [MAIN] choose theme, then show startup screen
    public void run() {   

        UIManager.put("Button.arc", 20);
        UIManager.put("ButtonHighlight.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("Component.focusArc", 20);
        UIManager.put("Component.focusedBorderColor", textColor);
        UIManager.put("Button.focusedBorderColor", textColor);
        UIManager.put("Button.hoverBorderColor", textColor);
        UIManager.put("Button.pressedBackground", pressedColor);
        UIManager.put("ToggleButton.pressedBackground", new Color(50,50,50));
        UIManager.put("ToggleButton.selectedBackground", pressedColor);
        UIManager.put("Frame.background", backgroundColor);
        Font customFont = new Font("Arial", Font.BOLD, 20);
        UIManager.put("Label.font", customFont);
        UIManager.put("Button.font", customFont);
        UIManager.put("ToggleButton.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("TextField.font", customFont);

        createAndShowStartup();
    }

    // [POPUP] displays startup screen with logo, add campaign, edit campaign, active effects, com port selection, and continue buttons
    private void createAndShowStartup() {

        startupFrame = new JFrame("Configuration Menu");
        startupFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        startupFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // panel to hold settings buttons
        JPanel configPanel = new JPanel();

        // add campaign to campaign list
        JButton addCampaign = createBtn("Add a new campaign preset");
        addCampaign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCampaignPreset();
            }
        });
        configPanel.add(addCampaign);

        // edit existing campaign preset
        JButton editCampaign = createBtn("Edit a campaign preset");
        editCampaign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editCampaignPreset();
            }
        });
        configPanel.add(editCampaign);

        // select which effects have buttons
        JButton enableEffects = createBtn("Select Active Effects");
        enableEffects.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectActiveEffects();
            }
        });
        configPanel.add(enableEffects);

        // add configPanel to startupFrame
        c.gridx = 0;
        c.gridy = 0;
        startupFrame.add(configPanel, c);

        // logo in center of screen
        try {
            BufferedImage logoImage = ImageIO.read(new File("GUI\\src\\imgs\\d20.jpg"));
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            c.gridx = 0;
            c.gridy = 1;
            startupFrame.add(logoLabel, c);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // dropdown menu to select COM port
        JLabel comPortLabel = new JLabel("Select COM Port:");
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(5, 0, 5, 0);
        startupFrame.add(comPortLabel, c);
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] portNames = new String[ports.length];
        System.out.println("Available COM ports:");
        for (int i = 0; i < ports.length; i++) {
            portNames[i] = ports[i].getDescriptivePortName();
            System.out.println(ports[i].getDescriptivePortName());
        }
        JComboBox<String> comPortList = new JComboBox<String>(portNames);
        comPortList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comPort = ports[comPortList.getSelectedIndex()];
                comPort.setBaudRate(9600);
                comPort.openPort();
                System.out.println("COM port set to " + comPort.getDescriptivePortName());
            }
        });
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(5, 0, 5, 0);
        startupFrame.add(comPortList, c);

        // continue button at bottom of screen
        JButton continueBtn = new JButton("Continue");
        continueBtn.setActionCommand("Continue");
        continueBtn.addActionListener(new ButtonClickListener());
        c.gridx = 0;
        c.gridy = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        startupFrame.add(continueBtn, c);

        startupFrame.setSize(825, 475);
        startupFrame.setUndecorated(false);
        startupFrame.setLocationByPlatform(true);
        startupFrame.setVisible(true);
    }

    // [POPUP] displays popup window to add a new campaign preset
    private void addCampaignPreset() {

        // initialize "Add campaign preset" popup window
        campaignFrame = new JFrame("Add campaign preset");
        campaignFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        // campaignFrame.setLayout(new GridLayout(5, 1));
        campaignFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // create "New campaign name" label, text entry, and submit button
        JPanel newCampaignPanel = new JPanel();
        newCampaignPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel newCampaignLabel = new JLabel("New campaign name: ");
        newCampaignPanel.add(newCampaignLabel);
        JTextField campaignName = new JTextField(10);
        newCampaignPanel.add(campaignName);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        campaignFrame.add(newCampaignPanel, c);

        // create "Default effect" selection
        JPanel defaultEffectPanel = new JPanel();
        JLabel defaultEffectLabel = new JLabel("Select default effect: ");
        defaultEffectPanel.add(defaultEffectLabel);
        DefaultComboBoxModel<String> effectModel = new DefaultComboBoxModel<>();
        JComboBox<String> effectSelector = new JComboBox<String>(effectModel);
        // populate dropdown with effect names
        for(int i = 0; i < effects.length; i++) {
            effectModel.addElement(effects[i]);
        }
        defaultEffectPanel.add(effectSelector);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        campaignFrame.add(defaultEffectPanel, c);


        // create "Color for campaign button" label and select button
        JPanel buttonColorPanel = new JPanel();
        JLabel buttonColorLabel = new JLabel("Color for campaign button: ");
        buttonColorPanel.add(buttonColorLabel);
        JColorChooser campaignButtonColor = new JColorChooser();
        AbstractColorChooserPanel[] originalPanels = campaignButtonColor.getChooserPanels();
        for(int i = 0; i < originalPanels.length; i++) {
            if(i == 0) continue;
            campaignButtonColor.removeChooserPanel(originalPanels[i]);
        }
        campaignButtonColor.remove(0);
        buttonColorPanel.add(campaignButtonColor);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = 20;
        campaignFrame.add(buttonColorPanel, c);

        // create "Number of players" label, slider, and submit button
        JPanel numPlayersPanel = new JPanel();
        JLabel numPlayersLabel = new JLabel("Number of players: ");
        numPlayersPanel.add(numPlayersLabel);
        JSlider numPlayersSlider = new JSlider(JSlider.HORIZONTAL, 1, 7, 3);
        numPlayersPanel.add(numPlayersSlider);
        numPlayersSlider.setMajorTickSpacing(1);
        numPlayersSlider.setPaintTicks(true);
        numPlayersSlider.setPaintLabels(true);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.ipady = 10;
        campaignFrame.add(numPlayersPanel, c);

        // background image for preset
        JPanel imageSelectionPanel = new JPanel();
        JLabel imageSelectionLabel = new JLabel("Select background image for preset: ");
        imageSelectionPanel.add(imageSelectionLabel);
        // select custom background image
        JButton addBackgroundImg = createBtn("Upload Image");
        addBackgroundImg.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                // filter the files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);
                // if the user clicks on save in JFileChooser
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = file.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    // set uploaded file as current background image
                    imageName = path;
                    // save file to "Background Images" folder
                    try {
                        BufferedImage bImage = null;
                        bImage = ImageIO.read(selectedFile);
                        ImageIO.write(bImage, "jpg",
                                new File("GUI\\src\\imgs\\" + selectedFile.getName()));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    // Files.copy(path, "GUI\\src\\imgs",
                    // StandardCopyOption.REPLACE_EXISTING);
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No File Selected");
                }
            }
        });
        imageSelectionPanel.add(addBackgroundImg);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 4;
        c.ipady = 20;
        campaignFrame.add(imageSelectionPanel, c);



        // submit preset button
        JButton submitPreset = createBtn("Submit Preset");
        submitPreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("Submit Preset")) {
                    String entry = campaignName.getText();
                    campaignName.setText(" ");
                    // add newly created campaign to JSON with default values
                    int effectIdx = 0;
                    for(int i = 0; i < effects.length; i++) {
                        if(effects[i].equals(effectSelector.getSelectedItem().toString())) {
                            effectIdx = i;
                            break;
                        }
                    }
                    addNewCampaignToJSON(entry, effectChars[effectIdx], campaignButtonColor.getColor(), numPlayersSlider.getValue() + "", imageName);
                    campaignFrame.dispose();
                }
            }
        });
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 5;
        c.ipady = 10;
        campaignFrame.add(submitPreset, c);

        campaignFrame.setSize(800, 550);
        campaignFrame.setUndecorated(false);
        campaignFrame.setLocationByPlatform(true);
        campaignFrame.setVisible(true);
    }

    // [POPUP] displays popup window to edit campaign presets
    @SuppressWarnings("unchecked")
    private void editCampaignPreset() {

        JTextField campaignName = new JTextField(10);
        JColorChooser campaignButtonColor = new JColorChooser();
        JSlider numPlayersSlider = new JSlider(JSlider.HORIZONTAL, 1, 7, 3);

        // initialize "Edit campaign preset" popup window
        editCampaignFrame = new JFrame("Edit campaign preset");
        editCampaignFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        editCampaignFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0;

        // create "Select Preset label and dropdown menu"
        JPanel presetPanel = new JPanel();
        JLabel presetLabel = new JLabel("Select preset to edit:");
        presetPanel.add(presetLabel, 0);
        // dropdown to select which preset to edit
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        JComboBox<String> presets = new JComboBox<String>(model);
        // populate dropdown with campaign names
        // get campaign names from JSON
        // populate campaign array from JSON
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("GUI//src//campaigns.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray presetList = (JSONArray) obj;
            ArrayList<String> campaignNames = new ArrayList<String>();
            presetList.forEach(preset -> {
                JSONObject presetObject = (JSONObject) preset;
                String name = (String) presetObject.get("campaignName");
                campaignNames.add(name);
            });
            campaigns = campaignNames.toArray(new String[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < campaigns.length; i++) {
            model.addElement(campaigns[i]);
        }
        presets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // set text of name editor to current campaign name
                campaignName.setText(presets.getSelectedItem().toString());
                // set starting values of editors based on selected preset
                try (FileReader reader = new FileReader("GUI//src//campaigns.json")) {
                    JSONParser jsonParser = new JSONParser();
                    Object obj = jsonParser.parse(reader);
                    JSONArray presetList = new JSONArray();
                    presetList = (JSONArray) obj;
                    presetList.forEach(preset -> {
                        JSONObject presetObject = (JSONObject) preset;
                        String currentName = (String) presetObject.get("campaignName");
                        if(currentName.equals(presets.getSelectedItem().toString())) {
                            String color = (String) presetObject.get("buttonColor");
                            String[] colorComponents = {"0","0","0"};
                            colorComponents = color.split(",");
                            campaignButtonColor.setColor(new Color(Integer.parseInt(colorComponents[0]), Integer.parseInt(colorComponents[1]), Integer.parseInt(colorComponents[2])));
                            String playerCount = (String) presetObject.get("numPlayers");
                            numPlayersSlider.setValue(Integer.parseInt(playerCount));
                        }
                    });
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
        presetPanel.add(presets);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 5, 0);
        editCampaignFrame.add(presetPanel, c);

        // create "Edit campaign name" label, text entry, and submit button
        JPanel newCampaignPanel = new JPanel();
        newCampaignPanel.setLayout(new GridLayout(1,2));
        JLabel newCampaignLabel = new JLabel("Edit campaign name: ");
        newCampaignPanel.add(newCampaignLabel, 0);
        campaignName.setText(presets.getSelectedItem().toString());
        newCampaignPanel.add(campaignName, 1);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 0, 5, 0);
        editCampaignFrame.add(newCampaignPanel, c);

        // create "Edit default effect" selection
        JPanel defaultEffectPanel = new JPanel();
        JLabel defaultEffectLabel = new JLabel("Edit default effect: ");
        defaultEffectPanel.add(defaultEffectLabel);
        DefaultComboBoxModel<String> effectModel = new DefaultComboBoxModel<>();
        JComboBox<String> effectSelector = new JComboBox<String>(effectModel);
        // populate dropdown with effect names
        for(int i = 0; i < effects.length; i++) {
            effectModel.addElement(effects[i]);
        }
        defaultEffectPanel.add(effectSelector);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        editCampaignFrame.add(defaultEffectPanel, c);

        // create "Edit color for campaign button" label and select button
        JPanel buttonColorPanel = new JPanel();
        JLabel buttonColorLabel = new JLabel("Edit color for campaign button: ");
        buttonColorPanel.add(buttonColorLabel);
        AbstractColorChooserPanel[] originalPanels = campaignButtonColor.getChooserPanels();
        for(int i = 0; i < originalPanels.length; i++) {
            if(i == 0) continue;
            campaignButtonColor.removeChooserPanel(originalPanels[i]);
        }
        campaignButtonColor.remove(0);
        buttonColorPanel.add(campaignButtonColor);
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(5, 0, 5, 0);
        editCampaignFrame.add(buttonColorPanel, c);
        c.weighty = 0;


        // create "Edit number of players" label, slider, and submit button
        JPanel numPlayersPanel = new JPanel();
        JLabel numPlayersLabel = new JLabel("Edit number of players: ");
        numPlayersPanel.add(numPlayersLabel);
        numPlayersSlider.setMajorTickSpacing(1);
        numPlayersSlider.setPaintTicks(true);
        numPlayersSlider.setPaintLabels(true);
        numPlayersPanel.add(numPlayersSlider);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(5, 0, 5, 0);
        editCampaignFrame.add(numPlayersPanel, c);

        // background image for preset
        JPanel imageSelectionPanel = new JPanel();
        JLabel imageSelectionLabel = new JLabel("Select background image for preset: ");
        imageSelectionPanel.add(imageSelectionLabel);
        // select custom background image
        JButton addBackgroundImg = createBtn("Upload Image");
        addBackgroundImg.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                // filter the files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);
                // if the user clicks on save in JFileChooser
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = file.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    // set uploaded file as current background image
                    imageName = path;
                    // save file to "Background Images" folder
                    try {
                        BufferedImage bImage = null;
                        bImage = ImageIO.read(selectedFile);
                        ImageIO.write(bImage, "jpg",
                                new File("GUI\\src\\imgs\\" + selectedFile.getName()));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    // Files.copy(path, "GUI\\src\\imgs",
                    // StandardCopyOption.REPLACE_EXISTING);
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No File Selected");
                }
            }
        });
        imageSelectionPanel.add(addBackgroundImg);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(5, 0, 5, 0);
        editCampaignFrame.add(imageSelectionPanel, c);

        // "edit health bar separators" button
        JButton editIndices = createBtn("Edit Health Bar Separators");
        editIndices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editIndices(presets.getSelectedItem().toString());
            }
        });
        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(5, 0, 5, 0);
        editCampaignFrame.add(editIndices, c);

        // "submit preset" button
        JButton submitPreset = createBtn("Submit Preset Changes");
        submitPreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("Submit Preset Changes")) {
                    String entry = campaignName.getText();
                    String campaignToEdit = presets.getSelectedItem().toString();
                    modifyCampaignJSONElement(campaignToEdit, "campaignName", entry);
                    int effectIdx = 0;
                    for(int i = 0; i < effects.length; i++) {
                        if(effects[i].equals(effectSelector.getSelectedItem().toString())) {
                            effectIdx = i;
                            break;
                        }
                    }
                    modifyCampaignJSONElement(campaignToEdit, "effect", effectChars[effectIdx]);
                    modifyCampaignJSONElement(campaignToEdit, "numPlayers", Integer.toString(numPlayersSlider.getValue()));
                    if(!imageName.equals("GUI\\src\\imgs\\parchment.jpg")) modifyCampaignJSONElement(campaignToEdit, "customBackground", imageName);
                    String colorString = campaignButtonColor.getColor().getRed() + "," + campaignButtonColor.getColor().getGreen() + "," + campaignButtonColor.getColor().getBlue();
                    System.out.println(colorString);
                    modifyCampaignJSONElement(campaignToEdit, "buttonColor", colorString);
                    campaignName.setText(" ");
                    editCampaignFrame.dispose();
                }
            }
        });
        c.gridx = 0;
        c.gridy = 7;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(5, 0, 5, 0);
        editCampaignFrame.add(submitPreset, c);

        // "delete preset" button
        JButton deletePreset = createBtn("Delete Preset");
        deletePreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame confirmDelete = new JFrame("CONFIRM DELETE PRESET");
                confirmDelete.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                confirmDelete.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                JLabel confirm = new JLabel("Are you sure you wish to delete this campaign preset?");
                c.gridy = 0;
                c.gridx = 0;
                c.gridwidth = 2;
                confirmDelete.add(confirm, c);
                
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 1;
                JButton yes = createBtn("Yes");
                yes.setBackground(new Color(255, 50, 50));
                confirmDelete.add(yes, c);

                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridy = 1;
                c.gridwidth = 1;
                JButton no = createBtn("No");
                confirmDelete.add(no, c);

                confirmDelete.setSize(400, 600);
                confirmDelete.setUndecorated(false);
                confirmDelete.setLocationByPlatform(true);
                confirmDelete.setVisible(true);
            }
        });
        deletePreset.setBackground(new Color(255, 50, 50));
        c.gridx = 0;
        c.gridy = 8;
        c.insets = new Insets(5, 0, 10, 0);
        editCampaignFrame.add(deletePreset, c);

        editCampaignFrame.setSize(775, 600);
        editCampaignFrame.setUndecorated(false);
        editCampaignFrame.setLocationByPlatform(true);
        editCampaignFrame.setVisible(true);
    }

    // [POPUP] displays popup window to edit health bar indices)
    private void editIndices(String campaignName) {
        JFrame editIndicesFrame = new JFrame("Edit Health Bar Indices");
        editIndicesFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        editIndicesFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        // enter edit health bar mode
        effect = '$';
        writeToPort();
        
        // exit edit mode when window closed
        editIndicesFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                effect = 'w';
                writeToPort();
                editIndicesFrame.dispose();
            }
        });

        JPanel adjustors = new JPanel();
        int[] separators = parseSeparatorString(campaignName);

        // // DMS
        // JLabel separator0 = new JLabel("DM Start");
        // adjustors.add(separator0);
        // JSpinner spinner0 = new JSpinner();
        // spinner0.setValue(separators[0]);
        // adjustors.add(spinner0);
        
        // // DME
        // JLabel separator1 = new JLabel("DM End");
        // adjustors.add(separator1);
        // JSpinner spinner1 = new JSpinner();
        // spinner1.setValue(separators[1]);
        // adjustors.add(spinner1);

        // P1S
        JLabel separator2 = new JLabel("Player 1 Start:");
        adjustors.add(separator2);
        JSpinner spinner2 = new JSpinner();
        spinner2.setValue(separators[2]);
        spinner2.setSize(300,300);
        adjustors.add(spinner2);

        // P1E
        JLabel separator3 = new JLabel("Player 1 End:");
        adjustors.add(separator3);
        JSpinner spinner3 = new JSpinner();
        spinner3.setValue(separators[3]);
        adjustors.add(spinner3);

        // P2S
        JLabel separator4 = new JLabel("Player 2 Start:");
        adjustors.add(separator4);
        JSpinner spinner4 = new JSpinner();
        spinner4.setValue(separators[4]);
        adjustors.add(spinner4);

        // P2E
        JLabel separator5 = new JLabel("Player 2 End:");
        adjustors.add(separator5);
        JSpinner spinner5 = new JSpinner();
        spinner5.setValue(separators[5]);
        adjustors.add(spinner5);
        
        // P3S
        JLabel separator6 = new JLabel("Player 3 Start:");
        adjustors.add(separator6);
        JSpinner spinner6 = new JSpinner();
        spinner6.setValue(separators[6]);
        adjustors.add(spinner6);

        // P3E
        JLabel separator7 = new JLabel("Player 3 End:");
        adjustors.add(separator7);
        JSpinner spinner7 = new JSpinner();
        spinner7.setValue(separators[7]);
        adjustors.add(spinner7);
        
        // P4S
        JLabel separator8 = new JLabel("Player 4 Start:");
        adjustors.add(separator8);
        JSpinner spinner8 = new JSpinner();
        spinner8.setValue(separators[8]);
        adjustors.add(spinner8);

        // P4E
        JLabel separator9 = new JLabel("Player 4 End:");
        adjustors.add(separator9);
        JSpinner spinner9 = new JSpinner();
        spinner9.setValue(separators[9]);
        adjustors.add(spinner9);
        
        // P5S
        JLabel separator10 = new JLabel("Player 5 Start:");
        adjustors.add(separator10);
        JSpinner spinner10 = new JSpinner();
        spinner10.setValue(separators[10]);
        adjustors.add(spinner10);

        // P5E
        JLabel separator11 = new JLabel("Player 5 End:");
        adjustors.add(separator11);
        JSpinner spinner11 = new JSpinner();
        spinner11.setValue(separators[11]);
        adjustors.add(spinner11);
        
        // P6S
        JLabel separator12 = new JLabel("Player 6 Start:");
        adjustors.add(separator12);
        JSpinner spinner12 = new JSpinner();
        spinner12.setValue(separators[12]);
        adjustors.add(spinner12);

        // P6E
        JLabel separator13 = new JLabel("Player 6 End:");
        adjustors.add(separator13);
        JSpinner spinner13 = new JSpinner();
        spinner13.setValue(separators[13]);
        adjustors.add(spinner13);
        
        // P7S
        JLabel separator14 = new JLabel("Player 7 Start:");
        adjustors.add(separator14);
        JSpinner spinner14 = new JSpinner();
        spinner14.setValue(separators[14]);
        adjustors.add(spinner14);

        // P7E
        JLabel separator15 = new JLabel("Player 7 End:");
        adjustors.add(separator15);
        JSpinner spinner15 = new JSpinner();
        spinner15.setValue(separators[15]);
        adjustors.add(spinner15);

        GridLayout adjustorLayout = new GridLayout(16, 2);
        adjustorLayout.setVgap(10);
        adjustors.setLayout(adjustorLayout);
        // adjustors.setLayout(new GridLayout(16, 2));

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        editIndicesFrame.add(adjustors, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 10);
        JButton viewChangesBtn = createBtn("View Current Inputs");
        viewChangesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                separatorIndices[2] = Integer.parseInt(spinner2.getValue().toString());
                separatorIndices[3] = Integer.parseInt(spinner3.getValue().toString());
                separatorIndices[4] = Integer.parseInt(spinner4.getValue().toString());
                separatorIndices[5] = Integer.parseInt(spinner5.getValue().toString());
                separatorIndices[6] = Integer.parseInt(spinner6.getValue().toString());
                separatorIndices[7] = Integer.parseInt(spinner7.getValue().toString());
                separatorIndices[8] = Integer.parseInt(spinner8.getValue().toString());
                separatorIndices[9] = Integer.parseInt(spinner9.getValue().toString());
                separatorIndices[10] = Integer.parseInt(spinner10.getValue().toString());
                separatorIndices[11] = Integer.parseInt(spinner11.getValue().toString());
                separatorIndices[12] = Integer.parseInt(spinner12.getValue().toString());
                separatorIndices[13] = Integer.parseInt(spinner13.getValue().toString());
                separatorIndices[14] = Integer.parseInt(spinner14.getValue().toString());
                separatorIndices[15] = Integer.parseInt(spinner15.getValue().toString());
                for (int i = 2; i < separatorIndices.length - 1; i+=2){
                    if (separatorIndices[i] > separatorIndices[i+1] || separatorIndices[i] > NUM_LEDS){
                        separatorIndices[i] = Math.min(separatorIndices[i+1] - 1, NUM_LEDS);
                    }
                }
                separatorString = "";
                for(int i = 0; i < separatorIndices.length - 1; i++) {
                    separatorString += separatorIndices[i] + ",";
                }
                separatorString += separatorIndices[separatorIndices.length - 1] + "";
                System.out.println("Got to write to port.");
                writeToPort();
            }
        });
        editIndicesFrame.add(viewChangesBtn, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 10);
        JButton submitChangesBtn = createBtn("Save current indices to file");
        submitChangesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                separatorIndices[2] = Integer.parseInt(spinner2.getValue().toString());
                separatorIndices[3] = Integer.parseInt(spinner3.getValue().toString());
                separatorIndices[4] = Integer.parseInt(spinner4.getValue().toString());
                separatorIndices[5] = Integer.parseInt(spinner5.getValue().toString());
                separatorIndices[6] = Integer.parseInt(spinner6.getValue().toString());
                separatorIndices[7] = Integer.parseInt(spinner7.getValue().toString());
                separatorIndices[8] = Integer.parseInt(spinner8.getValue().toString());
                separatorIndices[9] = Integer.parseInt(spinner9.getValue().toString());
                separatorIndices[10] = Integer.parseInt(spinner10.getValue().toString());
                separatorIndices[11] = Integer.parseInt(spinner11.getValue().toString());
                separatorIndices[12] = Integer.parseInt(spinner12.getValue().toString());
                separatorIndices[13] = Integer.parseInt(spinner13.getValue().toString());
                separatorIndices[14] = Integer.parseInt(spinner14.getValue().toString());
                separatorIndices[15] = Integer.parseInt(spinner15.getValue().toString());
                separatorString = "";
                for(int i = 0; i < separatorIndices.length - 1; i++) {
                    separatorString += separatorIndices[i] + ",";
                }
                separatorString += separatorIndices[separatorIndices.length - 1] + "";
                modifyCampaignJSONElement(campaignName, "separatorIndices", separatorString);
            }
        });
        editIndicesFrame.add(submitChangesBtn, c);

        editIndicesFrame.setSize(400, 600);
        editIndicesFrame.setUndecorated(false);
        editIndicesFrame.setLocationByPlatform(true);
        editIndicesFrame.setVisible(true);
    }

    // [POPUP]displays popup window to select active effects
    private void selectActiveEffects() {
        enableEffectsFrame = new JFrame("Select Active Effects");
        enableEffectsFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        enableEffectsFrame.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        JLabel label = new JLabel();
        label.setText("Select effects to disable:");
        c.gridx = 0;
        c.gridy = 0;
        enableEffectsFrame.add(label, c);


        JPanel selectEffectButtons = new JPanel();
        selectEffectButtons.setLayout(new GridBagLayout());
        int xCoord = 0;
        int yCoord = 0;

        for(int i = 0; i < effects.length; i++) {
            c.weightx = 0.5;
            c.weighty = 0.5;
            c.insets = new Insets(10,10,10,10);
            c.fill = GridBagConstraints.BOTH;
            c.gridx = xCoord;
            c.gridy = yCoord;
            JToggleButton temp = new JToggleButton(effects[i]);
            selectEffectButtons.add(temp, c);
            xCoord++;
            if(xCoord % 5 == 0) {
                xCoord = 0;
                yCoord++;
            }
        }

        c.gridx = 0;
        c.gridy = 1;
        enableEffectsFrame.add(selectEffectButtons, c);

        enableEffectsFrame.setSize(800, 510);
        enableEffectsFrame.setUndecorated(false);
        enableEffectsFrame.setLocationByPlatform(true);
        enableEffectsFrame.setVisible(true);
    }

    // [POPUP] displays GUI with campaign, combat, and effect buttons
    // combat mode and strip selection buttons generated here. Calls CampaignButtons() and EffectButtons() to generate campaign and effect buttons
    // web scraper polling code is also here
    private void createAndShowGUI() {
        System.out.println("Image name: " + imageName);
        // FOR TESTING ONLY
        // ---------------------------------------------------------------------
        // SerialPort list[] = SerialPort.getCommPorts();
        // for(int i = 0; i < list.length; i++) {
        // System.out.println("PORT: " + list[i] + list[i].getDescriptivePortName() +
        // "\n");
        // }
        // FOR TESTING ONLY
        // ---------------------------------------------------------------------

        frame = new JFrame("LED20");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // mouse listener to update background image when a menu is closed

        // create and add MenuBar
        // frame.setJMenuBar((createMenuBar()));

        // set layout of GUI master frame
        frame.setLayout(new GridBagLayout());
        GridBagConstraints frameConstraints = new GridBagConstraints();

        // create and add background color
        // frame.setBackground(new Color(0, 0, 0));
        frame.getContentPane().setBackground(new Color(50,50,50));
        //TODO: BACKGROUND
        // ImageIcon backgroundIcon = new ImageIcon(imageName);
        // Image img = backgroundIcon.getImage();
        // int width = backgroundIcon.getIconWidth();
        // int height = backgroundIcon.getIconHeight();
        // if(width < 1920) {
        //     width = 1920;
        // }
        // if(height < 1080) {
        //     height = 1080;
        // }
        // Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // ImageIcon image = new ImageIcon(scaledImg);
        // frame.setContentPane(new JLabel(image));
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        // create panel to hold elements
        panel = new JPanel(new GridLayout(4, campaigns.length));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.BOTH;
        
        // create and add campaign buttons
        CampaignBtns();

        // create and add LED strip selection buttons
        JToggleButton AccentHigh = new JToggleButton("Accent Low");
        AccentHigh.setActionCommand("Accent Low");
        AccentHigh.addActionListener(new ButtonClickListener());
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10,10,10,10);
        panel.add(AccentHigh, c);
        JToggleButton AccentLow = new JToggleButton("Accent High");
        AccentLow.setActionCommand("Accent High");
        AccentLow.addActionListener(new ButtonClickListener());
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10,10,10,10);
        panel.add(AccentLow, c);
        JToggleButton TableStrip = new JToggleButton("Table Strip");
        TableStrip.setActionCommand("Table Strip");
        TableStrip.addActionListener(new ButtonClickListener());
        c.gridx = 0;
        c.gridy = 1;
        panel.add(TableStrip, c);

        // create and add combat button
        JButton combatButton = new JButton("Combat Mode");
        combatButton.setBackground(new Color(175,50,50));
        combatButton.setActionCommand("Combat Mode");
        combatButton.addActionListener(new ButtonClickListener());
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,10,10,10);
        c.gridwidth = 3;
        c.insets = new Insets(10,10,10,10);
        panel.add(combatButton, c);

        // create panel to hold effect buttons
        effectPanel = new JPanel(new GridBagLayout());
        // create and add effect buttons
        EffectBtns();

        frameConstraints.fill = GridBagConstraints.BOTH;
        frame.add(panel, frameConstraints);

        frameConstraints.fill = GridBagConstraints.BOTH;
        frame.add(effectPanel, frameConstraints);
        
        frame.setSize(800, 475);
        frame.setUndecorated(false);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        // SCRAPER CODE STARTS HERE
        // poll HP from scraper txts
        
        if(webScraper) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    boolean isChanged = false;
                    int prevNum = 0;
                    if(combatMode){
                        try{
                            for (int num = 1; num <= numPlayers; num++){
                                Scanner scanner = new Scanner(new File(playerHPCurs[num]));
                                if (scanner.hasNextInt()){
                                    prevNum = playerHP[num][0];
                                    playerHP[num][0] = scanner.nextInt();
                                    if (prevNum != playerHP[num][0]){
                                        isChanged = true;
                                    }
                                }
                            }
                            for (int num = 1; num <= numPlayers; num++){
                                Scanner scanner = new Scanner(new File(playerHPMaxs[num]));
                                if (scanner.hasNextInt()){
                                    prevNum = playerHP[num][1];
                                    playerHP[num][1] = scanner.nextInt();
                                    if (prevNum != playerHP[num][1]){
                                        isChanged = true;
                                    }
                                }
                            }
                            for (int num = 1; num <= numPlayers; num++){
                                playerHPPercentage[num] = 100 * playerHP[num][0] / playerHP[num][1];
                            }
                            if (isChanged){
                                System.out.println("Got new HP from files.");
                                writeToPort();
                            }
                        } catch (Exception e){
                            System.out.println("Error reading HP from files: " + e.toString());
                        }
                    }
                }
            };
            
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(task, new Date(), 5000);
        }
        
        // SCRAPER CODE ENDS HERE
        
    }

    // [POPUP] displays popup window with manual combat mode controls
    // called when user clicks "Combat Mode" button from main GUI screen
    public void manualCombatMode() {
        manualCombatFrame = new JFrame("Combat Mode Controls");
        manualCombatFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        manualCombatFrame.setLayout(new GridBagLayout());
        manualCombatFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                    manualCombatFrame.dispose();
                    combatMode = false;
                    writeToPort();
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        JPanel healDamagePanel = new JPanel();
        healDamagePanel.setLayout(new GridBagLayout());

        JSpinner healDamage = new JSpinner(new SpinnerNumberModel(0,0,10000,1));
        c.insets = new Insets(10,10,0,5);
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        healDamagePanel.add(healDamage, c);

        // death saves panels
        JPanel playerOneDeathSavesPanel = new JPanel();
        JPanel playerTwoDeathSavesPanel = new JPanel();
        JPanel playerThreeDeathSavesPanel = new JPanel();
        JPanel playerFourDeathSavesPanel = new JPanel();
        JPanel playerFiveDeathSavesPanel = new JPanel();
        JPanel playerSixDeathSavesPanel = new JPanel();
        JPanel playerSevenDeathSavesPanel = new JPanel();

        JButton heal = new JButton("HEAL");
        heal.setBackground(new Color(64,210,80));
        c.insets = new Insets(10,5,0,10);
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        healDamagePanel.add(heal, c);

        JButton damage = new JButton("DAMAGE");
        damage.setBackground(new Color(210,64,64));
        
        c.insets = new Insets(5,5,0,10);
        c.gridx = 1;
        c.gridy = 1;
        healDamagePanel.add(damage,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        manualCombatFrame.add(healDamagePanel, c);

        JPanel playerHPPanel = new JPanel();
        playerHPPanel.setLayout(new GridBagLayout());

        // player one hp controls --------------------------------------------------------------------------------------------
        JPanel playerOnePanel = new JPanel();
        playerOnePanel.setLayout(new GridBagLayout());
        
        
        c.insets = new Insets(10,10,0,10);
        c.gridx = 0;
        c.gridy = 0;
        JToggleButton playerOneSelect = new JToggleButton("<html><B>P1 HP</B></html>");
        playerOnePanel.add(playerOneSelect, c);

        c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridy = 1;
        JLabel p1currentHPLabel = new JLabel("Current: " + playerHP[1][0]);
        playerOnePanel.add(p1currentHPLabel, c);
        
        c.insets = new Insets(0,10,5,10);
        c.gridx = 0;
        c.gridy = 2;
        JLabel p1maxHPLabel = new JLabel("Max: " + playerHP[1][1]);
        playerOnePanel.add(p1maxHPLabel, c);
        
        c.insets = new Insets(5,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        playerOneDeathSavesPanel.setLayout(new BoxLayout(playerOneDeathSavesPanel, BoxLayout.Y_AXIS));
        playerOneDeathSavesPanel.setVisible(false);

        JLabel deathSavesP1 = new JLabel("<html><B>Death Saves:</B></html>");
        playerOneDeathSavesPanel.add(deathSavesP1);

        JLabel playerOnePasses = new JLabel("Passes: " + deathSaves[1][0]);
        playerOneDeathSavesPanel.add(playerOnePasses);

        JLabel playerOneFails = new JLabel("Fails: " + deathSaves[1][1]);
        playerOneDeathSavesPanel.add(playerOneFails);

        JButton playerOnePass = new JButton("Pass");
        playerOneDeathSavesPanel.add(playerOnePass);

        JButton playerOneFail = new JButton("Fail");
        playerOneDeathSavesPanel.add(playerOneFail);

        playerOnePanel.add(playerOneDeathSavesPanel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        playerHPPanel.add(playerOnePanel, c);

        // player two hp controls --------------------------------------------------------------------------------------------
        JPanel playerTwoPanel = new JPanel();
        playerTwoPanel.setLayout(new GridBagLayout());

        c.insets = new Insets(10,10,0,10);
        c.gridx = 0;
        c.gridy = 0;
        JToggleButton playerTwoSelect = new JToggleButton("<html><B>P2 HP</B></html>");
        playerTwoPanel.add(playerTwoSelect, c);

        c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridy = 1;
        JLabel p2currentHPLabel = new JLabel("Current: " + playerHP[2][0]);
        playerTwoPanel.add(p2currentHPLabel, c);
        
        c.insets = new Insets(0,10,5,10);
        c.gridx = 0;
        c.gridy = 2;
        JLabel p2maxHPLabel = new JLabel("Max: " + playerHP[2][1]);
        playerTwoPanel.add(p2maxHPLabel, c);

        c.insets = new Insets(5,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        playerTwoDeathSavesPanel.setLayout(new BoxLayout(playerTwoDeathSavesPanel, BoxLayout.Y_AXIS));
        playerTwoDeathSavesPanel.setVisible(false);

        JLabel deathSavesP2 = new JLabel("<html><B>Death Saves:</B></html>");
        playerTwoDeathSavesPanel.add(deathSavesP2);

        JLabel playerTwoPasses = new JLabel("Passes: " + deathSaves[2][0]);
        playerTwoDeathSavesPanel.add(playerTwoPasses);
        JLabel playerTwoFails = new JLabel("Fails: " + deathSaves[2][1]);
        playerTwoDeathSavesPanel.add(playerTwoFails);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 4;
        JButton playerTwoPass = new JButton("Pass");
        playerTwoDeathSavesPanel.add(playerTwoPass);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 5;
        JButton playerTwoFail = new JButton("Fail");
        playerTwoDeathSavesPanel.add(playerTwoFail);

        playerTwoPanel.add(playerTwoDeathSavesPanel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        playerHPPanel.add(playerTwoPanel, c);

        // player three hp controls --------------------------------------------------------------------------------------------
        JPanel playerThreePanel = new JPanel();
        playerThreePanel.setLayout(new GridBagLayout());

        c.insets = new Insets(10,10,0,10);
        c.gridx = 0;
        c.gridy = 0;
        JToggleButton playerThreeSelect = new JToggleButton("<html><B>P3 HP</B></html>");
        playerThreePanel.add(playerThreeSelect, c);

        c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridy = 1;
        JLabel p3currentHPLabel = new JLabel("Current: " + playerHP[3][0]);
        playerThreePanel.add(p3currentHPLabel, c);
        
        c.insets = new Insets(0,10,5,10);
        c.gridx = 0;
        c.gridy = 2;
        JLabel p3maxHPLabel = new JLabel("Max: " + playerHP[3][1]);
        playerThreePanel.add(p3maxHPLabel, c);
        
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        playerThreeDeathSavesPanel.setLayout(new BoxLayout(playerThreeDeathSavesPanel, BoxLayout.Y_AXIS));
        playerThreeDeathSavesPanel.setVisible(false);

        JLabel deathSavesP3 = new JLabel("<html><B>Death Saves:</B></html>");
        playerThreeDeathSavesPanel.add(deathSavesP3);

        JLabel playerThreePasses = new JLabel("Passes: " + deathSaves[3][0]);
        playerThreeDeathSavesPanel.add(playerThreePasses);
        JLabel playerThreeFails = new JLabel("Fails: " + deathSaves[3][1]);
        playerThreeDeathSavesPanel.add(playerThreeFails);
        
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 4;
        JButton playerThreePass = new JButton("Pass");
        playerThreeDeathSavesPanel.add(playerThreePass);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 5;
        JButton playerThreeFail = new JButton("Fail");
        playerThreeDeathSavesPanel.add(playerThreeFail);

        playerThreePanel.add(playerThreeDeathSavesPanel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 2;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        playerHPPanel.add(playerThreePanel, c);

        // player four hp controls --------------------------------------------------------------------------------------------
        JPanel playerFourPanel = new JPanel();
        playerFourPanel.setLayout(new GridBagLayout());

        c.insets = new Insets(10,10,0,10);
        c.gridx = 0;
        c.gridy = 0;
        JToggleButton playerFourSelect = new JToggleButton("<html><B>P4 HP</B></html>");
        playerFourPanel.add(playerFourSelect, c);

        c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridy = 1;
        JLabel p4currentHPLabel = new JLabel("Current: " + playerHP[4][0]);
        playerFourPanel.add(p4currentHPLabel, c);
        
        c.insets = new Insets(0,10,5,10);
        c.gridx = 0;
        c.gridy = 2;
        JLabel p4maxHPLabel = new JLabel("Max: " + playerHP[4][1]);
        playerFourPanel.add(p4maxHPLabel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        playerFourDeathSavesPanel.setLayout(new BoxLayout(playerFourDeathSavesPanel, BoxLayout.Y_AXIS));
        playerFourDeathSavesPanel.setVisible(false);

        JLabel deathSavesP4 = new JLabel("<html><B>Death Saves:</B></html>");
        playerFourDeathSavesPanel.add(deathSavesP4);

        JLabel playerFourPasses = new JLabel("Passes: " + deathSaves[4][0]);
        playerFourDeathSavesPanel.add(playerFourPasses);
        JLabel playerFourFails = new JLabel("Fails: " + deathSaves[4][1]);
        playerFourDeathSavesPanel.add(playerFourFails);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 4;
        JButton playerFourPass = new JButton("Pass");
        playerFourDeathSavesPanel.add(playerFourPass);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 5;
        JButton playerFourFail = new JButton("Fail");
        playerFourDeathSavesPanel.add(playerFourFail);

        playerFourPanel.add(playerFourDeathSavesPanel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 3;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        playerHPPanel.add(playerFourPanel, c);

        // player five hp controls --------------------------------------------------------------------------------------------
        JPanel playerFivePanel = new JPanel();
        playerFivePanel.setLayout(new GridBagLayout());

        c.insets = new Insets(10,10,0,10);
        c.gridx = 0;
        c.gridy = 0;
        JToggleButton playerFiveSelect = new JToggleButton("<html><B>P5 HP</B></html>");
        playerFivePanel.add(playerFiveSelect, c);

        c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridy = 1;
        JLabel p5currentHPLabel = new JLabel("Current: " + playerHP[5][0]);
        playerFivePanel.add(p5currentHPLabel, c);
        
        c.insets = new Insets(0,10,5,10);
        c.gridx = 0;
        c.gridy = 2;
        JLabel p5maxHPLabel = new JLabel("Max: " + playerHP[5][1]);
        playerFivePanel.add(p5maxHPLabel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        playerFiveDeathSavesPanel.setLayout(new BoxLayout(playerFiveDeathSavesPanel, BoxLayout.Y_AXIS));
        playerFiveDeathSavesPanel.setVisible(false);

        JLabel deathSavesP5 = new JLabel("<html><B>Death Saves:</B></html>");
        playerFiveDeathSavesPanel.add(deathSavesP5);

        JLabel playerFivePasses = new JLabel("Passes: " + deathSaves[5][0]);
        playerFiveDeathSavesPanel.add(playerFivePasses);
        JLabel playerFiveFails = new JLabel("Fails: " + deathSaves[5][1]);
        playerFiveDeathSavesPanel.add(playerFiveFails);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 4;
        JButton playerFivePass = new JButton("Pass");
        playerFiveDeathSavesPanel.add(playerFivePass);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 5;
        JButton playerFiveFail = new JButton("Fail");
        playerFiveDeathSavesPanel.add(playerFiveFail);

        playerFivePanel.add(playerFiveDeathSavesPanel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 4;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        playerHPPanel.add(playerFivePanel, c);

        // player six hp controls --------------------------------------------------------------------------------------------
        JPanel playerSixPanel = new JPanel();
        playerSixPanel.setLayout(new GridBagLayout());

        c.insets = new Insets(10,10,0,10);
        c.gridx = 0;
        c.gridy = 0;
        JToggleButton playerSixSelect = new JToggleButton("<html><B>P6 HP</B></html>");
        playerSixPanel.add(playerSixSelect, c);

        c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridy = 1;
        JLabel p6currentHPLabel = new JLabel("Current: " + playerHP[6][0]);
        playerSixPanel.add(p6currentHPLabel, c);
        
        c.insets = new Insets(0,10,5,10);
        c.gridx = 0;
        c.gridy = 2;
        JLabel p6maxHPLabel = new JLabel("Max: " + playerHP[6][1]);
        playerSixPanel.add(p6maxHPLabel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        playerSixDeathSavesPanel.setLayout(new BoxLayout(playerSixDeathSavesPanel, BoxLayout.Y_AXIS));
        playerSixDeathSavesPanel.setVisible(false);

        JLabel deathSavesP6 = new JLabel("<html><B>Death Saves:</B></html>");
        playerSixDeathSavesPanel.add(deathSavesP6);

        JLabel playerSixPasses = new JLabel("Passes: " + deathSaves[6][0]);
        playerSixDeathSavesPanel.add(playerSixPasses);
        JLabel playerSixFails = new JLabel("Fails: " + deathSaves[6][1]);
        playerSixDeathSavesPanel.add(playerSixFails);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 4;
        JButton playerSixPass = new JButton("Pass");
        playerSixDeathSavesPanel.add(playerSixPass);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 5;
        JButton playerSixFail = new JButton("Fail");
        playerSixDeathSavesPanel.add(playerSixFail);

        playerSixPanel.add(playerSixDeathSavesPanel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 5;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        playerHPPanel.add(playerSixPanel, c);

        // player seven hp controls --------------------------------------------------------------------------------------------
        JPanel playerSevenPanel = new JPanel();
        playerSevenPanel.setLayout(new GridBagLayout());

        c.insets = new Insets(10,10,0,10);
        c.gridx = 0;
        c.gridy = 0;
        JToggleButton playerSevenSelect = new JToggleButton("<html><B>P7 HP</B></html>");
        playerSevenPanel.add(playerSevenSelect, c);

        c.insets = new Insets(5,10,5,10);
        c.gridx = 0;
        c.gridy = 1;
        JLabel p7currentHPLabel = new JLabel("Current: " + playerHP[7][0]);
        playerSevenPanel.add(p7currentHPLabel, c);
        
        c.insets = new Insets(0,10,5,10);
        c.gridx = 0;
        c.gridy = 2;
        JLabel p7maxHPLabel = new JLabel("Max: " + playerHP[7][1]);
        playerSevenPanel.add(p7maxHPLabel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        playerSevenDeathSavesPanel.setLayout(new BoxLayout(playerSevenDeathSavesPanel, BoxLayout.Y_AXIS));
        playerSevenDeathSavesPanel.setVisible(false);

        JLabel deathSavesP7 = new JLabel("<html><B>Death Saves:</B></html>");
        playerSevenDeathSavesPanel.add(deathSavesP7);

        JLabel playerSevenPasses = new JLabel("Passes: " + deathSaves[7][0]);
        playerSevenDeathSavesPanel.add(playerSevenPasses);
        JLabel playerSevenFails = new JLabel("Fails: " + deathSaves[7][1]);
        playerSevenDeathSavesPanel.add(playerSevenFails);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 4;
        JButton playerSevenPass = new JButton("Pass");
        playerSevenDeathSavesPanel.add(playerSevenPass);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 5;
        JButton playerSevenFail = new JButton("Fail");
        playerSevenDeathSavesPanel.add(playerSevenFail);

        playerSevenPanel.add(playerSevenDeathSavesPanel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 6;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        playerHPPanel.add(playerSevenPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        manualCombatFrame.add(playerHPPanel, c);

        // NOTE TO DEVS: action listeners need to be at the bottom of this function so that the elements exist before they are modified

        // heal button action listener
        heal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // check which players to heal
                try{
                    if(playerOneSelect.isSelected()) {
                        playerHP[1][0] += Integer.parseInt(healDamage.getValue().toString());
                        p1currentHPLabel.setText("Current: " + playerHP[1][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[1]);
                        fw.write(playerHP[1][0]);
                        fw.close();
                    }
                    if(playerTwoSelect.isSelected()) {
                        playerHP[2][0] += Integer.parseInt(healDamage.getValue().toString());
                        p2currentHPLabel.setText("Current: " + playerHP[2][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[2]);
                        fw.write(playerHP[2][0]);
                        fw.close();
                    }
                    if(playerThreeSelect.isSelected()) {
                        playerHP[3][0] += Integer.parseInt(healDamage.getValue().toString());
                        p3currentHPLabel.setText("Current: " + playerHP[3][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[3]);
                        fw.write(playerHP[3][0]);
                        fw.close();
                    }
                    if(playerFourSelect.isSelected()) {
                        playerHP[4][0] += Integer.parseInt(healDamage.getValue().toString());
                        p4currentHPLabel.setText("Current: " + playerHP[4][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[4]);
                        fw.write(playerHP[4][0]);
                        fw.close();
                    }
                    if(playerFiveSelect.isSelected()) {
                        playerHP[5][0] += Integer.parseInt(healDamage.getValue().toString());
                        p5currentHPLabel.setText("Current: " + playerHP[5][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[5]);
                        fw.write(playerHP[5][0]);
                        fw.close();
                    }
                    if(playerSixSelect.isSelected()) {
                        playerHP[6][0] += Integer.parseInt(healDamage.getValue().toString());
                        p6currentHPLabel.setText("Current: " + playerHP[6][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[6]);
                        fw.write(playerHP[6][0]);
                        fw.close();
                    }
                    if(playerSevenSelect.isSelected()) {
                        playerHP[7][0] += Integer.parseInt(healDamage.getValue().toString());
                        p7currentHPLabel.setText("Current: " + playerHP[7][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[7]);
                        fw.write(playerHP[7][0]);
                        fw.close();
                    }
                } catch (Exception ex){
                    System.out.println("ya done fucked up");
                }
                // hide death saves panel if HP > 0
                if(playerHP[1][0] > 0) {
                    playerOneDeathSavesPanel.setVisible(false);
                    deathSaves[1][0] = 0;
                    playerOnePasses.setText("Passes: " + deathSaves[1][0]);
                    deathSaves[1][1] = 0;
                    playerOneFails.setText("Fails: " + deathSaves[1][1]);
                }
                if(playerHP[2][0] > 0) {
                    playerTwoDeathSavesPanel.setVisible(false);
                    deathSaves[2][0] = 0;
                    playerTwoPasses.setText("Passes: " + deathSaves[2][0]);
                    deathSaves[2][1] = 0;
                    playerTwoFails.setText("Fails: " + deathSaves[2][1]);
                }
                if(playerHP[3][0] > 0) {
                    playerThreeDeathSavesPanel.setVisible(false);
                    deathSaves[3][0] = 0;
                    playerThreePasses.setText("Passes: " + deathSaves[3][0]);
                    deathSaves[3][1] = 0;
                    playerThreeFails.setText("Fails: " + deathSaves[3][1]);
                }
                if(playerHP[4][0] > 0) {
                    playerFourDeathSavesPanel.setVisible(false);
                    deathSaves[4][0] = 0;
                    playerFourPasses.setText("Passes: " + deathSaves[4][0]);
                    deathSaves[4][1] = 0;
                    playerFourFails.setText("Fails: " + deathSaves[4][1]);
                }
                if(playerHP[5][0] > 0) {
                    playerFiveDeathSavesPanel.setVisible(false);
                    deathSaves[5][0] = 0;
                    playerFivePasses.setText("Passes: " + deathSaves[5][0]);
                    deathSaves[5][1] = 0;
                    playerFiveFails.setText("Fails: " + deathSaves[5][1]);
                }
                if(playerHP[6][0] > 0) {
                    playerSixDeathSavesPanel.setVisible(false);
                    deathSaves[6][0] = 0;
                    playerSixPasses.setText("Passes: " + deathSaves[6][0]);
                    deathSaves[6][1] = 0;
                    playerSixFails.setText("Fails: " + deathSaves[6][1]);
                }
                if(playerHP[7][0] > 0) {
                    playerSevenDeathSavesPanel.setVisible(false);
                    deathSaves[7][0] = 0;
                    playerSevenPasses.setText("Passes: " + deathSaves[7][0]);
                    deathSaves[7][1] = 0;
                    playerSevenFails.setText("Fails: " + deathSaves[7][1]);
                }
                
                // recalculate HP percentages
                for (int num = 1; num <= numPlayers; num++){
                    playerHPPercentage[num] = 100 * playerHP[num][0] / playerHP[num][1];
                }

                // write new HP values and percentages to JSON file
                if(!currentCampaign.equals("DEFAULT")) {
                    String playerHPString = "";
                    String playerHPPercentageString = "";
                    for(int i = 0; i <= numPlayers; i++) {
                        playerHPString += playerHP[i][0] + "," + playerHP[i][1] + ",";
                        playerHPPercentageString += playerHPPercentage[i] + ",";
                    }
                    modifyCampaignJSONElement(currentCampaign, "playerHPValues", playerHPString);
                    modifyCampaignJSONElement(currentCampaign, "playerHPPercentages", playerHPPercentageString);
                }

                writeToPort();
            }
        });

        // damage button action listener
        damage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // check which players to damage
                try{
                    if(playerOneSelect.isSelected()) {
                        playerHP[1][0] -= Integer.parseInt(healDamage.getValue().toString());
                        if(playerHP[1][0] < 0) {
                            playerHP[1][0] = 0;
                        }
                        p1currentHPLabel.setText("Current: " + playerHP[1][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[1]);
                        fw.write(playerHP[1][0]);
                        fw.close();
                    }
                    if(playerTwoSelect.isSelected()) {
                        playerHP[2][0] -= Integer.parseInt(healDamage.getValue().toString());
                        if(playerHP[2][0] < 0) {
                            playerHP[2][0] = 0;
                        }
                        p2currentHPLabel.setText("Current: " + playerHP[2][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[2]);
                        fw.write(playerHP[2][0]);
                        fw.close();
                    }
                    if(playerThreeSelect.isSelected()) {
                        playerHP[3][0] -= Integer.parseInt(healDamage.getValue().toString());
                        if(playerHP[3][0] < 0) {
                            playerHP[3][0] = 0;
                        }
                        p3currentHPLabel.setText("Current: " + playerHP[3][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[3]);
                        fw.write(playerHP[3][0]);
                        fw.close();
                    }
                    if(playerFourSelect.isSelected()) {
                        playerHP[4][0] -= Integer.parseInt(healDamage.getValue().toString());
                        if(playerHP[4][0] < 0) {
                            playerHP[4][0] = 0;
                        }
                        p4currentHPLabel.setText("Current: " + playerHP[4][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[4]);
                        fw.write(playerHP[4][0]);
                        fw.close();
                    }
                    if(playerFiveSelect.isSelected()) {
                        playerHP[5][0] -= Integer.parseInt(healDamage.getValue().toString());
                        if(playerHP[5][0] < 0) {
                            playerHP[5][0] = 0;
                        }
                        p5currentHPLabel.setText("Current: " + playerHP[5][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[5]);
                        fw.write(playerHP[5][0]);
                        fw.close();
                    }
                    if(playerSixSelect.isSelected()) {
                        playerHP[6][0] -= Integer.parseInt(healDamage.getValue().toString());
                        if(playerHP[6][0] < 0) {
                            playerHP[6][0] = 0;
                        }
                        p6currentHPLabel.setText("Current: " + playerHP[6][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[6]);
                        fw.write(playerHP[6][0]);
                        fw.close();
                    }
                    if(playerSevenSelect.isSelected()) {
                        playerHP[7][0] -= Integer.parseInt(healDamage.getValue().toString());
                        if(playerHP[7][0] < 0) {
                            playerHP[7][0] = 0;
                        }
                        p7currentHPLabel.setText("Current: " + playerHP[7][0]);
                        FileWriter fw = new FileWriter(playerHPCurs[7]);
                        fw.write(playerHP[7][0]);
                        fw.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // check if any players at zero HP to show death save buttons
                if(playerHP[1][0] <= 0) {
                    playerOneDeathSavesPanel.setVisible(true);
                }
                if(playerHP[2][0] <= 0) {
                    playerTwoDeathSavesPanel.setVisible(true);
                }
                if(playerHP[3][0] <= 0) {
                    playerThreeDeathSavesPanel.setVisible(true);
                }
                if(playerHP[4][0] <= 0) {
                    playerFourDeathSavesPanel.setVisible(true);
                }
                if(playerHP[5][0] <= 0) {
                    playerFiveDeathSavesPanel.setVisible(true);
                }
                if(playerHP[6][0] <= 0) {
                    playerSixDeathSavesPanel.setVisible(true);
                }
                if(playerHP[7][0] <= 0) {
                    playerSevenDeathSavesPanel.setVisible(true);
                }

                // recalculate HP percentages
                for (int num = 1; num <= numPlayers; num++){
                    playerHPPercentage[num] = 100 * playerHP[num][0] / playerHP[num][1];
                }

                writeToPort();

                // write new HP values and percentages to file
                if(!currentCampaign.equals("DEFAULT")) {
                    String playerHPString = "";
                    String playerHPPercentageString = "";
                    for(int i = 0; i <= numPlayers; i++) {
                        playerHPString += playerHP[i][0] + "," + playerHP[i][1] + ",";
                        playerHPPercentageString += playerHPPercentage[i] + ",";
                    }
                    modifyCampaignJSONElement(currentCampaign, "playerHPValues", playerHPString);
                    modifyCampaignJSONElement(currentCampaign, "playerHPPercentages", playerHPPercentageString);
                }
            }
        });

        // death save pass action listeners
        // player one passes
        playerOnePass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[1][0]++;
                if(deathSaves[1][0] > 3) {
                    deathSaves[1][0] = 3;
                }
                playerOnePasses.setText("Passes:" + Integer.toString(deathSaves[1][0]));
                writeToPort();
            }
        });
        // player two passes
        playerTwoPass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[2][0]++;
                if(deathSaves[2][0] > 3) {
                    deathSaves[2][0] = 3;
                }
                playerTwoPasses.setText("Passes:" + Integer.toString(deathSaves[2][0]));
                writeToPort();
            }
        });
        // player three passes
        playerThreePass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[3][0]++;
                if(deathSaves[3][0] > 3) {
                    deathSaves[3][0] = 3;
                }
                playerThreePasses.setText("Passes:" + Integer.toString(deathSaves[3][0]));
                writeToPort();
            }
        });
        // player four passes
        playerFourPass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[4][0]++;
                if(deathSaves[4][0] > 3) {
                    deathSaves[4][0] = 3;
                }
                playerFourPasses.setText("Passes:" + Integer.toString(deathSaves[4][0]));
                writeToPort();
            }
        });
        // player five passes
        playerFivePass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[5][0]++;
                if(deathSaves[5][0] > 3) {
                    deathSaves[5][0] = 3;
                }
                playerFivePasses.setText("Passes:" + Integer.toString(deathSaves[5][0]));
                writeToPort();
            }
        });
        // player six passes
        playerSixPass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[6][0]++;
                if(deathSaves[6][0] > 3) {
                    deathSaves[6][0] = 3;
                }
                playerSixPasses.setText("Passes:" + Integer.toString(deathSaves[6][0]));
                writeToPort();
            }
        });
        // player seven passes
        playerSevenPass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[7][0]++;
                if(deathSaves[7][0] > 3) {
                    deathSaves[7][0] = 3;
                }
                playerSevenPasses.setText("Passes:" + Integer.toString(deathSaves[7][0]));
                writeToPort();
            }
        });
        // death save fail action listeners
        // player one fails
        playerOneFail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[1][1]++;
                if(deathSaves[1][1] > 3) {
                    deathSaves[1][1] = 3;
                }
                playerOneFails.setText("Fails: " + Integer.toString(deathSaves[1][1]));
                writeToPort();
            }
        });
        // player two fails
        playerTwoFail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[2][1]++;
                if(deathSaves[2][1] > 3) {
                    deathSaves[2][1] = 3;
                }
                playerTwoFails.setText("Fails: " + Integer.toString(deathSaves[2][1]));
                writeToPort();
            }
        });
        // player three fails
        playerThreeFail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[3][1]++;
                if(deathSaves[3][1] > 3) {
                    deathSaves[3][1] = 3;
                }
                playerThreeFails.setText("Fails: " + Integer.toString(deathSaves[3][1]));
                writeToPort();
            }
        });
        // player four fails
        playerFourFail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[4][1]++;
                if(deathSaves[4][1] > 3) {
                    deathSaves[4][1] = 3;
                }
                playerFourFails.setText("Fails: " + Integer.toString(deathSaves[4][1]));
                writeToPort();
            }
        });
        // player five fails
        playerFiveFail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[5][1]++;
                if(deathSaves[5][1] > 3) {
                    deathSaves[5][1] = 3;
                }
                playerFiveFails.setText("Fails: " + Integer.toString(deathSaves[5][1]));
                writeToPort();
            }
        });
        // player six fails
        playerSixFail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[6][1]++;
                if(deathSaves[6][1] > 3) {
                    deathSaves[6][1] = 3;
                }
                playerSixFails.setText("Fails: " + Integer.toString(deathSaves[6][1]));
                writeToPort();
            }
        });
        // player seven fails
        playerSevenFail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deathSaves[7][1]++;
                if(deathSaves[7][1] > 3) {
                    deathSaves[7][1] = 3;
                }
                playerSevenFails.setText("Fails: " + Integer.toString(deathSaves[7][1]));
                writeToPort();
            }
        });

        p1currentHPLabel.setText("Current: " + playerHP[1][0]);
        p2currentHPLabel.setText("Current: " + playerHP[2][0]);
        p3currentHPLabel.setText("Current: " + playerHP[3][0]);
        p4currentHPLabel.setText("Current: " + playerHP[4][0]);
        p5currentHPLabel.setText("Current: " + playerHP[5][0]);
        p6currentHPLabel.setText("Current: " + playerHP[6][0]);
        p7currentHPLabel.setText("Current: " + playerHP[7][0]);

        p1maxHPLabel.setText("Current: " + playerHP[1][1]);
        p2maxHPLabel.setText("Current: " + playerHP[2][1]);
        p3maxHPLabel.setText("Current: " + playerHP[3][1]);
        p4maxHPLabel.setText("Current: " + playerHP[4][1]);
        p5maxHPLabel.setText("Current: " + playerHP[5][1]);
        p6maxHPLabel.setText("Current: " + playerHP[6][1]);
        p7maxHPLabel.setText("Current: " + playerHP[7][1]);


        manualCombatFrame.setSize(1125, 400);
        manualCombatFrame.setUndecorated(false);
        manualCombatFrame.setLocationByPlatform(true);
        manualCombatFrame.setVisible(true);

    }

    // [UNUSED] creates menu bar currently used only for nonfunctional combat menu
    // TODO: remove as part of removing combat menu
    // private JMenuBar createMenuBar() {
    //     JMenuBar menuBar = new JMenuBar();
    //     menuBar.addMouseListener(new MenuListener());
    //     ;
    //     menuBar.add(createCombatMenu());
    //     return menuBar;
    // }
    
    // [UNUSED] drop down menu for combat settings seen in main GUI (active players, separator LEDs, number of players)
    // TODO: remove as it is replaced by campaign presets
    // TODO: add a way for non-campaign users to set number of players, separator LEDs, and active players

    // [HELPER] creates campaign buttons on main GUI screen from stored campaigns in settings.json
    @SuppressWarnings("unchecked")
    private void CampaignBtns() {
        // populate campaign array from JSON
        JSONParser jsonParser = new JSONParser();
        String[] colors = {};
        try (FileReader reader = new FileReader("GUI//src//campaigns.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray presetList = (JSONArray) obj;
            ArrayList<String> campaignNames = new ArrayList<String>();
            ArrayList<String> buttonColors = new ArrayList<String>(); 
            presetList.forEach(preset -> {
                JSONObject presetObject = (JSONObject) preset;
                String campaignName = (String) presetObject.get("campaignName");
                campaignNames.add(campaignName);
                String buttonColor = (String) presetObject.get("buttonColor");
                buttonColors.add(buttonColor);
            });
            campaigns = campaignNames.toArray(new String[0]);
            colors = buttonColors.toArray(new String[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(10,10,10,10);
        c.insets = new Insets(5,5,5,5);
        for (int i = 0; i < campaigns.length; i++) {
            JButton temp = createBtn(campaigns[i]);
            String[] components = colors[i].split(",");
            temp.setBackground(new Color(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2])));
            c.gridx = i;
            panel.add(temp, c);
        }

    }

    // [HELPER] creates effect buttons on main GUI screen
    // TODO: currently creates buttons for all effects, should be changed to only create buttons based on active effects settings
    private void EffectBtns() {
        GridBagConstraints c = new GridBagConstraints();
        int xCoord = 0;
        int yCoord = 0;
        for (int i = 0; i < effects.length; i++) {
            c.gridx = xCoord;
            c.gridy = yCoord;
            c.weightx = 0.5;
            c.weighty = 0.5;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(10,10,10,10);
            xCoord++;
            JButton effect = createBtn(effects[i]);
            effectPanel.add(effect, c);
            if(xCoord == 5) {
                xCoord = 0;
                yCoord++;
            }
        }
    }

    // [HELPER] create a button with parameter text and add a listener to it
    private JButton createBtn(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(buttonText);
        button.addActionListener(new ButtonClickListener());
        return button;
    }

    // copies values of separator indices of parameter campaign in settings.json to active separators[] array
    @SuppressWarnings("unchecked")
    public int[] parseSeparatorString(String campaignName) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("GUI//src//campaigns.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray presetList = (JSONArray) obj;
            presetList.forEach(preset -> {
                JSONObject presetObject = (JSONObject) preset;
                String name = (String) presetObject.get("campaignName");
                if(name.equals(campaignName)) {
                    System.out.println(presetObject.get("separatorIndices").toString());
                    String separatorString = presetObject.get("separatorIndices").toString();
                    String[] separatorStringParts = separatorString.replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
                    int[] separators = new int[separatorStringParts.length];
                    for(int i = 0; i < separatorStringParts.length; i++) {
                        separators[i] = Integer.parseInt(separatorStringParts[i]);
                    }
                    separatorIndices = separators;
                }
            });
            return separatorIndices;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return separatorIndices;
    }

    // writes effect global variable to currentEffectHigh.txt
    // currently only uses the network RPi interface
    // TODO: change this to to a com port interface
    public void writeEffectHigh() {
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Table\\Desktop\\currentEffectHigh.txt");
            fw.write(effect);
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // writes effect global variable to currentEffectLow.txt
    // currently only uses the network RPi interface
    // TODO: change this to to a com port interface
    public void writeEffectLow() {
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Table\\Desktop\\currentEffectLow.txt");
            fw.write(effect);
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // writes combat mode status, effect, player HP percentages, player death saves, and separator indices to table strip com port
    public void writeToPort() {
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
        portWriteString += "E" + effect + "," + effectAccentOne + "," + effectAccentTwo + "*";

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
        if (!devMode) {
            // write to port
            byte[] bytes = portWriteString.getBytes(StandardCharsets.UTF_8);
            
            OutputStream out = comPort.getOutputStream();
            // System.out.println("BAUD RATE: " + comPort.getBaudRate());
            try {
                out.write(bytes);
                out.close();
                System.out.print("NO ERRORS\t");
            } catch (Exception e) {
                e.printStackTrace();
            }
            comPort.flushIOBuffers();
            // comPort.closePort();
        }
        System.out.println("Done writing.\n");

    }

    // [JSON] adds a new campaign to settings.json
    // TODO: add check for if campaign already exists
    @SuppressWarnings("unchecked")
    public void addNewCampaignToJSON(String campaignName, String presetEffect, Color buttonColor, String playerCount, String imagePath) {

        JSONArray presetList = new JSONArray(); // dummy initialization to avoid angry errors

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("GUI//src//campaigns.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            presetList = (JSONArray) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject newPreset = new JSONObject();

        // add campaign name
        newPreset.put("campaignName", campaignName);

        // add preset effect
        newPreset.put("effect", presetEffect);

        // add campaign button color
        String colorString = buttonColor.getRed() + "," + buttonColor.getGreen() + "," + buttonColor.getBlue();
        newPreset.put("buttonColor", colorString);

        // add numPlayers
        newPreset.put("numPlayers", playerCount);

        // add playerHPPercentage
        newPreset.put("playerHPPercentage", "[100, 100, 100, 100, 100, 100, 100, 100]");

        // add playerHPValues
        newPreset.put("playerHPValues", "[[100, 100], [100, 100], [100, 100], [100, 100], [100, 100], [100, 100], [100, 100], [100, 100]]");

        // add deathSaves
        newPreset.put("deathSaves", "[[0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0], [0, 0]]");

        // add separatorIndices
        newPreset.put("separatorIndices", "[0, 8, 9, 46, 47, 81, 82, 119, 120, 174, 175, 212, 213, 248, 249, 286]");

        // add customBackground
        newPreset.put("customBackground", imagePath);

        // add new preset to presetList in json file
        presetList.add(newPreset);

        try (FileWriter file = new FileWriter("GUI//src//campaigns.json")) {
            file.write(presetList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // [JSON] modifies an existing campaign in settings.json
    @SuppressWarnings("unchecked")
    public void modifyCampaignJSONElement(String campaignName, String elementName, String newElementValue) {
        JSONArray presetList = new JSONArray(); // dummy initialization to avoid angry errors

        try {
            // read from existing settings.json
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader("GUI//src//campaigns.json");
            Object obj = jsonParser.parse(reader);
            presetList = (JSONArray) obj;
            presetList.forEach(preset -> {
                JSONObject presetObject = (JSONObject) preset;
                String currentName = (String) presetObject.get("campaignName");
                if(currentName.equals(campaignName)) {
                    presetObject.put(elementName, newElementValue);
                }
            });

        } catch (Exception e) {
            System.out.println("JSON Element modify error: " + e);
        }

        try (FileWriter file = new FileWriter("GUI//src//campaigns.json")) {
            file.write(presetList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // WOA2 IDs
    static int Roguen = 92433451;
    static int Blobert = 83984360;
    static int Sharpe = 83859064;
    static int Plus = 56369743;
    static int Arlene = 92881761;
    static int Anahel = 91590052;
    static int Lucien = 84185913;

    // LEFT: Zack, Ray, Josh, Seth

    // RIGHT: Miah, Maya, James


    // static int[] IDs = { // ORIGINAL ORDER
    //     Roguen,   Blobert,   Sharpe,   Plus,   Arlene,   Anahel,   Lucien
    // };
    static int[] IDs = { // CHANGED TO COUNTER CLOCKWISE FROM DM
        Lucien, Anahel, Arlene, Plus, Sharpe, Blobert, Roguen
    };
    // 91590052

    // SAKURA IDs
    // static int[] IDs = {
    //     45287318,   92881761,   58182262,   61556127,   45207279,   75718740,   46382806
    //     //josh  ,   jack   ,   samu   ,     seth   ,    patrick   , sam     ,   ray
    // };

    class AutoHPTask implements Runnable {
        public void run() {
    
            String baseURL = "https://character-service.dndbeyond.com/character/v5/character/";
    
            // get each character sheet in JSON form and save to a file
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            String[] newPlayerConditions = {"", "", "", "", "", "", "", ""};

            for(int currentPlayer = 0; currentPlayer < IDs.length; currentPlayer++) {
    
                try {
    
                    // Create URL object
                    URL url = new URL(baseURL + IDs[currentPlayer]);
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
    
                    // // Filename to save to
                    // BufferedWriter writer = new BufferedWriter(new FileWriter("character" + ID + ".json"));
    
                    // // read each line from stream till end
                    // String line;
                    // while((line = reader.readLine()) != null) {
                    //     writer.write(line);
                    // }
                    // writer.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    
                Object obj = null;
                try {
                    // obj = new JSONParser().parse(new FileReader("character" + ID + ".json"));
                    obj = new JSONParser().parse(reader);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
    
                JSONObject jo = (JSONObject) obj;
    
                Map data = ((Map)jo.get("data"));
                
                String name = (String) data.get("name");
                System.out.print("Name: " + name + "\t");
    
                String baseHP = "0";
                String removedHP = "0";
                String tempHP = "0";
                String bonusHP = "0";
                String conScore = "0";
                String bonusConScore = "0";
    
                int maxHP = 0;
    
                try {
                    Object baseHPObj = data.get("baseHitPoints");
                    if(baseHPObj != null) baseHP = data.get("baseHitPoints").toString();
    
                    Object removedHPObj = data.get("removedHitPoints");
                    if(removedHPObj != null) removedHP = data.get("removedHitPoints").toString();
    
                    Object tempHPObj = data.get("temporaryHitPoints");
                    if(tempHPObj != null) tempHP = data.get("temporaryHitPoints").toString();
    
                    Object bonusHPObj = data.get("bonusHitPoints");
                    if(bonusHPObj != null) bonusHP = data.get("bonusHitPoints").toString();
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                // parse level
                // go through and total levels of each subclass
                JSONArray classes = (JSONArray) data.get("classes");
                int totalLevel = 0;
                for(int i = 0; i < classes.size(); i++) {
                    JSONObject classObj = (JSONObject) classes.get(i);
                    String level = classObj.get("level").toString();
                    totalLevel += Integer.parseInt(level);
                }
    
                // parse con score
                // str, dex, con, int, wis, cha
    
                JSONArray stats = (JSONArray) data.get("stats");
                JSONObject con = (JSONObject) stats.get(2);
                Object conScoreObj = con.get("value");
                if(conScoreObj != null) conScore = con.get("value").toString();
    
                JSONArray bonusStats = (JSONArray) data.get("bonusStats");
                JSONObject bonusCon = (JSONObject) bonusStats.get(2);
                Object bonusConScoreObj = bonusCon.get("value");
                if(bonusConScoreObj != null) bonusConScore = bonusCon.get("value").toString();
    
                int totalCon = Integer.parseInt(conScore) + Integer.parseInt(bonusConScore);
    
                // parse racial con and racial hp bonus
                JSONObject modifiers = (JSONObject) data.get("modifiers");
                JSONArray race = (JSONArray) modifiers.get("race");
                int raceCon = 0;
                int raceHP = 0;
                for(int i = 0; i < race.size(); i++) {
                    JSONObject racialFeatureObject = (JSONObject) race.get(i);
                    String subType = racialFeatureObject.get("subType").toString();
                    if(subType.equals("constitution-score")) {
                        String value = racialFeatureObject.get("value").toString();
                        raceCon += Integer.parseInt(value);
                    }
                    if(subType.equals("hit-points-per-level")) {
                        String value = racialFeatureObject.get("value").toString();
                        raceHP += Integer.parseInt(value);
                    }
                }
                // add racial con to total con
                totalCon += raceCon;
    
                // parse class con (uses same "modifiers" object as racial con)
                JSONArray modifiers_class = (JSONArray) modifiers.get("class");
                int classCon = 0;
                for(int i = 0; i < modifiers_class.size(); i++) {
                    JSONObject classFeatureObject = (JSONObject) modifiers_class.get(i);
                    String subType = classFeatureObject.get("subType").toString();
                    if(subType.equals("constitution-score")) {
                        String value = classFeatureObject.get("value").toString();
                        classCon += Integer.parseInt(value);
                    } 
                }
                // add class con to total con
                totalCon += classCon;
    
                // parse background con
                JSONArray modifiers_background = (JSONArray) modifiers.get("background");
                int backgroundCon = 0;
                for(int i = 0; i < modifiers_background.size(); i++) {
                    JSONObject backgroundFeatureObject = (JSONObject) modifiers_background.get(i);
                    String subType = backgroundFeatureObject.get("subType").toString();
                    if(subType.equals("constitution-score")) {
                        String value = backgroundFeatureObject.get("value").toString();
                        backgroundCon += Integer.parseInt(value);
                    } 
                }
                // add background con to total con
                totalCon += backgroundCon;
    
                // parse feat con
                JSONArray modifiers_feat = (JSONArray) modifiers.get("feat");
                int featCon = 0;
                for(int i = 0; i < modifiers_feat.size(); i++) {
                    JSONObject featFeatureObject = (JSONObject) modifiers_feat.get(i);
                    String subType = featFeatureObject.get("subType").toString();
                    if(subType.equals("constitution-score")) {
                        String value = featFeatureObject.get("value").toString();
                        featCon += Integer.parseInt(value);
                    } 
                }
                // add feat con to total con
                totalCon += featCon;
    
                // calculate con modifier
                int conMod = (int) Math.floor(totalCon - 10) / 2;
    
                // check for tough feat
                JSONArray feats = (JSONArray) data.get("feats");
                boolean tough = false;
                for(int i = 0; i < feats.size(); i++) {
                    JSONObject feat = (JSONObject) feats.get(i);
                    JSONObject definition = (JSONObject) feat.get("definition");
                    String featName = definition.get("name").toString();
                    if(featName.equals("Tough")) {
                        tough = true;
                        break;
                    }
                }
    
                // calculate max HP = baseHP + ( con modifier * level)
                maxHP = Integer.parseInt(baseHP) + (conMod * totalLevel) + (raceHP * totalLevel);
    
                // if the player has the tough feat, add 2 * level to maxHP
                if(tough) maxHP += 2 * totalLevel;
                System.out.print("Max HP: " + maxHP + "\t");
    
                // calculate remaining HP = maxHP - removedHP _ tempHP + bonusHP
                int remainingHP = maxHP - Integer.parseInt(removedHP) + Integer.parseInt(tempHP) + Integer.parseInt(bonusHP);
                System.out.println("Remaining HP: " + remainingHP + "\n");
    
                // calculate HP percentage
                int hpPercentage = (int) Math.floor((double) remainingHP / (double) maxHP * 100);
                System.out.println("HP Percentage: " + hpPercentage);
    
                // put new HP calculation in playerHPPercentage array *NOTE* DM is index 0, so offset by +1
                playerHPPercentage[currentPlayer + 1] = hpPercentage;

                // conditions
                /*
                    Blinded - 1         Charmed - 2         Deafened - 3        Exhausted - 4
                    Frightened - 5      Grappled - 6        Incapacitated - 7   Invisible - 8
                    Paralyzed - 9       Petrified - 10      Poisoned - 11       Prone - 12
                    Restrained - 13     Stunned - 14        Unconscious - 15
                    Format in JSON: "conditions":[{"id":4,"level":1}]
                */
    
                HashMap<Integer, String> conditionMap = new HashMap<Integer, String>();
                conditionMap.put(1, "Blinded");           // 000000000000001
                conditionMap.put(2, "Charmed");           // 000000000000010
                conditionMap.put(3, "Deafened");          // 000000000000100
                conditionMap.put(4, "Exhausted");         // 000000000001000
                conditionMap.put(5, "Frightened");        // 000000000010000
                conditionMap.put(6, "Grappled");          // 000000000100000
                conditionMap.put(7, "Incapacitated");     // 000000001000000
                conditionMap.put(8, "Invisible");         // 000000010000000
                conditionMap.put(9, "Paralyzed");         // 000000100000000
                conditionMap.put(10, "Petrified");         // 000001000000000
                conditionMap.put(11, "Poisoned");          // 000010000000000
                conditionMap.put(12, "Prone");             // 000100000000000
                conditionMap.put(13, "Restrained");        // 001000000000000
                conditionMap.put(14, "Stunned");           // 010000000000000
                conditionMap.put(15, "Unconscious");       // 100000000000000

                HashMap<Integer, Integer> conditionIntegerMap = new HashMap<Integer, Integer>();
                conditionIntegerMap.put(1, 0b000000000000001); // Blinded
                conditionIntegerMap.put(2, 0b000000000000010); // Charmed
                conditionIntegerMap.put(3, 0b000000000000100); // Deafened
                conditionIntegerMap.put(4, 0b000000000001000); // Exhausted
                conditionIntegerMap.put(5, 0b000000000010000); // Frightened
                conditionIntegerMap.put(6, 0b000000000100000); // Grappled
                conditionIntegerMap.put(7, 0b000000001000000); // Incapacitated
                conditionIntegerMap.put(8, 0b000000010000000); // Invisible
                conditionIntegerMap.put(9, 0b000000100000000); // Paralyzed
                conditionIntegerMap.put(10, 0b000001000000000); // Petrified
                conditionIntegerMap.put(11, 0b000010000000000); // Poisoned
                conditionIntegerMap.put(12, 0b000100000000000); // Prone
                conditionIntegerMap.put(13, 0b001000000000000); // Restrained
                conditionIntegerMap.put(14, 0b010000000000000); // Stunned
                conditionIntegerMap.put(15, 0b100000000000000); // Unconscious
    
                String conditionsString = "0";

                int conditionsInt = 0;

                JSONArray conditions = (JSONArray) data.get("conditions");

                for(int i = 0; i < conditions.size(); i++) {

                    JSONObject condition = (JSONObject) conditions.get(i);
                    int id = Integer.parseInt(condition.get("id").toString());
                    
                    System.out.println("Condition: " + conditionMap.get(id) + "\t");
                    if(conditionMap.get(id) != null) {
                        conditionsString = "1";
                        conditionsInt = conditionsInt | conditionIntegerMap.get(id);
                        System.out.println("Conditions String: " + conditionsString + "\n");
                        System.out.println("Conditions Int: " + conditionsInt + "\n");
                        conditionsString = conditionsInt + "";
                    }
                    // conditionsString = conditionsString + Integer.toBinaryString(id);
                    
                    // For now we do not care about different levels of exhaustion
                    // if(conditionMap.get(id).equals("Exhausted")) {
                    //     System.out.println("Level: " + level);
                    // }
                    // int level = 0;
                    // Object levelObj = condition.get("level");
                    // if(levelObj == null) level = 0;
                    // else level = Integer.parseInt(levelObj.toString());
                }
                System.out.println("Conditions String: " + conditionsString + "\n");
                newPlayerConditions[currentPlayer + 1] = conditionsString;
            }

            // update player conditions
            for(int i = 0; i < newPlayerConditions.length; i++) {
                if(!newPlayerConditions[i].equals(playerConditions[i])) {
                    playerConditions[i] = newPlayerConditions[i];                }
            }

            writeToPort();
        }
    
    }

    private static ScheduledExecutorService executorService;

    public void startAutoHP() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new AutoHPTask(), 0, 10, TimeUnit.SECONDS);
    }

    public void stopAutoHP() {
        executorService.shutdown();
    }

    
    // listener and behaviors for all buttons
    @SuppressWarnings("unchecked")
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            System.out.println(command);

            // check startup menu buttons
            if (command.equals("Continue")) {
                createAndShowGUI();
                startupFrame.dispose();
            }

            // check combat mode button
            if (command.equals("Combat Mode")) {

                combatMode = !combatMode;

                if(manualHP && combatMode) {
                    manualCombatMode();
                    try{
                        for (int num = 1; num <= numPlayers; num++){
                            Scanner scanner = new Scanner(new File(playerHPCurs[num]));
                            if (scanner.hasNextInt()){
                                playerHP[num][0] = scanner.nextInt();
                            }
                        }
                        for (int num = 1; num <= numPlayers; num++){
                            Scanner scanner = new Scanner(new File(playerHPMaxs[num]));
                            if (scanner.hasNextInt()){
                                playerHP[num][1] = scanner.nextInt();
                            }
                        }
                        for (int num = 1; num <= numPlayers; num++){
                            playerHPPercentage[num] = 100 * playerHP[num][0] / playerHP[num][1];
                        }
                    }catch(Exception ex){
                        System.out.println("THIS MESSAGE SHOULD NOT BE POSSIBLE TO SEE");
                    }
                } else if (manualHP && !combatMode) {
                    manualCombatFrame.dispose();
                } else if (!manualHP && combatMode) {
                    // auto combat mode

                    startAutoHP();

                } else if (!manualHP && !combatMode) {
                    // end auto combat mode

                    stopAutoHP();

                }
                writeToPort();
                return;
            }

            // check LED strip select buttons
            else if (command.equals("Accent High")) {
                accentHigh = !accentHigh;
                // JToggleButton button = (JToggleButton) e.getSource();
                // button.setBackground(pressedColor);
                return;
            } else if (command.equals("Accent Low")) {
                accentLow = !accentLow;
                // JToggleButton button = (JToggleButton) e.getSource();
                // button.setBackground(pressedColor);
                return;
            } else if (command.equals("Table Strip")) {
                tableStrip = !tableStrip;
                // JToggleButton button = (JToggleButton) e.getSource();
                // button.setBackground(pressedColor);
                return;
            }

            // check campaign buttons
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("GUI//src//campaigns.json")) {
                // Read JSON file
                Object obj = jsonParser.parse(reader);
                JSONArray presetList = (JSONArray) obj;
                
                presetList.forEach(preset -> {
                    JSONObject presetObject = (JSONObject) preset;
                    String name = (String) presetObject.get("campaignName");
                    if(e.getActionCommand().equals(name)) {
                        // set currentCampaign to the selected campaign name to know which JSON object to edit
                        currentCampaign = name;

                        // get health bar separator indices
                        separatorString = (String) presetObject.get("separatorIndices");

                        // get player HP percentages
                        String playerHPPercentageString = (String) presetObject.get("playerHPPercentages");
                        System.out.println(playerHPPercentageString);
                        String[] playerHPPercentageArray = playerHPPercentageString.split(",");
                        for(int i = 0; i < playerHPPercentageArray.length; i++) {
                            playerHPPercentage[i] = Integer.parseInt(playerHPPercentageArray[i]);
                        }

                        // get player HP values
                        String playerHPString = (String) presetObject.get("playerHPValues");
                        System.out.println(playerHPString);
                        String[] playerHPArray = playerHPString.split(",");
                        for(int i = 0; i <= numPlayers; i++) {
                            playerHP[i][0] = Integer.parseInt(playerHPArray[i*2]);
                            playerHP[i][1] = Integer.parseInt(playerHPArray[(i*2)+1]);   
                        }

                        
                        // get preset background
                        imageName = (String) presetObject.get("customBackground");
                        
                        // get preset effect
                        String effectString = (String) presetObject.get("effect");
                        effect = effectString.charAt(0);

                        // write preset to ports
                        writeEffectHigh();
                        writeEffectLow();
                        writeToPort();

                        // close the previous frame
                        frame.dispose();

                        // create the new frame
                        createAndShowGUI();
                    }
            });

            } catch (Exception err) {
                err.printStackTrace();
            }
            

            // check effect buttons
            if (command.equals("Sakura")) {
                effect = 'h';
            } else if (command.equals("Morning")) {
                effect = 'm';
            } else if (command.equals("Fire")) {
                effect = 'F';
            } else if (command.equals("Flashbang")) {
                effect = 'f';
            } else if (command.equals("Thunder")) {
                // try {
                //     String currentDir = new File(".").getCanonicalPath();
                //     Runtime.getRuntime().exec("cmd /c start " + currentDir + "/Thunderstorm.url");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // }
                effect = 'U';
                deathSaves[4][0]++;
            } else if (command.equals("Upside Down")) {
                // try {
                //     String currentDir = new File(".").getCanonicalPath();
                //     Runtime.getRuntime().exec("cmd /c start " + currentDir + "/StopSound.url");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // }
                effect = '?';
                deathSaves[4][1]++;
            } else if (command.equals("Day")) {
                effect = 'd';
            } else if (command.equals("Water")) {
                effect = 'w';
            } else if (command.equals("UwU")) {
                effect = 'u';
            } else if (command.equals("Snow")) {
                effect = 's';
            } else if (command.equals("Big Storm")) {
                // try {
                //     String currentDir = new File(".").getCanonicalPath();
                //     Runtime.getRuntime().exec("cmd /c start " + currentDir + "/HeightOfTheStorm.url");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // }
                effect = 'U';
                writeEffectHigh();
                effect = 'T';
                writeEffectLow();
                effect = 'w';
                writeToPort();
                return;
            } else if (command.equals("Evening")) {
                effect = 'e';
            } else if (command.equals("Candles")) {
                // try {
                //     String currentDir = new File(".").getCanonicalPath();
                //     Runtime.getRuntime().exec("cmd /c start " + currentDir + "/Charlie.url");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // }
                effect = 'c';
            } else if (command.equals("Cyber")) {
                effect = 'b';
            } else if (command.equals("Rain")) {
                // try {
                //     String currentDir = new File(".").getCanonicalPath();
                //     Runtime.getRuntime().exec("cmd /c start " + currentDir + "/Rain.url");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // }
                effect = 'r';
            } else if (command.equals("Winter")) {
                effect = 'W';
            } else if (command.equals("Night")) {
                effect = 'n';
            } else if (command.equals("Forest")) {
                effect = 't';
            } else if (command.equals("Divine")) {
                effect = 'V';
            } else if (command.equals("Typhoon")) {
                // try {
                //     String currentDir = new File(".").getCanonicalPath();
                //     Runtime.getRuntime().exec("cmd /c start " + currentDir + "/Typhoon.url");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // }
                effect = 'T';
            } else if (command.equals("Ruby")) {
                effect = 'R';
            } else if (command.equals("Alden")) {
                effect = '1';
            } else if (command.equals("Nadine's Gift")) {
                effect = '2';
            } else if (command.equals("Off")) {
                effect = 'X';
            }

            if (accentHigh) {
                writeEffectHigh();
                effectAccentTwo = effect;
            }
            if (accentLow) {
                writeEffectLow();
                effectAccentOne = effect;
            }
            if (tableStrip)
                writeToPort();
        }
    }

}