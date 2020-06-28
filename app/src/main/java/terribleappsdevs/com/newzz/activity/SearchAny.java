package terribleappsdevs.com.newzz.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import terribleappsdevs.com.newzz.Adapter.TouristSpotCardAdapter;
import terribleappsdevs.com.newzz.Common.Common;
import terribleappsdevs.com.newzz.Interface.NewsService;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.model.Article;
import terribleappsdevs.com.newzz.model.News;

/**
 * Created by yogeshtripathi on 4/3/18.
 */

public class SearchAny extends Activity implements TextWatcher {
    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;
    private EditText search_view;
    private NewsService mNewsService;
    List<Article> articles;
    List<Article> offlinearticles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchanything);
        search_view = findViewById(R.id.search_view);
        search_view.addTextChangedListener(this);
        mNewsService = Common.getNewsService();
        offlinearticles = new ArrayList<>();
        Paper.init(this);
        setup();
       // setup();
       // reload();


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    private void setup() {
        progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        cardStackView = (CardStackView) findViewById(R.id.activity_main_card_stack_view);
        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                Log.d("CardStackView", "onCardReversed");
            }

            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
            }
        });
    }private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createTouristSpotCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);



                adapter.setOnitemClickListener(new TouristSpotCardAdapter.OnitemClickListener() {
                    @Override
                    public void click(Article url, String fav) {

                        if (fav.equals("fav")){
                            offlinearticles.add(url);
                            Snackbar.make(cardStackView,"Saved to Offline Reading...",Snackbar.LENGTH_SHORT).show();
                            if (Paper.book().read("urls")!=null)
                                offlinearticles = Paper.book().read("urls");
                            if (offlinearticles!=null)
                                offlinearticles.add(url);

                            Paper.book().write("urls",offlinearticles);


                        }else {
                            BranchUniversalObject buo = new BranchUniversalObject()
                                    .setCanonicalIdentifier("content/12345")
                                    .setTitle("Newzz")
                                    .setContentDescription("Newzz provides news from the leading news channels all around the world")
                                    .setContentImageUrl("https://firebasestorage.googleapis.com/v0/b/newzz-83f86.appspot.com/o/ic_launcher_1.png?alt=media&token=2db4a628-0d2d-44f1-84a8-52b1b3106b90")
                                    //.setContentImageUrl(url.getUrl())
                                    .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                                    .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                                    .setContentMetadata(new ContentMetadata().addCustomMetadata("imageUrl",url.getUrl() ));



                            LinkProperties lp = new LinkProperties()
                                    .setChannel("facebook")
                                    .setFeature("sharing")
                                    .setCampaign("content 123 launch")
                                    .setStage("new user")
                                    .addControlParameter("$desktop_url", "http://phototakenwith.com/")
                                    .addControlParameter("custom", "data")
                                    .addControlParameter("custom_random", Long.toString(Calendar.getInstance().getTimeInMillis()));

                            buo.generateShortUrl(SearchAny.this, lp, new Branch.BranchLinkCreateListener() {
                                @Override
                                public void onLinkCreate(String url, BranchError error) {
                                    if (error == null) {
                                        Log.i("BRANCH SDK", "got my Branch link to share: " + url);
                                    }
                                }
                            });


                            ShareSheetStyle ss = new ShareSheetStyle(SearchAny.this, "Check this out!", "This stuff is awesome: ")
                                    .setCopyUrlStyle(ContextCompat.getDrawable(SearchAny.this, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                                    .setMoreOptionStyle(ContextCompat.getDrawable(SearchAny.this, android.R.drawable.ic_menu_search), "Show more")
                                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                                    .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
                                    .setAsFullWidthStyle(true)
                                    .setSharingTitle("Share With");

                            buo.showShareSheet(SearchAny.this, lp,  ss,  new Branch.BranchLinkShareListener() {
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


                    }
                });
            }
        }, 1000);
    }

    private TouristSpotCardAdapter createTouristSpotCardAdapter() {
        final TouristSpotCardAdapter adapter = new TouristSpotCardAdapter(getApplicationContext());
        adapter.addAll(createTouristSpots());
        return adapter;
    }
    private List<Article> createTouristSpots() {

        return articles;
    }


    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.addAll(createTouristSpots());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, int start, int before, int count) {

        if (s.toString().getBytes()!=null&&s.length()>3);
        {
            new  Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    mNewsService.getEveryThing(Common.getEverythingApiUrl(s.toString(),Common.API_KEY)).enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            articles    = (List<Article>) response.body().getArticles();
                       if (articles!=null)

                       {

                           reload();

                       }}

                        @Override
                        public void onFailure(Call<News> call, Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
                }
            },3000);
        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
