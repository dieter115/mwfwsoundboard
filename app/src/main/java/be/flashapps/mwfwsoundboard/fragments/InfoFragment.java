package be.flashapps.mwfwsoundboard.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.databinding.FragmentInfoBinding;


public class InfoFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity mActivity;

    FragmentInfoBinding fragmentInfoBinding;
    private View mCustomMarkerView;


    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentInfoBinding = FragmentInfoBinding.inflate(inflater, container, false);
        Typeface tf = Typeface.createFromAsset(mActivity.getAssets(), "fonts/RosewoodStd-Fill.otf");
        fragmentInfoBinding.tvInfoGeneral.setTypeface(tf);
        fragmentInfoBinding.tvInfoGeneralExtra.setTypeface(tf);
        fragmentInfoBinding.tvInfoDj.setTypeface(tf);

        fragmentInfoBinding.mapView.onCreate(savedInstanceState);

        fragmentInfoBinding.mapView.onResume();
        fragmentInfoBinding.mapView.getMapAsync(this);

        mCustomMarkerView = ((LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);

        return fragmentInfoBinding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.9451556, 5.1665009))
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(mCustomMarkerView)))
                .title("Marker"));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng current = new LatLng(50.9451556, 5.1665009);
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition;

        cameraPosition = new CameraPosition.Builder().target(current).zoom(14).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private Bitmap getMarkerBitmapFromView(View view) {

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onStart() {
        /*mGoogleApiClient.connect();*/
        super.onStart();
    }

    @Override
    public void onStop() {
        /*mGoogleApiClient.disconnect();*/
        super.onStop();
    }


    @Override
    public void onResume() {
        super.onResume();
        fragmentInfoBinding.mapView.onResume();
    }

    @Override
    public void onPause() {
        fragmentInfoBinding.mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentInfoBinding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        fragmentInfoBinding.mapView.onLowMemory();
    }
}
