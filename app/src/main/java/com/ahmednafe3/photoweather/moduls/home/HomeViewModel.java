package com.ahmednafe3.photoweather.moduls.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<String> currentCity;
    public MutableLiveData<String> description;
    public MutableLiveData<Double> currentTimp;
    OpenWeatherMapHelper helper;

    public void callData() {
        helper = new OpenWeatherMapHelper("93f632c9a7e0d61c366b2f7458daa352");
        helper.setUnits(Units.METRIC);
        helper.setLang(Lang.ENGLISH);
        helper.getCurrentWeatherByCityName("cairo", new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                currentCity.setValue(currentWeather.getName());
                currentTimp.setValue(currentWeather.getMain().getTempMax());
                description.setValue(currentWeather.getWeather().get(0).getDescription());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public LiveData<String> getCurrentCity() {
        if (currentCity == null) {
            currentCity = new MutableLiveData<String>();
        }
        return currentCity;
    }

    public LiveData<String> getDescription() {
        if (description == null) {
            description = new MutableLiveData<String>();
        }
        return description;
    }

    public LiveData<Double> getCurrentTimp() {
        if (currentTimp == null) {
            currentTimp = new MutableLiveData<Double>();
        }
        return currentTimp;
    }


}
