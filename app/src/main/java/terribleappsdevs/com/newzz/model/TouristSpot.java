package terribleappsdevs.com.newzz.model;

import android.graphics.drawable.Drawable;

public class TouristSpot {
    public String name;
    public String city;
    public Drawable url;

    public TouristSpot(String name, String city, Drawable url) {
        this.name = name;
        this.city = city;
        this.url = url;
    }
}
