package architech.android.com.sunshineexcercise.data.network.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRetrofitClient {

    /*https://andfun-weather.udacity.com/staticweather?q=94043%2CUSA&mode=json&units=metric&cnt=14*/

    @GET("/staticweather")
    Call<WeatherData> weatherDataForLocation(@Query("q") String location,@Query("mode") String mode, @Query("units") String metric ,@Query("cnt") int noOfDays);

}
