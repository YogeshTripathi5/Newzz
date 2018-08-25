package terribleappsdevs.com.newzz.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.yugansh.tyagi.smileyrating.SmileyRatingView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;
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
    ArrayList<String> likedchannel = new ArrayList<>();
    ArrayList<Article> articleArrayList = new ArrayList<>();
    @BindView(R.id.fav_ch_ids)
    LinearLayout fav_ch_ids;
    @BindView(R.id.rateusbutton)
    LinearLayout rateusbutton;
    @BindView(R.id.sharebutton)
    LinearLayout sharebutton;
    @BindView(R.id.offlinereading)
    LinearLayout offlinereading;
    private String cache;
    @BindView(R.id.review_box)
    EditText review_box;
    @BindView(R.id.submit)
    Button submit;
    FirebaseAuth firebaseAuth;
    String ratings = "0";
    CardView rl;
    private int clicked = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        ButterKnife.bind(this);
        Paper.init(this);

        final Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/newzz-83f86.appspot.com/o/ic_launcher_1.png?alt=media&token=2db4a628-0d2d-44f1-84a8-52b1b3106b90");
        firebaseAuth = FirebaseAuth.getInstance();
        final SmileyRatingView smileyRatingView = findViewById(R.id.smiley_view);
        RatingBar ratingBar = findViewById(R.id.rating_bar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                smileyRatingView.setSmiley(rating);

                ratings = String.valueOf(rating);

            }
        });


        rateusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
launchMarket();
            }
        });


        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BranchUniversalObject buo = new BranchUniversalObject()
                        .setCanonicalIdentifier("content/12345")
                        .setTitle("Newzz")
                        .setContentDescription("Newzz provides news from the leading news channels all around the world")
                        .setContentImageUrl(String.valueOf(uri))
                        .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                        .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                        .setContentMetadata(new ContentMetadata().addCustomMetadata("imageUrl", String.valueOf(uri)));



                LinkProperties lp = new LinkProperties()
                        .setChannel("facebook")
                        .setFeature("sharing")
                        .setCampaign("content 123 launch")
                        .setStage("new user")
                        .addControlParameter("$desktop_url", "http://phototakenwith.com/")
                        .addControlParameter("custom", "data")
                        .addControlParameter("custom_random", Long.toString(Calendar.getInstance().getTimeInMillis()));

                buo.generateShortUrl(Profile.this, lp, new Branch.BranchLinkCreateListener() {
                    @Override
                    public void onLinkCreate(String url, BranchError error) {
                        if (error == null) {
                            Log.i("BRANCH SDK", "got my Branch link to share: " + url);
                        }
                    }
                });


                ShareSheetStyle ss = new ShareSheetStyle(Profile.this, "Check this out!", "Stay Updated With Current Affairs... ")
                        .setCopyUrlStyle(ContextCompat.getDrawable(Profile.this, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                        .setMoreOptionStyle(ContextCompat.getDrawable(Profile.this, android.R.drawable.ic_menu_search), "Show more")
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
                        .setAsFullWidthStyle(true)
                        .setSharingTitle("Newzz");

                buo.showShareSheet(Profile.this, lp,  ss,  new Branch.BranchLinkShareListener() {
                    @Override
                    public void onShareLinkDialogLaunched() {
                    }
                    @Override
                    public void onShareLinkDialogDismissed() {
                    }
                    @Override
                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                    }
                    @Override
                    public void onChannelSelected(String channelName) {
                    }
                });

            }
        });


        rl = (CardView) findViewById(R.id.rl);
        String clc = Paper.book().read("click");
        if (clc != null)
            if (clc.equals("1")) {
                rl.setVisibility(View.GONE);
            }
        articleArrayList = Paper.book().read("urls");
        if (articleArrayList != null && articleArrayList.size() > 0)
            offlinenumber.setText(String.valueOf(articleArrayList.size()));

        offlinereading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //articleArrayList =  new Gson().fromJson(cache, ArrayList.class); //Convert cache from json to obj

                // articleArrayList.add(article);

                Intent intent = new Intent(Profile.this, OfflineReading.class);
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
                Intent intent = new Intent(Profile.this, ChannelActivity.class);
                intent.putExtra("fromprofilefav", "true");
                startActivity(intent);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = review_box.getText().toString();

                if (data != null && !data.isEmpty()) {
                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("user");
                    //myRef.setValue(firebaseAuth.getCurrentUser().getEmail());

                    DatabaseReference databaseReference = myRef.child("id");
                    databaseReference.setValue(firebaseAuth.getCurrentUser().getEmail());
                    DatabaseReference databaseReference2 = myRef.child("message");
                    databaseReference2.setValue(data);
                    DatabaseReference databaseReference3 = myRef.child("ratings");
                    databaseReference3.setValue(ratings);


                    Snackbar.make(v, "Thank You For Your Valuable Feedback", Snackbar.LENGTH_SHORT).show();
                    rl.animate().translationY(rl.getHeight())
                            .alpha(0.0f)
                            .setDuration(2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rl.setVisibility(View.GONE);
                        }
                    }, 3000);

                    clicked = 1;
                    Paper.book().write("click", String.valueOf(clicked));
                } else {
                    Snackbar.make(v, "Please provide your Feedback", Snackbar.LENGTH_SHORT).show();

                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchMarket();
                    }
                },3000);

            }
        });
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public  void openShareIntent(String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);


        Uri uri = Uri.parse(String.valueOf(R.mipmap.ic_launcher));
        //  InputStream stream = getContentResolver().openInputStream(uri);
        Uri imageUri = uri;
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(intent, "Share Via:"));
    }

    private void getdatafromcache() {
        String cache = Paper.book().read("cache");
        if (cache != null && !cache.isEmpty()) {


            try {
                Website website = new Gson().fromJson(cache, Website.class); //Convert cache from json to obj
                ArrayList<Source> sources = website.getSources();

                for (int i = 0; i < sources.size(); i++) {
                    boolean liked = sources.get(i).getLiked();
                    if (liked == true) {
                        likedchannel.add(sources.get(i).getName());
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            if (likedchannel != null)
                numberofchannels.setText(String.valueOf(likedchannel.size()));
        }
    }


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

