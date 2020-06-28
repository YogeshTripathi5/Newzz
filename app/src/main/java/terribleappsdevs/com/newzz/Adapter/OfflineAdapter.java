package terribleappsdevs.com.newzz.Adapter;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import terribleappsdevs.com.newzz.Interface.ItemClickListener;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.activity.OfflineReading;
import terribleappsdevs.com.newzz.model.Article;

/**
 * Created by admin1 on 7/10/17.
 */

class OfflineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;
    ImageView source_image;
    TextView artical_title,artical_time;

    public OfflineViewHolder(View itemView) {
        super(itemView);
        source_image = itemView.findViewById(R.id.source_image);
        artical_title = itemView.findViewById(R.id.artical_title);
        artical_time = itemView.findViewById(R.id.artical_time);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {


        try {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }catch (Exception e)
        {
            e.printStackTrace();

        }


    }
}

public class OfflineAdapter extends RecyclerView.Adapter<OfflineViewHolder> {

    OnLikeItemClick onLikeItemClick;
    ArrayList< Article> articleArrayList;

    Context context;

    public OfflineAdapter(ArrayList< Article> articleArrayList, OfflineReading offlineReading) {
        this.articleArrayList = articleArrayList;
        context = offlineReading;
    }


    public interface OnLikeItemClick
    {

        void click(int position, String id);
    }

    public void setOnLikeItemClick(final OnLikeItemClick onLikeItemClick ){


        this.onLikeItemClick = onLikeItemClick;
    }

    @Override
    public OfflineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
            itemView = layoutInflater.inflate(R.layout.offline_row, parent, false);



        return new OfflineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OfflineViewHolder holder, final int position) {
        if (articleArrayList!=null)
          {
            try {
                Article article =  articleArrayList.get(position);
                holder.artical_title.setText(article.getDescription());
                Glide.with(context).load(  article.getUrlToImage()).into(holder.source_image);
                if (article.getAuthor()!=null)
                holder.artical_time.setText(article.getAuthor());

            }catch (Exception e){

                e.printStackTrace();
            }

        }




    }




    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }
}