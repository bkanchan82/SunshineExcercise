package architech.android.com.sunshineexcercise.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import architech.android.com.sunshineexcercise.data.WeatherRepository;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;

public class MainActivityViewModel extends ViewModel {

    private final WeatherRepository mWeatherRepository;
    private LiveData<List<WeatherEntry>> listLiveData;

    public MainActivityViewModel(WeatherRepository weatherRepository){
        mWeatherRepository = weatherRepository;
        listLiveData = mWeatherRepository.getTodaysWeather();
    }

    public LiveData<List<WeatherEntry>> getListLiveData() {
        return listLiveData;
    }
}
