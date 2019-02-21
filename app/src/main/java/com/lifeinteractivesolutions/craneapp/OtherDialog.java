package com.lifeinteractivesolutions.craneapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OtherDialog extends DialogFragment {



    private int newCommunicationorEmail =0;
    private String answerResult = "";
    public static String answerKey = "answer";
    private static String putInt = "1";
    public Context context;
    public int position;
    public static OtherDialog newInstance(int choice, Context context, int postion)
    {
        Bundle bundle = new Bundle();
        OtherDialog otherDialog = new OtherDialog();
        otherDialog.position = postion;
        otherDialog.setArguments(bundle);
        otherDialog.context = context;
        if(choice != 0)
        {
            otherDialog.setNewCommunicationorEmail(1);
        }
        return otherDialog;
    }
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState)
  {


     final LinearLayout layout = new LinearLayout(getActivity());
      layout.setOrientation(LinearLayout.VERTICAL);
      layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,250));
      final TextView textView = new TextView(getActivity());


      textView.setTextSize((float)20);
      textView.setGravity(Gravity.CENTER);
      textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,150));
      if(newCommunicationorEmail ==0)
      {
          textView.setText("Enter other communication type");
      }
      else
      {
          textView.setText("Enter email to send");
      }

      EditText editText = new EditText(getActivity());
      editText.setBackgroundResource(R.drawable.text_background2);
      LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
      editText.setLayoutParams(params1);
      editText.setTextSize((float)17.5);
      editText.setGravity(Gravity.CENTER);
      editText.addTextChangedListener(new TextWatcher(){

          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void afterTextChanged(Editable editable) {

              answerResult = editable.toString();
          }


      });
     layout.addView(textView);
      layout.addView(editText);

    ViewGroup v = new ViewGroup(getActivity()) {
          @Override
          protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
           if(((ViewGroup)textView.getParent()) != null) {((ViewGroup)textView.getParent()).removeView(textView);}
              addView(textView);
          }
      };
     // v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));


      return new  AlertDialog.Builder(getActivity()).setTitle("Other Dialog").setPositiveButton("okay", new AlertDialog.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              if(answerResult.length() > 0)
              {
                  if(newCommunicationorEmail == 0)
                  {
                      Intent intent = new Intent();
                      intent.putExtra(OtherDialog.answerKey,answerResult);
                      getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
                      dismiss();
                  }
                  else{
                      ListActivity.sendEmail(answerResult,context,position);
                  }


              }
              else
              {
                  getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_CANCELED,null);
                  Toast.makeText(getActivity(),"Enter a response or press cancel",Toast.LENGTH_LONG).show();
                  dismiss();
              }


          }
      }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              if(getTargetFragment() != null)
              {
                  getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_CANCELED,null);
              }

          }
      }).setView(layout).create();
  }
    public int getNewCommunicationorEmail() {
        return newCommunicationorEmail;
    }

    public void setNewCommunicationorEmail(int newCommunicationorEmail) {
        this.newCommunicationorEmail = newCommunicationorEmail;
    }
}
