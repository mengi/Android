package com.example.javier.spoonplayers.RecyclerView.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javier.spoonplayers.Model.Video;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.Utils.PicassoTransform.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Menginar on 4.4.2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<Video> videos;
    Context context;

    public VideoAdapter (Context context, ArrayList<Video> videos) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.ViewHolder holder, final int position) {

        final TextView textViewTitle = (TextView) holder.view.findViewById(R.id.textViewItemTitle);
        final TextView videoTitle = (TextView) holder.view.findViewById(R.id.video_title);
        final ImageView videoImage = (ImageView) holder.view.findViewById(R.id.video_image);
        videoTitle.setText(videos.get(position).getTitle());

        Picasso.with(context).load(videos.get(position).getPicturePath())
                .placeholder(holder.view.getResources()
                        .getDrawable(R.drawable.movies)).transform(new CircleTransform()).into(videoImage);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}
