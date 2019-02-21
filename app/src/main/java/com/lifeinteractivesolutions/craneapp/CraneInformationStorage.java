package com.lifeinteractivesolutions.craneapp;

public class CraneInformationStorage {

    public static String[] crane_hazards_checkList = {"Work Place Hazards","Crime Hazards","Crane Operator ticketed",
                                                     "Dogman Ticketed","Fatigue Manage","PPE correct", "crane cert correct",
                                                      "Lifting equip certs correct","Rating charts correct","counter weight correct",
                                                      "reeving correct","Outtriggers Exit Pinned", "Vision Conditions Checked",
                                                      "Weather Conditions Checked","Traffic Managed","Radio Checked", "Airport notified",
                                                      "Hazard notification required","Crane setup as per lift plan","Sli Configured and working",
                                                      "Electrical Hazards","Crane is earthed","Ground conditions Checked","obstructions Checked",
                                                      "No simultaneous operations","Mancage checked","Confined space checked","Enviroment checked"};

    public static String[] lift_details_array = {"Weight of Load:","Weight of rigging:","Weight of hooks:","Total","Boom Length",
                                                 "PickUp Radius","Set Down Radius","Max radius","Swl at max radius","LiftChecked Initials"};


    public static String [] crane_choices = {"Lift", "LiftPlanDetails","HazardChecks"};

    public static String [] lift_plan_details = {"customer","site_represenative","crane_supplier","crane_id","crane_make","first_aider","crane_operator","dog_man","date_of_lift","location","lift_description"};

    public static String [] communication_choices = {"Radio","Hands","other"};


}
