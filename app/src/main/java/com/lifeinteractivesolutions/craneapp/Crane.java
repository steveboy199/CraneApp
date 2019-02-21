package com.lifeinteractivesolutions.craneapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Crane implements JsonInterface{

    private static final String liftPlanDetailsKey = "liftPlanDetails";
    private static final String hazardChecksKey = "hazardChecks";
    private static final String liftsKey = "lifts";
    private static final String crane_name_key = "crane_name";
    private static final String tag = "craneapp.Crane";

    private LiftPlanDetails liftplandetails;
    private HazardChecks hazardchecks;
    private ArrayList<Lift> lifts;
    private String crane_name;
    public Crane(String crane_name)
    {
        this.crane_name = crane_name;
        liftplandetails = new LiftPlanDetails();
        lifts = new ArrayList<Lift>();
        hazardchecks = new HazardChecks();
    }
    public Crane(){
        liftplandetails = new LiftPlanDetails();
        lifts = new ArrayList<Lift>();
        hazardchecks = new HazardChecks();
    }

    public ArrayList<Lift> getLifts() {
        return lifts;
    }

    public void setLifts(ArrayList<Lift> lifts) {
        this.lifts = lifts;
    }

    public boolean upDateLift(Lift lift)
    {
        int indextoRemove = -1;
       for(int i = 0; i < lifts.size(); i++)
       {

            if(lifts.get(i).getLiftID() == lift.getLiftID())
            {

                indextoRemove = i;
            }
        }
        lifts.remove(indextoRemove);
        lifts.add(lift);
        return false;
    }


    public HazardChecks getHazardchecks() {
        return hazardchecks;
    }

    public void setHazardchecks(HazardChecks hazardchecks) {
        this.hazardchecks = hazardchecks;
    }



    public LiftPlanDetails getLiftplandetails() {
        return liftplandetails;
    }

    public void setLiftplandetails(LiftPlanDetails liftplandetails) {
        this.liftplandetails = liftplandetails;
    }

    public String getCrane_name() {
        return crane_name;
    }

    public void setCrane_name(String crane_name) {
        this.crane_name = crane_name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject liftPlanDetails = new JSONObject();
        JSONObject liftsJson = new JSONObject();
        JSONObject hazardChecks = new JSONObject();
        JSONObject crane = new JSONObject();

        try {
            for (int i = 0; i < lifts.size(); i++) {
                liftsJson.put(i + "", lifts.get(i).toJson());
            }
            liftPlanDetails = liftplandetails.toJson();
            hazardChecks = hazardchecks.toJson();

            crane.put(Crane.liftsKey,liftsJson);
            crane.put(Crane.hazardChecksKey,hazardChecks);
            crane.put(Crane.liftPlanDetailsKey,liftPlanDetails);
            crane.put(Crane.crane_name_key,crane_name);
        }
        catch(JSONException e)
        {
            Log.e(Crane.tag,e.getMessage(),e);
        }
        return crane;

    }

    @Override
    public String toEmail() {
        String result = "";

        result += "Crane: " + crane_name;
        for(int i = 0; i < 3; i++)
        {
            result += "\r\n";
        }
        for(int i = 0; i < lifts.size(); i++)
        {
            result += lifts.get(i).toEmail();
        }
        result += liftplandetails.toEmail();
        for(int i = 0; i < 3; i++)
        {
            result += "\r\n";
        }
        result += hazardchecks.toEmail();
        for(int i = 0; i < 3; i++)
        {
            result += "\r\n";
        }

        return result;
    }

    public boolean isLiftsValid()
    {
        boolean valid = true;
        for(Lift lift: lifts)
        {
            if(lift != null) {

                    valid = false;

            }
        }
        if(lifts.size() == 0)
        {
            valid = false;
        }
        return valid;
    }


    public static Crane parseJson(JSONObject jsonObject) {


        try{
            String craneName = jsonObject.getString(Crane.crane_name_key);
            Crane crane = new Crane(craneName);
          JSONObject liftsJson =   jsonObject.getJSONObject(Crane.liftsKey);
          JSONObject liftPlanDetails = jsonObject.getJSONObject(Crane.liftPlanDetailsKey);
          JSONObject hazardChecks = jsonObject.getJSONObject(Crane.hazardChecksKey);
          ArrayList<Lift> liftArray = new ArrayList<Lift>();
          crane.setHazardchecks(HazardChecks.parseJson(hazardChecks));
          crane.setLiftplandetails(LiftPlanDetails.parseJson(liftPlanDetails));

          for(int i = 0; i < liftsJson.length(); i++)
          {
              Lift lift = Lift.parseJson(liftsJson.getJSONObject(i + ""));
              liftArray.add(lift);
          }
          crane.setLifts(liftArray);
          return crane;
        }
        catch(JSONException e)
        {
             Log.e(Crane.tag,e.getMessage(),e);
        }

      return new Crane();
    }



}
