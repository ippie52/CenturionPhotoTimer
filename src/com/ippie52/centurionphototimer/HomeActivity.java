package com.ippie52.centurionphototimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class HomeActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void onQuitClicked(View aView) {
        Toast.makeText(this, "Quit", Toast.LENGTH_SHORT).show();
    }

    public void onSettingsClicked(View aView) {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();

    }

    public void onStartNewClicked(View aView) {
        Toast.makeText(this, "Start New", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int aRequest, int aResultCode, Intent aData) {
        super.onActivityResult(aRequest, aResultCode, aData);
        // TODO: Handle feedback from other activities
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        // Set layout
        setContentView(R.layout.activity_home);
    }

}
