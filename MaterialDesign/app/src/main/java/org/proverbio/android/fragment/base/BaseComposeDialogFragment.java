package org.proverbio.android.fragment.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import org.proverbio.android.activity.BaseActivity;
import org.proverbio.android.material.R;

/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co/>
 */
public abstract class BaseComposeDialogFragment extends AppCompatDialogFragment
{
    private BaseActivity context;

    private Toolbar dialogToolbar;

    @Override
    public void onAttach(Context activity)
    {
        context = (BaseActivity)activity;
        context.setTitle(getTitleResId());
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle params)
    {
        super.onActivityCreated(params);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);

        if (getView() == null)
        {
            View view = inflater.inflate(getLayoutResId(), container, false);
            dialogToolbar = (Toolbar)view.findViewById(R.id.toolbar);
            return view;
        }

        return getView();
    }

    public abstract int getLayoutResId();

    public abstract int getTitleResId();

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.menu_compose, menu);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        // the content
        final LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    @Override
    public void onResume()
    {
        if (dialogToolbar != null)
        {
            context.setSupportActionBar(dialogToolbar);
        }
        super.onResume();
    }

    @Override
    public void onStop()
    {
        context.setSupportActionBar(context.getToolbar());
        context.invalidateOptionsMenu();
        super.onStop();
    }
}
