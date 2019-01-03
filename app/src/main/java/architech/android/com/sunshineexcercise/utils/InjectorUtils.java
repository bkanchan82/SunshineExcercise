package architech.android.com.sunshineexcercise.utils;

import android.content.Context;

import architech.android.com.sunshineexcercise.data.SunshineDatabase;
import architech.android.com.sunshineexcercise.data.WeatherRepository;
import architech.android.com.sunshineexcercise.data.dao.WeatherDao;
import architech.android.com.sunshineexcercise.data.network.WeatherNetworkDataSource;
import architech.android.com.sunshineexcercise.data.network.retrofit.Weather;
import architech.android.com.sunshineexcercise.ui.MainViewModelFactory;

public class InjectorUtils {

    public static WeatherNetworkDataSource provideWeatherNetworkDataSource(Context context){
        provideMainViewModelFactory(context);
        AppExecutor appExecutor = AppExecutor.getInstance();
        return WeatherNetworkDataSource.getInstance(context,appExecutor);
    }

    public static WeatherDao provideWeatherDao(Context context){
        SunshineDatabase sunshineDatabase = SunshineDatabase.getInstance(context);
        return sunshineDatabase.weatherDao();
    }

    public static WeatherRepository provideWeatherRepository(Context context){
        AppExecutor appExecutor =  AppExecutor.getInstance();
        return WeatherRepository.getInstance(provideWeatherDao(context),
                WeatherNetworkDataSource.getInstance(context,appExecutor),
               appExecutor);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context){

        WeatherRepository weatherRepository = provideWeatherRepository(context);

        return new MainViewModelFactory(weatherRepository);

    }

}
