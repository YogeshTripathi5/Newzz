package terribleappsdevs.com.newzz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin1 on 7/10/17.
 */

public class Website  {

    private String status;
    private ArrayList<Source> sources;
    public Website() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }



    public Website(String status, ArrayList<Source> sources) {
        this.status = status;
        this.sources = sources;
    }
}
