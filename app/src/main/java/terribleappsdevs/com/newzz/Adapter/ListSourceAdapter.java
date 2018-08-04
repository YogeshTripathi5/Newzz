package terribleappsdevs.com.newzz.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import terribleappsdevs.com.newzz.Common.Common;
import terribleappsdevs.com.newzz.Interface.IconBetterIdeaService;
import terribleappsdevs.com.newzz.Interface.ItemClickListener;
import terribleappsdevs.com.newzz.activity.ListNews;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.model.Source;

/**
 * Created by admin1 on 7/10/17.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;
    TextView source_name;
    CircleImageView source_image;
    CardView card,grid_card;
    Dialog dialog;
    LikeButton likebutton;

    public ListSourceViewHolder(View itemView) {
        super(itemView);


        source_image = itemView.findViewById(R.id.source_image);
        source_name = itemView.findViewById(R.id.source_name);
        card = itemView.findViewById(R.id.card);
        grid_card = itemView.findViewById(R.id.grid_card);
        likebutton = itemView.findViewById(R.id.likebutton);
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
//    public Realm realm = Realm.getDefaultInstance();
    private Context context;
    //private Website website;
    ArrayList<Source> sources;
    ArrayList<Source> favsources;
    ArrayList<Source> tempSources;
    private IconBetterIdeaService mservice;
    boolean list,grid;
    Source source = new Source();
    JSONObject jsonObject = new JSONObject();
    OnLikeItemClick onLikeItemClick;
    private int lastPosition=-1;
//Website websitelist ;
    public ListSourceAdapter(Context context, ArrayList<Source> website, boolean list, boolean grid) {
        this.context = context;
        this.sources = website;
        mservice = Common.getIconService();
        this.list = list;
        this.grid = grid;
        tempSources = new ArrayList<>(website);

        favsources = new ArrayList<>();


    }

    public interface OnLikeItemClick
    {

        void click(int position, String id);
    }

    public void setOnLikeItemClick(final OnLikeItemClick onLikeItemClick ){


        this.onLikeItemClick = onLikeItemClick;
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (list == false && grid==true) {
            itemView = layoutInflater.inflate(R.layout.source_layout_grid, parent, false);


        }  else if (grid==false && list ==true){

            itemView = layoutInflater.inflate(R.layout.source_layout, parent, false);
        }
        else
        {
            itemView = layoutInflater.inflate(R.layout.source_layout,parent,false);

        }
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, final int position) {


            /*if (holder.grid_card!=null) {
                holder.likebutton.setTag(sources.get(position));
            }*/
            if (holder.card!=null) {
                holder.likebutton.setTag(sources.get(position));
            }
        if(sources.get(position).getLiked())
            holder.likebutton.setLiked(true);
        else
            holder.likebutton.setLiked(false);
        holder.source_name.setText(sources.get(position).getName());

        Picasso.with(context).load(sources.get(position).getLogoUrl())
                .into(holder.source_image);


      /*  String like;
        SharedPreferences preferences = context.getSharedPreferences("fav",Context.MODE_PRIVATE);
         like=  preferences.getString("fav","x");

        if (like.equals("true"))
       holder.likebutton.setLiked(true);
*/
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                Intent intent = new Intent(context,ListNews.class);
                intent.putExtra("source",sources.get(position).getId());
             //   intent.putExtra("sortBy",sources.get(position).getSortBysAvailable().get(0)); //get default sortby method
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });


            if (holder.card != null)
                setAnimation(holder.card, position);
            if (holder.grid_card != null)
                setAnimation(holder.grid_card, position);


            holder.likebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    source = (Source) v.getTag();
                    if (source.getId()!=null) {
                        String id = source.getId();

                        onLikeItemClick.click(position, id);
                    }



                   /* source.setLiked(true);
                    try {
                        jsonObject.put(source.getId(),true);

                        SharedPreferences.Editor editor = context.getSharedPreferences("fav",Context.MODE_PRIVATE).edit();
                        editor.putString("fav",jsonObject.toString());
                        editor.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/

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


    public void setFilter(String str)
    {


        if(!str.trim().isEmpty()) {
            sources = new ArrayList<>();
            for (Source obj:tempSources) {
                if(obj.getName().toLowerCase().contains(str)){
                    sources.add(obj);
                }
            }
            notifyDataSetChanged();
        }else{
            if(sources.size()!=tempSources.size()) {
                sources.addAll(tempSources);
                notifyDataSetChanged();
            }
        }

    }

    @Override
    public int getItemCount() {
        return sources.size();
    }
}