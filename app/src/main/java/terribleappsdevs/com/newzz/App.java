package terribleappsdevs.com.newzz;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import terribleappsdevs.com.newzz.utils.TypefaceUtil;


public class App extends Application {


    @Override
    protected void attachBaseContext(Context base) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // NightOwl.builder().defaultMode(0).create();
        TypefaceUtil.overrideFont(getApplicationContext(), "serif", "fonts/roboto_regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        MultiDex.install(this);

    }



}