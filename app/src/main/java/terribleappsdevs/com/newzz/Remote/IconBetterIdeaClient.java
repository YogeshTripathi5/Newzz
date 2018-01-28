package terribleappsdevs.com.newzz.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin1 on 7/10/17.
 */

public class IconBetterIdeaClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient()
    {

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://autocomplete.clearbit.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
