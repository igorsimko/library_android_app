package com.example.bcapp.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import com.example.bcapp.view.MapHolder;

/**
 * Created by Igor on 22.10.2015.
 */
public class MapFragment extends Fragment {

    MapHolder mapView;

    private int map_width;
    private int map_height;

    private Handler mHandler;

    private float mx, my;


    private ScrollView vScroll;
    private HorizontalScrollView hScroll;

    int x = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                float curX, curY;
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mx = event.getX();
                        my = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getX();
                        curY = event.getY();
                        System.out.println("ACTION_MOVE " + mx);
                        vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                        hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = event.getX();
                        curY = event.getY();
                        vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                        hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                        break;
                }
                return true;
            }
        });

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapView = (MapHolder) getView().findViewById(R.id.mapView);

        vScroll = (ScrollView) getView().findViewById(R.id.vScroll);
        hScroll = (HorizontalScrollView) getView().findViewById(R.id.hScroll);

        initData();

        mHandler = new Handler();
        mRunnable.run();
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mapView != null && MapFragment.this.isVisible()) {
                if (x > ((MainActivity) getActivity()).getWindowManager().getDefaultDisplay().getWidth())
                    x = 0;

                x = x + 50;

                mapView.drawPosition(x, 200);
                mapView.invalidate();
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };

    /**
     * Set data in View extended by MapHolder
     */
    private void initData() {
        if (((MainActivity) getActivity()).getMapManager() != null) {
            map_width = ((MainActivity) getActivity()).getMapManager().getWidth();
            map_height = ((MainActivity) getActivity()).getMapManager().getHeight();
        }

        if (mapView != null) {
            mapView.setDpi(((MainActivity) getActivity()).getDm().density);

            mapView.getLayoutParams().width = Math.round(map_width * ((MainActivity) getActivity()).getDm().density);
            mapView.getLayoutParams().height = Math.round(map_height * ((MainActivity) getActivity()).getDm().density);

            mapView.setImage_width(map_width);
            mapView.setImage_height(map_height);
        }
    }



    public void setMap_width(int map_width) {
        this.map_width = map_width;
    }

    public void setMap_height(int map_height) {
        this.map_height = map_height;
    }
}
