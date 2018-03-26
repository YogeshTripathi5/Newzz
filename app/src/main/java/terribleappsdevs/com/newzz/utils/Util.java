package terribleappsdevs.com.newzz.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by yogeshtripathi on 4/2/18.
 */

public class Util {


    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((localConnectivityManager.getActiveNetworkInfo() != null)
                && (localConnectivityManager.getActiveNetworkInfo()
                .isAvailable())
                && (localConnectivityManager.getActiveNetworkInfo()
                .isConnected())) {
            return true;
        } else {
            try {

                Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show();
                /*UIUtilities uiUtilities=new UIUtilities();
                String msg="No internet connection";*/
                //	uiUtilities.showToast(context, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

    }

}
