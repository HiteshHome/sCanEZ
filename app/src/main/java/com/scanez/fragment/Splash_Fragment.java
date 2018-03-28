package com.scanez.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.scanez.R;
import com.scanez.activity.BaseActivity;

/**
 * Created by aspl on 24/3/18.
 */

public class Splash_Fragment extends BaseFragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        view=inflater.inflate(R.layout.fragment_splash,container,false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            mainInterfaces.DashBoard();
            }
        }, 2500);

        return view;
    }

    @Override
    public void setToolbarForFragment() {
        ((BaseActivity)getActivity()).getAppbar().setVisibility(View.GONE);
        ((BaseActivity)getActivity()).getToolBar().setVisibility(View.GONE);
        ((BaseActivity)getActivity()).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
}
