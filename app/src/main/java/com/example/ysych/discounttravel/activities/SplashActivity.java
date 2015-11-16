package com.example.ysych.discounttravel.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.sync.GetToursService;

public class SplashActivity extends Activity {

    public static String GET_TOURS_RECEIVER_ACTION_START_APP = "startapp";
    public static String GET_TOURS_RECEIVER_ACTION_FINISH_APP = "finishapp";
    public static String GET_TOURS_RECEIVER_ACTION_ASK_USER = "askuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GET_TOURS_RECEIVER_ACTION_ASK_USER);
        intentFilter.addAction(GET_TOURS_RECEIVER_ACTION_START_APP);
        intentFilter.addAction(GET_TOURS_RECEIVER_ACTION_FINISH_APP);
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
            if(intent.getAction().equals(GET_TOURS_RECEIVER_ACTION_START_APP)){
                Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
            else if(intent.getAction().equals(GET_TOURS_RECEIVER_ACTION_ASK_USER)){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this, R.style.CustomDialogTheme);
                alertDialogBuilder.setMessage(R.string.tour_upload_error);
                alertDialogBuilder.setTitle(R.string.error_string);
                alertDialogBuilder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.exit_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                if(!(SplashActivity.this).isFinishing())
                {
                    alertDialogBuilder.create().show();
                }
            }
            else if(intent.getAction().equals(GET_TOURS_RECEIVER_ACTION_FINISH_APP)){
                Toast.makeText(SplashActivity.this, R.string.zero_version_error, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
