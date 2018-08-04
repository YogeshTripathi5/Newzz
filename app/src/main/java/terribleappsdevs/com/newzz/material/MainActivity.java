package terribleappsdevs.com.newzz.material;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import terribleappsdevs.com.newzz.Login.CoreLoginScreen;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.activity.About;
import terribleappsdevs.com.newzz.activity.Category;
import terribleappsdevs.com.newzz.activity.ChannelActivity;
import terribleappsdevs.com.newzz.activity.Profile;
import terribleappsdevs.com.newzz.activity.SearchAny;
import terribleappsdevs.com.newzz.material.fragment.RecyclerViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import terribleappsdevs.com.newzz.model.SelectedCategory;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    ArrayList<SelectedCategory> obj;
  //  private ImageView img;
 //   private TextView username,mailid;
  private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nav_view;
    private FirebaseAuth mAuth;
    private ImageView img;
    private TextView username,mailid;

    @Override
    protected void onResume() {
        super.onResume();

        getdatafrompref();

    }



    private void getdatafrompref() {
        SharedPreferences data = getSharedPreferences("logindata", MODE_PRIVATE);
        if (data != null) {
            String email, name, pic;
            email = data.getString("email", "x");
            name = data.getString("name", "x");
            pic = data.getString("pic", "x");
            if (username != null)
                username.setText(name);
            if (mailid != null)
                mailid.setText(email);
            if (img != null)
                Glide.with(this).load(pic).apply(RequestOptions.circleCropTransform()).into(img);

            username.requestLayout();
            mailid.requestLayout();
            img.requestLayout();
        }}


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
                startActivity(new Intent(MainActivity.this,Profile.class));
                break;
            case R.id.nav_search:
                startActivity(new Intent(MainActivity.this,SearchAny.class));
                break;
            case R.id.nav_channels:
                startActivity(new Intent(MainActivity.this,ChannelActivity.class));
                break;
            case R.id.logout:
                signOut();
                //setAppTheme(R.style.AppThemeDark);
                break;
            case R.id.navigation_sub_item_1:
                startActivity(new Intent(MainActivity.this, About.class));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);


   /*     img = findViewById(R.id.img);
        username = findViewById(R.id.username);
        mailid = findViewById(R.id.mailid);
   */     setTitle("");
        ButterKnife.bind(this);
     //   getdatafrompref();








        SharedPreferences preferences = getSharedPreferences("categorydata", MODE_PRIVATE);

        if (preferences != null) {
          String data =  preferences.getString("selectedcat","x");
            Gson gson = new Gson();
            obj = gson.fromJson(preferences.getString("selectedcat","x"), new TypeToken<ArrayList<SelectedCategory>>() {}.getType());

        }else {


            obj = getIntent().getParcelableArrayListExtra("categoryselected");
        }


        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return RecyclerViewFragment.newInstance(obj.get(position).getName());
            }

            @Override
            public int getCount() {
                return obj.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                SelectedCategory selectedCategory = new SelectedCategory();


                if(obj.get(position).getName()!=null)
                    return obj.get(position).getName();
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {

                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                return HeaderDesign.fromColorAndDrawable(
                        color,
                        getResources().getDrawable(obj.get(page).getImage()));


            }
        });

       mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());

    }
    @Override
    protected void onStart() {
        super.onStart();


        Branch branch = Branch.getInstance();

        // Branch init
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                    // params will be empty if no data found
                    // ... insert custom logic here ...
                    Log.i("BRANCH SDK", referringParams.toString());

                    try {
                    String par= (String) referringParams.get("$og_image_url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("BRANCH SDK", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);










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



    }
    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }




}

