package terribleappsdevs.com.newzz.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import terribleappsdevs.com.newzz.Adapter.OfflineAdapter;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.model.Article;

public class OfflineReading extends AppCompatActivity{
@BindView(R.id.offlinerv)
    RecyclerView offlinerv;
    ArrayList<Article> articleArrayList = new ArrayList<>();
        Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.offline);
        ButterKnife.bind(this);

        articleArrayList=   getIntent().getParcelableArrayListExtra("arraylist");

        setLayout();
    }


    private void setLayout() {

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        offlinerv.setLayoutManager(layoutManager);
        OfflineAdapter offlineAdapter = new OfflineAdapter(articleArrayList,OfflineReading.this);
        offlinerv.setAdapter(offlineAdapter);


    }
}
