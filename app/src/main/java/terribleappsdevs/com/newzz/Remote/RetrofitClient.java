package terribleappsdevs.com.newzz.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin1 on 7/10/17.
 */

public class RetrofitClient  {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String url)
    {

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
