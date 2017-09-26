package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.hyphenate.easeui.R;

public class EaseAmapActivity extends  EaseBaseActivity implements AMap.OnMarkerClickListener{
    private final static String TAG = "map";
    Button sendButton = null;
    MapView  map = null;
    protected double curLatitude=0.0;//纬度
    protected double curLongitude=0.0;//经度
    protected String curAdress="";//经度

    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;


    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private MarkerOptions markerOption;
    private AMap aMap;
    private LatLng latlng = null;
    private Marker marker;
    private static  int DHWHAT=0X126;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what==DHWHAT){
                if (curLatitude!=0.0) {
                    sendButton.setEnabled(true);
                    latlng = new LatLng(curLatitude, curLongitude);
                    init();
                    handler.removeMessages(DHWHAT);
                    stopDW();
                }else {
                    handler.sendEmptyMessageDelayed(DHWHAT,2000);
                }
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ease_amap);
        map = (MapView) findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        sendButton = (Button) findViewById(R.id.btn_location_send);

        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 0);
        if (latitude == 0) {
            getJwd();
            handler.sendEmptyMessageDelayed(DHWHAT, 2000);
        }else{
            sendButton.setVisibility(View.GONE);
            double longtitude = intent.getDoubleExtra("longitude", 0);
            String address = intent.getStringExtra("address");
            latlng = new LatLng(latitude, longtitude);
           init();
        }
    }

    public void getJwd() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//        ref=new WeakReference<AMapLocationListener>(mLocationListener);
//        AMapLocationListener refAMapLocationListener=ref.get();
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        /**
         * 获取一次定位
         */
        //该方法默认为false，true表示只定位一次
        mLocationOption.setOnceLocation(false);
//        mLocationOption.setInterval(10000);
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            // TODO Auto-generated method stub
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    double locationType = amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    curLatitude = amapLocation.getLatitude();//获取纬度
                    curLongitude = amapLocation.getLongitude();//经度
                    curAdress=amapLocation.getAddress();
//                    setCurLatitude(curLatitude);
//                    setCurLongitude(curLongitude);

                    Log.i(TAG+",Amap==经度：纬度", "locationType:" + locationType + ",curLatitude:" + curLatitude + ",curLongitude" + curLongitude);

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = map.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        setUpMap();
    }

    private void setUpMap() {
        aMap.setOnMarkerClickListener(this);
        addMarkersToMap();// 往地图上添加marker
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        Log.i("mapjwd", curLatitude + "," + curLongitude);
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(latlng)
                .draggable(true);
//                .title("地址：")
//                .snippet(curCity);

        marker = aMap.addMarker(markerOption);

        marker.showInfoWindow();
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (aMap != null) {
            jumpPoint(marker);
        }
//        com.amap.api.maps.model.LatLng epoint = new com.amap.api.maps.model.LatLng(latitude, longitude);
//        Poi epoi = new Poi(hosName, epoint, "");
//        AmapNaviPage.getInstance().showRouteActivity(getContext().getApplicationContext(), new AmapNaviParams(epoi), this);

//        daoHang(latitude,longitude);
        Log.i("curLatitude", curLatitude + "");

        return true;
    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    public void sendLocation(View view) {
        Intent intent = this.getIntent();
        intent.putExtra("latitude", curLatitude);
        intent.putExtra("longitude", curLongitude);
        intent.putExtra("address", curAdress);
        this.setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
    public void stopDW(){
        if (mLocationClient != null) {
            mLocationClient.stopLocation();

        }
    }

}
