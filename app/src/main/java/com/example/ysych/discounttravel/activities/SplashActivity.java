package com.example.ysych.discounttravel.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.sync.GetToursService;

public class SplashActivity extends Activity {

    public static String GET_TOURS_RECEIVER_ACTION = "startapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        IntentFilter intentFilter = new IntentFilter(GET_TOURS_RECEIVER_ACTION);
        GetToursServiceReceiver getToursServiceReceiver = new GetToursServiceReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(getToursServiceReceiver, intentFilter);

        Intent intent = new Intent(this, GetToursService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetToursServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(GET_TOURS_RECEIVER_ACTION)){
                Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        }
    }
}
