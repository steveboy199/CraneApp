package com.lifeinteractivesolutions.craneapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragment  extends Fragment {

    RecyclerView mRecyclerView;
    private Crane mCrane;
    private  CraneChoiceList craneChoiceList;

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;

    public ListFragment() {
        if(mCrane != null && craneChoiceList != null)
        {
            mCrane = CraneSingleton.getCraneSingleton().getCrane(mCrane.getCrane_name());
            craneChoiceList.setCrane(mCrane);
            craneChoiceList.notifyDataSetChanged();
            craneChoiceList.setCrane(mCrane);
            craneChoiceList.notifyDataSetChanged();
            Log.d("onStart","called");
        }

    }

    @Override
    public void onStart() {
        if(mCrane != null && craneChoiceList != null)
        {
            mCrane = CraneSingleton.getCraneSingleton().getCrane(mCrane.getCrane_name());
            craneChoiceList.setCrane(mCrane);
            craneChoiceList.notifyDataSetChanged();
            craneChoiceList.setCrane(mCrane);
            craneChoiceList.notifyDataSetChanged();
            Log.d("onStart","called");
        }
        super.onStart();

    }

    @Override
    public void onResume()
    {
        super.onResume();
        craneChoiceList.notifyDataSetChanged();
    }




    public static ListFragment newInstance(Context context, Crane crane)
    {
        Bundle bundle = new Bundle();
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);
        listFragment.setContext(context);
        listFragment.setCrane(crane);
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.listfrag,parent,false);
        mRecyclerView =(RecyclerView) v.findViewById(R.id.listView);
         craneChoiceList = new CraneChoiceList(context,mCrane);
        mRecyclerView.setAdapter(craneChoiceList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrane = CraneSingleton.getCraneSingleton().getCrane(mCrane.getCrane_name());
        craneChoiceList.setCrane(mCrane);
        craneChoiceList.notifyDataSetChanged();

        return v;
    }


    public Crane getCrane() {
        return mCrane;
    }

    public void setCrane(Crane crane) {
        mCrane = crane;
    }
}
