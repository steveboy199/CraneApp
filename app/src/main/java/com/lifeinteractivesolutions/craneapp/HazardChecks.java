package com.lifeinteractivesolutions.craneapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.res.Resources;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class HazardChecks  implements JsonInterface, Validation {

    private HashMap<String,Boolean> hazardsChecked;
    public static final String tag = "craneapp.HazardChecks";
    Resources r;


    public HazardChecks() {
               r = Resources.getSystem();
               hazardsChecked = new HashMap<String,Boolean>();
              int length_of_hazards_array = CraneInformationStorage.crane_hazards_checkList.length;
              for(int i = 0; i < length_of_hazards_array; i++)
              {
                 hazardsChecked.put(CraneInformationStorage.crane_hazards_checkList[i],false);
              }
    }

    public HashMap<String, Boolean> getHazardsChecked()
    {
        return hazardsChecked;
    }
    public void setHazardsChecked(HashMap<String, Boolean> hazardsChecked) {
        this.hazardsChecked = hazardsChecked;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        String [] hazard_array=   CraneInformationStorage.crane_hazards_checkList;
        try {
            for (int i = 0; i < hazard_array.length; i++) {
                jsonObject.put(hazard_array[i], hazardsChecked.get(hazard_array[i]).booleanValue());
            }
        }
        catch(JSONException j)
        {
            Log.e(HazardChecks.tag,j.getMessage(),j);
        }
        return jsonObject;
    }

    @Override
    public String toEmail() {
        Set<String> hazard = hazardsChecked.keySet();
        String result = "Hazards " + "\r\n";
        for(int i = 0; i < hazard.size();i++)
        {

            result += hazard.toArray()[i]  + " : " + (hazardsChecked.get(hazard.toArray()[i]) ? " checked" : " not checked") ;
            result += "\r\n";
        }
        for(int i =0; i < 100; i++)
        {
            result += "---";
        }
        return result;
    }

    public static HazardChecks parseJson(JSONObject object)
    {
        Resources r = Resources.getSystem();
        HazardChecks hazardChecks = new HazardChecks();
        String [] hazard_checks_array =  CraneInformationStorage.crane_hazards_checkList;
        HashMap<String,Boolean> hazard_checks_map = new HashMap<String,Boolean>();
        try{
            for(int i = 0; i < hazard_checks_array.length; i++)
            {
                hazard_checks_map.put(hazard_checks_array[i], object.getBoolean(hazard_checks_array[i]));
            }
            hazardChecks.setHazardsChecked(hazard_checks_map);
        }
        catch(JSONException e)
        {
            Log.e(HazardChecks.tag,e.getMessage(),e);
        }
        return hazardChecks;

    }


    @Override
    public boolean isValid() {
        boolean isValid = true;
        String[] keySet = hazardsChecked.keySet().toArray(new String [hazardsChecked.size()]);
        for(int i = 0; i < keySet.length; i++)
        {
            if(hazardsChecked.get(keySet[i]) != true)
            {
                isValid = false;
            }
        }

        return isValid;
    }
}
