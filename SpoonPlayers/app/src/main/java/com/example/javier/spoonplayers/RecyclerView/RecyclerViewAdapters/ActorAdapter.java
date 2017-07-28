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
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.Utils.PicassoTransform.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {

    private ArrayList<Actor> actors;
    Context context;

    public ActorAdapter(Context context, ArrayList<Actor> actors) {
        this.actors = actors;
        this.context = context;
    }

    @Override
    public ActorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String FullName = actors.get(position).getFirstName() + " " + actors.get(position).getLastName();

        final TextView textViewTitle = (TextView) holder.view.findViewById(R.id.textViewItemTitle);
        final TextView textViewContent = (TextView) holder.view.findViewById(R.id.textViewItemContent);
        final ImageView imageViewImage = (ImageView) holder.view.findViewById(R.id.imageViewImage);
        textViewTitle.setText(FullName);
        textViewContent.setText(actors.get(position).getEmail());
        Picasso.with(context).load(actors.get(position).getPicturePath())
                .placeholder(holder.view.getResources()
                        .getDrawable(R.drawable.ic_contact_icon)).transform(new CircleTransform()).into(imageViewImage);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}
