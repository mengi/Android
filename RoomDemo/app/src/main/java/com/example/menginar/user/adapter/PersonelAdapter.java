package com.example.menginar.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.menginar.user.DetailActivity;
import com.example.menginar.user.R;
import com.example.menginar.user.model.Personel;

import java.util.List;

/**
 * Created by menginar on 02.06.2017.
 */

public class PersonelAdapter extends RecyclerView.Adapter<PersonelAdapter.MyViewHolder> {

    public List<Personel> personels;
    Context context;


    public PersonelAdapter(List<Personel> personelList, Context context) {
        this.personels = personelList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {

        viewHolder.textViewFullName.setText(personels.get(position).fullName);
        viewHolder.imageViewPersonel.setImageResource(R.mipmap.ic_launcher);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("POSITION", position);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return personels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewPersonel;
        public final TextView textViewFullName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imageViewPersonel = (ImageView) itemView.findViewById(R.id.imageViewPersonel);
            textViewFullName = (TextView) itemView.findViewById(R.id.textViewFullName);

        }
    }
}
