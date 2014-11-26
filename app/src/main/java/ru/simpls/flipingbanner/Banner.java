package ru.simpls.flipingbanner;

import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by nikulin on 10.10.2014.
 */
public class Banner {
    //////////////////////
    private ListView listView;
    private View bannerHeader;
    private float pos;
    private FragmentActivity activity;
    //////////////////
    public void addBanner (FragmentActivity activity, ListView listView) {
        this.listView = listView;
        this.activity = activity;
        bannerHeader = activity.getLayoutInflater().inflate(R.layout.banner_header, null);
        //////////5 banners pager
        BannerPagerAdapter mAdapter;
        ViewPager mPager;
        mAdapter = new BannerPagerAdapter(activity.getSupportFragmentManager());
        mPager = (ViewPager) bannerHeader.findViewById(R.id.bannerPager);
        mPager.setAdapter(mAdapter);
        CirclePageIndicator indicator = (CirclePageIndicator) bannerHeader.findViewById(R.id.bannerIndicator);
        indicator.setViewPager(mPager);
        indicator.setSnap(true);
        ///////////////////////////////////////
        listView.addHeaderView(bannerHeader);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        flipBanner();
                    case MotionEvent.ACTION_UP:
                        checkScrollStopped();
                }
                return false;
            }
        });
    }
    ////////////////////////////////
    private void flipBanner() {
        float pos = listView.getChildAt(0).getTop();//lv.getFirstVisiblePosition();
        int headerH = bannerHeader.getHeight();
        if (Math.abs(pos) < headerH) {
            float katet = headerH - Math.abs(pos);
            double radians = Math.tan(katet / headerH);
            int degr = (int) Math.toDegrees(radians);
            if (Build.VERSION.SDK_INT > 10) {
                bannerHeader.setPivotY(bannerHeader.getHeight());
                bannerHeader.setPivotX(bannerHeader.getWidth() / 2);
                bannerHeader.setRotationX(90 - degr - 1);
            }
        }
    }
    /////////////////////////////////
    private void checkScrollStopped(){
        int timeBetweenChecks = 100;
        pos = listView.getChildAt(0).getTop();
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (pos!=listView.getChildAt(0).getTop()){
                    flipBanner();
                    checkScrollStopped();
                } else{
                    flipBanner();
                }
            }
        }, timeBetweenChecks);
    }
}
