package com.example.ysych.discounttravel.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.activities.MainActivity;
import com.example.ysych.discounttravel.fragments.TourFragment;
import com.example.ysych.discounttravel.model.Tour;
import com.example.ysych.discounttravel.sync.APIContract;

import java.util.List;

/**
 * Created by ysych on 06.11.2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TourViewHolder>{

    List<Tour> tours;
    Context context;

    public RecyclerViewAdapter(List<Tour> tours, Context context) {
        this.tours = tours;
        this.context = context;
    }

    @Override
    public TourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout, parent, false);
        return new TourViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TourViewHolder holder, final int position) {
        holder.tourTitle.setText(tours.get(position).getTitle());
        holder.tourText.setText(Html.fromHtml((tours.get(position).getIntrotext()).substring(0, 250) + "..."));
        String thumbnailImage;
        if(tours.get(position).getType().equals(Tour.TYPE_IMAGE)){
            thumbnailImage = (tours.get(position).getImages()).replace(".jpg","_M.jpg");
            Glide.with(context)
                    .load(APIContract.DISCOUNT_SERVER_URL + "/" + thumbnailImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.tourPhoto);
        }
        else {
            String[] allGalleryImages = (tours.get(position).getGallery()).split("///");
            thumbnailImage = (allGalleryImages[0]).replace(".jpg","_M.jpg");
            Glide.with(context)
                    .load(APIContract.DISCOUNT_SERVER_URL + "/" + thumbnailImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.tourPhoto);
        }
        holder.tourRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Tour.TOUR_ID, tours.get(position).getId());
                TourFragment tourFragment = new TourFragment();
                tourFragment.setArguments(bundle);
                ((MainActivity) context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, tourFragment)
                        .addToBackStack(getClass().getSimpleName())
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        CardView tourRelativeLayout;
        TextView tourTitle;
        TextView tourText;
        ImageView tourPhoto;

        TourViewHolder(View itemView) {
            super(itemView);
            tourRelativeLayout = (CardView)itemView.findViewById(R.id.item_layout_id);
            tourTitle = (TextView)itemView.findViewById(R.id.item_layout_title);
            tourText = (TextView) itemView.findViewById(R.id.item_layout_text);
            tourPhoto = (ImageView)itemView.findViewById(R.id.item_layout_background_image);
        }
    }
}