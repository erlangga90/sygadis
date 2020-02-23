package com.erlangga.sygadis.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;

public class LocalManager {

    private Context context;

    public LocalManager(Context context) {
        this.context = context;
    }

    public void writeBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();
    }

    public boolean readBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public void writeInteger(String key, int value) {
        getEditor().putInt(key, value).commit();

    }

    public int readInteger(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public void writeString(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public String readString(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public void writeFloat(String key, float value) {
        getEditor().putFloat(key, value).commit();
    }

    public float readFloat(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public void writeLong(String key, long value) {
        getEditor().putLong(key, value).commit();
    }

    public long readLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public void putObject(String key, Object object) {
        String objectString = convertObjectToString(object);
        writeString(key, objectString);
    }

    public <T> T getObject(String key, Object defValue, Class<T> tClass) {
        String defValueString = convertObjectToString(defValue);
        String jsonValue = getPreferences().getString(key, defValueString);

        if (jsonValue == null) {
            jsonValue = defValueString;
        }

        return convertJsonStringToObject(jsonValue, tClass);
    }

    private String convertObjectToString(Object value) {
        return new Gson().toJson(value);
    }

    private <T> T convertJsonStringToObject(String jsonValue, Class<T> tClass) {
        Gson gson = new Gson();
        return gson.fromJson(jsonValue, tClass);
    }

    public Boolean contains(String key) {
        return getPreferences().contains(key);
    }

    public void removePreference(String key) {
        getEditor().remove(key).apply();
    }

    private SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

}
