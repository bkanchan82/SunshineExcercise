package architech.android.com.sunshineexcercise.data.network.retrofit;

public class City {

    private String name;
    private Coord coord;
    private String country;

    public City(String name, Coord coord, String country) {
        this.name = name;
        this.coord = coord;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
