package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigninstagram.DetailActivity;
import com.example.android.materialdesigninstagram.R;
import com.example.android.materialdesigninstagram.SearchActivity;
import com.squareup.picasso.Picasso;

import model.InstagramData;

public class MediaCardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        InstagramAdapter adapter = new InstagramAdapter(getActivity(), SearchActivity.instagramData);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.instagram_image);
            name = (TextView) itemView.findViewById(R.id.instagram_title);
            description = (TextView) itemView.findViewById(R.id.instagram_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });

            Button button = (Button)itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Action is pressed",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            ImageButton favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Added to Favorite",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Share article",
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }


    public static class InstagramAdapter extends RecyclerView.Adapter<ViewHolder> {

        Context context;
        InstagramData insInstagramDataItem;

        public InstagramAdapter(Context context, InstagramData instagramData) {
            this.context = context;
            this.insInstagramDataItem = instagramData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {
                Picasso.with(context)
                        .load(insInstagramDataItem.getItems().get(position).getImages().getLowResolution().getUrl())
                        .into(holder.picture);
                if (insInstagramDataItem.getItems().get(position).getLocation().getName() != null) {
                    holder.name.setText(insInstagramDataItem.getItems().get(position).getLocation().getName());
                } else {
                    holder.name.setText(insInstagramDataItem.getItems().get(position).getUser().getFullName());
                }
                if (!insInstagramDataItem.getItems().get(position).getCaption().getText().isEmpty()) {
                    holder.description.setText(insInstagramDataItem.getItems().get(position).getCaption().getText());
                } else {
                    holder.description.setText(insInstagramDataItem.getItems().get(position).getUser().getUsername());
                }
            } catch (Exception e) {

            }
        }

        @Override
        public int getItemCount() {
            return insInstagramDataItem.getItems().size();
        }
    }
}
