package com.lifeinteractivesolutions.craneapp;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.Inflater;

public class LiftListFragment extends Fragment {

    ArrayList<Lift> lifts;
    Crane mCrane;
    liftsList adapter;


    public class LiftListHolder extends RecyclerView.ViewHolder  {

        public LiftListHolder( View v)
        {
            super(v);


        }


    }
    public class liftsList extends RecyclerView.Adapter<LiftListHolder>{
        ArrayList<Lift> lifts;
        LiftListFragment fragment;
        public liftsList( ArrayList<Lift> lifts,LiftListFragment fragment)
        {
            this.lifts = lifts;
            this.fragment = fragment;

        }
        @Override
        public LiftListHolder onCreateViewHolder( ViewGroup parent, int viewType)
        {
         return new LiftListHolder(getLayoutInflater().inflate(R.layout.item_choice_2,parent,false));
        }
        @Override
       public void onBindViewHolder(LiftListHolder holder, final int postion)
       {
       //  holder.itemView.findViewById(R.id.item_choice_choose).setVisibility(View.GONE);


         ((TextView) holder.itemView.findViewById(R.id.textView1)).setText("Crane: " + (postion + 1));
         ((ImageButton)holder.itemView.findViewById(R.id.deleteButton)).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 lifts.remove(postion);
                 fragment.upDateCrane();

                 notifyDataSetChanged();
             }
         });
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                LiftDetailsFragment fragment = LiftDetailsFragment.newInstance(mCrane,lifts.get(postion));
                ((InformationActivity)getActivity()).changeFragment(fragment);
             }
         });

           Log.d("dfdsf","onBind called");
       }
       @Override
        public int getItemViewType(int postition)
       {
         return 0;
       }
       @Override
        public int getItemCount()
       {
           return lifts.size();
       }

       public void setLift(ArrayList<Lift> array)
       {
           this.lifts = array;
       }
    }
    public static LiftListFragment newInstance(Crane crane,ArrayList<Lift> lifts)
    {
        LiftListFragment liftListFragment = new LiftListFragment();
        liftListFragment.setLiftListDetails(lifts);
        liftListFragment.setCrane(crane);
        return liftListFragment;
    }

    public LiftListFragment()
    {
        lifts = new ArrayList<Lift>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.list_resource,parent,false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        adapter = new  liftsList(mCrane.getLifts(),LiftListFragment.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu,inflater);
       inflater.inflate(R.menu.decide_menu,menu);
        Log.d("dfdsf","created Options menu");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.addTow:
                Lift lift = new Lift();
                lifts.add(lift);
                upDateCrane();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setLiftListDetails(ArrayList<Lift> lifts )
    {
        this.lifts = lifts;
    }
    public void setCrane(Crane crane)
    {
        this.mCrane = crane;
    }

    public void upDateCrane()
    {
        mCrane.setLifts(lifts);
        adapter.setLift(mCrane.getLifts());
        adapter.notifyDataSetChanged();
        CraneSingleton.getCraneSingleton().upDateCrane(mCrane);
        CraneSingleton.getCraneSingleton().saveCranesToFile(getActivity());
    }

    @Override
    public void onResume()
    {
        lifts = mCrane.getLifts();
        Log.d("tagdfsd", "onResume called");
        super.onResume();
    }



}
