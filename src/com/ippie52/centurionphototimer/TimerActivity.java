package com.ippie52.centurionphototimer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.ippie52.centurionphototimer.fragments.CountFragment.OnCountFragmentListener;
import com.ippie52.centurionphototimer.fragments.ShotGlassFragment.OnShotGlassFragmentListener;
import com.ippie52.centurionphototimer.fragments.TimeToNextFragment.OnTimeToNextFragmentListener;

public class TimerActivity extends FragmentActivity implements
        OnShotGlassFragmentListener, OnCountFragmentListener,
        OnTimeToNextFragmentListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timer, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
    }

}
