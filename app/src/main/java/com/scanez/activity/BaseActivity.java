package com.scanez.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scanez.R;
import com.scanez.fragment.Dashboard_Fragment;
import com.scanez.fragment.FullScanner_Fragment;
import com.scanez.fragment.History_Fragment;
import com.scanez.fragment.Splash_Fragment;
import com.scanez.interfaces.MainInterfaces;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aspl on 24/3/18.
 */

public class BaseActivity extends AppCompatActivity implements MainInterfaces {
    @BindView(R.id.imgToolBarBack)
    ImageView imgToolBarBack;
    @BindView(R.id.imgToolBarHome)
    ImageView imgToolBarHome;
    @BindView(R.id.textViewToolBarTitle)
    TextView textViewToolBarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container1)
    LinearLayout container1;

    @BindView(R.id.left_drawer)
    FrameLayout leftDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.selectqr)
    LinearLayout selectqr;
    @BindView(R.id.selectbarcode)
    LinearLayout selectbarcode;
    @BindView(R.id.scan_qrcode)
    LinearLayout scanQrcode;
    @BindView(R.id.scan_barcode)
    LinearLayout scanBarcode;
    @BindView(R.id.click_history)
    LinearLayout clickHistory;
    private float lastTranslate = 0.0f;

    public ImageView getImgToolBarBack() {
        return imgToolBarBack;
    }

    public void setImgToolBarBack(ImageView imgToolBarBack) {
        this.imgToolBarBack = imgToolBarBack;
    }

    public ImageView getImgToolBarHome() {
        return imgToolBarHome;
    }

    public void setImgToolBarHome(ImageView imgToolBarHome) {
        this.imgToolBarHome = imgToolBarHome;
    }

    public TextView getTextViewToolBarTitle() {
        return textViewToolBarTitle;
    }

    public void setTextViewToolBarTitle(TextView textViewToolBarTitle) {
        this.textViewToolBarTitle = textViewToolBarTitle;
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    public void setToolBar(Toolbar toolBar) {
        this.toolBar = toolBar;
    }

    public AppBarLayout getAppbar() {
        return appbar;
    }

    public void setAppbar(AppBarLayout appbar) {
        this.appbar = appbar;
    }

    public LinearLayout getContainer1() {
        return container1;
    }

    public void setContainer1(LinearLayout container1) {
        this.container1 = container1;
    }

    public FrameLayout getLeftDrawer() {
        return leftDrawer;
    }

    public void setLeftDrawer(FrameLayout leftDrawer) {
        this.leftDrawer = leftDrawer;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public ActionBarDrawerToggle getmDrawerToggle() {
        return mDrawerToggle;
    }

    public void setmDrawerToggle(ActionBarDrawerToggle mDrawerToggle) {
        this.mDrawerToggle = mDrawerToggle;
    }

    public LinearLayout getSelectqr() {
        return selectqr;
    }

    public void setSelectqr(LinearLayout selectqr) {
        this.selectqr = selectqr;
    }

    public LinearLayout getSelectbarcode() {
        return selectbarcode;
    }

    public void setSelectbarcode(LinearLayout selectbarcode) {
        this.selectbarcode = selectbarcode;
    }

    public LinearLayout getScanQrcode() {
        return scanQrcode;
    }

    public void setScanQrcode(LinearLayout scanQrcode) {
        this.scanQrcode = scanQrcode;
    }

    public LinearLayout getScanBarcode() {
        return scanBarcode;
    }

    public void setScanBarcode(LinearLayout scanBarcode) {
        this.scanBarcode = scanBarcode;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgToolBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.acc_drawer_open, R.string.acc_drawer_close) {
            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (leftDrawer.getWidth() * slideOffset);
                Log.e("SLIDE OFFSET::", ":" + slideOffset);

                if (slideOffset == 1.0f) {
                    container1.setScaleY(0.99f);
                } else {
                    container1.setScaleY(1);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    container1.setTranslationX(moveFactor);

                } else {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    container1.startAnimation(anim);
                    lastTranslate = moveFactor;
                }
            }
        };

        drawerLayout.addDrawerListener(mDrawerToggle);
        scanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFullScanner();
            }
        });
        scanQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFullScanner();
            }
        });
        clickHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCodeHistory();
            }
        });
    }

    public void replaceFragment(Fragment mFragment, int id, String tag, boolean addToStack) {
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
        }
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(id, mFragment);
        hideKeyboard();
        if (addToStack) {
            mTransaction.addToBackStack(tag);
        }
        mTransaction.commitAllowingStateLoss();
    }

    public void addFragment(Fragment mFragment, int id, String tag) {
        hideKeyboard();
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.addToBackStack(tag);
        mTransaction.commit();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof Splash_Fragment || fragment instanceof Dashboard_Fragment) {
            finish();
            hideKeyboard();
        } else if (fragment instanceof FullScanner_Fragment || fragment instanceof History_Fragment) {
            DashBoard();
        } else {
            hideKeyboard();
            super.onBackPressed();
        }
    }

    @Override
    public void OpenSplash() {
        replaceFragment(new Splash_Fragment(), R.id.container, null, false);
    }

    @Override
    public void DashBoard() {
        replaceFragment(new Dashboard_Fragment(), R.id.container, Dashboard_Fragment.class.getName(), false);
    }

    @Override
    public void OpenFullScanner() {
        replaceFragment(new FullScanner_Fragment(), R.id.container, FullScanner_Fragment.class.getName(), false);
    }

    @Override
    public void OpenCodeHistory() {
        replaceFragment(new History_Fragment(), R.id.container, History_Fragment.class.getName(), false);
    }
}
