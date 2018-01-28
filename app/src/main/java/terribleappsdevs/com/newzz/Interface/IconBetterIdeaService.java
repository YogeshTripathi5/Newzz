package terribleappsdevs.com.newzz.Interface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;
import terribleappsdevs.com.newzz.model.IconBetterIdea;

/**
 * Created by admin1 on 7/10/17.
 */

public interface IconBetterIdeaService {

    @GET("v1/companies/suggest")
    Call<ArrayList<IconBetterIdea>> getIconUrl(@Query("query") String query);

}
