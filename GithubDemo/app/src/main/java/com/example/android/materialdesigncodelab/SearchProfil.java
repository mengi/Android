package com.example.android.materialdesigncodelab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.content.Context;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import apiservice.GithubService;
import apiservice.RetroClient;

import model.UserSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Menginar on 27.3.2017.
 */

public class SearchProfil extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    Button btnSearch;
    EditText editTextSearch;
    GithubService githubService;
    SearchsAdapter adapter;
    public static UserSearch userSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        btnSearch = (Button) findViewById(R.id.button_search_user);
        editTextSearch = (EditText) findViewById(R.id.input_search_name);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getActionBar().setTitle("Search");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextSearch.getText().toString();
                if (!username.isEmpty()) {
                    getUserSearc(username);
                } else {
                    Snackbar.make(getCurrentFocus(), "Fill in the required fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getUserSearc(String username) {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait.. ...");
            progressDialog.show();
            getUserList(username);

        } catch (Exception e) {}
    }

    public void getUserList (String username) {
        try {
            githubService = RetroClient.getGithubService();
            Call<UserSearch> call = githubService.getUsersSearch(username);
            call.enqueue(new Callback<UserSearch>() {
                @Override
                public void onResponse(Call<UserSearch> call, Response<UserSearch> response) {
                    userSearch = response.body();
                    progressDialog.dismiss();

                    adapter = new SearchsAdapter(userSearch, SearchProfil.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<UserSearch> call, Throwable t) {
                    progressDialog.dismiss();
                    Snackbar.make(getCurrentFocus(), "No Connection to server", Snackbar.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView github_title;
        public TextView github_desc;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.github_avatar);
            github_title = (TextView) itemView.findViewById(R.id.github_title);
            github_desc = (TextView) itemView.findViewById(R.id.github_desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SearchDetailProfil.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class SearchsAdapter extends RecyclerView.Adapter<ViewHolder> {
        //

        UserSearch usersearch;
        Context context;

        public SearchsAdapter(UserSearch userSearch, Context context) {
            this.usersearch = userSearch;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Picasso.with(context)
                    .load(usersearch.getItems().get(position).getAvatarUrl())
                    .into(holder.picture);
            holder.github_title.setText(usersearch.getItems().get(position).getLogin());
            holder.github_desc.setText(usersearch.getItems().get(position).getGravatarId());
        }

        @Override
        public int getItemCount() {
            return userSearch.getItems().size();
        }
    }

}
