package terribleappsdevs.com.newzz.Login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;

import terribleappsdevs.com.newzz.activity.ChannelActivity;
import terribleappsdevs.com.newzz.material.MainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import terribleappsdevs.com.newzz.activity.Category;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.service.AlarmReceiver;

/**
 * Created by admin1 on 8/10/17.
 */

public class CoreLoginScreen extends AppCompatActivity  {

   public  GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "CoreLogin";

    String name;


    @Override
    protected void onResume() {
        super.onResume();

       String na =  name;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        setAlarm();

        if (getSharedPreferences("logindata",MODE_PRIVATE).getString("name","")!=null && !getSharedPreferences("logindata",MODE_PRIVATE).getString("name","").isEmpty())
        {


            startActivity(new Intent(this,MainActivity.class));overridePendingTransition(0,0);finish();
        }
        else
        {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {



                    setupui();

                }
            },3000);

           // setupui();

        }







    }

    public void setAlarm() {
        Intent myIntent = new Intent(CoreLoginScreen.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CoreLoginScreen.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar firingCal = Calendar.getInstance();


        firingCal.set(Calendar.HOUR, 17); // At the hour you wanna fire
        firingCal.set(Calendar.MINUTE, 53); // Particular minute
        firingCal.set(Calendar.SECOND, 0); // particular second

        long intendedTime = firingCal.getTimeInMillis();

        // you can add buffer time too here to ignore some small differences in milliseconds
        // set from today
        alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void setupui() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.mipmap.ic_launcher)
                        .setTheme(R.style.AppTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);

        printKeyHash(this);

// ...

// Choose authentication providers

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }





    public String printKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == ResultCodes.OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.getEmail();
                user.getUid();
                user.getDisplayName();
                user.getPhotoUrl();
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("logindata", MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("email", user.getEmail());
                editor.putString("name", user.getDisplayName());
                editor.putString("pic", String.valueOf(user.getPhotoUrl()));
                editor.commit();



                startActivity(new Intent(this,Category.class));

                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }



}
