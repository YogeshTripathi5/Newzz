package terribleappsdevs.com.newzz.Common;

import terribleappsdevs.com.newzz.Interface.IconBetterIdeaService;
import terribleappsdevs.com.newzz.Interface.NewsService;
import terribleappsdevs.com.newzz.Remote.IconBetterIdeaClient;
import terribleappsdevs.com.newzz.Remote.RetrofitClient;

/**
 * Created by admin1 on 7/10/17.
 */

public class Common {
    public static final String BASE_URL = "https://newsapi.org/";
   // public static final String NEWS_URL="https://autocomplete.clearbit.com/v1/companies/suggest";
    public static final String API_KEY = "8bc18e4d02b6428683117e94543cd467";

    public static NewsService getNewsService()
    {

        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }
    public static IconBetterIdeaService getIconService()
    {

        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

//https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=8bc18e4d02b6428683117e94543cd467

    public static  String getApiUrl(String source, String sortBy, int page, String apikey){

        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&page=")
                .append(page)
                .append("&apiKey=")
                .append(apikey)
                .toString();
    }

    //https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=8bc18e4d02b6428683117e94543cd467
    public static  String getNewsApiUrl(String country,String cat,String apikey){

        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?country=");
        return apiUrl.append(country)
                .append("&category=")
                .append(cat)
                .append("&apiKey=")
                .append(apikey)
                .toString();
    }
    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=API_KEY
    public static  String getEverythingApiUrl(String querry,String apikey){

        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/everything?q=");
        return apiUrl.append(querry)
                .append("&language=en")
                .append("&apiKey=")
                .append(apikey)
                .toString();
    }
}
