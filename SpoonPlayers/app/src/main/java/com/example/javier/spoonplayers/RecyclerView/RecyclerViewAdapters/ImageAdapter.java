package com.example.javier.spoonplayers.RecyclerView.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javier.spoonplayers.Model.Actor;
import com.example.javier.spoonplayers.Model.Image;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.Utils.PicassoTransform.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Menginar on 2.4.2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<Image> images;
    Context context;

    public ImageAdapter(Context context, ArrayList<Image> images) {
        this.images = images;
        this.context = context;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TextView textViewImageTitle = (TextView) holder.view.findViewById(R.id.image_title);
        textViewImageTitle.setText(Integer.toString(position));

        final ImageView imageViewImage = (ImageView) holder.view.findViewById(R.id.image);
        Picasso.with(context).load(images.get(position).getPicturePath())
                .placeholder(holder.view.getResources()
                        .getDrawable(R.drawable.ic_contact_icon)).into(imageViewImage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}
