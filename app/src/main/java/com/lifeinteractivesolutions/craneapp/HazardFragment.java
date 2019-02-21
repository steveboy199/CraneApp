package com.lifeinteractivesolutions.craneapp;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class HazardFragment extends Fragment {

    LinearLayout mLinearLayout;
    TextView [] mTextViews;
    CheckBox[]   mCheckBoxes;
    DisplayMetrics displayMetrics;
    private String checkBox = "checkBox";
    private HashMap<String,Boolean> hazards_checked;
    private HazardChecks hazardChecks;





    public static HazardFragment newInstance(HazardChecks hazardChecks){
        HazardFragment hazardFragment = new HazardFragment();
        hazardFragment.setHazardChecks(hazardChecks);
        hazardFragment.initialize();
        return hazardFragment;
    }

    public HazardFragment()
    {
        hazards_checked = new HashMap<String,Boolean>();

    }
    @Override
    public void onDetach()
    {
        CraneSingleton.getCraneSingleton().saveCranesToFile(getActivity().getApplicationContext());
        super.onDetach();
    }
    public void initialize()
    {
        hazards_checked = hazardChecks.getHazardsChecked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.hazards_checked,parent,false);
        mTextViews = new TextView[CraneInformationStorage.crane_hazards_checkList.length];
        mCheckBoxes = new CheckBox[CraneInformationStorage.crane_hazards_checkList.length];
        mLinearLayout = (LinearLayout) v.findViewById(R.id.hazards_checked_id_layout);
        displayMetrics = Resources.getSystem().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int widthmargin =((int)(( double) width * .25));
      //  MarginLayoutParams params = (MarginLayoutParams) mLinearLayout.getLayoutParams();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10,40,0,20);
        ScrollView scrollView = new ScrollView(getActivity());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getActivity());
        EditText editText = new EditText(getActivity());
        for(int i = 0; i < CraneInformationStorage.crane_hazards_checkList.length;i++)
        {
            final int i2 = i;
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            mTextViews[i] = new TextView(getActivity());

            mCheckBoxes[i] = new CheckBox(getActivity());
            mCheckBoxes[i].setChecked(hazards_checked.get(CraneInformationStorage.crane_hazards_checkList[i]).booleanValue());
          //  mCheckBoxes[i].setLa(Gravity.FILL_HORIZONTAL);
        mCheckBoxes[i].setGravity(Gravity.RIGHT);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params2.gravity = Gravity.RIGHT;
            params2.setMargins(100,0,0,0);
        mCheckBoxes[i].setLayoutParams(params2);

            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                    800, LinearLayout.LayoutParams.MATCH_PARENT);
             mTextViews[i].setLayoutParams(params3);
             mCheckBoxes[i].setGravity(Gravity.CENTER);
            final String[] check = CraneInformationStorage.crane_hazards_checkList;
            mCheckBoxes[i].setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    hazards_checked.put(CraneInformationStorage.crane_hazards_checkList[i2],b);
                    hazardChecks.setHazardsChecked(hazards_checked);

                }
            });
            mTextViews[i].setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.releases,0,0,0);
            mTextViews[i].setCompoundDrawablePadding(20);
            mTextViews[i].setText(CraneInformationStorage.crane_hazards_checkList[i]);
            mTextViews[i].setTextSize(22);

            ViewGroup.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            //mTextViews[i].setTextSize(TextView.)
            layout.setLayoutParams(params1);
            layout.addView(mTextViews[i]);
            layout.addView(mCheckBoxes[i]);
            MarginLayoutParams Textparams = (MarginLayoutParams)mTextViews[i].getLayoutParams();

            Textparams.setMargins(10,10,widthmargin,0);
            if(i == CraneInformationStorage.crane_hazards_checkList.length -1)
            {
                Textparams.setMargins(10,10,widthmargin,60);
            }
            mTextViews[i].setLayoutParams(Textparams);


            linearLayout.addView(layout);
        }
        scrollView.addView(linearLayout);
        mLinearLayout.addView(scrollView);

        v = mLinearLayout;
        return v;
    }
    public HazardChecks getHazardChecks() {
        return hazardChecks;
    }

    public void setHazardChecks(HazardChecks hazardChecks) {
        this.hazardChecks = hazardChecks;
    }
}
