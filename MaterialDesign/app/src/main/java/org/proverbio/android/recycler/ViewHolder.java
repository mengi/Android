package org.proverbio.android.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.proverbio.android.material.R;


/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co>
 */
public class ViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView;
    public TextView nameView;
    public TextView emailView;
    public ViewGroup countLayout;
    public TextView countView;
    public TextView likeButton;
    public TextView likeCount;
    public ImageView commentButton;
    public TextView commentCount;
    public ImageView shareButton;
    public TextView shareCount;

    public ViewHolder(View itemView)
    {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.icon);
        nameView = (TextView) itemView.findViewById(R.id.item_name);
        emailView = (TextView) itemView.findViewById(R.id.email);
        countLayout = (ViewGroup) itemView.findViewById(R.id.item_count_layout);
        countView = (TextView) itemView.findViewById(R.id.item_count);
        likeButton = (TextView) itemView.findViewById(R.id.like);
        likeCount = (TextView) itemView.findViewById(R.id.likeCount);
        commentButton = (ImageView) itemView.findViewById(R.id.comment);
        commentCount = (TextView) itemView.findViewById(R.id.commentCount);
        shareButton = (ImageView) itemView.findViewById(R.id.share);
        shareCount = (TextView) itemView.findViewById(R.id.shareCount);
    }
}
