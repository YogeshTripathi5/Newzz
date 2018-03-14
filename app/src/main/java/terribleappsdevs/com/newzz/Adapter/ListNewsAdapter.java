package terribleappsdevs.com.newzz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import terribleappsdevs.com.newzz.Common.ISO8601DateParser;
import terribleappsdevs.com.newzz.activity.DetailActivity;
import terribleappsdevs.com.newzz.Interface.ItemClickListener;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.model.Article;

/**
 * Created by admin1 on 8/10/17.
 */


class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
        ItemClickListener itemClickListener;
    TextView artical_title;
    RelativeTimeTextView artical_time;
    ImageView source_image;
    CardView card;
    public ListNewsViewHolder(View itemView) {
        super(itemView);

        source_image = itemView.findViewById(R.id.source_image);
        artical_time = itemView.findViewById(R.id.artical_time);
        artical_title = itemView.findViewById(R.id.artical_title);
        card = (CardView) itemView.findViewById(R.id.card);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder>{
    private List<Article> articleList;
    private Context context;
    private int lastPosition=-1;

    public ListNewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View ItemView = layoutInflater.inflate(R.layout.news_layout,parent,false);

        return new ListNewsViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(ListNewsViewHolder holder, final int position) {


        Picasso.with(context)
                .load(articleList.get(position).getUrlToImage())
                .into(holder.source_image);

        if (articleList.get(position).getTitle().length()>65)
        {
            holder.artical_title.setText(articleList.get(position).getTitle().substring(0,65)+"...");
        }else {

            holder.artical_title.setText(articleList.get(position).getTitle());
        }
            Date date = null;

            try {

                date = ISO8601DateParser.parse(articleList.get(position).getPublishedAt());
                holder.artical_time.setReferenceTime(date.getTime());

            }catch (Exception e)
            {
                e.printStackTrace();
            }


        setAnimation(holder.card, position);


   holder.setItemClickListener(new ItemClickListener() {
       @Override
       public void onClick(View view, int pos, boolean isLongClick) {

           Intent intent = new Intent(context,DetailActivity.class);
           intent.putExtra("webUrl",articleList.get(pos).getUrl());
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);

       }
   });


    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
