package terribleappsdevs.com.newzz.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
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
import terribleappsdevs.com.newzz.model.TouristSpot;

public class TouristSpotCardAdapter extends ArrayAdapter<Article> {
    List<Article> touristSpots;
    public TouristSpotCardAdapter(Context context) {
        super(context, 0);

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

        holder.name.setText(spot.getTitle());
        holder.city.setText(spot.getDescription());

       // holder.image.setImageDrawable(spot.url);
       Glide.with(getContext()).load(spot.getUrlToImage()).into(holder.image);

        return contentView;
    }




    private static class ViewHolder {
        public TextView name;
        public TextView city;
        public ImageView image;

        public ViewHolder(View view) {
            this.name = (TextView) view.findViewById(R.id.item_tourist_spot_card_name);
            this.city = (TextView) view.findViewById(R.id.item_tourist_spot_card_city);
            this.image = (ImageView) view.findViewById(R.id.item_tourist_spot_card_image);
        }
    }

}

