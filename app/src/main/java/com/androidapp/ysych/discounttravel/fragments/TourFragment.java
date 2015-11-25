package com.androidapp.ysych.discounttravel.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ysych.discounttravel.R;
import com.androidapp.ysych.discounttravel.adapters.SlidesPagerAdapter;
import com.androidapp.ysych.discounttravel.data.HelperFactory;
import com.androidapp.ysych.discounttravel.model.Tour;
import com.androidapp.ysych.discounttravel.util.HeightWrappingViewPager;

import java.sql.SQLException;

public class TourFragment extends Fragment {

    public static final String BUNDLE_TOUR_NAME = "tourName";
    public static final String PHONE_DIALOG_TAG = "phoneDialog";
    public static final String EMAIL_DIALOG_TAG = "emailDialog";

    Tour tour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tour, container, false);

        Bundle bundle = getArguments();
        int tourSiteId = bundle.getInt(Tour.TOUR_ID);

        try {
            tour = HelperFactory.getHelper().getTourDAO().queryForId(tourSiteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextView tourDetailTitle = (TextView) view.findViewById(R.id.tour_detail_title);
        TextView tourDetailText = (TextView) view.findViewById(R.id.tour_detail_text);
        Button callToOffice = (Button) view.findViewById(R.id.call_to_office);
        Button emailToOffice = (Button) view.findViewById(R.id.email_to_office);
        Button share = (Button) view.findViewById(R.id.share_to_office);
        final HeightWrappingViewPager viewPager = (HeightWrappingViewPager) view.findViewById(R.id.viewPager);

        ImageView chevronRight = (ImageView) view.findViewById(R.id.chevron_right);
        ImageView chevronLeft = (ImageView) view.findViewById(R.id.chevron_left);

        chevronLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

        chevronRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });

        callToOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialPhoneDialogFragment dialog = new DialPhoneDialogFragment();
                dialog.show(getChildFragmentManager(), PHONE_DIALOG_TAG);
            }
        });
        emailToOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDialogFragment dialog = new OrderDialogFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(BUNDLE_TOUR_NAME, tour.getTitle());
                dialog.setArguments(bundle1);
                dialog.show(getChildFragmentManager(), EMAIL_DIALOG_TAG);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    intent.putExtra(Intent.EXTRA_SUBJECT, tour.getTitle() + "\n" + getString(R.string.site_url));
                    intent.putExtra(Intent.EXTRA_TEXT, tour.getTitle() + "\n" + getString(R.string.site_url));
                    startActivity(intent);
                }
            }
        });

        String[] allGalleryImages;
        if (tour.getType().equals(Tour.TYPE_IMAGE)) {
            allGalleryImages = new String[1];
            allGalleryImages[0] = (tour.getImages()).replace(".jpg", "_M.jpg");
        } else {
            String[] images = (tour.getGallery()).split("///");
            allGalleryImages = new String[images.length];
            for(int i = 0; i < allGalleryImages.length; i++){
                allGalleryImages[i] = images[i].replace(".jpg", "_M.jpg");
            }
        }

        if(allGalleryImages.length == 1){
            chevronLeft.setImageDrawable(null);
            chevronRight.setImageDrawable(null);
        }

        viewPager.setAdapter(new SlidesPagerAdapter(getChildFragmentManager(), allGalleryImages));
        tourDetailTitle.setText(tour.getTitle());
        tourDetailText.setText(Html.fromHtml(tour.getIntrotext()));

        return view;
        }
}