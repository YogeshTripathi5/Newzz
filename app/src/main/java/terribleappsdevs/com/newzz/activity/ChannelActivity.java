package terribleappsdevs.com.newzz.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

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
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.Util;
import terribleappsdevs.com.newzz.model.Website;

public class ChannelActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService newsService;
    ListSourceAdapter listSourceAdapter;
    SpotsDialog alertDialog;
    //SwipeRefreshLayout swipe;
    Button signout;
    String cache="";
    private FirebaseAuth mAuth;
    public  GoogleApiClient mGoogleApiClient;
    private IconBetterIdeaService mservice;
    int i,j;
    boolean list = false;
    boolean grid = false;
   // AlertDialog dialog;
    boolean doubleBackToExitPressedOnce = false;
    Website website;
    Toolbar toolbar;
    MaterialSearchView materialSearchView;
    SharedPreferences.Editor editor;

    MenuItem listitem,griditem;
    public ChannelActivity()
    {
        mservice = Common.getIconService();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_main);

        //init cache
        Paper.init(this);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor((getResources().getColor(R.color.white)));
        materialSearchView = findViewById(R.id.search_view);


        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getApplicationContext(), "onQueryTextSubmit", Toast.LENGTH_SHORT).show();

                //Do some magic
                return true;
            }

            @Override
            public boolean onQueryTextChange(String source) {
                //Do some magic

                Toast.makeText(getApplicationContext(), "onQueryTextChange", Toast.LENGTH_SHORT).show();
                if (listSourceAdapter != null)
                    listSourceAdapter.setFilter(source);

                return true;


            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


        //init service
        newsService = Common.getNewsService();

        mAuth = FirebaseAuth.getInstance();


        //init view
        listWebsite = (RecyclerView) findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);
        alertDialog = new SpotsDialog(this);

        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        if (prefs != null) {
            boolean restoredText, restoredText2;


            restoredText = prefs.getBoolean("list", false);
            restoredText2 = prefs.getBoolean("grid", false);

            list = restoredText;
            grid = restoredText2;


        }
        if (list == true) {
            layoutManager = new LinearLayoutManager(this);
            listWebsite.setLayoutManager(layoutManager);


        } else if (grid == true)
        {

            layoutManager = new GridLayoutManager(this,2);
            listWebsite.setLayoutManager(layoutManager);

        }


        loadwebsitesources(list, grid);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);//Menu Resource, Menu


        MenuItem item = menu.findItem(R.id.action_search);
        listitem  = menu.findItem(R.id.List);
        // griditem = menu.findItem(R.id.Grid);

        materialSearchView.setMenuItem(item);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.List:


                if (listitem.getTitle().toString().equalsIgnoreCase("List"))


                {
                    Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                    layoutManager = new LinearLayoutManager(this);
                    listWebsite.setLayoutManager(layoutManager);
                    list = true;
                    grid = false;
                    loadwebsitesources(list, grid);
                    editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
                    editor.putBoolean("list", list);
                    editor.putBoolean("grid", grid);
                    editor.apply();


                }else if (listitem.getTitle().toString().equalsIgnoreCase("Grid"))
                {



                    list=false;
                    grid=true;
                    layoutManager = new GridLayoutManager(this,2);
                    listWebsite.setLayoutManager(layoutManager);
                    loadwebsitesources(list,grid);
                    editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
                    editor.putBoolean("grid",grid );
                    editor.putBoolean("list",list);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();

                }
                if (listitem.getTitle().toString().equalsIgnoreCase("list")) {
                    listitem.setTitle("Grid");
                }else if (listitem.getTitle().toString().equalsIgnoreCase("grid"))
                {
                    listitem.setTitle("list");

                }

                   return true;


                 case R.id.Logout:
                signOut();
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;

              default:
                return super.onOptionsItemSelected(item);


        }

    }






    private void loadwebsitesources(final boolean list, final boolean grid) {


    boolean check = Util.checkInternetConnection(getBaseContext());
    if (check) {

        cache = Paper.book().read("cache");
        if (cache != null && !cache.isEmpty()) {


            try {
                website = new Gson().fromJson(cache, Website.class); //Convert cache from json to obj


                listSourceAdapter = new ListSourceAdapter(getBaseContext(), website.getSources(), list, grid);
                listSourceAdapter.notifyDataSetChanged();
                listWebsite.setAdapter(listSourceAdapter);

                listSourceAdapter.setOnLikeItemClick(new ListSourceAdapter.OnLikeItemClick() {
                    @Override
                    public void click(int position) {

                        website.getSources().get(position).setLiked(!website.getSources().get(position).getLiked());
                        Paper.book().write("cache", new Gson().toJson(website));
                        listSourceAdapter.notifyDataSetChanged();

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {

            alertDialog.show();
            //fetch
            newsService.getReources().enqueue(new Callback<Website>() {
                @Override
                public void onResponse(Call<Website> call, Response<Website> response) {


                    website = response.body();

                    for (i = 0; i < website.getSources().size(); i++) {
                        String data = website.getSources().get(i).getUrl();


                        String host = "";
                        URL url = null;
                        try {
                            url = new URL(data);


                            host = url.getHost();

                            if (host.contains("http://"))
                                host = host.replace("http://", "");
                            else if (host.contains("www."))
                                host = host.replace("www.", "");

                            //   hitwebservice(url);


                            website.getSources().get(i).setUrl("https://autocomplete.clearbit.com/v1/companies/suggest?query=" + host);


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }


                    }
                    alertDialog.hide();
                    Paper.book().write("cache", new Gson().toJson(website));


                    for (j = 0; j < website.getSources().size(); j++) {


                        MyAsyncTask myAsyncTask = new MyAsyncTask(j);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            myAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, website.getSources().get(j).getUrl());
                        } else {
                            myAsyncTask.execute(website.getSources().get(j).getUrl());
                        }


                    }


                }

                @Override
                public void onFailure(Call<Website> call, Throwable throwable) {
                    throwable.printStackTrace();
                }


            });


        }

    }else
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.create();

        alertDialog.setView(R.layout.nointernet);

        alertDialog.show();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                loadwebsitesources(list,grid);
            }
        });

        Toast.makeText(getBaseContext(),"no net.png",Toast.LENGTH_SHORT).show();

    }
        }






    private void signOut() {

        if (mAuth!=null) {
            mAuth.signOut();
            //FirebaseAuth.getInstance().signOut();
            //LoginManager.getInstance().logOut();

            // Google sign out

            SharedPreferences.Editor editor = getSharedPreferences("logindata",MODE_PRIVATE).edit();
            if (editor!=null) {
                editor.clear();
                editor.apply();

            }
            finish();

            startActivity(new Intent(ChannelActivity.this,CoreLoginScreen.class));



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

    int size=0;
    public class MyAsyncTask extends android.os.AsyncTask<String,Void,String>
    {

        int posi;
        public MyAsyncTask(int posi) {
            this.posi=posi;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=strings[0];




            BufferedReader in = null;
            try{
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet request = new HttpGet();
                URI website = new URI(url);
                request.setURI(website);
                HttpResponse response = httpclient.execute(request);
                in = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));

                // NEW CODE
                String line = in.readLine();
                return line;
                // END OF NEW CODE

            }catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());
            }

        return null;



        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           /* Log.e("TAG",s.toString());
            if(size<=1)
            {

            }*/
            alertDialog.show();
            size++;

            if (s!=null) {
                Log.e("dda", s.toString());




                if (size >=website.getSources().size()) {
                    Paper.book().write("cache",new Gson().toJson(website));
                    listSourceAdapter = new ListSourceAdapter(getBaseContext(), website.getSources(), list, grid);
                    listSourceAdapter.notifyDataSetChanged();
                    listWebsite.setAdapter(listSourceAdapter);
                    alertDialog.dismiss();


                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(s);

                        if(jsonArray.length()>0)
                        {
                            JSONObject jsonObject =jsonArray.getJSONObject(0);

                            String s1 = (String) jsonObject.getString("logo");

                            website.getSources().get(posi).setLogoUrl(s1);
                        }

                        else {
                            website.getSources().get(posi).setLogoUrl("http://avveniretechnologies.com/wp-content/uploads/2016/05/news.png");
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }


}
