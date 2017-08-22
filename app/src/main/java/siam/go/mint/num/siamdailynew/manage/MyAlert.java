package siam.go.mint.num.siamdailynew.manage;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import siam.go.mint.num.siamdailynew.R;

/**
 * Created by masterung on 8/16/2017 AD.
 */

public class MyAlert {

    private Context context;

    public MyAlert(Context context) {
        this.context = context;
    }   // Constructor

    public void myDialog(String strTitle, String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_alert);
        builder.setCancelable(false);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


}   // Main Class
