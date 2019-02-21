package com.lifeinteractivesolutions.craneapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;


public class LiftDetailsFragment extends Fragment {

    public static String tag = "com.lifeinteractivesolutions.craneapp.LiftDetailsFragment";
    public static final int request_code = 1;
    public int loaded = 0;

    private HashMap<String,Double> liftMeasurements;

    private Lift lift;

    private Crane mCrane;
    public static LiftDetailsFragment newInstance(Crane crane, Lift lift)
    {
        LiftDetailsFragment liftDetailsFragment = new LiftDetailsFragment();
        liftDetailsFragment.setDetails(lift);
        liftDetailsFragment.setCrane(crane);
        liftDetailsFragment.setLiftMeasurements(lift.getLiftMeasurements());

        return liftDetailsFragment;
    }

    public void setDetails(Lift lift)
    {
        this.lift = lift;
    }
    Context mContext;
    EditText[] measurements;
    EditText totalEditText;
    CheckBox[] communication_checkbox;
    public LiftDetailsFragment()
    {

        mContext = getActivity();
    }
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        measurements = new EditText[3];
        communication_checkbox = new CheckBox[3];
        DisplayMetrics displayMetrics;
        displayMetrics = Resources.getSystem().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width =  displayMetrics.widthPixels;
        int heightDP = pxToDp(height);
        int widthDP = pxToDp(width);
        Log.i(LiftDetailsFragment.tag,"Width is " + width + " pixels");
        Log.i(LiftDetailsFragment.tag,"height is " + height + " pixels");
        Log.i(LiftDetailsFragment.tag,"Width is " + widthDP + " denstity pixels");
        Log.i(LiftDetailsFragment.tag,"height is " + heightDP + " density pixels");
        ViewGroup  v = new ViewGroup(getContext()) {
            @Override
            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

            }};

        ScrollView   scrollView = new ScrollView(getActivity());
        ScrollView.LayoutParams scrollLayoutParams = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollLayoutParams.setMargins(70,0,0,0);
        scrollView.setLayoutParams(scrollLayoutParams);

        LinearLayout layout = new LinearLayout(getActivity());

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for( int w =0; w < CraneInformationStorage.lift_details_array.length; w++)
        {
            final int i = w;
        //   getActivity().getWindowManager().getDefaultDisplay().getMetrics();
           LinearLayout layout1 = new LinearLayout(getActivity());
           layout1.setOrientation(LinearLayout.HORIZONTAL);
           TextView textView = new TextView(getActivity());
           textView.setText(CraneInformationStorage.lift_details_array[i]);
           int heightT = widthDP * (1/5);
          LinearLayout.LayoutParams params1 =new LinearLayout.LayoutParams(((int)(width /2.5) ) + 1 ,(height/10) );

           //LinearLayout.LayoutParams params1 =new LinearLayout.LayoutParams(400,100);
           textView.setLayoutParams(params1);
           textView.setTextSize(22);

            TextView textView2 = new TextView(getActivity());

            textView2.setTextSize(22);


           final EditText editText = new EditText(getActivity());
           editText.setBackgroundResource(R.drawable.text_background2);
           editText.setPadding(10,0,0,0);

           LinearLayout.LayoutParams params2 =new LinearLayout.LayoutParams(360,(height/15));
            Log.d(LiftDetailsFragment.tag,"" + width /3  );
           editText.setLayoutParams(params2);
           layout1.addView(textView);
           layout1.addView(editText);
           layout1.addView(textView2);
           layout.addView(layout1);
            if(i <= 3){
                textView2.setText("Kg");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.setText(liftMeasurements.get(CraneInformationStorage.lift_details_array[i]) + "");
                if(i< 3) {
                    measurements[i] = editText;
                }
                if(i == 3)
                {
                  editText.setEnabled(false);
                  totalEditText = editText;

                }
                else
                {
                    editText.addTextChangedListener(new TextWatcher(){
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i3, int i1, int i2) {



                                double value = 0.0;
                                for(int w = 0; w < 3; w++)
                                {
                                    if(!measurements[w].getText().toString().equalsIgnoreCase(""))
                                    {
                                        value += Double.parseDouble(measurements[w].getText().toString());

                                    }

                                }

                              //  value += Double.parseDouble(editText.getText().toString());
                                 totalEditText.setText(value + "");
                                 if(!measurements[i].getText().toString().equalsIgnoreCase(""))
                                 {
                                     liftMeasurements.put(CraneInformationStorage.lift_details_array[i], Double.parseDouble(charSequence.toString()));
                                 }
                                 else
                                 {
                                     liftMeasurements.put(CraneInformationStorage.lift_details_array[i], 0.00);
                                 }



                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }



                    });
                }

            }
            else if(i == 8)
            {
                editText.setText(lift.getLiftMeasurements().get(CraneInformationStorage.lift_details_array[i]) + "");
                textView2.setText("kg");
            }
            else if (i == 9 || i ==10)
            {
                //editText.setText(lift.getLiftMeasurements().get(CraneInformationStorage.lift_details_array[i]) + "");
                textView2.setText(" ");
            }
            else
            {
                editText.setText(lift.getLiftMeasurements().get(CraneInformationStorage.lift_details_array[i]) + "");
                textView2.setText("m");
            }

            if( i <= 8 && i > 3)
            {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i3, int i1, int i2) {
                        if(charSequence.toString().length() > 0) {
                            liftMeasurements.put(CraneInformationStorage.lift_details_array[i], Double.parseDouble(charSequence.toString()));
                            Log.d(LiftDetailsFragment.tag,"Value Changed and i is " + i);
                        }
                        else
                        {
                            lift.getLiftMeasurements().put(CraneInformationStorage.lift_details_array[i], 0.00);
                            Log.d(LiftDetailsFragment.tag,"Value Changed to 0");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
            else if(i == 9)
            {
                if(lift.getLiftCheckedInitials().length() > 0)
                {
                    editText.setText(lift.getLiftCheckedInitials());
                }
               editText.addTextChangedListener(new TextWatcher(){

                   @Override
                   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                   }

                   @Override
                   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lift.setLiftCheckedInitials(charSequence.toString());
                    Log.d(LiftDetailsFragment.tag,"initails changed");
                   }

                   @Override
                   public void afterTextChanged(Editable editable) {

                   }
               }) ;
            }

           Log.d(LiftDetailsFragment.tag,"Created " + i);

        }
        LinearLayout radioGroup = new LinearLayout(getActivity());
        TextView textView4 = new TextView(getActivity());
        textView4.setText("Communication");
        textView4.setTextSize(22);
        textView4.setPadding(0,0,10,0);

        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        radioGroup.setLayoutParams(params3);
        radioGroup.addView(textView4);
        for(int a = 0; a < 3; a++)
        {
            final int i = a;
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(CraneInformationStorage.communication_choices[i]);
            ViewGroup.LayoutParams params4 = new ViewGroup.LayoutParams((width/5), (height/10));
            checkBox.setLayoutParams(params4);


            checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(i == 2)
                    {
                        if(checkBox.isChecked() == true) {
                            if(loaded != 0) {
                                OtherDialog otherDialog = OtherDialog.newInstance(0,getActivity().getApplicationContext(),-1);
                                otherDialog.setTargetFragment(LiftDetailsFragment.this, LiftDetailsFragment.request_code);
                                otherDialog.show(getFragmentManager(), LiftDetailsFragment.tag);
                            }

                        }
                        else
                        {
                            lift.getCommunication().setOther(false,null);

                        }

                    }
                    else if (i == 1)
                    {

                        if(checkBox.isChecked() == true) {
                          lift.getCommunication().setHands(true);

                        }
                        else
                        {
                            lift.getCommunication().setHands(false);
                        }
                    }
                    else
                    {
                        if(checkBox.isChecked())
                        {
                            lift.getCommunication().setRadio(true);
                        }
                        else
                        {
                            lift.getCommunication().setRadio(false);
                        }
                    }
                }
            });

            if(lift.getCommunication().getValueofCommunication(i) != false)
            {
                checkBox.setChecked(true);
            }


            communication_checkbox[i]  = checkBox;
            radioGroup.addView(communication_checkbox[i]);

        }
        layout.addView(radioGroup);
       scrollView.addView(layout);
      //  v.addView(layout);
        loaded = 1;
        return scrollView;
    }

    public  int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public  int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    public HashMap<String, Double> getLiftMeasurements() {
        return liftMeasurements;
    }

    public void setLiftMeasurements(HashMap<String, Double> liftMeasurements) {
        this.liftMeasurements = liftMeasurements;
    }



    @Override
    public void onPause()
    {
        lift.setLiftMeasurements(liftMeasurements);

        mCrane.upDateLift(lift);
        CraneSingleton.getCraneSingleton().upDateCrane(mCrane);
        CraneSingleton.getCraneSingleton().saveCranesToFile(getActivity());


        super.onPause();
    }

    public Crane getCrane() {
        return mCrane;
    }

    public void setCrane(Crane crane) {
        mCrane = crane;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
       if(requestCode == LiftDetailsFragment.request_code && resultCode == Activity.RESULT_OK)
       {
           String otherAnswer = data.getStringExtra(OtherDialog.answerKey);
           lift.getCommunication().setOther(true,otherAnswer);
       }
    }
}
