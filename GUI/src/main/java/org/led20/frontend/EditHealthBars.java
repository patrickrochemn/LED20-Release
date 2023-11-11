package org.led20.frontend;

public class EditHealthBars {
    
    // // [POPUP] displays popup window to edit health bar indices)
    // private void editIndices(String campaignName) {
    //     JFrame editIndicesFrame = new JFrame("Edit Health Bar Indices");
    //     editIndicesFrame.setLayout(new GridBagLayout());
    //     GridBagConstraints c = new GridBagConstraints();
    //     editIndicesFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
    //     // enter edit health bar mode
    //     effect = '$';
    //     writeToPort();
        
    //     // exit edit mode when window closed
    //     editIndicesFrame.addWindowListener(new WindowAdapter() {
    //         public void windowClosing(WindowEvent evt) {
    //             effect = 'w';
    //             writeToPort();
    //             editIndicesFrame.dispose();
    //         }
    //     });

    //     JPanel adjustors = new JPanel();
    //     int[] separators = parseSeparatorString(campaignName);

    //     // P1S
    //     JLabel separator2 = new JLabel("Player 1 Start:");
    //     adjustors.add(separator2);
    //     JSpinner spinner2 = new JSpinner();
    //     spinner2.setValue(separators[2]);
    //     spinner2.setSize(300,300);
    //     adjustors.add(spinner2);

    //     // P1E
    //     JLabel separator3 = new JLabel("Player 1 End:");
    //     adjustors.add(separator3);
    //     JSpinner spinner3 = new JSpinner();
    //     spinner3.setValue(separators[3]);
    //     adjustors.add(spinner3);

    //     // P2S
    //     JLabel separator4 = new JLabel("Player 2 Start:");
    //     adjustors.add(separator4);
    //     JSpinner spinner4 = new JSpinner();
    //     spinner4.setValue(separators[4]);
    //     adjustors.add(spinner4);

    //     // P2E
    //     JLabel separator5 = new JLabel("Player 2 End:");
    //     adjustors.add(separator5);
    //     JSpinner spinner5 = new JSpinner();
    //     spinner5.setValue(separators[5]);
    //     adjustors.add(spinner5);
        
    //     // P3S
    //     JLabel separator6 = new JLabel("Player 3 Start:");
    //     adjustors.add(separator6);
    //     JSpinner spinner6 = new JSpinner();
    //     spinner6.setValue(separators[6]);
    //     adjustors.add(spinner6);

    //     // P3E
    //     JLabel separator7 = new JLabel("Player 3 End:");
    //     adjustors.add(separator7);
    //     JSpinner spinner7 = new JSpinner();
    //     spinner7.setValue(separators[7]);
    //     adjustors.add(spinner7);
        
    //     // P4S
    //     JLabel separator8 = new JLabel("Player 4 Start:");
    //     adjustors.add(separator8);
    //     JSpinner spinner8 = new JSpinner();
    //     spinner8.setValue(separators[8]);
    //     adjustors.add(spinner8);

    //     // P4E
    //     JLabel separator9 = new JLabel("Player 4 End:");
    //     adjustors.add(separator9);
    //     JSpinner spinner9 = new JSpinner();
    //     spinner9.setValue(separators[9]);
    //     adjustors.add(spinner9);
        
    //     // P5S
    //     JLabel separator10 = new JLabel("Player 5 Start:");
    //     adjustors.add(separator10);
    //     JSpinner spinner10 = new JSpinner();
    //     spinner10.setValue(separators[10]);
    //     adjustors.add(spinner10);

    //     // P5E
    //     JLabel separator11 = new JLabel("Player 5 End:");
    //     adjustors.add(separator11);
    //     JSpinner spinner11 = new JSpinner();
    //     spinner11.setValue(separators[11]);
    //     adjustors.add(spinner11);
        
    //     // P6S
    //     JLabel separator12 = new JLabel("Player 6 Start:");
    //     adjustors.add(separator12);
    //     JSpinner spinner12 = new JSpinner();
    //     spinner12.setValue(separators[12]);
    //     adjustors.add(spinner12);

    //     // P6E
    //     JLabel separator13 = new JLabel("Player 6 End:");
    //     adjustors.add(separator13);
    //     JSpinner spinner13 = new JSpinner();
    //     spinner13.setValue(separators[13]);
    //     adjustors.add(spinner13);
        
    //     // P7S
    //     JLabel separator14 = new JLabel("Player 7 Start:");
    //     adjustors.add(separator14);
    //     JSpinner spinner14 = new JSpinner();
    //     spinner14.setValue(separators[14]);
    //     adjustors.add(spinner14);

    //     // P7E
    //     JLabel separator15 = new JLabel("Player 7 End:");
    //     adjustors.add(separator15);
    //     JSpinner spinner15 = new JSpinner();
    //     spinner15.setValue(separators[15]);
    //     adjustors.add(spinner15);

