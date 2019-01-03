package architech.android.com.sunshineexcercise.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import architech.android.com.sunshineexcercise.R;
import architech.android.com.sunshineexcercise.data.SunshinePreferences;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;
import architech.android.com.sunshineexcercise.databinding.ForecastListItemBinding;

public class WeatherPagedListAdapter extends PagedListAdapter<WeatherEntry,WeatherPagedListAdapter.PagedListViewHolder> {

    private static DiffUtil.ItemCallback<WeatherEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<WeatherEntry>() {
        @Override
        public boolean areItemsTheSame(WeatherEntry oldItem, WeatherEntry newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(WeatherEntry oldItem, WeatherEntry newItem) {
            return oldItem.getDate() == newItem.getDate();
        }
    };

    private WeatherListItemCLickListener mCallback;

    protected WeatherPagedListAdapter(WeatherListItemCLickListener callback){
        super(DIFF_CALLBACK);
        mCallback = callback;
    }

    @NonNull
    @Override
    public PagedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        ForecastListItemBinding binding = DataBindingUtil.inflate(inflater,
                layout,
                parent,
                false);
        binding.setCallback(mCallback);
        binding.setIsMetric(SunshinePreferences.isMetric(context));
        WeatherPagedListAdapter.PagedListViewHolder viewHolder = new WeatherPagedListAdapter.PagedListViewHolder(binding);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PagedListViewHolder holder, int position) {
        WeatherEntry weatherEntry = getItem(position);
        if(weatherEntry != null) {
            holder.mBinding.setWeatherEntry(weatherEntry);
        }
    }

    public class PagedListViewHolder extends RecyclerView.ViewHolder {
        ForecastListItemBinding mBinding;

        public PagedListViewHolder(ForecastListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
