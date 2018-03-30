package com.scanez.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scanez.R;
import com.scanez.activity.BaseActivity;
import com.scanez.adapter.Recycler_Adapter;
import com.scanez.db.DatabaseHelper;
import com.scanez.model.Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aspl on 27/3/18.
 */

public class History_Fragment extends BaseFragment {

    View view;
    @BindView(R.id.reycler_view)
    RecyclerView reyclerView;
    Unbinder unbinder;
    Recycler_Adapter recycler_adapter;
    List<Data> dataList=new ArrayList<>();
    private DatabaseHelper mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        unbinder = ButterKnife.bind(this, view);

        mDatabase = new DatabaseHelper(getActivity());
        List<Data> allProducts = mDatabase.listcodes();

            reyclerView.setVisibility(View.VISIBLE);
            recycler_adapter=new Recycler_Adapter(getContext(),allProducts);
            reyclerView.setAdapter(recycler_adapter);
            reyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_adapter.notifyDataSetChanged();



        return view;
    }

    @Override
    public void setToolbarForFragment() {
        ((BaseActivity) getActivity()).getAppbar().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getToolBar().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getImgToolBarHome().setVisibility(View.GONE);
        ((BaseActivity) getActivity()).getImgToolBarBack().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getImgToolBarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainInterfaces.DashBoard();
            }
        });
        ((BaseActivity)getActivity()).getImgToolBarMenu().setVisibility(View.GONE);
        ((BaseActivity) getActivity()).getTextViewToolBarTitle().setText("History");
        ((BaseActivity) getActivity()).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
