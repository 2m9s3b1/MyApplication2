package com.example.lg.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class Fragment1 extends Fragment {
    private NMapContext mMapContext;
    private static final String CLIENT_ID = "f0l4w4ocuo";// 애플리케이션 클라이언트 아이디 값
    NMapController mapController;

    private static final boolean DEBUG = false;
    private NMapLocationManager mMapLocationManager;
    private NMapController mMapController;
    private NMapMyLocationOverlay mMyLocationOverlay;
    private NMapOverlayManager mOverlayManager;
    private NMapCompassManager mMapCompassManager;
    private NMapView mapView;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private MapContainerView mMapContainerView;


    //mapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);

    /**
     * Fragment에 포함된 NMapView 객체를 반환함
     */
    private NMapView findMapView(View v) {

        if (!(v instanceof ViewGroup)) {
            return null;
        }

        ViewGroup vg = (ViewGroup)v;
        if (vg instanceof NMapView) {
            return (NMapView)vg;
        }

        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);
            if (!(child instanceof ViewGroup)) {
                continue;
            }

            NMapView mapView = findMapView(child);
            if (mapView != null) {
                return mapView;
            }
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapContext =  new NMapContext(super.getActivity());

        mMapContext.onCreate();

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = findMapView(super.getView());
        if (mapView == null) {
            throw new IllegalArgumentException("NMapFragment dose not have an instance of NMapView.");
        }
        mapView.setNcpClientId( CLIENT_ID );// 클라이언트 아이디 설정
        mMapContext.setupMapView(mapView);
        mapController = mapView.getMapController();

        //초기화
        mapView.setClickable( true );

        mapView.setOnMapStateChangeListener( onMapStateChangeListener );
        mapView.setOnMapViewTouchEventListener( onMapViewTouchEventListener );

        mMapController = mapView.getMapController();


        //위치 서비스 매니저
        mMapLocationManager= new NMapLocationManager(super.getActivity());
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

        //나침반 매니저
        mMapCompassManager = new NMapCompassManager(super.getActivity());

        //생성
        mMapViewerResourceProvider = new NMapViewerResourceProvider(super.getActivity());

        mOverlayManager=  new NMapOverlayManager(super.getActivity(), mapView, mMapViewerResourceProvider);


        // set POI data
        //NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        //poiData.beginPOIdata(2);

        //poiData.endPOIdata();

        // create POI data overlay
        //NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);


        //내위치를 표시하기 위한 오버레이
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);
    }

    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();
        startMyLocation();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }


    private final NMapView.OnMapStateChangeListener onMapStateChangeListener = new NMapView.OnMapStateChangeListener() {
        @Override
        public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
            if (errorInfo == null) { // success
                mapController.setMapCenter( new NGeoPoint( 126.978371, 37.5666091 ), 11 );
            } else { // fail
                Log.e( "ERROR", "onMapInitHandler: error=" + errorInfo.toString() );
            }
        }

        @Override
        public void onMapCenterChange(NMapView nMapView, NGeoPoint center) {
            if (DEBUG) {
                Log.i( "ERROR", "onMapCenterChange: center=" + center.toString() );
            }
        }

        @Override
        public void onMapCenterChangeFine(NMapView mapView) {

        }

        @Override
        public void onZoomLevelChange(NMapView mapView, int level) {
            if (DEBUG) {
                Log.i( "ERROR", "onZoomLevelChange: level=" + level );
            }
        }

        @Override
        public void onAnimationStateChange(NMapView mapView, int animType, int animState) {
            if (DEBUG) {
                Log.i( "ERROR", "onAnimationStateChange: animType=" + animType + ", animState=" + animState );
            }
        }
    };

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {
        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {
            if (mapController != null) {
                mapController.animateTo(myLocation);
            }

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
// stop location updating
            //			Runnable runnable = new Runnable() {
            //				public void run() {
            //					stopMyLocation();
            //				}
            //			};
            //			runnable.run();

            //Toast.makeText(Fragment1.this, "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {

        }
    };
    private final NMapView.OnMapViewTouchEventListener onMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {
        @Override
        public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onLongPressCanceled(NMapView nMapView) {

        }

        @Override
        public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

        }

        @Override
        public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

        }
    };
    private void startMyLocation() {

        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }

            if (mMapLocationManager.isMyLocationEnabled()) {

                if (!mapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(true);

                    mMapCompassManager.enableCompass();

                    mapView.setAutoRotateEnabled(true, false);

                    mMapContainerView.requestLayout();
                } else {
                    stopMyLocation();
                }

                mapView.postInvalidate();
            } else {
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(super.getActivity(), "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                }
            }
        }
    }

    private void stopMyLocation() {
        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation();

            if (mapView.isAutoRotateEnabled()) {
                mMyLocationOverlay.setCompassHeadingVisible(false);

                mMapCompassManager.disableCompass();

                mapView.setAutoRotateEnabled(false, false);

                mMapContainerView.requestLayout();
            }
        }
    }

    private class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super( context );
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt( i );
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout( childLeft, childTop, childLeft + childWidth, childTop + childHeight );
            }

            if (changed) {
                mOverlayManager.onSizeChanged( width, height );
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = getDefaultSize( getSuggestedMinimumWidth(), widthMeasureSpec );
            int h = getDefaultSize( getSuggestedMinimumHeight(), heightMeasureSpec );
            int sizeSpecWidth = widthMeasureSpec;
            int sizeSpecHeight = heightMeasureSpec;

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt( i );

                if (view instanceof NMapView) {
                    if (mapView.isAutoRotateEnabled()) {
                        int diag = (((int) (Math.sqrt( w * w + h * h )) + 1) / 2 * 2);
                        sizeSpecWidth = MeasureSpec.makeMeasureSpec( diag, MeasureSpec.EXACTLY );
                        sizeSpecHeight = sizeSpecWidth;
                    }
                }

                view.measure( sizeSpecWidth, sizeSpecHeight );
            }
            super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        }
    }
}
