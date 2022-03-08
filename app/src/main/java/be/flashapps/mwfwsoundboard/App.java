package be.flashapps.mwfwsoundboard;

/**
 * Created by dietervaesen on 9/11/15.
 */

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

public class App extends Application {


    private static App instance;
    private static Context mContext;
    public static final String GOOGLE_MAPS_KEY = "AIzaSyCuOBj33ITM5FKktWxaNdYr2mKr_tuG3AI";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("mwfw soundboard");
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
