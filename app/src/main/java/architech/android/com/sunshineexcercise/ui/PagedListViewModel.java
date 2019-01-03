package architech.android.com.sunshineexcercise.ui;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import architech.android.com.sunshineexcercise.data.WeatherRepository;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;

public class PagedListViewModel extends ViewModel {

    private WeatherRepository mRepository;
    private LiveData<PagedList<WeatherEntry>> mWeatherEntries;

    public PagedListViewModel(WeatherRepository weatherRepository){
        mRepository = weatherRepository;
        mWeatherEntries = mRepository.getTodaysLiveWeather(false);
    }

    public LiveData<PagedList<WeatherEntry>> getWeatherEntries() {
        return mWeatherEntries;
    }

    public void triggerPreferenceChange(){
        mWeatherEntries = mRepository.getTodaysLiveWeather(false);
    }
}
