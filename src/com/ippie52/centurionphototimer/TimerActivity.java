package com.ippie52.centurionphototimer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.ippie52.centurionphototimer.fragments.CountFragment;
import com.ippie52.centurionphototimer.fragments.CountdownTimerFragment;
import com.ippie52.centurionphototimer.fragments.CountdownTimerFragment.OnCountdownTimerListener;
import com.ippie52.centurionphototimer.fragments.ShotGlassFragment;
import com.ippie52.centurionphototimer.fragments.TimeToNextFragment.OnTimeToNextFragmentListener;

public class TimerActivity extends FragmentActivity implements
        OnTimeToNextFragmentListener, OnCountdownTimerListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timer, menu);
        return true;
    }

    @Override
    public void onFinalTickEvent() {
        Toast.makeText(this, "Timer Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMajorTickEvent(long aMsRemaining) {
        final int msTillMinute = (int) (aMsRemaining % MILLISECONDS_PER_MINUTE);
        final float percentage = (msTillMinute * 100f)
                / MILLISECONDS_PER_MINUTE;
        mShotGlass.updateLevel(percentage);
        Log.d("MajorTick", "msTillMinute: " + msTillMinute + " : percent: "
                + percentage);
        final int count = 1 + (int) ((TOTAL_DURATION_MILLISECONDS - aMsRemaining) / MILLISECONDS_PER_MINUTE);
        mCount.setCount(count);
    }

    @Override
    public void onMinorTickEvent(long aMsRemaining) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        android.support.v4.app.FragmentManager mgr = getSupportFragmentManager();
        // Initialise shot glass
        mShotGlass = (ShotGlassFragment) mgr
                .findFragmentById(R.id.timer_shot_glass_fragment);
        if (mShotGlass == null) {
            try {
                throw new Exception("Couldn't find shot glass, oops.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Initialise shot count
        mCount = (CountFragment) mgr
                .findFragmentById(R.id.timer_count_fragment);
        mCount.setMax(TOTAL_DURATION_MINUTES);
        mCount.setCount(0);

        // Initialise timer
        mCountDownTimer = new CountdownTimerFragment();
        try {
            mCountDownTimer.initialise(TOTAL_DURATION_MINUTES,
                    MAJOR_TICK_MILLISECONDS, MINOR_TICK_MILLISECONDS);
            mCountDownTimer.attachListener(this);
            mCountDownTimer.startTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Class constants
     * **/
    private static final int TOTAL_DURATION_MINUTES = 10;
    // Stubbed thing to make stuff happen faster
    private static final int MILLISECONDS_PER_MINUTE = (60 * 1000);
    // private static final int MILLISECONDS_PER_MINUTE = (60 * 1000);
    private static final long TOTAL_DURATION_MILLISECONDS = TOTAL_DURATION_MINUTES
            * MILLISECONDS_PER_MINUTE;
    private static final int MAJOR_TICK_MILLISECONDS = 1000;

    private static final int MINOR_TICK_MILLISECONDS = 250;
    /**
     * Member variables
     * **/
    private CountdownTimerFragment mCountDownTimer;
    private ShotGlassFragment mShotGlass;
    private CountFragment mCount;
}
