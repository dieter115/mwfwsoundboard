package be.flashapps.mwfwsoundboard.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.splashscreen.SplashScreen;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Random;

import be.flashapps.mwfwsoundboard.Helpers.Helper_Fragments;
import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.databinding.ActivityMainBinding;
import be.flashapps.mwfwsoundboard.fragments.HomeFragment;
import be.flashapps.mwfwsoundboard.fragments.InfoFragment;
import be.flashapps.mwfwsoundboard.fragments.MoviesFragment;
import be.flashapps.mwfwsoundboard.fragments.SocialMediaFragment;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    HomeFragment homeFragment;
    MoviesFragment moviesFragment;
    InfoFragment infoFragment;
    SocialMediaFragment socialMediaFragment;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private float[] mGravity;
    private double mAccel;
    private double mAccelCurrent;
    private double mAccelLast;
    private MaterialDialog kupkesDialog;
    private static final String MAINMENUTAG = "mainmenu";
    int teller = 0;

    private ActivityMainBinding binding;

    private ArrayList<Integer> kupkes = new ArrayList<Integer>() {
        {
            add(R.drawable.viktor_1);
            add(R.drawable.viktor_2);
            add(R.drawable.koentje_1);
            add(R.drawable.koentje_2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        homeFragment = HomeFragment.newInstance();
        moviesFragment = MoviesFragment.newInstance();
        infoFragment = InfoFragment.newInstance();
        socialMediaFragment = SocialMediaFragment.newInstance();

        fixBottomMenu();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (mSensor != null) {
            // Success! There's a magnetometer.
            Logger.d("sensor for you!!!!");
        } else {
            // Failure! No magnetometer.
            Logger.d("no sensor for you!!!");
        }

        kupkesDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.kupkes_layout, false)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    public void fixBottomMenu() {
        binding.bottomBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                Helper_Fragments.replaceFragment(MainActivity.this, homeFragment, false, MAINMENUTAG);
            } else if (item.getItemId() == R.id.action_movie) {
                //beheermodule openen in webview
                Helper_Fragments.replaceFragment(MainActivity.this, moviesFragment, false, MAINMENUTAG);
            } else if (item.getItemId() == R.id.action_info) {
                //settings openen
                Helper_Fragments.replaceFragment(MainActivity.this, infoFragment, false, MAINMENUTAG);
            } else if (item.getItemId() == R.id.action_social) {
                //settings openen
                Helper_Fragments.replaceFragment(MainActivity.this, socialMediaFragment, false, MAINMENUTAG);
            }
            return true;
        });

        binding.bottomBar.setSelectedItemId(R.id.action_home);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private static final float THRESHOLD = 15f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;
    private float rotationCurrent;


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            // This timestep's delta rotation to be multiplied by the current rotation
            // after computing it from the gyro sample data.
            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp) * NS2S;
                // Axis of the rotation sample, not normalized yet.
                float axisX = event.values[0];
                float axisY = event.values[1];
                float axisZ = event.values[2];

                // Calculate the angular speed of the sample
                float omegaMagnitude = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

                // Normalize the rotation vector if it's big enough to get the axis
                // (that is, EPSILON should represent your maximum allowable margin of error)
                //do something if magnitude is high enough and the x value is higher then the y and z value

                if (omegaMagnitude > THRESHOLD && axisX == Math.max(axisX, axisY) && axisX == Math.max(axisX, axisZ)) {
                    if (event.timestamp - timestamp > 1000) {
                        teller++;
                        Logger.e("omegamagnitude " + omegaMagnitude + " teller " + teller);
                    }

                    if (teller >= 2) {
                        int random = (int)(Math.random() * kupkes.size());
                        Glide.with(this).load(kupkes.get(random)).into((AppCompatImageView) kupkesDialog.findViewById(R.id.ivKupke));
                        kupkesDialog.show();
                        teller = 0;
                    }
                }
            }
            timestamp = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
