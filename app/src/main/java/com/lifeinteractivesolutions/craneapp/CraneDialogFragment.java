package com.lifeinteractivesolutions.craneapp;

import android.app.Activity;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CraneDialogFragment extends DialogFragment {


    EditText mEditText;
    Context mContext;
    public void setmContext(Context context)
    {
        this.mContext = context;
    }
    public static CraneDialogFragment newInstance(Context context)
    {
        CraneDialogFragment craneDialogFragment = new CraneDialogFragment();
        craneDialogFragment.setmContext(context);
        Bundle args = new Bundle();

        craneDialogFragment.setArguments(args);
        return craneDialogFragment;
    }
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState)
    {

       View v = getActivity().getLayoutInflater().inflate(R.layout.crane_dialog_layout,null);
       mEditText = (EditText)v.findViewById(R.id.editText);
       mEditText.addTextChangedListener(new TextWatcher(){

           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });
       return new  AlertDialog.Builder(getActivity()).setTitle(R.string.add_new_crane_item).setView(v).setPositiveButton(R.string.confirm_crane_name,new DialogInterface.OnClickListener(){
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
                 Intent data = new Intent();
                if(!(mEditText.getText().toString().length() < 1)) {
                    data.putExtra(ListActivity.crane_name_code,mEditText.getText().toString());
                    ((ListActivity)mContext).onActivityResult(ListActivity.crane_request_code, Activity.RESULT_OK, data);

                    Toast.makeText(getActivity(),mEditText.getText().toString(),Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                }
                else
                {
                  Toast.makeText(getActivity(),R.string.crane_dialog_error,Toast.LENGTH_LONG).show();
                }
           }


       }).setNegativeButton(R.string.cancel_crane_creation, new DialogInterface.OnClickListener(){
           @Override
           public void onClick(DialogInterface dialogInterface,int i)
           {
               Intent data = new Intent();
               ((ListActivity)getActivity()).onActivityResult(ListActivity.crane_request_code, Activity.RESULT_CANCELED,data);
               dialogInterface.dismiss();
           }
       }).create();

    }
}


