package terribleappsdevs.com.newzz.material;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import terribleappsdevs.com.newzz.Login.CoreLoginScreen;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.activity.About;
import terribleappsdevs.com.newzz.activity.ChannelActivity;
import terribleappsdevs.com.newzz.activity.Profile;
import terribleappsdevs.com.newzz.activity.SearchAny;


/**
 * Created by florentchampigny on 27/05/2016.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nav_view;
    private FirebaseAuth mAuth;
    private ImageView img;
    private TextView username,mailid;

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getdatafrompref();


    }

    @Override
    protected void onStart() {
        super.onStart();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);
        nav_view = findViewById(R.id.nav_view);


        nav_view.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        View view = getLayoutInflater().inflate(R.layout.nav_header, nav_view, true);

        img = view.findViewById(R.id.img);
        mailid = view.findViewById(R.id.mailid);
        username= view.findViewById(R.id.username);

        getdatafrompref();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
            super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                startActivity(new Intent(DrawerActivity.this,Profile.class));
                break;
       case R.id.nav_search:
                startActivity(new Intent(DrawerActivity.this,SearchAny.class));
                break;
    case R.id.nav_channels:
                startActivity(new Intent(DrawerActivity.this,ChannelActivity.class));
                break;
            case R.id.logout:
                signOut();
                //setAppTheme(R.style.AppThemeDark);
                break;
    case R.id.navigation_sub_item_1:
                startActivity(new Intent(DrawerActivity.this, About.class));
                //setAppTheme(R.style.AppThemeDark);
                break;

        }

        mDrawer.closeDrawers();
        return true;
    }
    private void signOut() {

        if (mAuth!=null) {
            mAuth.signOut();
            //FirebaseAuth.getInstance().signOut();
            //LoginManager.getInstance().logOut();

            // Google sign out

            SharedPreferences.Editor editor = getSharedPreferences("logindata",MODE_PRIVATE).edit();
            if (editor!=null) {
                editor.clear();
                editor.apply();

            }
            finish();

            startActivity(new Intent(this,CoreLoginScreen.class));



        }
    }

    private void getdatafrompref() {
        SharedPreferences data = getSharedPreferences("logindata", MODE_PRIVATE);
        if (data != null) {
            String email, name, pic;
            email = data.getString("email", "x");
            name = data.getString("name", "x");
            pic = data.getString("pic", "x");
        if (username!=null)
            username.setText(name);
            if (mailid!=null)
                mailid.setText(email);
            if (img!=null)
                Glide.with(this).load(pic).apply(RequestOptions.circleCropTransform()).into(img);


        }

    }
}
