package architech.android.com.sunshineexcercise.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import architech.android.com.sunshineexcercise.R;

public class FragmentSettings extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.weather_prefs,rootKey);



        PreferenceScreen preferenceScreen = getPreferenceScreen();

        EditTextPreference editTextPreference = (EditTextPreference) preferenceScreen.findPreference(getString(R.string.pref_location_key));
        editTextPreference.setOnPreferenceChangeListener(this);

        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        int preferenceCount = preferenceScreen.getPreferenceCount();
        for (int i =0 ; i < preferenceCount ; i++){
            Preference preference = preferenceScreen.getPreference(i);
            String key = preference.getKey();
            String value = sharedPreferences.getString(key,"");
            setSummary(preference,value);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setSummary(Preference preference, String value){
        if(preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference)preference;
            int index = listPreference.findIndexOfValue(value);
            if(index < listPreference.getEntries().length) {
                String prefrenceSummary = listPreference.getEntries()[index].toString();
                preference.setSummary(prefrenceSummary);
            }
        }else if(preference instanceof EditTextPreference){
            preference.setSummary(value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = getPreferenceScreen().findPreference(key);
        String value = sharedPreferences.getString(key,"");
        setSummary(preference,value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = String.valueOf(newValue);
        setSummary(preference,value);
        return true;
    }
}
