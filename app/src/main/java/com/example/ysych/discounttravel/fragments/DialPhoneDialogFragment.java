package com.example.ysych.discounttravel.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ysych.discounttravel.R;

public class DialPhoneDialogFragment extends android.support.v4.app.DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_phone, container, false);

        final Dialog dialog = getDialog();
        dialog.setTitle(R.string.phone_number_chooser);

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);

        Button phoneOffice = (Button) view.findViewById(R.id.phone_office);
        phoneOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setData(Uri.parse(getString(R.string.city_phone_number)));
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    startActivity(intent);
                }
            }
        });
        Button phoneKyivstar = (Button) view.findViewById(R.id.phone_kyivstar);
        phoneKyivstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setData(Uri.parse(getString(R.string.kyivstar_phone_number)));
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    startActivity(intent);
                }            }
        });
        Button phoneMTC = (Button) view.findViewById(R.id.phone_mtc);
        phoneMTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setData(Uri.parse(getString(R.string.mts_phone_number)));
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    startActivity(intent);
                }            }
        });
        Button phoneLife = (Button) view.findViewById(R.id.phone_life);
        phoneLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setData(Uri.parse(getString(R.string.life_phone_number)));
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    startActivity(intent);
                }
            }
        });
        Button phoneCancel = (Button) view.findViewById(R.id.phone_cancel);
        phoneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return view;
    }
}
