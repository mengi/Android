package com.example.javier.spoonplayers.Tabs.TabsViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.javier.spoonplayers.Connect.GroupApiService;
import com.example.javier.spoonplayers.Connect.RetroClient;
import com.example.javier.spoonplayers.Model.Video;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewAdapters.VideoAdapter;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewDecorations.DividerItemDecoration;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewUtils.ItemClickSupport;
import com.example.javier.spoonplayers.Tabs.TabsUtils.SlidingTabLayout;
import com.example.javier.spoonplayers.Utils.ErrorMessage;
import com.example.javier.spoonplayers.Utils.ScrollManagerToolbarTabs;
import com.example.javier.spoonplayers.VideoActivity;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabVideo extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    View view;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    TypedValue typedValueToolbarHeight = new TypedValue();
    SlidingTabLayout tabs;
    int recyclerViewPaddingTop;
    GroupApiService groupApiService;
    ArrayList<Video> videoArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_design, container, false);
        sharedPreferences = getActivity().getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        recyclerViewDesign(view);
        swipeToRefresh(view);

        return view;
    }

    private void recyclerViewDesign(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDesign);
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);

        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(android.R.drawable.divider_horizontal_bright)));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        if (ErrorMessage.isConnection(getActivity())) getVideos(); else ErrorMessage.isConnectMessage(getActivity());

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                final Intent lightboxIntent = new Intent(getActivity(), VideoActivity.class);
                lightboxIntent.putExtra(VideoActivity.KEY_VIDEO_ID, videoArrayList.get(position).getId());
                startActivity(lightboxIntent);
            }
        });
    }

    public void getVideos() {
        try {
            groupApiService = RetroClient.getGroupApiService();
            Call<ArrayList<Video>> call = groupApiService.getVideoList();

            call.enqueue(new Callback<ArrayList<Video>>() {
                @Override
                public void onResponse(Call<ArrayList<Video>> call, Response<ArrayList<Video>> response) {

                    videoArrayList = new ArrayList<Video>();
                    videoArrayList = response.body();

                    // Create the recyclerViewAdapter
                    recyclerViewAdapter = new VideoAdapter(getActivity(), videoArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
                    swipeRefreshLayout.setRefreshing(false);

                    // Create the recyclerViewAdapter
                    recyclerViewAdapter = new VideoAdapter(getActivity(), videoArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
                    swipeRefreshLayout.setRefreshing(false);

                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ArrayList<Video>> call, Throwable t) {
                    ErrorMessage.getErrorMessage(getActivity());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void swipeToRefresh(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        int start = recyclerViewPaddingTop - convertToPx(48), end = recyclerViewPaddingTop + convertToPx(16);
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
                if (ErrorMessage.isConnection(getActivity())) getVideos(); else ErrorMessage.getErrorMessage(getActivity());
            }
        });
    }
    public int convertToPx(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    public void toolbarHideShow() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                ScrollManagerToolbarTabs manager = new ScrollManagerToolbarTabs(getActivity());
                manager.attach(recyclerView);
                manager.addView(toolbar, ScrollManagerToolbarTabs.Direction.UP);
                manager.setInitialOffset(toolbar.getHeight());
            }
        });
    }
}
