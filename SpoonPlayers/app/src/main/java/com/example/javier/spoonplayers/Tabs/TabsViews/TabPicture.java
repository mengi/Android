package com.example.javier.spoonplayers.Tabs.TabsViews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.javier.spoonplayers.Connect.GroupApiService;
import com.example.javier.spoonplayers.Connect.RetroClient;
import com.example.javier.spoonplayers.Model.Image;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewAdapters.ImageAdapter;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewDecorations.DividerItemDecoration;
import com.example.javier.spoonplayers.RecyclerView.RecyclerViewUtils.ItemClickSupport;
import com.example.javier.spoonplayers.Tabs.TabsUtils.SlidingTabLayout;
import com.example.javier.spoonplayers.Utils.ErrorMessage;
import com.example.javier.spoonplayers.Utils.ScrollManagerToolbarTabs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabPicture extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    View view;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    TypedValue typedValueToolbarHeight = new TypedValue();
    SlidingTabLayout tabs;
    int recyclerViewPaddingTop;
    Context context;
    LayoutInflater layoutInflater;

    GroupApiService groupApiService;
    ArrayList<Image> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       layoutInflater = getLayoutInflater(savedInstanceState);

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
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        if (ErrorMessage.isConnection(getActivity())) getImage(); else ErrorMessage.isConnectMessage(getActivity());

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, final View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final AlertDialog dialog = builder.create();
                View dialogLayout = layoutInflater.inflate(R.layout.item_image_show, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);



                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {

                        ImageView image = (ImageView) dialog.findViewById(R.id.showDialogImage);
                        Picasso.with(context).load(images.get(position).getPicturePath())
                                .placeholder(view.getResources()
                                        .getDrawable(R.drawable.ic_contact_icon)).into(image);
                    }
                });

                dialog.show();

            }
        });

    }

    public void getImage() {

        try {
            groupApiService = RetroClient.getGroupApiService();
            Call<ArrayList<Image>> call = groupApiService.getImageList();

            call.enqueue(new Callback<ArrayList<Image>>() {
                @Override
                public void onResponse(Call<ArrayList<Image>> call, Response<ArrayList<Image>> response) {
                    images = new ArrayList<Image>();
                    images = response.body();

                    // Create the recyclerViewAdapter
                    recyclerViewAdapter = new ImageAdapter(getActivity(), images);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
                    swipeRefreshLayout.setRefreshing(false);

                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ArrayList<Image>> call, Throwable t) {
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
                if (ErrorMessage.isConnection(getActivity())) getImage(); else ErrorMessage.isConnectMessage(getActivity());
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

