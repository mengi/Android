package com.example.javier.spoonplayers.Fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.javier.spoonplayers.ActorActivity;
import com.example.javier.spoonplayers.Connect.GroupApiService;
import com.example.javier.spoonplayers.Connect.RetroClient;
import com.example.javier.spoonplayers.MainActivity;
import com.example.javier.spoonplayers.Model.Actor;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewAdapters.ActorAdapter;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewDecorations.DividerItemDecoration;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewUtils.ItemClickSupport;
import com.example.javier.spoonplayers.Utils.ErrorMessage;
import com.example.javier.spoonplayers.Utils.ScrollManagerToolbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentActor extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    View view;
    int recyclerViewPaddingTop;
    TypedValue typedValueToolbarHeight = new TypedValue();
    Toolbar toolbar;
    FrameLayout statusBar;

    GroupApiService groupApiService;
    ArrayList<Actor> actors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_develop, container, false);
        sharedPreferences = getActivity().getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        statusBar = (FrameLayout) getActivity().findViewById(R.id.statusBar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.actor);
        toolbarHideShow();

        recyclerViewDevelop(view);

        swipeToRefresh(view);

        return view;
    }

    public void getUserInfo() {

        try {
            groupApiService = RetroClient.getGroupApiService();
            Call<ArrayList<Actor>> call = groupApiService.getActorList();

            call.enqueue(new Callback<ArrayList<Actor>>() {
                @Override
                public void onResponse(Call<ArrayList<Actor>> call, Response<ArrayList<Actor>> response) {

                    try {
                        actors = new ArrayList<Actor>();
                        actors = response.body();

                        adapter = new ActorAdapter(getActivity(), actors);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
                        swipeRefreshLayout.setRefreshing(false);

                        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        ErrorMessage.getErrorMessage(getActivity());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Actor>> call, Throwable t) {
                    ErrorMessage.getErrorMessage(getActivity());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recyclerViewDevelop(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDevelop);
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(android.R.drawable.divider_horizontal_bright)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValueToolbarHeight, true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (Build.VERSION.SDK_INT >= 19) {
                recyclerViewPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics()) + convertToPx(25);
            }else{
                recyclerViewPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 21) {
                recyclerViewPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21){
                recyclerViewPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics()) + convertToPx(25);
            }
            if (Build.VERSION.SDK_INT < 19) {
                recyclerViewPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
        }

        recyclerView.setPadding(0, recyclerViewPaddingTop, 0, 0);

        if (ErrorMessage.isConnection(getActivity())) getUserInfo(); else ErrorMessage.isConnectMessage(getActivity());

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                String FullName = actors.get(position).getFirstName() + " " + actors.get(position).getLastName();
                sharedPreferences.edit().putString("FULLNAME", FullName ).apply();
                sharedPreferences.edit().putString("EMAIL", actors.get(position).getEmail()).apply();
                sharedPreferences.edit().putString("PHONE", actors.get(position).getPhone()).apply();
                sharedPreferences.edit().putString("IMAGE", actors.get(position).getPicturePath()).apply();
                Intent intent = new Intent(getActivity(), ActorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void swipeToRefresh(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        int start = convertToPx(0), end = recyclerViewPaddingTop + convertToPx(16);
        swipeRefreshLayout.setProgressViewOffset(true, start, end);
        TypedValue typedValueColorPrimary = new TypedValue();
        TypedValue typedValueColorAccent = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValueColorPrimary, true);
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValueColorAccent, true);
        final int colorPrimary = typedValueColorPrimary.data, colorAccent = typedValueColorAccent.data;
        swipeRefreshLayout.setColorSchemeColors(colorPrimary, colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ErrorMessage.isConnection(getActivity())) getUserInfo(); else ErrorMessage.isConnectMessage(getActivity());
            }
        });
    }

    public void toolbarHideShow() {
        final Activity activity = getActivity();
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                ScrollManagerToolbar manager = new ScrollManagerToolbar(activity);
                manager.attach(recyclerView);
                manager.addView(toolbar, ScrollManagerToolbar.Direction.UP);
                manager.addView(statusBar, ScrollManagerToolbar.Direction.UP);
                manager.setInitialOffset(toolbar.getHeight());
            }
        });
    }

    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    public void animationTranslationY(View view, int duration, int interpolator, int translationY) {
        Animator slideInAnimation = ObjectAnimator.ofFloat(view, "translationY", translationY);
        slideInAnimation.setDuration(duration);
        slideInAnimation.setInterpolator(new AccelerateInterpolator(interpolator));
        slideInAnimation.start();
    }
}