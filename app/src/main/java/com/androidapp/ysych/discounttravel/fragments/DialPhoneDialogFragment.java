package com.androidapp.ysych.discounttravel.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.android.ysych.discounttravel.R;
import com.androidapp.ysych.discounttravel.activities.MainActivity;

public class DialPhoneDialogFragment extends DialogFragment{

    private RadioButton phoneOffice;
    private RadioButton phoneKyivstar;
    private RadioButton phoneMTC;
    private RadioButton phoneLife;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_phone, container, false);

        final Dialog dialog = getDialog();
        dialog.setTitle(R.string.phone_number_chooser);

        phoneOffice = (RadioButton) view.findViewById(R.id.phone_office);
        phoneKyivstar = (RadioButton) view.findViewById(R.id.phone_kyivstar);
        phoneMTC = (RadioButton) view.findViewById(R.id.phone_mtc);
        phoneLife = (RadioButton) view.findViewById(R.id.phone_life);

        Button phoneCancel = (Button) view.findViewById(R.id.phone_cancel);
        phoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button phoneCall = (Button) view.findViewById(R.id.phone_call);
        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                if (phoneOffice.isChecked()) {
                    intent.setData(Uri.parse(getString(R.string.city_phone_number)));
                    if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                        startActivity(intent);
                    }
                    else noPhoneApp();
                } else if (phoneKyivstar.isChecked()) {
                    intent.setData(Uri.parse(getString(R.string.kyivstar_phone_number)));
                    if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                        startActivity(intent);
                    }
                    else noPhoneApp();
                } else if (phoneMTC.isChecked()) {
                    intent.setData(Uri.parse(getString(R.string.mts_phone_number)));
                    if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                        startActivity(intent);
                    }
                    else noPhoneApp();
                } else if (phoneLife.isChecked()) {
                    intent.setData(Uri.parse(getString(R.string.life_phone_number)));
                    if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                        startActivity(intent);
                    }
                    else noPhoneApp();
                }
            }
        });
        return view;
    }
    private void noPhoneApp(){
        ((MainActivity) getActivity()).showSnack(getString(R.string.no_phone_app));
        dismiss();
    }
}
