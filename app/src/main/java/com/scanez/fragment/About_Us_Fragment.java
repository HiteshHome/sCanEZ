package com.scanez.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scanez.R;
import com.scanez.activity.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aspl on 29/3/18.
 */

public class About_Us_Fragment extends BaseFragment {

    View view;
    @BindView(R.id.clickGmail_hitesh)
    ImageView clickGmailHitesh;
    @BindView(R.id.clickTwitter_hitesh)
    ImageView clickTwitterHitesh;
    @BindView(R.id.clickInstagram_hitesh)
    ImageView clickInstagramHitesh;
    @BindView(R.id.clickSkype_hitesh)
    ImageView clickSkypeHitesh;
    @BindView(R.id.clickGmail_harsh)
    ImageView clickGmailHarsh;
    @BindView(R.id.clickTwitter_harsh)
    ImageView clickTwitterHarsh;
    @BindView(R.id.clickSkype_harsh)
    ImageView clickSkypeHarsh;
    @BindView(R.id.clickBlogger_harsh)
    ImageView clickBloggerHarsh;
    Unbinder unbinder;
    String mySkypeUriHitesh = "skype:72f00c909f2cbbae?chat";
    String mySkypeUriHarsh = "skype:harsh_366?chat";
    @BindView(R.id.click_rateus)
    TextView clickRateus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setToolbarForFragment() {
        ((BaseActivity) getActivity()).getAppbar().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getToolBar().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getImgToolBarHome().setVisibility(View.GONE);
        ((BaseActivity) getActivity()).getImgToolBarBack().setVisibility(View.GONE);
        ((BaseActivity) getActivity()).getImgToolBarMenu().setVisibility(View.GONE);
        ((BaseActivity) getActivity()).getTextViewToolBarTitle().setText("About Us");
        ((BaseActivity) getActivity()).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.clickGmail_hitesh, R.id.clickTwitter_hitesh, R.id.clickInstagram_hitesh, R.id.clickSkype_hitesh, R.id.clickGmail_harsh, R.id.clickTwitter_harsh, R.id.clickSkype_harsh, R.id.clickBlogger_harsh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clickGmail_hitesh:
                openGmail(getActivity(), new String[]{"hitesh.manwani1994@gmail.com"}, "Feedback", "");
                break;
            case R.id.clickTwitter_hitesh:

                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_SEND);
                final PackageManager packageManager = getActivity().getPackageManager();
                List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo resolveInfo : list) {
                    String packageName = resolveInfo.activityInfo.packageName;

                    //In case that the app is installed, lunch it.
                    if (packageName != null && packageName.equals("com.twitter.android")) {
                        try {
                            String formattedTwitterAddress = "twitter://user/";
                            Intent browseTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
                            long twitterId = Long.parseLong("manwani_hitesh");
                            browseTwitter.putExtra("user_id", twitterId);
                            startActivity(browseTwitter);

                            return;
                        } catch (Exception e) {

                        }
                    }
                }
                try {
                    String twitterName = "manwani_hitesh";
                    String formattedTwitterAddress = "http://twitter.com/" + twitterName;
                    Intent browseTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
                    startActivity(browseTwitter);
                } catch (Exception e) {
                }

                break;
            case R.id.clickInstagram_hitesh:
                callInstagram();
                break;
            case R.id.clickSkype_hitesh:
                initiateSkypeUri(getContext(), mySkypeUriHitesh);
                break;
            case R.id.clickGmail_harsh:
                openGmail(getActivity(), new String[]{"harshlplv@gmail.com"}, "Feedback", "");
                break;
            case R.id.clickTwitter_harsh:
                Intent intents = new Intent();
                intents.setType("text/plain");
                intents.setAction(Intent.ACTION_SEND);
                final PackageManager packageManagers = getActivity().getPackageManager();
                List<ResolveInfo> lists = packageManagers.queryIntentActivities(intents, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo resolveInfo : lists) {
                    String packageName = resolveInfo.activityInfo.packageName;

                    //In case that the app is installed, lunch it.
                    if (packageName != null && packageName.equals("com.twitter.android")) {
                        try {
                            String formattedTwitterAddress = "twitter://user/";
                            Intent browseTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
                            long twitterId = Long.parseLong("HarshAhiraokar1");
                            browseTwitter.putExtra("user_id", twitterId);
                            startActivity(browseTwitter);
                            return;
                        } catch (Exception e) {
                        }
                    }
                }
                try {
                    String twitterName = "HarshAhiraokar1";
                    String formattedTwitterAddress = "http://twitter.com/" + twitterName;
                    Intent browseTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
                    startActivity(browseTwitter);
                } catch (Exception e) {
                }
                break;
            case R.id.clickSkype_harsh:
                initiateSkypeUri(getContext(), mySkypeUriHarsh);
                break;
            case R.id.clickBlogger_harsh:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://harshahiraokar.blogspot.in/"));
                startActivity(browserIntent);
                break;
        }
    }

    //For Gmail

    public static void openGmail(Activity activity, String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);

        activity.startActivity(emailIntent);
    }

    // For Skype
    public void initiateSkypeUri(Context myContext, String mySkypeUri) {

        // Make sure the Skype for Android client is installed
        if (!isSkypeClientInstalled(myContext)) {
            goToMarket(myContext);
            return;
        }

        // Create the Intent from our Skype URI
        Uri skypeUri = Uri.parse(mySkypeUri);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);

        // Restrict the Intent to being handled by the Skype for Android client
        // only
        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Initiate the Intent. It should never fail since we've already
        // established the
        // presence of its handler (although there is an extremely minute window
        // where that
        // handler can go away...)
        startActivity(myIntent);
        return;
    }

    public void goToMarket(Context myContext) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);

        return;
    }

    public boolean isSkypeClientInstalled(Context myContext) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    private void callInstagram() {
        String apppackage = "com.instagram.android";
        Context cx = getActivity();
        try {
            Intent i = cx.getPackageManager().getLaunchIntentForPackage(apppackage);
            cx.startActivity(i);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/hbmanwani")));
        }

    }


    @OnClick(R.id.click_rateus)
    public void onViewClicked() {
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
