package architech.android.com.sunshineexcercise.data.network.retrofit;

public class Temp {

    private double max;
    private double min;

    public Temp(double max, double min) {
        this.max = max;
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
