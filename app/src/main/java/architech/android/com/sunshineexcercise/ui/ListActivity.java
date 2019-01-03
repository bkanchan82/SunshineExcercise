package architech.android.com.sunshineexcercise.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import architech.android.com.sunshineexcercise.R;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;
import architech.android.com.sunshineexcercise.ui.settings.SettingsActivity;
import architech.android.com.sunshineexcercise.utils.InjectorUtils;


public class ListActivity extends AppCompatActivity implements WeatherListItemCLickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = ListActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private TextView defaultMessageTextView;

    //    private WeatherListAdapter adapter;
    private WeatherPagedListAdapter adapter;
    private PagedListViewModel mainActivityViewModel;

    CountingIdlingResource mIdlingRes = new CountingIdlingResource("name");

    public CountingIdlingResource getIdlingResourceInTest() {
        return mIdlingRes;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.weather_recycler_view);
        defaultMessageTextView = findViewById(R.id.default_message);

//        adapter = new WeatherListAdapter(this);
        adapter = new WeatherPagedListAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        loadViewModel();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void loadViewModel() {
        mIdlingRes.increment();
        MainViewModelFactory mainViewModelFactory = InjectorUtils.provideMainViewModelFactory(this);
        mainActivityViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(PagedListViewModel.class);
        mainActivityViewModel.getWeatherEntries().observe(this, new Observer<PagedList<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable PagedList<WeatherEntry> weatherEntries) {
                adapter.submitList(weatherEntries);
                mIdlingRes.decrement();
            }
        });

    }

    @Override
    public void onWeatherClick(WeatherEntry weatherEntry) {
        Toast.makeText(this, "WeatherEntry : " + weatherEntry.getWeatherIconId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mainActivityViewModel.triggerPreferenceChange();
    }
}
