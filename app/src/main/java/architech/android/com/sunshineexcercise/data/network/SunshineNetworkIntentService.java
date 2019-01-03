package architech.android.com.sunshineexcercise.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import architech.android.com.sunshineexcercise.utils.InjectorUtils;

public class SunshineNetworkIntentService extends IntentService {

    private static final String LOG_TAG = SunshineNetworkIntentService.class.getSimpleName();

    public SunshineNetworkIntentService(){
        super(SunshineNetworkIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(LOG_TAG, "Intent service started");
        WeatherNetworkDataSource networkDataSource =
                InjectorUtils.provideWeatherNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchWeather();
    }
}
