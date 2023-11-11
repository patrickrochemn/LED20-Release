package org.led20.backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AutoHP {

    public void getCharacterJSON(int playerID) {
        
        // get a character sheet in JSON form and save to a file
        String baseURL = "https://character-service.dndbeyond.com/character/v5/character/";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        // Create URL object
        try {
            URL playerURL = new URL(baseURL + playerID);
            reader = new BufferedReader(new InputStreamReader(playerURL.openStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
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
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) jo.get("data");
        
        String name = (String) data.get("name");
        System.out.print("Name: " + name + "\t");

        // save player json to resources folder as playerID.json
        try {
            FileWriter saveFile = new FileWriter("src/main/resources/character" + playerID + ".json");
            saveFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject readJSONFile(String fileName) {

        Object obj = null;
        try {
            FileReader reader = new FileReader(fileName);
            obj = new JSONParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }

        JSONObject jo = (JSONObject) obj;

        return jo;
    }

    public int getCharacterHP(int playerID) {

        // load player json from resources folder

        String fileName = "src/main/resources/character" + playerID + ".json";

        JSONObject jo = readJSONFile(fileName);

        @SuppressWarnings("unchecked")
        Map<String, Object> data = ((Map<String, Object>)jo.get("data"));

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
        
        // parse feat-based con
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
        
        // return HP percentae as an int
        return hpPercentage;
    }

    public int getCharacterConditions(int playerID) {
        
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
                
        int conditionsInt = 0;

        String fileName = "src/main/resources/character" + playerID + ".json";

        JSONObject jo = readJSONFile(fileName);

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) jo.get("data");


        JSONArray conditions = (JSONArray) data.get("conditions");
        
        for(int i = 0; i < conditions.size(); i++) {
            
            JSONObject condition = (JSONObject) conditions.get(i);
            int id = Integer.parseInt(condition.get("id").toString());
            
            System.out.println("Condition: " + conditionMap.get(id) + "\t");
            if(conditionMap.get(id) != null) {
                conditionsInt = conditionsInt | conditionIntegerMap.get(id);
                System.out.println("Conditions Int: " + conditionsInt + "\n");
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
            //}            
        }

        return conditionsInt;
    }
    
    
}