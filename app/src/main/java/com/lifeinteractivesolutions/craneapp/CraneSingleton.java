package com.lifeinteractivesolutions.craneapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class CraneSingleton {

    public static CraneSingleton craneSingleton;
    public static String FILE_NAME ="cranes_storage.txt";
    public static String EMAIL_SUBJECT = "Crane Info";
    private ArrayList<Crane> list_of_cranes;
    public static String craneSingleTon ="com.lifeInteractiveSOlutions.craneapp.CraneSingleton";
    public CraneSingleton()
    {
      list_of_cranes = new ArrayList<Crane>();
    }
    public static CraneSingleton getCraneSingleton()
    {
        if(CraneSingleton.craneSingleton == null)
        {
            CraneSingleton.craneSingleton = new CraneSingleton();
            return CraneSingleton.craneSingleton;
        }
        return CraneSingleton.craneSingleton;

    }
    public Crane getCrane(String crane_name)
    {
        Crane crane = null;
        for(int i = 0; i < list_of_cranes.size(); i++)
        {
            if(list_of_cranes.get(i).getCrane_name().equalsIgnoreCase(crane_name))
            {
                crane = list_of_cranes.get(i);
            }
        }
        return crane;
    }
    public void upDateCrane(Crane crane)
    {
        for(int i = 0; i < list_of_cranes.size(); i++)
        {
            if(list_of_cranes.get(i).getCrane_name().equalsIgnoreCase(crane.getCrane_name()))
            {
                list_of_cranes.set(i , crane);
            }
        }
    }
    public void addNewCrane(String craneName,Context context)
    {
        boolean found = false;
        for(int i = 0; i < list_of_cranes.size(); i++)
        {
            if(list_of_cranes.get(i).getCrane_name().equalsIgnoreCase(craneName))
            {
                found = true;
            }
        }

        if(found == false)
        {
            list_of_cranes.add(new Crane(craneName));
        }
        else
        {
            Toast.makeText(context,"Crane name already exist, try new name, crane creation failed",Toast.LENGTH_LONG).show();
        }

    }
    public ArrayList<Crane> getArrayList()
    {
        return this.list_of_cranes;
    }
    public void updateArrayList(ArrayList<Crane> list_of_cranes)
    {
        this.list_of_cranes = list_of_cranes;
    }

    public boolean readCranesFromFile(Context context)
    {
        try {

            String jsonArray = "";
            File file = new File(context.getFilesDir(), CraneSingleton.FILE_NAME);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            while(reader.read() != -1)
            {
               jsonArray += reader.readLine();
            }

            Log.i(CraneSingleton.craneSingleTon,jsonArray.toString());
            JSONObject array = new JSONObject("{" +jsonArray +"}");
            list_of_cranes.clear();
            for(int i = 0; i < array.length();i++)
            {
               JSONObject craneObject =  array.getJSONObject(i + "");
               Crane crane = Crane.parseJson(craneObject);
               list_of_cranes.add(crane);
            }

            return true;
        }
        catch(FileNotFoundException f)
        {
             Toast.makeText(context,"we failed 1", Toast.LENGTH_LONG).show();
             return false;
        }
        catch(IOException e)
        {
            Toast.makeText(context,"we failed 2", Toast.LENGTH_LONG).show();
            return false;
        }
        catch(JSONException e)
        {
            Toast.makeText(context,e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(craneSingleTon,e.getMessage(),e);
            return false;
        }
    }

    public boolean saveCranesToFile(Context context)
    {
        try{
            File file = new File(context.getFilesDir(),CraneSingleton.FILE_NAME);
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileoutputstream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            JSONObject jsonObjectArray = new JSONObject();
            for(int i  = 0 ; i < list_of_cranes.size(); i++)
            {
                jsonObjectArray.put(i+"",list_of_cranes.get(i).toJson());
            }
            writer.write(jsonObjectArray.toString());
            writer.flush();
            writer.close();
            Toast.makeText(context, "We succeded", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch(FileNotFoundException e)
        {
          return false;
        }
        catch(IOException e)
        {
             return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void mailTo(Context context, String mailTo, int position)
    {
       Intent emailIntent = new Intent(Intent.ACTION_SEND);
       emailIntent.setData(Uri.parse("mailto:"));
       emailIntent.setType("text/plain");
       emailIntent.putExtra(Intent.EXTRA_EMAIL,mailTo);
       emailIntent.putExtra(Intent.EXTRA_SUBJECT,CraneSingleton.EMAIL_SUBJECT);
       emailIntent.putExtra(Intent.EXTRA_TEXT,list_of_cranes.get(position).toEmail());
        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }



    }
}
