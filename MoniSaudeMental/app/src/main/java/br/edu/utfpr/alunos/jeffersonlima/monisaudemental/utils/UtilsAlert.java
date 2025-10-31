package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.R;

public final class UtilsAlert {

    private UtilsAlert(){

    }
    public static void showAlert(Context context, int  idMessage) {

        showAlert(context, context.getString(idMessage), null);
    }
    public static void showAlert(Context context, int  idMessage, DialogInterface.OnClickListener listener) {

        showAlert(context, context.getString(idMessage), listener);
    }
    public static void showAlert(Context context, String message, DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.warning);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(message);

        builder.setNeutralButton(R.string.ok, listener);
        AlertDialog alert = builder.create();

        alert.show();
    }

    public static void actionConfirm(Context context, int idMessage,
                                     DialogInterface.OnClickListener listernerYes,
                                     DialogInterface.OnClickListener listernerNo){
        actionConfirm(context, idMessage, listernerYes, listernerNo);
    }

    public static void actionConfirm(Context context, String message,
                                     DialogInterface.OnClickListener listernerYes,
                                     DialogInterface.OnClickListener listernerNo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmation);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.yes, listernerYes);
        builder.setNegativeButton(R.string.no, listernerNo);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
