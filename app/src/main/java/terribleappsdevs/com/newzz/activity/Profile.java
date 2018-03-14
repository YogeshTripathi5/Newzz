package terribleappsdevs.com.newzz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.CircularPropagation;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.material.MainActivity;

/**
 * Created by yogeshtripathi on 3/2/18.
 */

public class Profile extends LocalizationActivity {
    @BindView(R.id.name)
    TextView nametv;
    @BindView(R.id.email)
    TextView emailtv;
    @BindView(R.id.pic)
    ImageView picimgview;
    @BindView(R.id.btn_switch)
    Switch btn_switch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        ButterKnife.bind(this);

        getdatafrompref();

        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage(Locale.CHINA);


            }
        });

    }

    private void getdatafrompref() {
        SharedPreferences data= getSharedPreferences("logindata",MODE_PRIVATE);
        if (data!=null) {
         String email,name,pic;
            email = data.getString("email", "x");
            name = data.getString("name", "x");
            pic = data.getString("pic", "x");

            nametv.setText(name);
            emailtv.setText(email);
            Glide.with(this).load(pic).apply(RequestOptions.circleCropTransform()).into(picimgview);


        }
    }
}
