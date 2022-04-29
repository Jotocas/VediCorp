package com.torresj.cliente.vedicorp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.torresj.cliente.vedicorp.R;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class ProgressBarGenerico {
    private static ProgressDialog progressDialog;


    public static void LoadProgress(Context context){
        //progressDialog.setCancelable(false);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        PulsatorLayout pulsator = (PulsatorLayout)progressDialog.findViewById(R.id.pulsator);
        pulsator.start();
    }

    public static void HideProgreess(){

        if (progressDialog != null)
            progressDialog.dismiss();


    }

}
