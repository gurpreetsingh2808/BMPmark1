package com.bmpmark1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gurpreet on 1/17/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    //List<YoutubeConnector2> videoData = Collections.emptyList();
    ArrayList<YoutubeConnector2> videosList = new ArrayList<>();


    public CustomAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setVideosList(ArrayList<YoutubeConnector2> listVideos) {
        this.videosList = listVideos;
        notifyItemRangeChanged(0, listVideos.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.video_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoutubeConnector2 current = videosList.get(position);
        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDescription());
        //holder.thumbnail.setImageResource();
        Picasso.with(ScrollingActivity.getContext()).load(current.getThumbnailURL()).into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.video_title);
            description = (TextView) itemView.findViewById(R.id.video_description);
            thumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);

        }
    }
}
