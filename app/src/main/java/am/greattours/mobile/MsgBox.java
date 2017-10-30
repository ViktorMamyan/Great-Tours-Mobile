package am.greattours.mobile;

import android.app.AlertDialog;
import android.content.Context;

public class MsgBox
{

    enum MessageIcon
    {
        OK
        ,Question
        ,Error
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
                builder.setIcon(R.drawable.img_ok);
        }
        builder.setMessage(message);

        builder.setPositiveButton("Լավ", null);

        AlertDialog dialog = builder.show();
    }

}