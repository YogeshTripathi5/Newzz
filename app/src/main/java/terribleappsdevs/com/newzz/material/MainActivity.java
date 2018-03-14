package terribleappsdevs.com.newzz.material;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.Toolbar;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        ButterKnife.bind(this);


        SharedPreferences preferences = getSharedPreferences("categorydata", MODE_PRIVATE);

        if (preferences != null) {
          String data =  preferences.getString("selectedcat","x");
            Gson gson = new Gson();
            obj = gson.fromJson(preferences.getString("selectedcat","x"), new TypeToken<ArrayList<SelectedCategory>>() {}.getType());

        }else {


            obj = getIntent().getParcelableArrayListExtra("categoryselected");
        }
     // obj.getEntertainment();


 /*      SharedPreferences data= getSharedPreferences("category",MODE_PRIVATE);
        if (data!=null) {
            Set<String> strings = new HashSet<>();
            strings = data.getStringSet("set", strings);

           // Category.selected.add(strings);
           // strings.addAll(Category.selected);

            Set<String> setTemp = new HashSet<>();
            setTemp.addAll(strings);
         //   List<List<String>> list = new ArrayList<List<String>> ();
            for (String subset : setTemp) {
                Category.selected.add(subset);
            }
        }
*/
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

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
     /*   final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }*/
    }
}