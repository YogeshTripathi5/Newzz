package terribleappsdevs.com.newzz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.yugansh.tyagi.smileyrating.SmileyRatingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.model.Article;
import terribleappsdevs.com.newzz.model.Source;
import terribleappsdevs.com.newzz.model.Website;

/**
 * Created by yogeshtripathi on 3/2/18.
 */

public class Profile extends AppCompatActivity {
    @BindView(R.id.name)
    TextView nametv;
    @BindView(R.id.email)
    TextView emailtv;
    @BindView(R.id.numberofchannels)
    TextView numberofchannels;
    @BindView(R.id.offlinenumber)
    TextView offlinenumber;
    @BindView(R.id.pic)
    ImageView picimgview;
    ArrayList<String>likedchannel = new ArrayList<>();
    ArrayList<Article>articleArrayList = new ArrayList<>();
    @BindView(R.id.fav_ch_ids)
    LinearLayout fav_ch_ids;
    @BindView(R.id.offlinereading)
    LinearLayout offlinereading;
    private String cache;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        final SmileyRatingView smileyRatingView = findViewById(R.id.smiley_view);
      RatingBar  ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                smileyRatingView.setSmiley(rating);
            }
        });

        ButterKnife.bind(this);
        Paper.init(this);
        articleArrayList = Paper.book().read("urls");
        offlinenumber.setText(String.valueOf(articleArrayList.size()));

        offlinereading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //articleArrayList =  new Gson().fromJson(cache, ArrayList.class); //Convert cache from json to obj

               // articleArrayList.add(article);

                Intent intent = new Intent(Profile.this,OfflineReading.class);
                intent.putParcelableArrayListExtra("arraylist", articleArrayList);
                startActivity(intent);
                finish();



            }
        });
        getdatafrompref();
        getdatafromcache();

        fav_ch_ids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setLanguage(Locale.CHINA);
                Intent intent = new Intent(Profile.this,ChannelActivity.class);
                intent.putExtra("fromprofilefav","true");
                startActivity(intent);

            }
        });

    }

    private void getdatafromcache() {
       String cache = Paper.book().read("cache");
        if (cache != null && !cache.isEmpty()) {


            try {
              Website  website = new Gson().fromJson(cache, Website.class); //Convert cache from json to obj
                ArrayList<Source> sources = website.getSources();

                for (int i = 0; i<sources.size();i++)
                {
                    boolean liked =sources.get(i).getLiked();
                        if (liked==true)
                        {
                            likedchannel.add(sources.get(i).getName());
                        }

                }



            }catch (Exception e){
                e.printStackTrace();
            }
            if (likedchannel!=null)
                numberofchannels.setText(String.valueOf(likedchannel.size()));
    }}




    private void getdatafrompref() {
        SharedPreferences data = getSharedPreferences("logindata", MODE_PRIVATE);
        if (data != null) {
            String email, name, pic;
            email = data.getString("email", "x");
            name = data.getString("name", "x");
            pic = data.getString("pic", "x");

            nametv.setText(name);
            emailtv.setText(email);
            Glide.with(this).load(pic).apply(RequestOptions.circleCropTransform()).into(picimgview);


        }
    }
    }

