package com.lifeinteractivesolutions.craneapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static String crane_fragment ="com.lifeinteractivesolutions.CraneDialogFragment";
    public static int crane_request_code = 1;
    public static String crane_name_code = "crane_name";

    public static int crane_id_extra = 2;
    //Declaration of UI objects
    Toolbar mToolbar;
    FragmentManager manager;
    RecyclerView mRecyclerView;
    ArrayList<Crane> crane_array;
    //
    CraneFormList mCraneAdapter;
    @Override
    protected void onStart(){
        super.onStart();
        CraneSingleton.getCraneSingleton().readCranesFromFile(getApplicationContext());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbarm);
        mRecyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        crane_array = CraneSingleton.getCraneSingleton().getArrayList();
        mCraneAdapter = new CraneFormList(crane_array,ListActivity.this);
        mRecyclerView.setAdapter(mCraneAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        setSupportActionBar(mToolbar);
        manager = getSupportFragmentManager();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate( R.menu.decide_menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch(item.getItemId())
        {

            case R.id.addTow : CraneDialogFragment craneDialogFragment = CraneDialogFragment.newInstance(ListActivity.this);
                               craneDialogFragment.show(manager,ListActivity.crane_fragment);
                               break;
            default: break;

        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        Toast.makeText(ListActivity.this,"adding crane",Toast.LENGTH_LONG).show();
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == ListActivity.crane_request_code && resultCode == Activity.RESULT_OK)
        {
           String craneName = data.getStringExtra(ListActivity.crane_name_code);
           CraneSingleton.getCraneSingleton().addNewCrane(craneName,this);
           crane_array = CraneSingleton.getCraneSingleton().getArrayList();
           mCraneAdapter.setCraneList(crane_array);
            mCraneAdapter.notifyDataSetChanged();
          CraneSingleton.getCraneSingleton().saveCranesToFile(getApplicationContext());

        }

    }

    @Override
    public void onResume()
    {
        mCraneAdapter.setCraneList(CraneSingleton.getCraneSingleton().getArrayList());
        mCraneAdapter.notifyDataSetChanged();

        super.onResume();

    }

    public static void sendEmail(String email, Context context, int position)
    {
        if(email.contains("@")  && email.contains("."))
        {
            CraneSingleton.getCraneSingleton().mailTo(context,email,position);
        }
        else
        {
            Toast.makeText(context,"Email is Invalid", Toast.LENGTH_LONG).show();
        }
    }
}
