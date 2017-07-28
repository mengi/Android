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
import com.example.javier.spoonplayers.DetailActivity;
import com.example.javier.spoonplayers.Model.Notice;
import com.example.javier.spoonplayers.NoticeDetailActivity;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewAdapters.NoticeAdapter;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewDecorations.DividerItemDecoration;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewUtils.ItemClickSupport;
import com.example.javier.spoonplayers.Tabs.TabsUtils.SlidingTabLayout;
import com.example.javier.spoonplayers.Utils.ErrorMessage;
import com.example.javier.spoonplayers.Utils.ScrollManagerToolbarTabs;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabNotice extends Fragment {

    GroupApiService groupApiService;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    View view;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    TypedValue typedValueToolbarHeight = new TypedValue();
    SlidingTabLayout tabs;
    int recyclerViewPaddingTop;
    ArrayList<Notice> notices;

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

        // Divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(android.R.drawable.divider_horizontal_bright)));

        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (ErrorMessage.isConnection(getActivity())) getNotices(); else ErrorMessage.isConnectMessage(getActivity());

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                sharedPreferences.edit().putString("TITLE", notices.get(position).getTopicTitle()).apply();
                sharedPreferences.edit().putString("SUBJECT", notices.get(position).getSubject()).apply();
                sharedPreferences.edit().putString("DATE", notices.get(position).getDateToString()).apply();
                sharedPreferences.edit().putString("IMAGE", notices.get(position).getPicturePath()).apply();
                Intent intent = new Intent(getActivity(), NoticeDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getNotices() {
        try {
            groupApiService = RetroClient.getGroupApiService();
            Call<ArrayList<Notice>> call = groupApiService.getNoticeList();

            call.enqueue(new Callback<ArrayList<Notice>>() {
                @Override
                public void onResponse(Call<ArrayList<Notice>> call, Response<ArrayList<Notice>> response) {
                    notices = new ArrayList<Notice>();
                    notices = response.body();

                    // Create the recyclerViewAdapter
                    recyclerViewAdapter = new NoticeAdapter(getActivity(), response.body());
                    recyclerView.setAdapter(recyclerViewAdapter);

                    swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
                    swipeRefreshLayout.setRefreshing(false);

                    // Create the recyclerViewAdapter
                    recyclerViewAdapter = new NoticeAdapter(getActivity(), notices);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
                    swipeRefreshLayout.setRefreshing(false);

                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ArrayList<Notice>> call, Throwable t) {
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
                if (ErrorMessage.isConnection(getActivity())) getNotices(); else ErrorMessage.isConnectMessage(getActivity());
            }
        });
    }

    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
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

