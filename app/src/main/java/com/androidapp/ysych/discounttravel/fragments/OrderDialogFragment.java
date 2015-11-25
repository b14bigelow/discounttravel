package com.androidapp.ysych.discounttravel.fragments;

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
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.ysych.discounttravel.R;
import com.androidapp.ysych.discounttravel.activities.MainActivity;
import com.androidapp.ysych.discounttravel.util.GMailOauthSender;


public class OrderDialogFragment extends DialogFragment {

    private static final String AUTH_TOKEN_TYPE = "oauth2:https://mail.google.com/";
    private static final String ACCOUNT_TYPE = "com.google";

    private String accountName;
    private String accountToken;
    private Activity activity;
    private Bundle bundle;
    private Dialog dialog;
    private EditText name;
    private EditText phone;
    private EditText comments;
    private TextInputLayout nameLayout;
    private TextInputLayout phoneLayout;

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

        bundle = getArguments();

        dialog = getDialog();
        dialog.setTitle(R.string.order_form);

        nameLayout = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        phoneLayout = (TextInputLayout) view.findViewById(R.id.input_layout_phone);

        name = (EditText) view.findViewById(R.id.order_name);
        phone = (EditText) view.findViewById(R.id.order_phone);
        comments = (EditText) view.findViewById(R.id.order_comments);

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
                submitForm();
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
                e.printStackTrace();
            }
        }
    }

    private void submitForm(){
        if (!validateName()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }

        if(accountName != null && accountToken != null){
            final String userName = name.getText().toString();
            final String userPhone = phone.getText().toString();
            final String userComments = comments.getText().toString();

            AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

                ProgressDialog progressDialog;
                boolean result;

                @Override
                protected void onPreExecute() {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
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
                        result = gMailOauthSender.sendMail(
                                getString(R.string.order_letter_subject),
                                bundle.getString(TourFragment.BUNDLE_TOUR_NAME) + "\n\n" + userName + "\n" + userPhone + "\n" + userComments,
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
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    progressDialog.dismiss();
                    dialog.dismiss();
                    if(result){
                        ((MainActivity) getActivity()).showSnack(getString(R.string.order_sent));                        
                    }
                    else ((MainActivity) getActivity()).showSnack(getString(R.string.order_not_sent));
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

    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            nameLayout.setError(getString(R.string.your_name_error));
            requestFocus(name);
            return false;
        } else {
            nameLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone() {
        String regEx = "^[+]?[0-9]{10,13}$";
        String phoneString = phone.getText().toString().trim();

        if (phoneString.isEmpty() || !phoneString.matches(regEx)) {
            phoneLayout.setError(getString(R.string.phone_number_error));
            requestFocus(phone);
            return false;
        } else {
            phoneLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
