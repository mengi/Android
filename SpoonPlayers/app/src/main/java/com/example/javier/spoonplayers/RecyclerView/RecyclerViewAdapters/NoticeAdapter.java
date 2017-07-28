package com.example.javier.spoonplayers.RecyclerView.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javier.spoonplayers.Model.Notice;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.Utils.PicassoTransform.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private ArrayList<Notice> notices;
    Context context;

    public NoticeAdapter(Context context, ArrayList<Notice> notices) {
        this.notices = notices;
        this.context = context;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ImageView imageViewAppIcon = (ImageView) holder.view.findViewById(R.id.imageViewAppIcon);
        final ImageView imageViewNotice = (ImageView) holder.view.findViewById(R.id.imageViewNotice);

        final  TextView textViewNoticeDate = (TextView) holder.view.findViewById(R.id.textViewNoticeDate);
        final TextView textViewNoticeTitle = (TextView) holder.view.findViewById(R.id.textViewNoticeTitle);
        final TextView textViewSubtitleView = (TextView) holder.view.findViewById(R.id.textViewSubtitleView);

        textViewNoticeDate.setText(notices.get(position).getDates());
        textViewNoticeTitle.setText(notices.get(position).getTopicTitle());
        textViewSubtitleView.setText(notices.get(position).getSubject());

        Picasso.with(context).load(R.drawable.notice_two_png).transform(new CircleTransform()).into(imageViewAppIcon);
        Picasso.with(context).load(notices.get(position).getPicturePath())
                .placeholder(holder.view.getResources()
                        .getDrawable(R.drawable.notice_two_png)).into(imageViewNotice);
    }
    @Override
    public int getItemCount() {
        return notices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}
