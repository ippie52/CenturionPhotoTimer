package com.ippie52.centurionphototimer.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.ippie52.centurionphototimer.R;

public class DialogBuilder {

    /**
     * Constructor
     * 
     * @param aContext
     *            The context associated with this DialogBuilder
     * **/
    public DialogBuilder(final Context aContext) {
        mContext = aContext;
        mPositiveText = mContext
                .getString(R.string.dialog_builder_default_positive);

        try {
            mListener = (OnDialogResultLisener) mContext;
        } catch (Exception e) {
            throw new ClassCastException("Error: Cannot cast "
                    + mContext.toString() + " to OnDialogResultLisener");
        }
    }

    /**
     * Method to get an error {@link AlertDialog}, which will display a title,
     * message one or two buttons and an error icon
     * 
     * @param aTitle
     *            The resource ID of the title to be displayed on the
     *            {@link AlertDialog}
     * @param aMessage
     *            The resource ID of the message to be displayed on the
     *            {@link AlertDialog}
     * @param aId
     *            The Id associated with the {@link AlertDialog}
     * @return The created {@link AlertDialog} with a common look/feel for the
     *         application
     * **/
    public AlertDialog getErrorDialog(final int aTitle, final int aMessage,
            final int aId) {
        return getQuestionDialog(mContext.getString(aTitle),
                mContext.getString(aMessage), aId);
    }

    /**
     * Method to get an error {@link AlertDialog}, which will display a title,
     * message one or two buttons and an error icon
     * 
     * @param aTitle
     *            The title to be displayed on the {@link AlertDialog}
     * @param aMessage
     *            The message to be displayed on the {@link AlertDialog}
     * @param aId
     *            The Id associated with the {@link AlertDialog}
     * @return The created {@link AlertDialog} with a common look/feel for the
     *         application
     * 
     *         TODO: Add a warning icon
     * **/
    public AlertDialog getErrorDialog(final String aTitle,
            final String aMessage, final int aId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(aTitle).setMessage(aMessage)
                .setIcon(R.drawable.ic_error);
        setButtons(aId, builder);

        return builder.create();
    }

    /**
     * Method to get a question {@link AlertDialog}, which will display a title,
     * message one or two buttons and a question icon
     * 
     * @param aTitle
     *            The resource ID of the title to be displayed on the
     *            {@link AlertDialog}
     * @param aMessage
     *            The resource ID of the message to be displayed on the
     *            {@link AlertDialog}
     * @param aId
     *            The Id associated with the {@link AlertDialog}
     * @return The created {@link AlertDialog} with a common look/feel for the
     *         application
     * **/
    public AlertDialog getQuestionDialog(final int aTitle, final int aMessage,
            final int aId) {
        return getQuestionDialog(mContext.getString(aTitle),
                mContext.getString(aMessage), aId);
    }

    /**
     * Method to get a question {@link AlertDialog}, which will display a title,
     * message one or two buttons and a question icon
     * 
     * @param aTitle
     *            The title to be displayed on the {@link AlertDialog}
     * @param aMessage
     *            The message to be displayed on the {@link AlertDialog}
     * @param aId
     *            The Id associated with the {@link AlertDialog}
     * @return The created {@link AlertDialog} with a common look/feel for the
     *         application
     * **/
    public AlertDialog getQuestionDialog(final String aTitle,
            final String aMessage, final int aId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(aTitle).setMessage(aMessage)
                .setIcon(R.drawable.ic_question);
        setButtons(aId, builder);

        return builder.create();
    }

    /**
     * Method to get a warning {@link AlertDialog}, which will display a title,
     * message one or two buttons and a warning icon
     * 
     * @param aTitle
     *            The resource ID of the title to be displayed on the
     *            {@link AlertDialog}
     * @param aMessage
     *            The resource ID of the message to be displayed on the
     *            {@link AlertDialog}
     * @param aId
     *            The Id associated with the {@link AlertDialog}
     * @return The created {@link AlertDialog} with a common look/feel for the
     *         application
     * **/
    public AlertDialog getWarningDialog(final int aTitle, final int aMessage,
            final int aId) {
        return getQuestionDialog(mContext.getString(aTitle),
                mContext.getString(aMessage), aId);
    }

    /**
     * Method to get a warning {@link AlertDialog}, which will display a title,
     * message one or two buttons and a warning icon
     * 
     * @param aTitle
     *            The title to be displayed on the {@link AlertDialog}
     * @param aMessage
     *            The message to be displayed on the {@link AlertDialog}
     * @param aId
     *            The Id associated with the {@link AlertDialog}
     * @return The created {@link AlertDialog} with a common look/feel for the
     *         application
     * 
     *         TODO: Add a warning icon
     * **/
    public AlertDialog getWarningDialog(final String aTitle,
            final String aMessage, final int aId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(aTitle).setMessage(aMessage)
                .setIcon(R.drawable.ic_error);
        setButtons(aId, builder);

        return builder.create();
    }

    /**
     * Method to set the negative text resource ID for the negative button. Once
     * set, the button will be displayed
     * 
     * @param aNegText
     *            The negative text resource ID
     * @return This instance of {@link DialogBuilder}
     * **/
    public DialogBuilder setNegativeText(final int aNegText) {
        return setNegativeText(mContext.getString(aNegText));
    }

    /**
     * Method to set the negative text for the negative button. Once set, the
     * button will be displayed
     * 
     * @param aNegText
     *            The negative text to be displayed
     * @return This instance of {@link DialogBuilder}
     * **/
    public DialogBuilder setNegativeText(final String aNegText) {
        mNegativeText = aNegText;
        return this;
    }

    /**
     * Method to set the positive text resource ID for the positive button. Once
     * set, the button will be displayed
     * 
     * @param aPosText
     *            The positive text resource ID
     * @return This instance of {@link DialogBuilder}
     * **/
    public DialogBuilder setPositiveText(final int aPosText) {
        return setPositiveText(mContext.getString(aPosText));
    }

    /**
     * Method to set the positive text for the positive button. Once set, the
     * button will be displayed
     * 
     * @param aPosText
     *            The positive text
     * @return This instance of {@link DialogBuilder}
     * **/
    public DialogBuilder setPositiveText(final String aPosText) {
        mPositiveText = aPosText;
        return this;
    }

    /**
     * Method to initialise the common buttons for the {@link AlertDialog} being
     * created
     * 
     * @param aId
     *            The ID of the {@link AlertDialog} to create
     * @param aBuilder
     *            The builder being used to create
     */
    private void setButtons(final int aId, AlertDialog.Builder aBuilder) {
        aBuilder.setPositiveButton(mPositiveText, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.OnDialogResult(aId, true);
            }
        });

        if (mNegativeText != null) {
            aBuilder.setNegativeButton(mNegativeText, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.OnDialogResult(aId, false);

                }
            });
        }
    }

    /**
     * Public interface that the attached context must implement
     * **/
    public interface OnDialogResultLisener {
        /**
         * Interface method to handle the feedback from the {@link AlertDialog}
         * created
         * **/
        public void OnDialogResult(final int aId, final boolean aResult);
    }

    /**
     * Member Variables
     * **/
    private final Context mContext;
    private String mNegativeText;
    private String mPositiveText;
    private OnDialogResultLisener mListener;
}
