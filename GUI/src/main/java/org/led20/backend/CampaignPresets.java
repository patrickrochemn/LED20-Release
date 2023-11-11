package org.led20.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CampaignPresets {

    private static final String JSON_FILE = "campaignPresets.json";

    // Read contents from campaignPresets.json
    private JSONObject readCampaignPresets() {
        JSONParser parser = new JSONParser();
        JSONObject JSONobj = null;

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(JSON_FILE);
            if (inputStream == null) {
                throw new Exception("Campaign presets file not found");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            JSONobj = (JSONObject) parser.parse(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + JSON_FILE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return JSONobj;
    }
    

    // Write contents to campaignPresets.json
    // private void writeCampaignPresets(JSONObject campaignData) {
    //     try (FileWriter writer = new FileWriter(JSON_FILE)) {
    //         writer.write(campaignData.toJSONString());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // Get list of campaign names from campaignPresets.json
    public List<String> getCampaignNames() {
        List<String> campaignNames = new ArrayList<>();
        JSONObject rootObject = readCampaignPresets(); // This should be your JSON object that you've read
        if (rootObject != null) {
            JSONArray campaignsArray = (JSONArray) rootObject.get("campaignPresets");
            if (campaignsArray != null) {
                for (Object campaignObject : campaignsArray) {
                    JSONObject campaign = (JSONObject) campaignObject;
                    String name = (String) campaign.get("campaignName");
                    campaignNames.add(name);
                }
            }
        }
        return campaignNames;
    }
    
    public JSONObject getPresetData(String presetName) {
        JSONObject rootObject = readCampaignPresets();
        if (rootObject != null) {
            JSONArray campaignPresetsArray = (JSONArray) rootObject.get("campaignPresets");
            if (campaignPresetsArray != null) {
                for (Object campaignPresetObj : campaignPresetsArray) {
                    JSONObject campaignPreset = (JSONObject) campaignPresetObj;
                    if (campaignPreset.get("campaignName").equals(presetName)) {
                        return campaignPreset;
                    }
                }
            }
        }
        return null;
    }
    

    // Edit campaign preset
    public void editCampaignPreset(String campaignName, JSONArray players, JSONArray healthBarIndices) {
        // JSONObject campaignData = readCampaignPresets();
        // JSONArray campaignPresets = (JSONArray) campaignData.get("campaignPresets");
        // for (Object campaignPresetObj : campaignPresets) {
        //     JSONObject campaignPreset = (JSONObject) campaignPresetObj;
        //     if (campaignPreset.get("campaignName").equals(campaignName)) {
        //         campaignPreset.put("players", players);
        //         campaignPreset.put("healthBarIndices", healthBarIndices);
        //         break;
        //     }
        // }
        // writeCampaignPresets(campaignData);
    }

    // Delete campaign preset
    public void deleteCampaignPreset(String campaignName) {
        // JSONObject campaignData = readCampaignPresets();
        // JSONArray campaignPresets = (JSONArray) campaignData.get("campaignPresets");
        // for (int i = 0; i < campaignPresets.size(); i++) {
        //     JSONObject campaignPreset = (JSONObject) campaignPresets.get(i);
        //     if (campaignPreset.get("campaignName").equals(campaignName)) {
        //         campaignPresets.remove(i);
        //         break;
        //     }
        // }
        // writeCampaignPresets(campaignData);
    }

    // Create new campaign preset
    public void createCampaignPreset(String campaignName, JSONArray players, JSONArray healthBarIndices) {
        // JSONObject campaignData = readCampaignPresets();
        // JSONArray campaignPresets = (JSONArray) campaignData.get("campaignPresets");
        // JSONObject newCampaignPreset = new JSONObject();
        // newCampaignPreset.put("campaignName", campaignName);
        // newCampaignPreset.put("players", players);
        // newCampaignPreset.put("healthBarIndices", healthBarIndices);
        // campaignPresets.add(newCampaignPreset);
        // writeCampaignPresets(campaignData);
    }

}
