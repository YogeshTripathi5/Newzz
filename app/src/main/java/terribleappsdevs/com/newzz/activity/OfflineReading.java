package terribleappsdevs.com.newzz.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;

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
        @BindView(R.id.frame)
    FrameLayout frame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.offline);
        ButterKnife.bind(this);

        articleArrayList=   getIntent().getParcelableArrayListExtra("arraylist");

        setLayout();
    }


    private void setLayout() {
            if (articleArrayList!=null) {
                frame.setVisibility(View.GONE);
                RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                offlinerv.setLayoutManager(layoutManager);
                OfflineAdapter offlineAdapter = new OfflineAdapter(articleArrayList, OfflineReading.this);
                offlinerv.setAdapter(offlineAdapter);
            }else {

                Snackbar.make(offlinerv,"No offline contents stored yet...",Snackbar.LENGTH_SHORT).show();
                //startActivity(new Intent(this,Profile.class));
            }

    }
}
