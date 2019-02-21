package com.lifeinteractivesolutions.craneapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CraneFormList extends RecyclerView.Adapter<CraneFormList.CraneListViewHolder> {

    ListActivity mListActivity;
    private List<Crane> craneList;

    public  class CraneListViewHolder extends RecyclerView.ViewHolder
    {

        public CraneListViewHolder( View craneView)
        {
            super(craneView);
        }


    }

    public CraneFormList(List<Crane> craneList, ListActivity listActivity)
    {
       this.mListActivity = listActivity;
       this.craneList = craneList;
    }
    @Override
    public CraneListViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mListActivity.getApplicationContext());
        View v =inflater.inflate( R.layout.crane_list_view,parent,false);
        return new CraneListViewHolder(v);

    }
    @Override
    public void onBindViewHolder(CraneListViewHolder holder, final int position)
    {
       TextView view = (TextView)holder.itemView.findViewById(R.id.textView);
       String crane_name = craneList.get(position).getCrane_name();
       view.setText(crane_name);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Intent intent = new Intent(mListActivity,InformationActivity.class);
              intent.putExtra(ListActivity.crane_name_code,craneList.get(position).getCrane_name());
              mListActivity.startActivity(intent);
              mListActivity.overridePendingTransition(R.anim.slideleft,R.anim.slidelefttwo);
           }
       });
       holder.itemView.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v)
           {
             OtherDialog other = OtherDialog.newInstance(1,mListActivity,position);

             other.show(mListActivity.manager,ListActivity.crane_fragment);
           }
       });

        Toast.makeText(mListActivity.getApplicationContext(),craneList.get(position).getCrane_name(),Toast.LENGTH_LONG).show();
    }
    @Override
    public int getItemCount()
    {
        return craneList.size();
    }

    public List<Crane> getCrane()
    {
        return craneList;
    }

    public void setCraneList(ArrayList<Crane> craneList)
    {
       this.craneList = craneList;
    }
}
