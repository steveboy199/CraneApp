package com.lifeinteractivesolutions.craneapp;

public class Communications {

    private boolean hasRadio;
    private boolean hasOther;
    private String other;
    private boolean hasHands;

    public Communications()
    {
        hasRadio = false;
        hasOther = false;
        hasHands = false;
        other = "";
    }
    public static String otherValue()
    {
        return "otherValue";
    }
    public String getOtherValue()
    {
        return other;
    }
    public static String getCommunicationString(int i)
    {
        String result = "";
        switch(i)
        {
            case 0:
                result = "radio";
                break;
            case 1:
                result = "Hands";
                break;
            case 2:
                result = "other";
                break;

        }
        return result;
    }
    public boolean getValueofCommunication(int i)
    {
        switch(i)
        {
            case 0:
                return hasRadio;
            case 1:
                 return hasHands;
            case 2:
                return hasOther;
            default:
                return false;
        }
    }

    public boolean setRadio(boolean b)
    {
        this.hasRadio = b;
        return this.hasRadio;
    }

    public boolean setOther(boolean b, String other)
    {
        this.hasOther = b;
        this.other = other;
        return this.hasOther;
    }
    public boolean setHands(boolean b)
    {
        this.hasHands = b;
        return this.hasHands;
    }
}
