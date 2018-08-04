package terribleappsdevs.com.newzz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.model.Article;

public class TouristSpotCardAdapter extends ArrayAdapter<Article> {
    List<Article> touristSpots;
    public OnitemClickListener onitemClickListener;
    public TouristSpotCardAdapter(Context context) {
        super(context, 0);

    }
    public void setOnitemClickListener(final OnitemClickListener onitemClickListener)
    {
        this.onitemClickListener = onitemClickListener;
    }
    public interface OnitemClickListener{
        void click(Article url, String fav);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.search_row, parent, false);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);

        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        Article spot = getItem(position);
        holder.fav.setTag(getItem(position));
        holder.share.setTag(getItem(position));
        holder.name.setText(spot.getTitle());
        holder.city.setText(spot.getDescription());
        holder.author.setText(spot.getAuthor());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Article spot= (Article) v.getTag();
               // String url = spot.getUrl();
                onitemClickListener.click(spot, "share");

            }
        });

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Article spot= (Article) v.getTag();
                // String url = spot.getUrl();
                onitemClickListener.click(spot,"fav");
            }
        });

       // holder.image.setImageDrawable(spot.url);
       Glide.with(getContext()).load(spot.getUrlToImage()).into(holder.image);

        return contentView;
    }




    private static class ViewHolder {
        public TextView name,author;
        public TextView city;
        public ImageView image,fav,share;

        public ViewHolder(View view) {
            this.name = (TextView) view.findViewById(R.id.item_tourist_spot_card_name);
            this.author = (TextView) view.findViewById(R.id.author);
            this.city = (TextView) view.findViewById(R.id.item_tourist_spot_card_city);
            this.image = (ImageView) view.findViewById(R.id.item_tourist_spot_card_image);
            this.fav = (ImageView) view.findViewById(R.id.fav);
            this.share = (ImageView) view.findViewById(R.id.share);
        }
    }

}

