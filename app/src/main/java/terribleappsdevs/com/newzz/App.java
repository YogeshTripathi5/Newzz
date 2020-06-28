package terribleappsdevs.com.newzz;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import io.branch.referral.Branch;


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


