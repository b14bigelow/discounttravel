package com.example.ysych.discounttravel.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.util.GMailOauthSender;


public class OrderDialogFragment extends DialogFragment {

    private static final String AUTH_TOKEN_TYPE = "oauth2:https://mail.google.com/";
    private static final String ACCOUNT_TYPE = "com.google";

    private String accountName;
    private String accountToken;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountManager accountManager = AccountManager.get(getActivity());
        Account[] userAccounts = accountManager.getAccounts();
        for(Account account : userAccounts){
            if(account.type.equals(ACCOUNT_TYPE)){
                accountName = account.name;
                accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, getActivity(), new OnTokenAcquired(), null);
            }
        }
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_order, container, false);

        final Bundle bundle = getArguments();

        final Dialog dialog = getDialog();
        dialog.setTitle(R.string.order_form);

        final EditText name = (EditText) view.findViewById(R.id.order_name);
        final EditText phone = (EditText) view.findViewById(R.id.order_phone);

        Button cancel = (Button) view.findViewById(R.id.order_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button send = (Button) view.findViewById(R.id.order_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accountName != null && accountToken != null){
                    final String userName = name.getText().toString();
                    final String userPhone = phone.getText().toString();

                    AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

                        ProgressDialog progressDialog;

                        @Override
                        protected void onPreExecute() {
                            progressDialog = ProgressDialog.show(
                                    getActivity(),
                                    getString(R.string.order_from_app_sending),
                                    getString(R.string.order_from_app_sending_letter),
                                    true);
                        }
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                GMailOauthSender gMailOauthSender = new GMailOauthSender();
                                gMailOauthSender.sendMail(
                                        getString(R.string.order_letter_subject),
                                        bundle.getString(TourFragment.BUNDLE_TOUR_NAME) + "\n\n" + userName + "\n" + userPhone,
                                        accountName,
                                        accountToken,
                                        getString(R.string.developer_email)
                                );
                            } catch (Exception e) {
                                Log.e(getClass().getSimpleName(), e.getMessage(), e);
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                    };
                    asyncTask.execute();
                }
                else {
                    dialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.error_string);
                    builder.setMessage(R.string.add_system_account);
                    builder.setNegativeButton(R.string.exit_string, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    });
                    builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.startActivity(new Intent(Settings.ACTION_ADD_ACCOUNT));
                            dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        return view;
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result){
            try{
                Bundle bundle = result.getResult();
                accountToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);

            } catch (Exception e){
                Log.d(getClass().getSimpleName(), e.getMessage());
            }
        }
    }
}
