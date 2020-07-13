package com.example.gamerapp.Others;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.gamerapp.R;

public class DialogMessage extends AlertDialog{

    protected DialogMessage(@NonNull Context context) {
        super(context);
    }

    public static void singlemsg(String title, String msg,Context c,Boolean type)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        if(type==false) {
            builder1.setIcon(R.drawable.ic_alert_foreground);
        }else if (type==true)
        {
            builder1.setIcon(R.drawable.ic_sucess_foreground);
        }
        builder1.setTitle(title);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

      /*  builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
