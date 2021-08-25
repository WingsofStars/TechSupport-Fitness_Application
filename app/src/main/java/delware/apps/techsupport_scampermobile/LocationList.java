package delware.apps.techsupport_scampermobile;

import android.app.Application;
import android.location.Location;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

//global list for storing locations

public class LocationList extends Application {

    private static LocationList singleton;

    private List<Location> myLocations;

    public List<Location> getMyLocations() {
        return myLocations;
    }

    public void setMyLocations(List<Location> myLocations) {
        this.myLocations = myLocations;
    }

    public LocationList getInstance() {
        return singleton;
    }

    public void onCreate() {
        super.onCreate();
        singleton = this;
        myLocations = new ArrayList<>();
    }


}
