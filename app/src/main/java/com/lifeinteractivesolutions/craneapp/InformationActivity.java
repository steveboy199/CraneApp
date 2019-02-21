package com.lifeinteractivesolutions.craneapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class InformationActivity extends AppCompatActivity {

    FragmentManager manager;
    Fragment previousFragment;
    Crane crane;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_host);
        String crane_ = getIntent().getStringExtra(ListActivity.crane_name_code);
         crane = CraneSingleton.getCraneSingleton().getCrane(crane_);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.frame_hosting,ListFragment.newInstance(InformationActivity.this,crane)).commit();
       getSupportActionBar().setDisplayHomeAsUpEnabled(false);


    }
    @Override
    public void onResume()
    {
        if(crane != null)
        {
            String crane_ = getIntent().getStringExtra(ListActivity.crane_name_code);
            crane = CraneSingleton.getCraneSingleton().getCrane(crane_);
        }

        super.onResume();
    }

    public void changeFragment(Fragment fragment)
    {
        String tag;
        if(fragment instanceof LiftDetailsFragment)
        {
          tag = fragment.getTag();
        }
        else
        {
            tag = fragment.getTag();
        }
        previousFragment = manager.findFragmentById(R.id.frame_hosting);
        manager.beginTransaction().setCustomAnimations(R.anim.slideleft,R.anim.slidelefttwo).addToBackStack(tag).replace(R.id.frame_hosting, fragment).commit();


    }
    public void setLiftPlanDetails(LiftPlanDetails details)
    {
        crane.setLiftplandetails(details);
        CraneSingleton.getCraneSingleton().upDateCrane(crane);

    }
    @Override
    public void onBackPressed()
    {
        Log.d("Information Activity","On Back pressed");
        final Fragment fragment = manager.findFragmentById(R.id.frame_hosting);
        if(fragment instanceof ListFragment) //change this steve
        {
            NavUtils.navigateUpTo(this, new Intent(InformationActivity.this,ListActivity.class));
            overridePendingTransition(R.anim.slideright,R.anim.sliderighttwo);
        }
        else if(fragment instanceof  LiftDetailsFragment)
        {
            Log.d("Information Activity","Method Called");

                    manager.beginTransaction().setCustomAnimations(R.anim.slideright,R.anim.sliderighttwo).replace(R.id.frame_hosting,previousFragment).commit();



         //   manager.beginTransaction().setCustomAnimations(R.anim.slideright,R.anim.sliderighttwo).replace()
        }
        else
        {
            manager.beginTransaction().setCustomAnimations(R.anim.slideright,R.anim.sliderighttwo).replace(R.id.frame_hosting,ListFragment.newInstance(InformationActivity.this,crane)).commit();
        }
//        super.onBackPressed();
    }


}
