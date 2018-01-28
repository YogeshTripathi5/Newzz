package terribleappsdevs.com.newzz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import terribleappsdevs.com.newzz.Adapter.ListSourceAdapter;
import terribleappsdevs.com.newzz.Common.Common;
import terribleappsdevs.com.newzz.Interface.IconBetterIdeaService;
import terribleappsdevs.com.newzz.Interface.NewsService;
import terribleappsdevs.com.newzz.Login.CoreLoginScreen;
import terribleappsdevs.com.newzz.model.IconBetterIdea;
import terribleappsdevs.com.newzz.model.Website;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService newsService;
    ListSourceAdapter listSourceAdapter;
    SpotsDialog alertDialog;
    SwipeRefreshLayout swipe;
    Button signout;
    String cache="";
    private FirebaseAuth mAuth;
    public  GoogleApiClient mGoogleApiClient;
    private IconBetterIdeaService mservice;
    int i,j;
    final ArrayList<String> list1 = new ArrayList<>();
    boolean list = false;

    boolean doubleBackToExitPressedOnce = false;
    public  MainActivity()
    {
        mservice = Common.getIconService();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init cache
        Paper.init(this);

        //init service
        newsService = Common.getNewsService();

        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //init view
        listWebsite = (RecyclerView) findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);
        alertDialog = new SpotsDialog(this);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                loadwebsitesources(true);
            }
        });
        loadwebsitesources(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);//Menu Resource, Menu
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Website website;
        switch (item.getItemId()) {

            case R.id.List:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();

                layoutManager = new LinearLayoutManager(this);
                listWebsite.setLayoutManager(layoutManager);
                list = true;
                 website = new Gson().fromJson(cache,Website.class); //Convert cache from json to obj
                listSourceAdapter = new ListSourceAdapter(getBaseContext(),website,list, list1);
                listSourceAdapter.notifyDataSetChanged();
                listWebsite.setAdapter(listSourceAdapter);

                return true;
            case R.id.Grid:

                list=false;
                layoutManager = new GridLayoutManager(this,2);
                listWebsite.setLayoutManager(layoutManager);

                 website = new Gson().fromJson(cache,Website.class); //Convert cache from json to obj
                listSourceAdapter = new ListSourceAdapter(getBaseContext(),website, list, list1);
                listSourceAdapter.notifyDataSetChanged();
                listWebsite.setAdapter(listSourceAdapter);

                Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.About:
                startActivity(new Intent(MainActivity.this,About.class));
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.Logout:
                signOut();
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }






    private void loadwebsitesources(boolean isRefreshed) {

        if (!isRefreshed)
        {

            cache = Paper.book().read("cache");
            if (cache!=null && !cache.isEmpty())
            {

                ArrayList<String> urllist = new ArrayList<>();
                Website website = new Gson().fromJson(cache,Website.class); //Convert cache from json to obj






                for ( i=0;i<website.getSources().size();i++)
                {
                    String[] data2;
                    String newdata;
                    StringBuilder iconBetterAPI = new StringBuilder("https://logo.clearbit.com/");
                   // iconBetterAPI.append(website.getSources().get(i).getUrl());
                    String data = website.getSources().get(i).getUrl();


                    String host="";
                    URL url = null;
                    try {
                        url = new URL(data);
                        host = url.getHost();



                        hitwebservice(url);

                        urllist.add(host);




                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }



                }
/*

            for (j=0;j<urllist.size();j++)
            {




                mservice.getIconUrl(urllist.get(j)).enqueue(new Callback<ArrayList<IconBetterIdea>>() {
                    @Override
                    public void onResponse(Call<ArrayList<IconBetterIdea>> call, Response<ArrayList<IconBetterIdea>> response) {

                        try {
                            if (response.body().get(j).getLogo() != null) {

                           */
/* Picasso.with(context).load(response.body().get(position).getLogo())
                                    .into(holder.source_image);
*//*

                                list1.add(response.body().get(j).getLogo());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<IconBetterIdea>> call, Throwable t) {
                        t.printStackTrace();


                    }
                });

            }
*/





                        listSourceAdapter = new ListSourceAdapter(getBaseContext(), website, list, list1);
                        listSourceAdapter.notifyDataSetChanged();
                        listWebsite.setAdapter(listSourceAdapter);




            }
            else {

                alertDialog.show();
                //fetch
                newsService.getReources().enqueue(new Callback<Website>() {
                    @Override
                    public void onResponse(Call<Website> call, Response<Website> response) {







                        listSourceAdapter = new ListSourceAdapter(getBaseContext(),response.body(), list, list1);
                        listSourceAdapter.notifyDataSetChanged();
                        listWebsite.setAdapter(listSourceAdapter);

                        //save to cache

                        Paper.book().write("cache",new Gson().toJson(response.body()));

                        //dismiss refresh
                        swipe.setRefreshing(false);
                        alertDialog.hide();

                    }

                    @Override
                    public void onFailure(Call<Website> call, Throwable t) {

                    }
                });
            }
        }
        else //if from swipe to refresh
        {

            alertDialog.show();
            //fetch
            newsService.getReources().enqueue(new Callback<Website>() {
                @Override
                public void onResponse(Call<Website> call, Response<Website> response) {


                    listSourceAdapter = new ListSourceAdapter(getBaseContext(),response.body(), list, list1);
                    listSourceAdapter.notifyDataSetChanged();
                    listWebsite.setAdapter(listSourceAdapter);

                    //save to cache

                    Paper.book().write("cache",new Gson().toJson(response.body()));

                    //dismiss refresh
                    swipe.setRefreshing(false);
                    alertDialog.hide();
                }

                @Override
                public void onFailure(Call<Website> call, Throwable t) {

                }
            });

        }


    }

    private void hitwebservice(URL url) {





    }


    private void signOut() {

        if (mAuth!=null) {
            mAuth.signOut();
            //FirebaseAuth.getInstance().signOut();
            //LoginManager.getInstance().logOut();

            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            //do what you want


                            SharedPreferences.Editor editor = getSharedPreferences("logindata",MODE_PRIVATE).edit();
                            if (editor!=null) {
                                editor.clear();
                                editor.apply();

                            }
                            finish();

                            startActivity(new Intent(MainActivity.this,CoreLoginScreen.class));




                        }
                    });


        }
        }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
