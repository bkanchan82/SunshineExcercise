package architech.android.com.sunshineexcercise.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.NewInstanceFactory;
import android.support.annotation.NonNull;

import architech.android.com.sunshineexcercise.data.WeatherRepository;

public class MainViewModelFactory extends NewInstanceFactory {
    private final WeatherRepository mWeatherRepository;

    public MainViewModelFactory(WeatherRepository weatherRepository){
        mWeatherRepository = weatherRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return (T) new MainActivityViewModel(mWeatherRepository);
        return (T) new PagedListViewModel(mWeatherRepository);
    }
}
