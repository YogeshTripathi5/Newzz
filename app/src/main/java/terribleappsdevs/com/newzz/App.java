package terribleappsdevs.com.newzz;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import io.branch.referral.Branch;
import terribleappsdevs.com.newzz.utils.TypefaceUtil;


public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();



        // initialize the AdMob app
       // MobileAds.initialize(this, getString(R.string.admob_app_id));
// Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}


