package com.task.priyanka.joshtflickr.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.task.priyanka.joshtflickr.R;
import com.task.priyanka.joshtflickr.model.PhotoData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FlickrRecycleViewAdapter extends RecyclerView.Adapter<FlickrRecycleViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = FlickrRecycleViewAdapter.class.getSimpleName();
    private ArrayList<PhotoData> photoDataArrayList;
    private Context context;

    public FlickrRecycleViewAdapter(ArrayList<PhotoData> PhotoDataArrayList, Context Context) {
        photoDataArrayList = PhotoDataArrayList;
        context = Context;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_data, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {

        if (photoDataArrayList == null || photoDataArrayList.size() == 0) {
            holder.iv_image.setImageResource(R.drawable.ic_photo);
            holder.tv_heading.setText(context.getString(R.string.search_data));
            holder.tv_tap.setVisibility(View.GONE);

        } else {
            holder.tv_heading.setVisibility(View.VISIBLE);
            holder.tv_tap.setVisibility(View.VISIBLE);

            PhotoData photoItem = photoDataArrayList.get(position);
            Log.d(TAG, "Heading" + photoItem.getTitle() + "---" + position);
            Glide.with(context)
                    .load(photoItem.getImage())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_photo)
                            .optionalCenterCrop()
                    )
                    .into(holder.iv_image);
            holder.tv_heading.setText(photoItem.getTitle());
        }
    }

    @Override
    public int getItemCount() {
//  Log.d(TAG, "getItemCount: called");
        return (((photoDataArrayList != null) && (photoDataArrayList.size() != 0)) ? photoDataArrayList.size() : 1);
    }

    public void loadNewData(ArrayList<PhotoData> newphotoData) {
        Log.d(TAG, "loadNewData");
        photoDataArrayList = newphotoData;
        notifyDataSetChanged();
    }

    public PhotoData getPhotoData(int position) {
        // returns photo data of is being tapped item
        return ((photoDataArrayList != null) && (photoDataArrayList.size() > 0) ? photoDataArrayList.get(position) : null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_heading, tv_tap;

        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            iv_image =  itemView.findViewById(R.id.iv_image);
            tv_heading = itemView.findViewById(R.id.tv_heading);
            tv_tap = itemView.findViewById(R.id.tv_tap);
        }
    }
}
