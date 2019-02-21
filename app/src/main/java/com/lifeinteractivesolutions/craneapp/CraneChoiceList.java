package com.lifeinteractivesolutions.craneapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CraneChoiceList extends RecyclerView.Adapter<CraneChoiceList.CraneChoiceHolder>  {

    private List<String> choiceList;
    private Context mContext;


    private Crane crane;
    public class CraneChoiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CraneChoiceHolder(View v){
            super(v);
        }


        @Override
        public void onClick(View view) {

        }
    }

    public CraneChoiceList(Context context, Crane crane)
    {
        this.crane = crane;

        choiceList = new ArrayList<String>();
        for(int i = 0; i < CraneInformationStorage.crane_choices.length; i++)
        {

            choiceList.add(CraneInformationStorage.crane_choices[i]);
        }
        mContext = context;
    }

    @Override
    public CraneChoiceHolder onCreateViewHolder( ViewGroup root, int itemType)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_choice,root,false);


        return new CraneChoiceHolder(v);
    }

    @Override
    public void onBindViewHolder(CraneChoiceHolder holder, final int position)
    {
        TextView textView = (TextView) holder.itemView.findViewById(R.id.textView1);
        textView.setText(choiceList.get(position));
        ImageView view = (ImageView)holder.itemView.findViewById(R.id.item_choice_choose);
        switch(position)
        {
            case 0: if(crane.isLiftsValid())
                    {
                      view.setImageResource(R.drawable.valid);
                    }
                    else
                    {
                       view.setImageResource(R.drawable.warning);
                    }
                    break;
            case 1: if(crane.getLiftplandetails().isValid())
                    {
                        view.setImageResource(R.drawable.valid);
                    }
                    else
                    {
                      view.setImageResource(R.drawable.warning);
                    }
                   break;
            case 2:if(crane.getHazardchecks().isValid())
                    {
                        view.setImageResource(R.drawable.valid);
                    }
                    else
                    {
                      view.setImageResource(R.drawable.warning);
                    }
                   break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)
            {
                switch(position)
                {
                    case 0:
                          LiftListFragment liftListFragment = LiftListFragment.newInstance(crane,crane.getLifts());
                         ((InformationActivity)mContext).changeFragment(liftListFragment);

                        break;
                    case 1:
                        LiftPlanFragment liftPlanFragment = LiftPlanFragment.newInstance(crane.getLiftplandetails());
                        ((InformationActivity)mContext).changeFragment(liftPlanFragment);
                        break;
                    case 2:
                        HazardFragment hazardFragment = HazardFragment.newInstance(crane.getHazardchecks());
                        ((InformationActivity)mContext).changeFragment(hazardFragment);
                        break;
                    default:
                        break;
                }
            }
        });

    }
    @Override
    public int getItemCount()
    {
        return choiceList.size();
    }

    public Crane getCrane() {
        return crane;
    }

    public void setCrane(Crane crane) {
        this.crane = crane;
    }


}
