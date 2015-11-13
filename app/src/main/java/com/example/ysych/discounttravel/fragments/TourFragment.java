package com.example.ysych.discounttravel.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Tour;
import com.example.ysych.discounttravel.sync.APIContract;

import java.sql.SQLException;

/**
 * Created by ysych on 06.11.2015.
 */
public class TourFragment extends Fragment implements BaseSliderView.OnSliderClickListener {

    Tour tour;
    private SliderLayout mSlider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        TextView tourDetailTitle = (TextView) view.findViewById(R.id.tour_detail_title);
        TextView tourDetailText = (TextView) view.findViewById(R.id.tour_detail_text);
        mSlider = (SliderLayout) view.findViewById(R.id.slider);
        Button callToOffice = (Button) view.findViewById(R.id.call_to_office);
        Button emailToOffice = (Button) view.findViewById(R.id.email_to_office);
        Button share = (Button) view.findViewById(R.id.share_to_office);
        callToOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    intent.setData(Uri.parse("tel:0445002125"));
                    startActivity(intent);
                }
            }
        });
        emailToOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/octet-stream");
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    intent.setData(Uri.parse("discounttravel.info@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Письмо");
                    intent.putExtra(Intent.EXTRA_TEXT, "hi jack!");
                    startActivity(intent);
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Письмо");
                    intent.putExtra(Intent.EXTRA_TEXT, "hi jack!");
                    startActivity(intent);
                }
            }
        });
        Bundle bundle = getArguments();
        int tourSiteId = bundle.getInt(Tour.TOUR_ID);
        try {
            tour = HelperFactory.getHelper().getTourDAO().queryForId(tourSiteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tourDetailTitle.setText(tour.getTitle());
        tourDetailText.setText(Html.fromHtml(tour.getIntrotext()));

        String thumbnailImage;
        if (tour.getType().equals(Tour.TYPE_IMAGE)) {
            thumbnailImage = (tour.getImages()).replace(".jpg", "_M.jpg");
            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
            defaultSliderView
                    .image(APIContract.DISCOUNT_SERVER_URL + "/" + thumbnailImage)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mSlider.addSlider(defaultSliderView);
            mSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        } else {
            String[] allGalleryImages = (tour.getGallery()).split("///");
            DefaultSliderView defaultSliderView;
            for (String allGalleryImage : allGalleryImages) {
                defaultSliderView = new DefaultSliderView(getContext());
                // initialize a SliderLayout
                defaultSliderView
                        .image(APIContract.DISCOUNT_SERVER_URL + "/" + (allGalleryImage).replace(".jpg", "_M.jpg"))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                mSlider.addSlider(defaultSliderView);
            }
        }
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setDuration(2000);

            return view;
        }

        @Override
        public void onStop () {
            mSlider.stopAutoCycle();
            super.onStop();
        }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

    }
}
