package com.androidapp.ysych.discounttravel.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.ysych.discounttravel.R;
import com.androidapp.ysych.discounttravel.sync.GetToursService;

public class SplashActivity extends Activity {

    public static String ACTION_START_APP = "startapp";
    public static String ACTION_FINISH_APP = "finishapp";
    public static String ACTION_ASK_USER = "askuser";
    GetToursServiceReceiver getToursServiceReceiver;


    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.android.ysych.discounttravel";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "discounttravel.com";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    ContentResolver mResolver;
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
        accountManager.addAccountExplicitly(newAccount, null, null);
        mResolver = getContentResolver();
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                300);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ASK_USER);
        intentFilter.addAction(ACTION_START_APP);
        intentFilter.addAction(ACTION_FINISH_APP);
        getToursServiceReceiver = new GetToursServiceReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(getToursServiceReceiver, intentFilter);

        Intent intent = new Intent(this, GetToursService.class);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getToursServiceReceiver);
    }

    private class GetToursServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_START_APP)){
                Intent intentStartMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intentStartMainActivity);
                finish();
            }
            else if(intent.getAction().equals(ACTION_ASK_USER)){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this, R.style.CustomDialogTheme);
                alertDialogBuilder.setMessage(R.string.tour_upload_error);
                alertDialogBuilder.setTitle(R.string.error_string);
                alertDialogBuilder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentStartMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intentStartMainActivity);
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
            else if(intent.getAction().equals(ACTION_FINISH_APP)){
                Toast.makeText(SplashActivity.this, R.string.zero_version_error, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
