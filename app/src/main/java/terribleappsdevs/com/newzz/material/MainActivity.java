package terribleappsdevs.com.newzz.material;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Random;

import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.activity.Category;
import terribleappsdevs.com.newzz.material.fragment.RecyclerViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import terribleappsdevs.com.newzz.model.SelectedCategory;


public class MainActivity extends DrawerActivity {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    ArrayList<SelectedCategory> obj;
  //  private ImageView img;
 //   private TextView username,mailid;

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




}

