package architech.android.com.sunshineexcercise;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import architech.android.com.sunshineexcercise.data.SunshineDatabase;
import architech.android.com.sunshineexcercise.data.WeatherRepository;
import architech.android.com.sunshineexcercise.data.network.WeatherNetworkDataSource;
import architech.android.com.sunshineexcercise.utils.AppExecutors;

public class SunshineAppController extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public SunshineDatabase getAppDatabase(){
        return SunshineDatabase.getInstance(this);
    }

  /*  public WeatherRepository getWeatherRepository(){
        return WeatherRepository.getInstance(getAppDatabase(),WeatherNetworkDataSource.getInstance(),mAppExecutors);
    }*/
}
