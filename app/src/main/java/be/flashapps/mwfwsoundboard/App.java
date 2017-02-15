package be.flashapps.mwfwsoundboard;

/**
 * Created by dietervaesen on 9/11/15.
 */

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.orhanobut.logger.Logger;

import io.fabric.sdk.android.Fabric;


public class App extends Application {


    private static App instance;
    private static Context mContext;
    public static final String GOOGLE_MAPS_KEY = "AIzaSyCuOBj33ITM5FKktWxaNdYr2mKr_tuG3AI";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("mwfw soundboard");


        if(!BuildConfig.DEBUG) {
            CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
            Fabric.with(this, new Crashlytics.Builder().core(core).build(), new Crashlytics());
        }
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
    }


    public static Context getContext() {
        return mContext;
    }

    public static App getInstance() {
        return instance;
    }
}
