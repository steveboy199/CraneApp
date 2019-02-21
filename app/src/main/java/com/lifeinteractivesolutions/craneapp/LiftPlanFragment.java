package com.lifeinteractivesolutions.craneapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class LiftPlanFragment extends Fragment {

    public static String tag = "com.lifeInteractiveSolutions.craneapp.LiftPlanFragment";
    public static String date = "com.lifeInteractiveSolutions.craneapp.liftPlanFragment.date";
    public static String time = "com.lifeInteractiveSolutions.craneapp.liftPlanFragment.time";
    public static String date_tag = "com.lifeInteractiveSolutions.craneapp.DateFragment";
    public static String other_dialog_tag = "com.lifeInteractiveSolutions.craneapp.otherdialog";
    public static int date_request_code = 0;
    public static int time_request_code = 1;
    public static int other_request_code =2;
    private LiftPlanDetails details;
    private EditText dateEditText;
    private TextView textViews[];
    private EditText editTexts[];
    private CheckBox checkBoxes[];
    private String[] communication;
    private HashMap<String,String> valuesMap;
    private String date_choice = "date";
    private String time_choice = "time";
    private View v;

    public static LiftPlanFragment newInstance(LiftPlanDetails liftPlanDetails)
    {
        Bundle bundle = new Bundle();
        LiftPlanFragment liftPlanFragment = new LiftPlanFragment();
        liftPlanFragment.setDetails(liftPlanDetails);
        return liftPlanFragment;

    }

   public LiftPlanFragment()
   {
       valuesMap = new HashMap<String,String>();
       communication = new String[]{"","",""};

   }

  @Override
  public void onResume()
  {
      valuesMap = new HashMap<String,String>( details.getValuesMap());
      communication = details.getCommunication();
      if(v != null)
      {
          upDateViews();
      }


      super.onResume();
  }
  @Override
  public void onStop()
  {
      Log.d("dshfjkd",valuesMap.toString());
      details.upDateHashMap(valuesMap);
      details.setCommunication(communication);
      ( (InformationActivity)getActivity()).setLiftPlanDetails(details);
      Log.d("dshfjkd2",valuesMap.toString());
      CraneSingleton.getCraneSingleton().saveCranesToFile(getActivity().getApplicationContext());
      super.onStop();
  }
   @Override
   public void onDetach()
   {
       details.upDateHashMap(valuesMap);
       details.setCommunication(communication);
       ( (InformationActivity)getActivity()).setLiftPlanDetails(details);
       Log.d("dshfjkd","updated");
       CraneSingleton.getCraneSingleton().saveCranesToFile(getActivity().getApplicationContext());
       super.onDetach();

   }

   public void upDateViews()
   {
       int [] editText =  ResourceReferenceStorage.refrencesEditText;
       final String [] keys = CraneInformationStorage.lift_plan_details;
       valuesMap = new HashMap<String,String>(details.getValuesMap());
       for(int i = 0; i < editText.length; i++ )
       {
           editTexts[i] = (EditText)v.findViewById(editText[i]);
           editTexts[i].setText(valuesMap.get(keys[i]));
           Log.d("hfd",i + " : ");

       }
       Log.d("hfd",valuesMap.toString());

       if(checkBoxes != null)
       {
           String [] get = details.getCommunication();
           for(int i = 0; i < get.length; i++)
           {
               if(get[i] != null)
               {
                   if(!get[i].equalsIgnoreCase(""))
                   {
                       checkBoxes[i].setChecked(true);
                   }
                   else
                   {
                       checkBoxes[i].setChecked(false);
                   }

               }
               else
               {
                   checkBoxes[i].setChecked(false);
               }
           }
       }
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceSate)
    {
        v = inflater.inflate(R.layout.liftdetails_layout,parent,false);
        int [] textviews = ResourceReferenceStorage.referencesTextView;
        int [] editText =  ResourceReferenceStorage.refrencesEditText;
        textViews = new TextView[textviews.length];
        editTexts = new EditText[editText.length];

        for(int i = 0; i < textviews.length; i++)
        {
            final int i3 = i;
            textViews[i] = (TextView)v.findViewById(textviews[i]);
            textViews[i].setTextSize(new Float(17.50));
            if(CraneInformationStorage.lift_plan_details[i].indexOf('_') != -1)
            {
               // textViews[i].setText(CraneInformationStorage.lift_plan_details[i].substring(0,CraneInformationStorage.lift_plan_details[i].indexOf("_")) +
                 //       CraneInformationStorage.lift_plan_details[i].substring(CraneInformationStorage.lift_plan_details[i].indexOf("_") +1,CraneInformationStorage.lift_plan_details[i].length()));
                textViews[i].setText(CraneInformationStorage.lift_plan_details[i].replace('_', ' '));
                if(CraneInformationStorage.lift_plan_details[i].length() > 13)
                {
                    textViews[i].setTextSize(new Float(20));
                }
                if(CraneInformationStorage.lift_plan_details[i].length() > 16)
                {
                    textViews[i].setTextSize(new Float(13.50));
                }
              //  textViews[i].setText(CraneInformationStorage.lift_plan_details[i]);
            }
            else
            {
                textViews[i].setText(CraneInformationStorage.lift_plan_details[i]);
            }

            editTexts[i] = (EditText)v.findViewById(editText[i]);
            Log.d("valuesmap",valuesMap.get(CraneInformationStorage.lift_plan_details[i]));
            Log.d("valuesmap",CraneInformationStorage.lift_plan_details[i]);
            editTexts[i].setText(valuesMap.get(CraneInformationStorage.lift_plan_details[i]));
            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                       String change = charSequence.toString();

                       valuesMap.put(CraneInformationStorage.lift_plan_details[i3],change);

                    Log.d("fsdfsd",change + "   : "  + CraneInformationStorage.lift_plan_details[i3]);



                }

                @Override
                public void afterTextChanged(Editable editable) {
                  Log.i(LiftPlanFragment.tag, CraneInformationStorage.lift_plan_details[i3] + "has changed to " + editable.toString());
                    String change = editable.toString();
                    valuesMap.put(CraneInformationStorage.lift_plan_details[i3],change);

                }
            });
        }
        checkBoxes = new CheckBox[ResourceReferenceStorage.referencesCheckBox.length];
        for(int i =0; i < checkBoxes.length;i++ )
        {
            final int i2 = i;
            checkBoxes[i] = (CheckBox)v.findViewById(ResourceReferenceStorage.referencesCheckBox[i]);
            checkBoxes[i].setText(CraneInformationStorage.communication_choices[i]);
            if(!communication[i].equalsIgnoreCase(""))
            {
               checkBoxes[i].setChecked(true);
            }
            checkBoxes[i].setText("dfsdf");
            checkBoxes[i].setTextSize(40);
            checkBoxes[i].setTextColor(Color.BLACK);
            checkBoxes[i].setGravity(Gravity.RIGHT);


                checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(checkBoxes[i2].isChecked() ) {
                            if(i2 != 2)
                            {
                             Toast.makeText(getContext(), "checked", Toast.LENGTH_LONG).show();
                             communication[i2] = CraneInformationStorage.communication_choices[i2];
                             details.setCommunication(communication);


                            }
                            else
                            {
                                OtherDialog otherDialog = OtherDialog.newInstance(-1,getActivity(),-1);
                                otherDialog.setTargetFragment(LiftPlanFragment.this,LiftPlanFragment.other_request_code);
                                otherDialog.show(getFragmentManager(),LiftPlanFragment.other_dialog_tag);
                                communication[i2] = CraneInformationStorage.communication_choices[i2];
                                details.setCommunication(communication);
                            }
                        }
                        else
                        {
                            communication[i2] = "";
                            Toast.makeText(getContext(), "unchecked", Toast.LENGTH_LONG).show();
                            details.setCommunication(communication);
                        }
                    }
                });
            }



        ((ImageButton)v.findViewById(R.id.imageButton_1)).setOnClickListener(new View.OnClickListener()
            {


                @Override
                public void onClick(View view) {
                         DateFragment dateFragment = DateFragment.newInstance(date_choice);
                         dateFragment.setTargetFragment(LiftPlanFragment.this,LiftPlanFragment.date_request_code);
                         dateFragment.show(getFragmentManager(),LiftPlanFragment.date_tag);
                }
            });
        ((ImageButton)v.findViewById(R.id.imageButton_2)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)
            {
              DateFragment dateFragment = DateFragment.newInstance(time);
              dateFragment.setTargetFragment(LiftPlanFragment.this,LiftPlanFragment.time_request_code);
              dateFragment.show(getFragmentManager(),LiftPlanFragment.date_tag);


            }

        });


        ((TextView)v.findViewById(R.id.textview12)).setText("Communication");
        ((TextView)v.findViewById(R.id.textview13)).setText(CraneInformationStorage.communication_choices[0]);
        ((TextView)v.findViewById(R.id.textview14)).setText(CraneInformationStorage.communication_choices[1]);
        ((TextView)v.findViewById(R.id.textview15)).setText(CraneInformationStorage.communication_choices[2]);

        upDateViews();

        return v;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onActivityResult(int RequestCode,int resultCode, Intent data)
    {
        if(RequestCode == LiftPlanFragment.date_request_code && resultCode == Activity.RESULT_OK)
        {
            Date date = new Date(data.getStringExtra(LiftPlanFragment.date));
            details.setDateOfLift(date);
            Toast.makeText(getActivity(),date.toString(),Toast.LENGTH_LONG).show();
            ((EditText)v.findViewById(R.id.EditText9)).setText(details.getDateOfLift().toString());
        }
        else if(RequestCode == LiftPlanFragment.time_request_code && resultCode == Activity.RESULT_OK)
        {
            Date date = new Date(data.getStringExtra(LiftPlanFragment.time));
            Time time = new Time(date.getTime());
            details.setTimeforDate(time);
            Toast.makeText(getActivity(),time.toString(),Toast.LENGTH_LONG).show();
            ((EditText)v.findViewById(R.id.EditText9)).setText(details.getDateOfLift().toString());

        }
        else if(RequestCode == LiftPlanFragment.other_request_code && resultCode == Activity.RESULT_OK)
        {
          communication[2] = data.getStringExtra(OtherDialog.answerKey);
        }

    }
    public LiftPlanDetails getDetails() {
        return details;
    }

    public void setDetails(LiftPlanDetails details) {
        this.details = details;
        this.valuesMap = new HashMap<String,String>(details.getValuesMap());
    }
}
