package terribleappsdevs.com.newzz.material;

import android.content.Intent;
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

import terribleappsdevs.com.newzz.R;
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

      @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void onStart() {
        super.onStart();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }
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
            case R.id.nav_fav:

                setAppTheme(R.style.AppThemeDark);
                break;

        }

        mDrawer.closeDrawers();
        return true;
    }

    private void setAppTheme(@StyleRes int style) {
        setTheme(style);
    }
}
