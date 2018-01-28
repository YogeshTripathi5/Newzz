package terribleappsdevs.com.newzz.Interface;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import terribleappsdevs.com.newzz.model.News;
import terribleappsdevs.com.newzz.model.Website;

/**
 * Created by admin1 on 7/10/17.
 */

public interface NewsService {
    @GET("v1/sources?language=en")
    Call<Website> getReources();


    @GET
    Call<News> getNewestArticles(@Url String url);
}
