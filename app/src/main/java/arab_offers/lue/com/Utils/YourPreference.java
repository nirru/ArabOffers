package arab_offers.lue.com.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import arab_offers.lue.com.Models.ModelObj;
import arab_offers.lue.com.Models.ObjectModel;
import arab_offers.lue.com.Models.OfferModel;


/**
 * Created by Fujitsu on 29-02-2016.
 */
public class YourPreference {
    public static String MY_DATA_SHAREF_PREF = "QashGift_USER_DETAILS";
    private static YourPreference yourPreference;
    private SharedPreferences sharedPreferences;

    public static YourPreference getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new YourPreference(context);
        }
        return yourPreference;
    }

    private YourPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_DATA_SHAREF_PREF, Context.MODE_PRIVATE);
    }
    public void saveDataboolean(String key,boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putBoolean(key, value);
        prefsEditor.commit();
    }
    public void saveDatahashJob(String key,HashSet<ModelObj> value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor .putString(key, json);
        prefsEditor.commit();
    }
    public void saveModelObj(String key,OfferModel value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor .putString(key, json);
        prefsEditor.commit();
    }
    public void saveKeyModel(String key,ObjectModel value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor .putString(key, json);
        prefsEditor.commit();
    }
    public void saveDataArrayString(String key,ArrayList<String> value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor .putString(key, json);
        prefsEditor.commit();
    }
    public void saveOffersList(String key, ArrayList<Object> value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor .putString(key, json);
        prefsEditor.commit();
    }

    public void saveDataString(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }
    public void saveDataSet(String key, Set value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putStringSet(key, value);
        prefsEditor.commit();
    }
    public void saveDataInt(String key,int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putInt(key, value);
        prefsEditor.commit();
    }


    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, null);
        }
        return null;
    }
    public ArrayList<String> getDataArrayListString(String key) {
        if (sharedPreferences!= null) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> arrayList = gson.fromJson(json, type);
            return arrayList;
        }
        return null;
    }
    public HashSet<ModelObj> getdatahashJob(String key) {
        if (sharedPreferences!= null) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, null);
            Type type = new TypeToken<HashSet<ModelObj>>() {}.getType();
            HashSet<ModelObj> arrayList = gson.fromJson(json, type);
            return arrayList;
        }
        return null;
    }
    public OfferModel getModelObj(String key) {
        if (sharedPreferences!= null) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, null);
            Type type = new TypeToken<OfferModel>() {}.getType();
            OfferModel arrayList = gson.fromJson(json, type);
            return arrayList;
        }
        return null;
    }
    public ObjectModel getModelKey(String key) {
        if (sharedPreferences!= null) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, null);
            Type type = new TypeToken<ObjectModel>() {}.getType();
            ObjectModel arrayList = gson.fromJson(json, type);
            return arrayList;
        }
        return null;
    }
    public ArrayList<Object> getOffersList(String key) {
        if (sharedPreferences!= null) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, null);
            Type type = new TypeToken<ArrayList<OfferModel>>() {}.getType();
            ArrayList<Object> arrayList = gson.fromJson(json, type);
            return arrayList;
        }
        return null;
    }
    public Integer getDataInteger(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }
    public boolean getDataboolean(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }
    public Set getDataSet(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getStringSet(key, null);
        }
        return null;
    }
}