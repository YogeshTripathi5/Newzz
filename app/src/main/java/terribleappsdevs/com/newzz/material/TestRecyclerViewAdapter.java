package terribleappsdevs.com.newzz.material;

import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.util.Date;
import java.util.List;

import terribleappsdevs.com.newzz.Common.ISO8601DateParser;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.material.fragment.RecyclerViewFragment;
import terribleappsdevs.com.newzz.model.Article;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.Myviewholder> {

    List<Article> articles;
    RecyclerViewFragment context;

      public TestRecyclerViewAdapter(List<Article> articles, RecyclerViewFragment context) {
        this.articles= articles;
        this.context = context;
    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_big,parent,false);

        return new TestRecyclerViewAdapter.Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, final int position) {

          if (articles!=null)
          { try {

              Article article = articles.get(position);
              holder.artical_title.setText(article.getTitle());
              Glide.with(context).load(article.getUrlToImage()).into(holder.source_image);
              holder.artical_time.setText(article.getPublishedAt());
              Date date = null;

              date = ISO8601DateParser.parse(article.getPublishedAt());
                  holder.artical_time.setReferenceTime(date.getTime());

              }catch (Exception e)
              {
                  e.printStackTrace();
              }

              holder.card.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {



                      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                      CustomTabsIntent customTabsIntent = builder.build();
                      customTabsIntent.launchUrl(context.getContext(), Uri.parse(articles.get(position).getUrl()));
                  }
              });

          }

    }



    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView artical_title;
        RelativeTimeTextView artical_time;
        ImageView source_image;
        CardView card;

        public Myviewholder(View itemView) {
            super(itemView);
            artical_title = itemView.findViewById(R.id.artical_title);
            source_image = itemView.findViewById(R.id.source_image);
            artical_time = itemView.findViewById(R.id.artical_time);
            card = itemView.findViewById(R.id.card);

        }
    }
}