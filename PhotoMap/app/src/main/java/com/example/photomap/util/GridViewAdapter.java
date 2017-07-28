package com.example.photomap.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.photomap.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tan on 9/22/2015.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private List<String> data = new ArrayList<String>();
    private int resourceId;
    LayoutInflater inflater;

    public GridViewAdapter(Context context, int resourceId, List<String> data) {
        super(context, resourceId, data);
        this.resourceId = resourceId;
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView image;
        TextView imagePath;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            row = inflater.inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.imagePath = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Glide.with(context)
                .load("file://" + data.get(position))
                .fitCenter()
                .centerCrop()
                .into(holder.image);
        holder.imagePath.setText(data.get(position));

        return row;
    }
}