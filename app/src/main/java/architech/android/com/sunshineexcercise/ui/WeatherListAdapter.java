package architech.android.com.sunshineexcercise.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import architech.android.com.sunshineexcercise.R;
import architech.android.com.sunshineexcercise.data.SunshinePreferences;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;
import architech.android.com.sunshineexcercise.databinding.ForecastListItemBinding;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder> {

    private List<WeatherEntry> mWeatherEntries;
    private WeatherListItemCLickListener mCallback;

    public WeatherListAdapter(WeatherListItemCLickListener callback){
        mCallback = callback;
    }


    @NonNull
    @Override
    public WeatherListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layout = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        ForecastListItemBinding binding = DataBindingUtil.inflate(inflater,
                layout,
                parent,
                false);
        binding.setCallback(mCallback);

        binding.setIsMetric(SunshinePreferences.isMetric(context));

        WeatherListViewHolder viewHolder = new WeatherListViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherListViewHolder holder, int position) {
        WeatherEntry weatherEntry = mWeatherEntries.get(position);
        holder.mBinding.setWeatherEntry(weatherEntry);
    }

    public void swapWeatherList(List<WeatherEntry> weatherEntries){
        if(weatherEntries != null){
            mWeatherEntries = weatherEntries;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(mWeatherEntries != null){
            return mWeatherEntries.size();
        }else {
            return 0;
        }
    }

    public class WeatherListViewHolder extends RecyclerView.ViewHolder{

        ForecastListItemBinding mBinding;

        public WeatherListViewHolder(ForecastListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

}
