package terribleappsdevs.com.newzz.activity;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;
import terribleappsdevs.com.newzz.R;

public class DetailActivity extends AppCompatActivity {

    WebView webview;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        webview = (WebView) findViewById(R.id.webview);
        dialog = new SpotsDialog(this);
        dialog.show();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {

            //ctrl o
            @Override
            public void onPageFinished(WebView view, String url) {

                dialog.dismiss();



            }
        });


        if (getIntent() != null) {

            if (!getIntent().getStringExtra("webUrl").isEmpty())
                webview.loadUrl(getIntent().getStringExtra("webUrl"));

        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);//Menu Resource, Menu
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {






        switch (item.getItemId()) {

            case R.id.mShare:

              String url =  getIntent().getStringExtra("webUrl");

                Intent i = new Intent(

                        android.content.Intent.ACTION_SEND);

                i.setType("text/plain");

                i.putExtra(

                        android.content.Intent.EXTRA_TEXT, url+"\n"+"Get this app on google play store"+"\n "+"https://play.google.com/store/apps/details?id=com.terrible_app_developers.gagan.muze");

                startActivity(Intent.createChooser(

                        i,

                        "Title of your share dialog"));

                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }




}
