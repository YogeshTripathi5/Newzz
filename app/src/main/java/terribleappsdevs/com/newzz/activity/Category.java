package terribleappsdevs.com.newzz.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.gson.Gson;
import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.material.MainActivity;
import terribleappsdevs.com.newzz.model.SelectedCategory;

/**
 * Created by yogeshtripathi on 7/2/18.
 */

public class Category extends AppCompatActivity implements View.OnClickListener {
      @BindView(R.id.continuebtn)
    Button continuebtn;
    Toolbar toolbar;
    public  static ArrayList<String> selected = new ArrayList<>();
    public static String countryCode="in";
    ArrayList<SelectedCategory> selectedCategories  = new ArrayList<>();
    BubblePicker picker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble);
        ButterKnife.bind(this);

        picker = findViewById(R.id.picker);
        final String[] titles = getResources().getStringArray(R.array.category);
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getResources().obtainTypedArray(R.array.images);
        picker.setBubbleSize(10);

        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setTextSize(25);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                //  item.setTypeface(mediumTypeface);
                item.setTextColor(ContextCompat.getColor(Category.this, android.R.color.white));
                item.setBackgroundImage(ContextCompat.getDrawable(Category.this, images.getResourceId(position, 0)));
                return item;
            }
        });
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
              //  Toast.makeText(Category.this, item.getTitle(), Toast.LENGTH_SHORT).show();


                switch (item.getTitle().toLowerCase()) {


                    case "business":

                    {


                        SelectedCategory selectedCategory = new SelectedCategory();
                        selectedCategory.setName(item.getTitle());
                        selectedCategory.setImage(R.drawable.business);
                        selectedCategories.add(selectedCategory);
                    }
                            // Toast.makeText(getApplicationContext(),selectedCategory.getBusiness(),Toast.LENGTH_SHORT).show();           one.setTextColor(Color.BLACK);


                        break;
                    case "general":
                    {
                            SelectedCategory selectedCategory = new SelectedCategory();
                             selectedCategory.setName(item.getTitle());
                            selectedCategory.setImage(R.drawable.general);
                            selectedCategories.add(selectedCategory);

                        }

                        break;
                    case "health":
                    {
                            SelectedCategory selectedCategory = new SelectedCategory();
                            selectedCategory.setName(item.getTitle());
                            selectedCategory.setImage(R.drawable.health);
                            selectedCategories.add(selectedCategory);

                        }
                        break;
                    case"science":
                         {
                            SelectedCategory selectedCategory = new SelectedCategory();
                            selectedCategory.setName(item.getTitle());
                            selectedCategory.setImage(R.drawable.science);
                            selectedCategories.add(selectedCategory);

                        }
                        break;
                    case "sports":
                        {
                            SelectedCategory selectedCategory = new SelectedCategory();
                             selectedCategory.setName(item.getTitle());
                            selectedCategory.setImage(R.drawable.sports);
                            selectedCategories.add(selectedCategory);

                        }
                        break;
                    case "technology":
                        {
                            SelectedCategory selectedCategory = new SelectedCategory();
                            selectedCategory.setName(item.getTitle());
                            selectedCategory.setImage(R.drawable.tech);
                            selectedCategories.add(selectedCategory);

                        }
                        break;
                    case"entertainment":
                         {
                            SelectedCategory selectedCategory = new SelectedCategory();
                            selectedCategory.setName(item.getTitle());
                            selectedCategory.setImage(R.drawable.entertainment);
                            selectedCategories.add(selectedCategory);

                        }
                        break;
                }

            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {
            int removePosition=0;
                for (int i=0;i<selectedCategories.size();i++)
                {

                  if (item.getTitle().equalsIgnoreCase(selectedCategories.get(i).getName()))
                    {
                        removePosition=i;

                    }


                }

                selectedCategories.remove(removePosition);


            }
        });

        //setContentView(R.layout.bubble);


      /*  toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Category");
        toolbar.setTitleTextColor((getResources().getColor(R.color.white)));
*/
        continuebtn.setOnClickListener(this);





        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        countryCode  = tm.getSimCountryIso();
       // Toast.makeText(getApplicationContext(),countryCode,Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        picker.onPause();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

         case R.id.continuebtn:
                    if (selectedCategories.size()>0) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("countrycode",countryCode);
                        intent.putParcelableArrayListExtra("categoryselected",selectedCategories);


                        SharedPreferences.Editor editor= getSharedPreferences("categorydata",MODE_PRIVATE).edit();

                        Gson gson = new Gson();
                        String stringValue = gson.toJson(selectedCategories);
                        editor.putString("selectedcat",stringValue);
                        editor.commit();

                        startActivity(intent);
                        finish();

                        //Collections.sort(selected, String.CASE_INSENSITIVE_ORDER);

                    }
                    else
                    {
                        Toast.makeText(Category.this,"please select atleast one category",Toast.LENGTH_SHORT).show();

                    }



        }
    }
}
