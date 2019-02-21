package com.lifeinteractivesolutions.craneapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class LiftList extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Lift> liftList;
    public LiftList()
    {
        setHasOptionsMenu(true); // setting this to true alerts the fragment manager to call onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.liftlistlayout,parent,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(null); // replace this
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.decide_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            default:

        }
       return  super.onOptionsItemSelected(item);
    }
}
