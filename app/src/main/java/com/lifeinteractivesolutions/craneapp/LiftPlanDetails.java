package com.lifeinteractivesolutions.craneapp;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LiftPlanDetails implements JsonInterface, Validation{

    private HashMap<String,String> valuesMap;
    public static final String tag = "craneapp.LiftPlanDetail";
    private   UUID    mUUID;
    private Date date_of_lift;
    private String[]communication;
    private String lift_description;

    public LiftPlanDetails()
    {
     date_of_lift = new Date();
     mUUID = UUID.randomUUID();
     valuesMap = new HashMap<String,String>();
     communication = new String[3];


     valuesMap.put("customer","");
     valuesMap.put("site_represenative","");
     valuesMap.put("crane_supplier","");
     valuesMap.put("crane_id","");
     valuesMap.put("crane_make","");
     valuesMap.put("first_aider","");
     valuesMap.put("crane_operator","");
     valuesMap.put("dog_man","");
     valuesMap.put("date_of_lift",date_of_lift.toString());
     valuesMap.put("location","");
     valuesMap.put("lift_description","");
    }

   public void setTimeforDate(Time time)
   {
       date_of_lift.setTime(time.getTime());
       valuesMap.put("date_of_lift",date_of_lift.toString());
   }
    public HashMap<String,String> getValuesMap()
    {

        return valuesMap;

    }

    public void upDateHashMap(HashMap<String,String> valuesMap)
    {
        this.valuesMap = new HashMap<String,String>(valuesMap);
        Log.d("three",this.valuesMap.toString());
    }
    public void setDateOfLift(Date date)
    {
        this.date_of_lift = date;
        valuesMap.put("date_of_lift",date_of_lift.toString());
    }
    public Date getDateOfLift()
    {
        return date_of_lift;
    }

    @Override
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        Set<String> keys = valuesMap.keySet();
        String [] keyArray = keys.toArray(new String[keys.size()]);
        try {
            for (int i = 0; i < keyArray.length; i++) {
                jsonObject.put(keyArray[i], valuesMap.get(keyArray[i]));
                Log.d("keys",keyArray[i]);
            }
            JSONObject obj = new JSONObject();
            if(communication != null) {
                for (int i = 0; i < communication.length; i++) {
                    obj.put(i + "", communication[i]);
                }
            }
            jsonObject.put("communication",obj);
        }
        catch (JSONException e) {
          Log.e( LiftPlanDetails.tag,e.getMessage(),e);
        }
        return jsonObject;
    }

    @Override
    public String toEmail() {
        String result = "";
        for(int i = 0; i < 100; i++)
        {
            result += "------";
        }
        result += "\r\n";
        String [] keys = valuesMap.keySet().toArray(new String[valuesMap.keySet().size()]);
        for(int i = 0; i < keys.length; i++)
        {
            result += keys[i] + " : " + valuesMap.get(keys[i]) + " \r\n";
        }
        for(int i =0; i <communication.length;i++)
        {
            if(communication[i] != null)
            {
                result += "Communication : " + communication[i];
                result += "\r\n";
            }

        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public static  LiftPlanDetails parseJson(JSONObject jsonObject) {
        LiftPlanDetails liftPlanDetails = new LiftPlanDetails();
        HashMap<String,String> valuesMap = new HashMap<String,String>();

        try {
            valuesMap.put("customer",jsonObject.getString("customer"));
            valuesMap.put("site_represenative",jsonObject.getString("site_represenative"));
            valuesMap.put("crane_supplier",jsonObject.getString("crane_supplier"));
            valuesMap.put("crane_id",jsonObject.getString("crane_id"));
            valuesMap.put("crane_make",jsonObject.getString("crane_make"));
            valuesMap.put("first_aider",jsonObject.getString("first_aider"));
            valuesMap.put("crane_operator",jsonObject.getString("crane_operator"));
            valuesMap.put("dog_man",jsonObject.getString("dog_man"));
            valuesMap.put("date_of_lift",jsonObject.getString("date_of_lift"));
            valuesMap.put("location",jsonObject.getString("location"));
            valuesMap.put("lift_description",jsonObject.getString("lift_description"));
            liftPlanDetails.upDateHashMap(valuesMap);
            Log.d(LiftPlanDetails.tag,"date is " + valuesMap.get("date_of_lift"));
            try
            {
                liftPlanDetails.setDateOfLift(new Date(valuesMap.get("date_of_lift")));
                return liftPlanDetails;
            }catch(Exception e) {
                valuesMap.put("date_of_lift", new Date().toString());
                liftPlanDetails.setDateOfLift(new Date(valuesMap.get("date_of_lift")));


                JSONObject object = jsonObject.getJSONObject("communication");
                if (object == null) {
                    Log.d(LiftPlanDetails.tag, "Its null");
                } else {
                    Log.d(LiftPlanDetails.tag, "not null");
                }
                Log.d(LiftPlanDetails.tag, object.toString());
                String[] communication = new String[3];
                for (int i = 0; i < object.length(); i++) {
                    communication[i] = object.getString(i + "");
                }
                liftPlanDetails.setCommunication(communication);
                return liftPlanDetails;
            }
        }
        catch(JSONException e)
        {
            Log.e(LiftPlanDetails.tag,e.getMessage(),e);
            return new LiftPlanDetails();
        }


    }

    @Override
    public boolean isValid() {
        boolean isValid = true;
        int counter = 0;
        Set<String> keySet =valuesMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        for(int i = 0; i < keyArray.length; i++)
        {
            if(valuesMap.get(keyArray[i]).equalsIgnoreCase(""))
            {
                isValid = false;
            }
        }
        for(int i =0; i < communication.length; i++)
        {

            if(communication[i] != null && communication[i].equalsIgnoreCase(""))
            {
                counter += 1;
            }

        }
        if(counter == 3)
        {
            isValid = false;
        }
        return isValid;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }
    public String[] getCommunication() {
        return communication;
    }

    public void setCommunication(String[] communication) {
        this.communication = communication;
    }

}
