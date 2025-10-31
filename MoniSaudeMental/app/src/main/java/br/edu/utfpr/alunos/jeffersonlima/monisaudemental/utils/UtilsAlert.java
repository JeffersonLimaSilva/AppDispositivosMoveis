package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.R;

public final class UtilsAlert {

    private UtilsAlert(){

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
}
