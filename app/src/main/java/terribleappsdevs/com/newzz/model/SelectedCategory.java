package terribleappsdevs.com.newzz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by yogeshtripathi on 11/3/18.
 */

public class SelectedCategory  implements Parcelable {

    private String name;
    private int image;




    protected SelectedCategory(Parcel in) {
        name = in.readString();
        image = in.readInt();
    }

    public static final Creator<SelectedCategory> CREATOR = new Creator<SelectedCategory>() {
        @Override
        public SelectedCategory createFromParcel(Parcel in) {
            return new SelectedCategory(in);
        }

        @Override
        public SelectedCategory[] newArray(int size) {
            return new SelectedCategory[size];
        }
    };

    public SelectedCategory() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(image);
    }


}
