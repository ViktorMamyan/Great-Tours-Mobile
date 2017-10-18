package com.example.greattours.greattours;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MsgBox
{

    enum MessageIcon
    {
        OK
        ,Question
        ,Error
    }

    enum MessageCommands
    {
        EXIT_APP,
    }

    public void ShowMessageBox(String title,String message,MessageIcon icon,Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setCancelable(false);
        switch (icon)
        {
            case Error:
                builder.setIcon(R.drawable.error);
                break;
            case Question:
                builder.setIcon(R.drawable.question);
                break;
            default:
                builder.setIcon(R.drawable.ok);
        }
        builder.setMessage(message);

        builder.setPositiveButton("Լավ", null);

        AlertDialog dialog = builder.show();
    }



    void ShowMessageBox(String title,String message,MessageIcon icon, final MessageCommands app_command, final Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        switch (icon)
        {
            case Error:
                builder.setIcon(R.drawable.error);
                break;
            case Question:
                builder.setIcon(R.drawable.question);
                break;
            default:
                builder.setIcon(R.drawable.ok);
        }

        builder.setPositiveButton("Այո",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        switch (app_command) {
                            //Exit App
                            case EXIT_APP:
                                //finish();
                                System.exit(0);
                                break;
                            default:
                                dialog.cancel();
                        }

                    }
                });
        builder.setNegativeButton("Ոչ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }


}