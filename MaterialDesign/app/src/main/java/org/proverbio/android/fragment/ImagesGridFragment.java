package org.proverbio.android.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.proverbio.android.activity.ImageViewerActivity;
import org.proverbio.android.fragment.base.BaseFragment;
import org.proverbio.android.util.Utils;
import org.proverbio.android.material.R;
import org.proverbio.android.recycler.RecyclerItem;


/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co>
 */
public class ImagesGridFragment extends BaseFragment implements CardAdapter.RecyclerCardCallback
{
    public static final String TAG = ImagesGridFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private CardAdapter cardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        if (recyclerView == null)
        {
            recyclerView = (RecyclerView)inflater.inflate(R.layout.fragment_recycler_card_fragment, container, false);
            cardAdapter = new CardAdapter(getContext(), this);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Utils.getMaxColumnsForScreen(getContext(), 300));
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(cardAdapter);
            getSwipeRefreshLayout().addView(recyclerView);
        }

        return getSwipeRefreshLayout();
    }

    @Override
    public int getTitleResId()
    {
        return R.string.drawer_item_one;
    }

    @Override
    public void onItemImageClick(int position)
    {
        RecyclerItem selectedItem = cardAdapter.getItems().get(position);
        ImageViewerActivity.launch(getContext(), selectedItem.getImageView(), selectedItem.getUrl());
    }

    @Override
    public void onItemLikeButtonClick(int position)
    {
        Toast.makeText(getContext(), getContext().getString(R.string.like_label) + cardAdapter.getItems().get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemCommentButtonClick(int position)
    {
        Toast.makeText(getContext(), getContext().getString(R.string.comment_label) + cardAdapter.getItems().get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemShareButtonClick(int position)
    {
        Toast.makeText(getContext(), getContext().getString(R.string.share_label) + cardAdapter.getItems().get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    public boolean isNavigationFragment()
    {
        return true;
    }
}
