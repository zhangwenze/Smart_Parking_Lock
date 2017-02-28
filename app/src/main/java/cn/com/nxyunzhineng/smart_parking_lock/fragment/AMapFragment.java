package cn.com.nxyunzhineng.smart_parking_lock.fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;


import cn.com.nxyunzhineng.smart_parking_lock.R;


public class AMapFragment extends Fragment
    implements LocationSource,AMapLocationListener{

    private MapView mapView ;
    private AMap aMap;
    private View lyview;
    private com.amap.api.maps2d.LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private UiSettings mUiSettings = null;//定义一个UiSettings对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

       if(lyview == null) {
           lyview = inflater.inflate(R.layout.frag_map, null);
           mapView = (MapView) lyview.findViewById(R.id.my_map);
           mapView.onCreate(savedInstanceState);
       }
        if(aMap == null){
            aMap = mapView.getMap();
            aMap.setLocationSource(this);// 设置定位监听

            mUiSettings = aMap.getUiSettings();//设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
            aMap.setLocationSource(this);// 设置定位监听
            mUiSettings.setMyLocationButtonEnabled(true); // 显示默认的定位按钮
            mUiSettings.setCompassEnabled(true);

            aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层

            com.amap.api.maps2d.model.MyLocationStyle myLocationStyle = new com.amap.api.maps2d.model.MyLocationStyle();
            // 自定义定位蓝点图标
            myLocationStyle.myLocationIcon(com.amap.api.maps2d.model.BitmapDescriptorFactory.
                    fromResource(R.drawable.gps_point));
            // 自定义精度范围的圆形边框颜色
            myLocationStyle.strokeColor(STROKE_COLOR);
            //自定义精度范围的圆形边框宽度
            myLocationStyle.strokeWidth(5);
            // 设置圆形的填充颜色
            myLocationStyle.radiusFillColor(FILL_COLOR);
            // 将自定义的 myLocationStyle 对象添加到地图上
            aMap.setMyLocationStyle(myLocationStyle);
//            aMap.setLocationSource(this);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));

        }
        else
        {
            if(lyview.getParent() != null)
                ((ViewGroup)lyview.getParent()).removeView(lyview);
        }
        return lyview;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
               // aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            } else {
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this.getContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationOption.setInterval(2000);
            mLocationOption.setMockEnable(true);
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
