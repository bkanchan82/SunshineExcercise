package architech.android.com.sunshineexcercise.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import architech.android.com.sunshineexcercise.SunshineAppController;
import architech.android.com.sunshineexcercise.data.WeatherRepository;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;

public class ListViewModel extends AndroidViewModel {

    private LiveData<List<WeatherEntry>> mListLiveData;

    public ListViewModel(@NonNull Application application) {
        super(application);

            /*if (application instanceof SunshineAppController) {
                SunshineAppController sunshineAppController = (SunshineAppController) application;
                WeatherRepository weatherRepository = sunshineAppController.getWeatherRepository();
                mListLiveData = weatherRepository.getTodaysWeather();
        }*/
    }

    public LiveData<List<WeatherEntry>> getListLiveData() {
        return mListLiveData;
    }
}
