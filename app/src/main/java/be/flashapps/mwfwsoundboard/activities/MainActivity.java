package be.flashapps.mwfwsoundboard.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.orhanobut.logger.Logger;

import be.flashapps.mwfwsoundboard.App;
import be.flashapps.mwfwsoundboard.Helpers.Helper_Fragments;
import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.fragments.HomeFragment;
import be.flashapps.mwfwsoundboard.fragments.InfoFragment;
import be.flashapps.mwfwsoundboard.fragments.MoviesFragment;
import be.flashapps.mwfwsoundboard.fragments.SocialMediaFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final int IDENTIFIER_HOME = 4, IDENTIFIER_MOVIES = 1, IDENTIFIER_INFO = 2, IDENTIFIER_SOCIAL = 5;
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
    private MaterialDialog loesDialog;
    private static final String MAINMENUTAG = "mainmenu";
    int teller=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        homeFragment = HomeFragment.newInstance("", "");
        moviesFragment = MoviesFragment.newInstance("", "");
        infoFragment = InfoFragment.newInstance("", "");
        socialMediaFragment = SocialMediaFragment.newInstance("", "");
        fixSideMenu();


        mSensorManager = (SensorManager) getSystemService(App.getContext().SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // Success! There's a magnetometer.
            Logger.d("accelometer!!!!");
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            // Failure! No magnetometer.
            Logger.d("no accelometer!!!!");
        }

        loesDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.loes_layout, false)
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


    public void fixSideMenu() {
        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_HOME).withName(R.string.home);
        PrimaryDrawerItem moviesDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_MOVIES).withName(R.string.movies);
        PrimaryDrawerItem infoDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_INFO).withName(R.string.info);
        PrimaryDrawerItem socialDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_SOCIAL).withName(R.string.sociale_media);


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.mwfw2017)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .withSelectionListEnabledForSingleProfile(false)
               /* .addProfiles(
                        new ProfileDrawerItem().withName("Beveiligingsafdeling")*//*.withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile))*//*
                )*/
                /*.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })*/
                .build();

        headerResult.getView().findViewById(R.id.material_drawer_account_header_current).setVisibility(View.GONE);//volledig hiden van profile bezel image view in header.xml


        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        homeDrawerItem,
                        moviesDrawerItem,
                        infoDrawerItem,
                        socialDrawerItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == IDENTIFIER_HOME) {
                            Helper_Fragments.replaceFragment(MainActivity.this, homeFragment, false, MAINMENUTAG);
                        } else if (drawerItem.getIdentifier() == IDENTIFIER_MOVIES) {
                            //beheermodule openen in webview
                            Helper_Fragments.replaceFragment(MainActivity.this, moviesFragment, false, MAINMENUTAG);
                        } else if (drawerItem.getIdentifier() == IDENTIFIER_INFO) {
                            //settings openen
                            Helper_Fragments.replaceFragment(MainActivity.this, infoFragment, false, MAINMENUTAG);
                        } else if (drawerItem.getIdentifier() == IDENTIFIER_SOCIAL) {
                            //settings openen
                            Helper_Fragments.replaceFragment(MainActivity.this, socialMediaFragment, false, MAINMENUTAG);
                        }
                        return false;
                    }
                })
                .build();


        drawer.setSelection(IDENTIFIER_HOME);
    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_main, menu);
         return true;
     }
 */
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(x * x + y * y + z * z);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if (mAccel > 25) {
                // do something
                if (x == Math.max(x, y) && x == Math.max(x, z)) {
                    if(teller>=5) {
                        if (!loesDialog.isShowing()) {
                            int range = (100 - 1) + 1;
                            int random = (int) (Math.random() * range) + 1;
                            ImageView ivLoes = (ImageView) loesDialog.getCustomView().findViewById(R.id.iv_loes);
                            if (random % 2 == 1) {
                                Glide.with(App.getContext())
                                        .load(R.drawable.loes1)
                                        .fitCenter()
                                        .crossFade()
                                        .into(ivLoes);
                            } else {
                                Glide.with(App.getContext())
                                        .load(R.drawable.loes2)
                                        .fitCenter()
                                        .crossFade()
                                        .into(ivLoes);
                            }
                            loesDialog.show();
                        }
                        teller=0;
                    }
                    teller++;
                }
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