    //     GridLayout adjustorLayout = new GridLayout(16, 2);
    //     adjustorLayout.setVgap(10);
    //     adjustors.setLayout(adjustorLayout);
    //     // adjustors.setLayout(new GridLayout(16, 2));

    //     c.gridx = 0;
    //     c.gridy = 0;
    //     c.fill = GridBagConstraints.HORIZONTAL;
    //     c.gridwidth = 2;
    //     editIndicesFrame.add(adjustors, c);

    //     c.gridx = 0;
    //     c.gridy = 1;
    //     c.gridwidth = 1;
    //     c.insets = new Insets(0, 0, 0, 10);
    //     JButton viewChangesBtn = createBtn("View Current Inputs");
    //     viewChangesBtn.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //             separatorIndices[2] = Integer.parseInt(spinner2.getValue().toString());
    //             separatorIndices[3] = Integer.parseInt(spinner3.getValue().toString());
    //             separatorIndices[4] = Integer.parseInt(spinner4.getValue().toString());
    //             separatorIndices[5] = Integer.parseInt(spinner5.getValue().toString());
    //             separatorIndices[6] = Integer.parseInt(spinner6.getValue().toString());
    //             separatorIndices[7] = Integer.parseInt(spinner7.getValue().toString());
    //             separatorIndices[8] = Integer.parseInt(spinner8.getValue().toString());
    //             separatorIndices[9] = Integer.parseInt(spinner9.getValue().toString());
    //             separatorIndices[10] = Integer.parseInt(spinner10.getValue().toString());
    //             separatorIndices[11] = Integer.parseInt(spinner11.getValue().toString());
    //             separatorIndices[12] = Integer.parseInt(spinner12.getValue().toString());
    //             separatorIndices[13] = Integer.parseInt(spinner13.getValue().toString());
    //             separatorIndices[14] = Integer.parseInt(spinner14.getValue().toString());
    //             separatorIndices[15] = Integer.parseInt(spinner15.getValue().toString());
    //             for (int i = 2; i < separatorIndices.length - 1; i+=2){
    //                 if (separatorIndices[i] > separatorIndices[i+1] || separatorIndices[i] > NUM_LEDS){
    //                     separatorIndices[i] = Math.min(separatorIndices[i+1] - 1, NUM_LEDS);
    //                 }
    //             }
    //             separatorString = "";
    //             for(int i = 0; i < separatorIndices.length - 1; i++) {
    //                 separatorString += separatorIndices[i] + ",";
    //             }
    //             separatorString += separatorIndices[separatorIndices.length - 1] + "";
    //             System.out.println("Got to write to port.");
    //             writeToPort();
    //         }
    //     });
    //     editIndicesFrame.add(viewChangesBtn, c);

    //     c.gridx = 1;
    //     c.gridy = 1;
    //     c.gridwidth = 1;
    //     c.insets = new Insets(0, 0, 0, 10);
    //     JButton submitChangesBtn = createBtn("Save current indices to file");
    //     submitChangesBtn.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //             separatorIndices[2] = Integer.parseInt(spinner2.getValue().toString());
    //             separatorIndices[3] = Integer.parseInt(spinner3.getValue().toString());
    //             separatorIndices[4] = Integer.parseInt(spinner4.getValue().toString());
    //             separatorIndices[5] = Integer.parseInt(spinner5.getValue().toString());
    //             separatorIndices[6] = Integer.parseInt(spinner6.getValue().toString());
    //             separatorIndices[7] = Integer.parseInt(spinner7.getValue().toString());
    //             separatorIndices[8] = Integer.parseInt(spinner8.getValue().toString());
    //             separatorIndices[9] = Integer.parseInt(spinner9.getValue().toString());
    //             separatorIndices[10] = Integer.parseInt(spinner10.getValue().toString());
    //             separatorIndices[11] = Integer.parseInt(spinner11.getValue().toString());
    //             separatorIndices[12] = Integer.parseInt(spinner12.getValue().toString());
    //             separatorIndices[13] = Integer.parseInt(spinner13.getValue().toString());
    //             separatorIndices[14] = Integer.parseInt(spinner14.getValue().toString());
    //             separatorIndices[15] = Integer.parseInt(spinner15.getValue().toString());
    //             separatorString = "";
    //             for(int i = 0; i < separatorIndices.length - 1; i++) {
    //                 separatorString += separatorIndices[i] + ",";
    //             }
    //             separatorString += separatorIndices[separatorIndices.length - 1] + "";
    //             modifyCampaignJSONElement(campaignName, "separatorIndices", separatorString);
    //         }
    //     });
    //     editIndicesFrame.add(submitChangesBtn, c);

    //     editIndicesFrame.setSize(400, 600);
    //     editIndicesFrame.setUndecorated(false);
    //     editIndicesFrame.setLocationByPlatform(true);
    //     editIndicesFrame.setVisible(true);
    // }

}
