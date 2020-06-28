package terribleappsdevs.com.newzz.material.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import terribleappsdevs.com.newzz.Common.Common;
import terribleappsdevs.com.newzz.Interface.NewsService;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.activity.Category;
import terribleappsdevs.com.newzz.material.TestRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import terribleappsdevs.com.newzz.model.Article;
import terribleappsdevs.com.newzz.model.News;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    private NewsService mNewsService;


    RecyclerView mRecyclerView;
    List<Article> articles = new ArrayList<>();


    public static RecyclerViewFragment newInstance(String s) {

        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cId", s);
        recyclerViewFragment.setArguments(bundle);

        return recyclerViewFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);


        LinearLayoutManager datervlLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(datervlLayoutManager);



        mRecyclerView.setHasFixedSize(true);


        mNewsService = Common.getNewsService();

        String cid = getArguments().getString("cId");

        hitwebservice(cid);

        return view;



    }

    private void hitwebservice(String s) {


            mNewsService.getNewsOnBasisOfCategory(Common.getNewsApiUrl(Category.countryCode, s, Common.API_KEY)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {


                    articles  = response.body().getArticles();


                    //Use this now
                    mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                    mRecyclerView.setAdapter(new TestRecyclerViewAdapter(articles,RecyclerViewFragment.this));

                }

                @Override
                public void onFailure(Call<News> call, Throwable throwable) {

                }
            });

        }




}
