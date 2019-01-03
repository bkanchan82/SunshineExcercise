package architech.android.com.sunshineexcercise.data.network.retrofit;

import java.util.ArrayList;

public class WeatherData {

    private City city;
    private ArrayList<ListItem> list;

    public WeatherData(City city, ArrayList<ListItem> list) {
        this.city = city;
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<ListItem> getList() {
        return list;
    }

    public void setList(ArrayList<ListItem> list) {
        this.list = list;
    }
}
