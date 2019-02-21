package com.lifeinteractivesolutions.craneapp;

import android.content.res.Resources;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Lift implements JsonInterface, Validation{

   private HashMap<String,Double> LiftMeasurements;
   private DecimalFormat format;
   public static final String Lift_Tag = "craneapp.Lift";
   private static final String measurements_key = "JSON_ARRAY";
   private static final String communications_key = "COMMUNICATION_ARRAY";
   private static final String lift_checked_key = "LiftCheckedInitials";
   private Communications communication_storage;
   private String [] communication_array;
   private String LiftCheckedInitials;
   private UUID liftID;

   public Lift()
   {

       LiftMeasurements = new HashMap<String,Double>();
       format = new DecimalFormat("#.###############");
       communication_storage = new Communications();

       liftID = UUID.randomUUID();
       Resources r = Resources.getSystem();
       String[] Measurements = CraneInformationStorage.lift_details_array;

       for(int i = 0; i < Measurements.length; i++)
       {
           LiftMeasurements.put(Measurements[i],0.0); //remmeber to set to null;
       }
       LiftCheckedInitials = "";


   }

   public HashMap<String,Double> getLiftMeasurements()
   {
       return LiftMeasurements;
   }
   public void setLiftMeasurements(HashMap<String,Double> Lifts)
   {
       this.LiftMeasurements = Lifts;
   }



   public Communications getCommunication()
   {
       return communication_storage;
   }
   public void setCommunication(Communications communications)
   {
       this.communication_storage = communications;
   }

    public void setCommunication(int position,boolean set, String other)
    {
        if(communication_array.length == 0)
        {
            communication_array = new String[3];
        }
        switch(position)
        {
            case 0:
                if(set == true)
                {
                  communication_storage.setRadio(true);
                }
                else
                {
                    communication_storage.setRadio(false);
                }
                break;
            case 1:
                if(set == true)
                {
                    communication_storage.setRadio(true);
                }
                else
                {
                    communication_storage.setRadio(true);
                }
                break;
            case 2 :
                if(set == true)
                {
                    communication_storage.setRadio(true);
                }
                else
                {
                    communication_storage.setRadio(true);
                }
                break;
        }
    }
    public void setCommunication(String[]communication) {
        communication_array = communication;
    }

    public String getLiftCheckedInitials() {
        return LiftCheckedInitials;
    }

    public void setLiftCheckedInitials(String liftCheckedInitials) {
        LiftCheckedInitials = liftCheckedInitials;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONObject liftMeasurements = new JSONObject();
        JSONObject communication = new JSONObject();
        Resources r = Resources.getSystem();
        String[] measurements = CraneInformationStorage.lift_details_array;
     try {
         for (int i = 0; i < measurements.length; i++) {
             liftMeasurements.put(measurements[i], LiftMeasurements.get(measurements[i]));
         }
         jsonObject.put(measurements_key,liftMeasurements);

         for(int i =0; i < 3; i++)
         {
             if(communication_storage.getValueofCommunication(i) == true)
             {
                 communication.put(Communications.getCommunicationString(i), true);
             }
             else
             {
                 communication.put(Communications.getCommunicationString(i), false);
             }
             if(i ==2 && communication_storage.getValueofCommunication(i) == true)
             {
                 communication.put(Communications.otherValue() , communication_storage.getOtherValue());
             }

         }
         jsonObject.put(Lift.communications_key,communication);
         jsonObject.put(Lift.lift_checked_key,LiftCheckedInitials);



     }
     catch(JSONException e)
     {
         Log.e(Lift_Tag,e.getMessage(),e);

     }
     return jsonObject;
    }

    @Override
    public String toEmail() {
        String result = "";

        String [] keys = LiftMeasurements.keySet().toArray(new String[LiftMeasurements.keySet().size()]);
        for(int i = 0; i < keys.length; i++)
        {
            result += keys[i] + "  " + "value: " + format.format(LiftMeasurements.get(keys[i])) + "\r\n";
        }
        for(int i =0; i < 3; i++)
        {
            if(communication_storage.getValueofCommunication(0) == true)
            {
                result += "communication: " + Communications.getCommunicationString(0) + "\r\n";
            }

        }
        result += "LiftCheckedInitials: " + LiftCheckedInitials;

        return result;
    }


    public static Lift parseJson(JSONObject jsonObject)
    {
        Lift lift;
        Resources r = Resources.getSystem();
        String[] Measurements = CraneInformationStorage.lift_details_array;
        String [] lift_details_array = CraneInformationStorage.lift_details_array;
        try {
            lift = new Lift();
            JSONObject measurementObject = new JSONObject(jsonObject.get(Lift.measurements_key).toString());
            String [] measurements = CraneInformationStorage.lift_details_array;
            HashMap<String,Double> measurementHashMap = new HashMap<String,Double>();

            for(int i = 0; i < measurementObject.length(); i++)
            {
               measurementHashMap.put( Measurements[i],measurementObject.getDouble(Measurements[i] )) ;
            }
            lift.setLiftMeasurements(measurementHashMap);
            String liftCrendentials = jsonObject.getString(Lift.lift_checked_key);
            lift.setLiftCheckedInitials(liftCrendentials);
            JSONObject communicationJsonObject = jsonObject.getJSONObject(Lift.communications_key);
            Communications communications = new Communications();
            for(int i = 0; i < communicationJsonObject.length();i++)
            {
               switch(i)
               {
                   case 0:
                       communications.setRadio(communicationJsonObject.getBoolean(Communications.getCommunicationString(i)));
                       break;
                   case 1:
                       communications.setHands(communicationJsonObject.getBoolean(Communications.getCommunicationString(i)));
                       break;
                   case 2:
                       if(communicationJsonObject.getBoolean(Communications.getCommunicationString(i)) == true)
                       {
                           communications.setOther(communicationJsonObject.getBoolean(Communications.getCommunicationString(i)),communicationJsonObject.getString(Communications.otherValue()));
                       }

               }
            }
            lift.setCommunication(communications);

            return lift;



        }
        catch(JSONException e)
        {
          Log.e(Lift.Lift_Tag,e.getMessage(),e);
          return new Lift();
        }

    }

    @Override
    public boolean isValid() {
       if(LiftCheckedInitials != null || LiftCheckedInitials.length() > 0)
       {
           if(communication_array != null)
           {
               for(int i = 0; i <communication_array.length;i++)
               {
                   if(communication_array[i] != null)
                   {
                       return true;
                   }

               }
           }

       }
       return false;
    }

    public UUID getLiftID() {
        return liftID;
    }

    public void setLiftID(UUID liftID) {
        this.liftID = liftID;
    }

    public void setCommunicationsArray(Communications communication)
    {
        this.communication_storage = communication;
    }
}
