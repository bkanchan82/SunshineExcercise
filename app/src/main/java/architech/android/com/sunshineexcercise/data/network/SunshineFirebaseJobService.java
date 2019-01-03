package architech.android.com.sunshineexcercise.data.network;

import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import architech.android.com.sunshineexcercise.ui.NotificationUtils;
import architech.android.com.sunshineexcercise.utils.InjectorUtils;

public class SunshineFirebaseJobService extends JobService {

    private AsyncTask mAsyncTask;

    @Override
    public boolean onStartJob(final JobParameters job) {

        mAsyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                WeatherNetworkDataSource weatherNetworkDataSource = InjectorUtils.provideWeatherNetworkDataSource(SunshineFirebaseJobService.this);
                weatherNetworkDataSource.fetchWeather();

                NotificationUtils.showNotification(SunshineFirebaseJobService.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job,false);
            }
        };

      mAsyncTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mAsyncTask != null){
            mAsyncTask.cancel(true);
        }
        return true;
    }
}
