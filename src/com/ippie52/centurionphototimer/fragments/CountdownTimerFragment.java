package com.ippie52.centurionphototimer.fragments;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link CountdownTimerFragment.OnCountdownTimerListener} interface to handle
 * interaction events.
 * 
 */
public class CountdownTimerFragment extends Fragment {

    /**
     * Constructor
     * **/
    public CountdownTimerFragment() {
        // Required empty public constructor
    }

    /**
     * cancel - Method to cancel the timers
     * **/
    public void cancel() {
        if (mInitialised && mRunning) {
            mMajorTickTimer.cancel();
            mMinorTickTimer.cancel();
            for (OnCountdownTimerListener l : mListeners) {
                l.onFinalTickEvent();
            }
            mRunning = false;
        }
    }

    /**
     * initialise - Method used to initialise the count down timer
     * 
     * @throws Exception
     * **/
    public boolean initialise(final int aTotalMinutes, final int aMajorTick,
            final int aMinorTick) throws Exception {
        boolean result = false;
        if (mRunning) {
            throw new Exception("Cannot initialise when running");
        } else {
            mTotalMinutes = aTotalMinutes;
            mMajorTickMs = aMajorTick;
            mMinorTickMs = aMinorTick;
            mInitialised = true;
            result = true;
        }
        return result;
    }

    /**
     * onAttach - Overridden method to attach an activity as a listener
     * **/
    @Override
    public void onAttach(Activity aActivity) {
        super.onAttach(aActivity);
        try {
            mListeners.add((OnCountdownTimerListener) aActivity);
        } catch (ClassCastException e) {
            throw new ClassCastException(aActivity.toString()
                    + " must implement OnCountdownTimerListener");
        }
    }

    /**
     * onAttach - Overridden method to attach an activity as a listener
     * **/
    public void onAttachListener(OnCountdownTimerListener aListener) {
        try {
            mListeners.add(aListener);
        } catch (ClassCastException e) {
            throw new ClassCastException(aListener.toString()
                    + " must implement OnCountdownTimerListener");
        }
    }

    /**
     * onCreateView - This fragment is not designed to have a view
     * **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return null;
    }

    /**
     * onDetach - Overridden method used to detach from an activity
     * **/
    @Override
    public void onDetach() {
        super.onDetach();
        mListeners.clear();
    }

    /**
     * startTimer - Method used to begin the count down timer
     * 
     * @throws Exception
     * **/
    public boolean startTimer() throws Exception {
        boolean result = false;
        if (mInitialised) {
            if (mRunning) {
                throw new Exception("Error: Attempting to start whilst running");
            }
            mStartTimeMs = System.currentTimeMillis();
            mEndTimeMs = calculateEndTime(mStartTimeMs, mTotalMinutes);
            startMajorTimer(mMajorTickMs);
            startMinorTimer(mMinorTickMs);
            mRunning = true;
            result = true;
        }
        return result;
    }

    /**
     * calculateEndTime - Method used to calculate the end time in milliseconds
     * **/
    private long calculateEndTime(final long aStart, final int aMinutes) {
        final int msPerSec = 1000;
        final int secPerMin = 60;
        return (aStart + (secPerMin * msPerSec * aMinutes));
    }

    /**
     * getTimeRemaining - Method to get the number of milliseconds remaining
     * **/
    private long getTimeRemaining() {
        return mEndTimeMs - System.currentTimeMillis();
    }

    /**
     * startMajorTimer - Start the major tick timer
     * 
     * @param aTickMs
     *            The tick increment time in milliseconds
     * **/
    private void startMajorTimer(final int aTickMs) {
        if (aTickMs != 0) {
            mMajorTickTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            for (OnCountdownTimerListener l : mListeners) {
                                l.onMajorTickEvent(getTimeRemaining());
                            }
                        }
                    });
                }
            }, 0, aTickMs);
        }
    }

    /**
     * startMinorTimer - Start the major tick timer
     * 
     * @param aTickMs
     *            The tick increment time in milliseconds
     * **/
    private void startMinorTimer(final int aTickMs) {
        if (aTickMs != 0) {
            mMinorTickTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            for (OnCountdownTimerListener l : mListeners) {
                                l.onMinorTickEvent(getTimeRemaining());
                            }
                        }
                    });
                }
            }, 0, aTickMs);
        }
    }

    /**
     * OnCountdownTimerListener - Interface used to communicate events to
     * listeners
     * **/
    public interface OnCountdownTimerListener {
        /**
         * onFinalTickEvent - Interface method - used to inform the listener
         * that this is the last tick event to be received
         * **/
        public void onFinalTickEvent();

        /**
         * onMajorTickEvent - Interface method - used to inform the listener of
         * a major tick event, as desired in the initialise method
         * 
         * @param aMsRemaining
         *            The number of milliseconds remaining until the count down
         *            is over
         * **/
        public void onMajorTickEvent(final long aMsRemaining);

        /**
         * onMinorTickEvent - Interface method - used to inform the listener of
         * a minor tick event, as desired in the initialise method
         * 
         * @param aMsRemaining
         *            The number of milliseconds remaining until the count down
         *            is over
         * **/
        public void onMinorTickEvent(final long aMsRemaining);
    }

    /**
     * Member variables
     * **/
    private final ArrayList<OnCountdownTimerListener> mListeners = new ArrayList<OnCountdownTimerListener>();
    private long mStartTimeMs;
    private long mEndTimeMs;
    private int mMajorTickMs;
    private int mMinorTickMs;
    private int mTotalMinutes;
    private boolean mInitialised = false;
    private boolean mRunning = false;
    private final Timer mMajorTickTimer = new Timer();
    private final Timer mMinorTickTimer = new Timer();
    private final Handler mHandler = new Handler();
}
