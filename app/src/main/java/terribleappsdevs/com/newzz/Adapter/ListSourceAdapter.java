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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import terribleappsdevs.com.newzz.Common.Common;
import terribleappsdevs.com.newzz.Interface.IconBetterIdeaService;
import terribleappsdevs.com.newzz.Interface.ItemClickListener;
import terribleappsdevs.com.newzz.ListNews;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.model.IconBetterIdea;
import terribleappsdevs.com.newzz.model.Website;

/**
 * Created by admin1 on 7/10/17.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;
    TextView source_name;
    ImageView source_image;
    CardView card,grid_card;

    public ListSourceViewHolder(View itemView) {
        super(itemView);


        source_image = itemView.findViewById(R.id.source_image);
        source_name = itemView.findViewById(R.id.source_name);
        card = itemView.findViewById(R.id.card);
        grid_card = itemView.findViewById(R.id.grid_card);

        itemView.setOnClickListener(this);

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

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

    private Context context;
    private Website website;
    private IconBetterIdeaService mservice;
    boolean list;
    ArrayList<String> list1;
    private int lastPosition=-1;

    public ListSourceAdapter(Context context, Website website, boolean list, ArrayList<String> list1) {
        this.context = context;
        this.website = website;
        mservice = Common.getIconService();
        this.list = list;
        this.list1 = list1;


    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (list == false) {
            itemView = layoutInflater.inflate(R.layout.source_layout_grid, parent, false);


        } else {

            itemView = layoutInflater.inflate(R.layout.source_layout, parent, false);
        }
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, final int position) {





        holder.source_name.setText(website.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                Intent intent = new Intent(context,ListNews.class);
                intent.putExtra("source",website.getSources().get(position).getId());
                intent.putExtra("sortBy",website.getSources().get(position).getSortBysAvailable().get(0)); //get default sortby method
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
        if (holder.card!=null)
            setAnimation(holder.card, position);
        if (holder.grid_card!=null)
            setAnimation(holder.grid_card, position);


/*
        StringBuilder iconBetterAPI = new StringBuilder("https://autocomplete.clearbit.com/v1/companies/suggest");
        iconBetterAPI.append(list1.get(position));

        String data = iconBetterAPI.toString();*/




     /*   mservice.getIconUrl(list1.get(position)).enqueue(new Callback<ArrayList<IconBetterIdea>>() {
            @Override
            public void onResponse(Call<ArrayList<IconBetterIdea>> call, Response<ArrayList<IconBetterIdea>> response) {

                try {
                    if (response.body().get(position).getLogo() != null) {

                            Picasso.with(context).load(response.body().get(position).getLogo())
                                    .into(holder.source_image);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<IconBetterIdea>> call, Throwable t) {



            }
        });*/

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
        return website.getSources().size();
    }
}