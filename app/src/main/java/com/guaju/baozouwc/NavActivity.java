package com.guaju.baozouwc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.guaju.baozouwc.listener.MyAMapNaviListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-6-22.
 */

public class NavActivity extends Activity implements AMapNaviViewListener{
    AMapNaviView mAMapNaviView;
    private AMapNavi mAMapNavi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout);

        //算路终点坐标
         NaviLatLng mEndLatlng = new NaviLatLng(22.652, 113.966);
//算路起点坐标
         NaviLatLng mStartLatlng = new NaviLatLng(22.540332, 113.939961);
//存储算路起点的列表
         final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
//存储算路终点的列表
         final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();



        //获取 AMapNaviView 实例
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);

        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        AMapNaviViewOptions viewOptions = mAMapNaviView.getViewOptions();
        viewOptions.setLayoutVisible(true);
        viewOptions.setLaneInfoShow(true);
        viewOptions.setAutoChangeZoom(true);
        viewOptions.setAutoDrawRoute(true);
        viewOptions.setTrafficLine(true);
        //获取AMapNavi实例
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
//设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);

        sList.add(mStartLatlng);
        eList.add(mEndLatlng);//添加监听回调，用于处理算路成功
        mAMapNavi.addAMapNaviListener(new MyAMapNaviListener() {

            @Override
            public void myOnInitNaviSuccess() {
                /**
                 * 方法:
                 *   int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute);
                 * 参数:
                 * @congestion 躲避拥堵
                 * @avoidhightspeed 不走高速
                 * @cost 避免收费
                 * @hightspeed 高速优先
                 * @multipleroute 多路径
                 *
                 * 说明:
                 *      以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
                 * 注意:
                 *      不走高速与高速优先不能同时为true
                 *      高速优先与避免收费不能同时为true
                 */
                int strategy=0;
                try {
                    strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAMapNavi.calculateDriveRoute(sList, eList, null, strategy);
            }

            @Override
            public void myOnCalculateRouteSuccess() {
                mAMapNavi.startNavi(NaviType.EMULATOR);
            }
        });
    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
    }
}
