package terribleappsdevs.com.newzz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import terribleappsdevs.com.newzz.Adapter.ListNewsAdapter;
import terribleappsdevs.com.newzz.Common.Common;
import terribleappsdevs.com.newzz.Interface.NewsService;
import terribleappsdevs.com.newzz.model.Article;
import terribleappsdevs.com.newzz.model.News;

public class ListNews extends AppCompatActivity {
        KenBurnsView kenBurnsView;
    AlertDialog dialog;
    NewsService mNewsService;
    TextView top_author,top_title;
    SwipeRefreshLayout swipeRefreshLayout;
    String source = "",sortBy = "",webHotUrl="";
    ListNewsAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DiagonalLayout diagonalLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //service
        mNewsService = Common.getNewsService();
        dialog = new SpotsDialog(this);

        //view
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadNews(source,true);
            }
        });


        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonalLayout);
        kenBurnsView  = (KenBurnsView) findViewById(R.id.top_image);
        top_author = (TextView) findViewById(R.id.topAuthor);
        top_title = (TextView) findViewById(R.id.toptitle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //click to read latest news

                Intent intent = new Intent(getBaseContext(),DetailActivity.class);
                intent.putExtra("webUrl",webHotUrl);
                startActivity(intent);

            }
        });

        //intent
        if(getIntent()!=null)

        {

            source = getIntent().getStringExtra("source");
            sortBy = getIntent().getStringExtra("sortBy");
            if (!source.isEmpty() && !sortBy.isEmpty())
            {

                loadNews(source,false);
            }
        }
    }

    private void loadNews(String source, boolean isRefreshed) {

        if (!isRefreshed)
        {

            dialog.show();
            mNewsService.getNewestArticles(Common.getApiUrl(source,sortBy,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {

                            dialog.dismiss();
                                //first ar
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kenBurnsView);

                                    top_author.setText(response.body().getArticles().get(0).getAuthor());
                                    top_title.setText(response.body().getArticles().get(0).getTitle());


                            webHotUrl = response.body().getArticles().get(0).getUrl();


                            //load remaining articles
                            List<Article> articles = response.body().getArticles();
                            //since we already loaded d 1st article so we need to remove from rclcrview list
                            articles.remove(0);
                            adapter = new ListNewsAdapter(articles,getBaseContext());
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);



                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
        }
        else {
            dialog.show();
            mNewsService.getNewestArticles(Common.getApiUrl(source,sortBy,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {

                            dialog.dismiss();
                            //first ar
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kenBurnsView);

                            top_author.setText(response.body().getArticles().get(0).getAuthor());
                            top_title.setText(response.body().getArticles().get(0).getTitle());


                            webHotUrl = response.body().getArticles().get(0).getUrl();


                            //load remaining articles
                            List<Article> articles = response.body().getArticles();
                            //since we already loaded d 1st article so we need to remove from rclcrview list
                            articles.remove(0);
                            adapter = new ListNewsAdapter(articles,getBaseContext());
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);



                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });

            swipeRefreshLayout.setRefreshing(false);

        }



    }

}
