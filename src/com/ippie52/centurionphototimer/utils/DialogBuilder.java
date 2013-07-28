package com.ippie52.centurionphototimer.utils;

import android.app.AlertDialog;
import android.content.Context;

public class DialogBuilder {

    public DialogBuilder(final Context aContext) {
        mContext = aContext;
    }

    public AlertDialog getQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        return builder.create();
    }

    public AlertDialog getWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        return builder.create();
    }

    private final Context mContext;
}
