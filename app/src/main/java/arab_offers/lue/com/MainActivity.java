package arab_offers.lue.com;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import arab_offers.lue.com.Asyncs.LongOperationC;
import arab_offers.lue.com.Asyncs.LongOperationD;
import arab_offers.lue.com.Asyncs.LongOperationE;
import arab_offers.lue.com.FourAdapters.AgeAdapter;
import arab_offers.lue.com.FourAdapters.AreaAdapter;
import arab_offers.lue.com.FourAdapters.CityAdapter;
import arab_offers.lue.com.FourAdapters.CountryAdapter;
import arab_offers.lue.com.Interfaces.PassText;
import arab_offers.lue.com.Models.CityModel;
import arab_offers.lue.com.Models.CountryModel;
import arab_offers.lue.com.Models.RegionModel;
import arab_offers.lue.com.Utils.ConnectionDetector;
import arab_offers.lue.com.Utils.MarshMallowPermission;
import arab_offers.lue.com.Utils.Urls;
import arab_offers.lue.com.Utils.YourPreference;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
LinearLayout linearlater;
    ConnectionDetector connectionDetector;
    ArrayList<CountryModel> arrayListCountry= new ArrayList<CountryModel>();
    ArrayList<CityModel> arrayListCity= new ArrayList<CityModel>();
    ArrayList<RegionModel> arrayListRegion= new ArrayList<RegionModel>();
    LinearLayout areaL, cityL, countryL, ageL, okay, mobileL;
    AppCompatEditText area, city, country, age, name, phonecode, mobile;
    RadioGroup radioGroup;
    CoordinatorLayout mainLayoutRel;
    MarshMallowPermission marshMallowPermission;
    AppCompatEditText spinnergender;
    YourPreference yourPreference=null;
    @SuppressWarnings("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        yourPreference=YourPreference.getInstance(MainActivity.this);
        connectionDetector= new ConnectionDetector(MainActivity.this);
       String[] PERMISSIONS_ARRAY = {android.Manifest.permission_group.STORAGE/*android.Manifest.permission.CAMERA, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_INPUT_STATE, android.Manifest.permission.GET_ACCOUNTS_PRIVILEGED,
                android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK*/};
        int PERMISSION_ALL = 1;
        //String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};

        if(!marshMallowPermission.hasPermissions(this, PERMISSIONS_ARRAY)){
            ActivityCompat.requestPermissions(this, PERMISSIONS_ARRAY, PERMISSION_ALL);
        }
        registerUI();
        registerFuxnc();
        getAllCountries();
        try {
            PackageInfo info = getPackageManager().getPackageInfo("arab_offers.lue.com", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                yourPreference.saveDataString("tcmtoken", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private void registerFuxnc() {
    linearlater.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         startActivity(new Intent(MainActivity.this, PagerActivity.class));
        }
    });
        ageL.setOnClickListener(this);
        countryL.setOnClickListener(this);
        cityL.setOnClickListener(this);
        areaL.setOnClickListener(this);
        age.setOnClickListener(this);
        area.setOnClickListener(this);
        city.setOnClickListener(this);
        country.setOnClickListener(this);
        //switchCheck.setOnClickListener(this);
        //undefinedCheck.setOnClickListener(this);
        okay.setOnClickListener(this);
        spinnergender.setOnClickListener(this);

    }

    private void registerUI(){
        linearlater=(LinearLayout)findViewById(R.id.linearlater);
        areaL=(LinearLayout)findViewById(R.id.areaL);
        countryL=(LinearLayout)findViewById(R.id.countryL);
        cityL=(LinearLayout)findViewById(R.id.cityL);
        ageL=(LinearLayout)findViewById(R.id.ageL);
        name=(AppCompatEditText) findViewById(R.id.name);
        age=(AppCompatEditText) findViewById(R.id.age);
        country=(AppCompatEditText) findViewById(R.id.country);
        city=(AppCompatEditText) findViewById(R.id.city);
        area=(AppCompatEditText) findViewById(R.id.area);
        spinnergender=(AppCompatEditText) findViewById(R.id.spinnergender);
        //switchCheck=(SwitchCompat)findViewById(R.id.switchcheck);
        //undefinedCheck=(AppCompatRadioButton)findViewById(R.id.undefinedCheck);

        okay=(LinearLayout)findViewById(R.id.okay);
        phonecode=(AppCompatEditText)findViewById(R.id.phonecode);
        mobile=(AppCompatEditText)findViewById(R.id.mobile);
        mainLayoutRel=(CoordinatorLayout) findViewById(R.id.mainLayoutRel);
        try {
            PackageInfo info = getPackageManager().getPackageInfo("arab_offers.lue.com", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private void getAllCountries(){
        new LongOperationD(MainActivity.this, Urls.getAllCountries, null, new LongOperationD.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray jsonArray = new JSONArray(output);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        int id= jsonObject.getInt("id");
                        String name= jsonObject.getString("name");
                        String phone_code= jsonObject.getString("phone_code");
                        String english_name= jsonObject.getString("english_name");
                        arrayListCountry.add(new CountryModel(id, name, phone_code, english_name));
                        Log.i("result", output+"==========="+arrayListCountry.size());

                    }
//                    country.setText(arrayListCountry.get(0).getName());

                }catch (Exception e){e.printStackTrace();}
            }
        }).execute();
    }
    private void getAllCities(int id){
        arrayListCity.clear();
        arrayListRegion.clear();
        new LongOperationD(MainActivity.this, Urls.getAllCities+id, null, new LongOperationD.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray jsonArray = new JSONArray(output);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        int id= jsonObject.getInt("id");
                        String name= jsonObject.getString("name");
                        String english_name= jsonObject.getString("english_name");
                        arrayListCity.add(new CityModel(id, name, english_name));
                    }

                }catch (Exception e){e.printStackTrace();}
            }
        }).execute();
    }
    private void getAllRegions(int id){
        arrayListRegion.clear();
        new LongOperationD(MainActivity.this, Urls.getAllRegions+id, null, new LongOperationD.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray jsonArray = new JSONArray(output);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        int id= jsonObject.getInt("id");
                        String name= jsonObject.getString("name");
                        String english_name= jsonObject.getString("english_name");
                        arrayListRegion.add(new RegionModel(id, name, english_name));
                    }

                }catch (Exception e){e.printStackTrace();}
            }
        }).execute();
    }
    private static int selectedAge=3;
    private static int selectedCountry=-1;
    private static int selectedCity=-1;
    private static int selectedArea=-1;
    private int chooseItemFromArray(int titleId, int arrayId, final PassText passText, int selectedPosition){
          alertUserAge(getResources().getStringArray(R.array.age_range));
        /*new MaterialDialog.Builder(this)
                .title(titleId)
                .items(arrayId).backgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                .itemsCallbackSingleChoice(selectedPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        selectedAge=which;
                        Log.i("which", which+"");
                        passText.pass(which);
                        *//**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **//*
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();*/

        return selectedAge;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.spinnergender:
                alertUserGender(getResources().getStringArray(R.array.gender));
                break;
            case R.id.ageL:
                alertUserAge(getResources().getStringArray(R.array.age_range));

                break;
            case R.id.countryL:

                final String[] countries= new String[arrayListCountry.size()];
                for(int i=0; i<arrayListCountry.size(); i++){
                    countries[i]= arrayListCountry.get(i).getName();
                }
                alertUserWithList(arrayListCountry);
                break;
            case R.id.cityL:
                 if(country.getText().toString().equalsIgnoreCase("")){
                     alertUser(R.string.choosecountry);
                 }else if(!connectionDetector.isConnectingToInternet()){
                     Toast.makeText(MainActivity.this, "please check your internet connection. ", Toast.LENGTH_SHORT).show();
                 }else {
                     final String[] cities = new String[arrayListCity.size()];
                     for (int i = 0; i < arrayListCity.size(); i++) {
                         cities[i] = arrayListCity.get(i).getName();
                     }
                     alertUserCity(arrayListCity);
                     /*new MaterialDialog.Builder(this)
                             .title(R.string.city)
                             .items(cities).backgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                             .itemsCallbackSingleChoice(selectedCity, new MaterialDialog.ListCallbackSingleChoice() {
                                 @Override
                                 public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                     try {
                                         selectedCity = which;
                                         city.setText(cities[which]);
                                         getAllRegions(arrayListCity.get(which).getId());
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                     *//**
                                      * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                                      * returning false here won't allow the newly selected radio button to actually be selected.
                                      **//*
                                     return true;
                                 }
                             })
                             .positiveText(R.string.choose)
                             .show();*/
                 }
                break;
            case R.id.areaL:
                if(country.getText().toString().equalsIgnoreCase("")){
                    alertUser(R.string.choosecountry);
                } else if(city.getText().toString().equalsIgnoreCase("")){
                   alertUser(R.string.choosecity);
                 }else if(!connectionDetector.isConnectingToInternet()){
                    Toast.makeText(MainActivity.this, "please check your internet connection. ", Toast.LENGTH_SHORT).show();
                }else {
                    final String[] areas = new String[arrayListRegion.size()];
                    for (int i = 0; i < arrayListRegion.size(); i++) {
                        areas[i] = arrayListRegion.get(i).getName();
                    }
                    alertUserArea(arrayListRegion);
                    /*new MaterialDialog.Builder(this)
                            .title(R.string.region)
                            .items(areas).backgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                            .itemsCallbackSingleChoice(selectedArea, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    try {
                                        selectedArea = which;
                                        area.setText(areas[which]);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    *//**
                                     * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                                     * returning false here won't allow the newly selected radio button to actually be selected.
                                     **//*
                                    return true;
                                }
                            })
                            .positiveText(R.string.choose)
                            .show();*/
                }
                break;
            case R.id.area:
                areaL.performClick();
                break;
            case R.id.country:
                countryL.performClick();
                break;
            case R.id.city:
                cityL.performClick();
                break;
            case R.id.age:
                ageL.performClick();
                break;
            /*case R.id.switchcheck:
               radioGroup.clearCheck();
                break;
            case R.id.undefinedCheck:
                break;*/
            case R.id.okay:
                performRequest();
                break;


        }
    }
private void alertUser( int id){
    final MaterialDialog materialDialog=new MaterialDialog.Builder(MainActivity.this).
            customView(R.layout.alert_layout, false).backgroundColor(getResources().getColor(android.R.color.white)).build();
    materialDialog.show();
    materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    AppCompatButton s= ((AppCompatButton)materialDialog.findViewById(R.id.alertOk));
    AppCompatTextView text=(AppCompatTextView)materialDialog.findViewById(R.id.alertText);
    s.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            materialDialog.dismiss();
        }
    });
    final SpannableStringBuilder str = new SpannableStringBuilder(getString(R.string.app_name));
    String sourceString = "<b>" + getString(R.string.app_name) + "</b> "+"<br>\n"+getString(id);
    str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, getString(R.string.app_name).length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    text.setText(Html.fromHtml(sourceString));
}
    String[] str;
    int k=0;
    int m=0;
    private void alertUserWithList(final ArrayList<CountryModel> coun){
            final MaterialDialog materialDialog=new MaterialDialog.Builder(MainActivity.this).
                    customView(R.layout.list_alerts, false).backgroundColor(getResources().getColor(android.R.color.white)).build();
            materialDialog.show();
            materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            AppCompatButton alertCancel= ((AppCompatButton)materialDialog.findViewById(R.id.alertCancel));
            AppCompatButton alertOkay= ((AppCompatButton)materialDialog.findViewById(R.id.alertOkay));
            AppCompatTextView list_head= ((AppCompatTextView)materialDialog.findViewById(R.id.list_head));
            RecyclerView listView= ((RecyclerView) materialDialog.findViewById(R.id.list_alert));
        if(coun!=null){
             str=new String[coun.size()];
            for(int i=0; i<coun.size(); i++){
                str[i]= coun.get(i).getName();
            }
        }



        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(recyclerViewLayoutManager);
        CountryAdapter recyclerView_Adapter = new CountryAdapter(MainActivity.this, coun, new PassText() {
            @Override
            public void pass(int position) {
                m=position;
            }
        });
        listView.setAdapter(recyclerView_Adapter);
        alertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        alertOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    country.setText(coun.get(m).getName());
                    phonecode.setText(coun.get(m).getPhonecode());
                    materialDialog.dismiss();
                    city.setText("");
                    area.setText("");
                    getAllCities(coun.get(m).getId());
                    selectedCountry = m;
                }catch (Exception e){e.printStackTrace();}
            }
        });
        list_head.setText(getString(R.string.country));





    }
    int n=0;
    private void alertUserCity(ArrayList<CityModel> coun){

        final MaterialDialog materialDialog=new MaterialDialog.Builder(MainActivity.this).
                customView(R.layout.list_alerts, false).backgroundColor(getResources().getColor(android.R.color.white)).build();
        materialDialog.show();
        materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AppCompatButton alertCancel= ((AppCompatButton)materialDialog.findViewById(R.id.alertCancel));
        AppCompatButton alertOkay= ((AppCompatButton)materialDialog.findViewById(R.id.alertOkay));
        AppCompatTextView list_head= ((AppCompatTextView)materialDialog.findViewById(R.id.list_head));
        RecyclerView listView= ((RecyclerView) materialDialog.findViewById(R.id.list_alert));
        if(coun!=null){
            str=new String[coun.size()];
            for(int i=0; i<coun.size(); i++){
                str[i]= coun.get(i).getName();
            }
        }



        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(recyclerViewLayoutManager);
        CityAdapter recyclerView_Adapter = new CityAdapter(MainActivity.this, coun, new PassText() {
            @Override
            public void pass(int position) {
                n=position;
            }
        });
        listView.setAdapter(recyclerView_Adapter);
        alertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        alertOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city.setText(arrayListCity.get(n).getName());
                materialDialog.dismiss();
                area.setText("");
                getAllRegions(arrayListCity.get(n).getId());
                selectedCity=n;
            }
        });
        list_head.setText(getString(R.string.city));





    }
    int l=0;
    private void alertUserArea(ArrayList<RegionModel> coun){
        final MaterialDialog materialDialog=new MaterialDialog.Builder(MainActivity.this).
                customView(R.layout.list_alerts, false).backgroundColor(getResources().getColor(android.R.color.white)).build();
        materialDialog.show();
        materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AppCompatButton alertCancel= ((AppCompatButton)materialDialog.findViewById(R.id.alertCancel));
        AppCompatButton alertOkay= ((AppCompatButton)materialDialog.findViewById(R.id.alertOkay));
        AppCompatTextView list_head= ((AppCompatTextView)materialDialog.findViewById(R.id.list_head));
        RecyclerView listView= ((RecyclerView) materialDialog.findViewById(R.id.list_alert));
        if(coun!=null){
            str=new String[coun.size()];
            for(int i=0; i<coun.size(); i++){
                str[i]= coun.get(i).getName();
            }
        }



        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(recyclerViewLayoutManager);
        AreaAdapter recyclerView_Adapter = new AreaAdapter(MainActivity.this, coun, new PassText() {
            @Override
            public void pass(int position) {
                l=position;
            }
        });
        listView.setAdapter(recyclerView_Adapter);
        alertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        alertOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area.setText(arrayListRegion.get(l).getName());
                materialDialog.dismiss();
                selectedArea=l;
            }
        });
        list_head.setText(getString(R.string.region));
    }
    ArrayList<String> coun= new ArrayList<String>();
    private void alertUserAge(String[] str){
        coun.clear();
        final MaterialDialog materialDialog=new MaterialDialog.Builder(MainActivity.this).
                customView(R.layout.list_alerts, false).backgroundColor(getResources().getColor(android.R.color.white)).build();
        materialDialog.show();
        materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AppCompatButton alertCancel= ((AppCompatButton)materialDialog.findViewById(R.id.alertCancel));
        AppCompatButton alertOkay= ((AppCompatButton)materialDialog.findViewById(R.id.alertOkay));
        AppCompatTextView list_head= ((AppCompatTextView)materialDialog.findViewById(R.id.list_head));
        RecyclerView listView= ((RecyclerView) materialDialog.findViewById(R.id.list_alert));
        if(str!=null){

            for(int i=0; i<str.length; i++){
               coun.add(i, str[i]);
            }
        }



        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(recyclerViewLayoutManager);
        AgeAdapter recyclerView_Adapter = new AgeAdapter(MainActivity.this, coun, new PassText() {
            @Override
            public void pass(int position) {
                k=position;
            }
        });
        listView.setAdapter(recyclerView_Adapter);
        alertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
                coun.clear();
            }
        });
        alertOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                age.setText(coun.get(k));
                materialDialog.dismiss();
                selectedAge=k;
                coun.clear();
            }
        });
        list_head.setText(getString(R.string.reg_age));
    }
    public  static  int gender=1;
    private void alertUserGender(String[] str){
        coun.clear();
        final MaterialDialog materialDialog=new MaterialDialog.Builder(MainActivity.this).
                customView(R.layout.list_alerts, false).backgroundColor(getResources().getColor(android.R.color.white)).build();
        materialDialog.show();
        materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AppCompatButton alertCancel= ((AppCompatButton)materialDialog.findViewById(R.id.alertCancel));
        AppCompatButton alertOkay= ((AppCompatButton)materialDialog.findViewById(R.id.alertOkay));
        AppCompatTextView list_head= ((AppCompatTextView)materialDialog.findViewById(R.id.list_head));
        RecyclerView listView= ((RecyclerView) materialDialog.findViewById(R.id.list_alert));
        if(str!=null){

            for(int i=0; i<str.length; i++){
                coun.add(i, str[i]);
            }
        }



        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(recyclerViewLayoutManager);
        AgeAdapter recyclerView_Adapter = new AgeAdapter(MainActivity.this, coun, new PassText() {
            @Override
            public void pass(int position) {
                k=position;
            }
        });
        listView.setAdapter(recyclerView_Adapter);
        alertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
                coun.clear();
            }
        });
        alertOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnergender.setText(coun.get(k));
                materialDialog.dismiss();
                gender=k;
                coun.clear();
            }
        });
        list_head.setText(getString(R.string.reg_gender));
    }
    String regex = "[0-9]+";
    private void performRequest() {
        final YourPreference yourPreference=YourPreference.getInstance(MainActivity.this);
        try {
            /*if(name.getText().toString().length()<4){
                Urls.notifystate(MainActivity.this, mainLayoutRel,getResources().getString(R.string.validatename) );
            }else if(age.getText().toString().equalsIgnoreCase("")){
                Urls.notifystate(MainActivity.this, mainLayoutRel,getResources().getString(R.string.validateage) );
            }*/ if(country.getText().toString().equalsIgnoreCase("")){
                //Urls.notifystate(MainActivity.this, mainLayoutRel,getResources().getString(R.string.validatecountry) );
                alertUser(R.string.choosecountry);
            }/*else if(mobile.getText().toString().equalsIgnoreCase("")|| mobile.getText().toString().length()<6){
                Urls.notifystate(MainActivity.this, mainLayoutRel,getResources().getString(R.string.validatemobile) );
            }*/else if(city.getText().toString().equalsIgnoreCase("")){
                //Urls.notifystate(MainActivity.this, mainLayoutRel,getResources().getString(R.string.validatecity) );
                alertUser(R.string.choosecity);
            }else if(area.getText().toString().equalsIgnoreCase("")){
                //Urls.notifystate(MainActivity.this, mainLayoutRel,getResources().getString(R.string.validatearea) );
                alertUser(R.string.choosearea);
            }else if(mobile.getText().toString().equalsIgnoreCase("")){
                alertUser(R.string.enter_mobile_no);
            }else if(!mobile.getText().toString().matches(regex) ||
                    !(mobile.getText().toString().length()>8) ||
                    !(mobile.getText().toString().length()<11) ){
                  alertUser(R.string.correct_mobile_enter);
            }else {
                //int gender = undefinedCheck.isChecked() ? 1 : switchCheck.isChecked() ? 2 : 1;
                 name.setText(name.getText().toString().equalsIgnoreCase("")?"Guest user": name.getText().toString());
                mobile.setText(mobile.getText().toString().equalsIgnoreCase("")?"0000":mobile.getText().toString());
                /*final JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", name.getText().toString());
                jsonObject.accumulate("age", selectedAge+1);
                jsonObject.accumulate("gender", spinnergender.getSelectedItemPosition()==0?1:spinnergender.getSelectedItemPosition());
                jsonObject.accumulate("mobile", mobile.getText().toString());
                jsonObject.accumulate("country", arrayListCountry.get(selectedCountry).getId());
                jsonObject.accumulate("city", arrayListCity.get(selectedCity).getId());
                jsonObject.accumulate("region", arrayListRegion.get(selectedArea).getId());
                jsonObject.accumulate("device_token", yourPreference.getData("fcmtoken"));
                jsonObject.accumulate("device_type", 2);
                 Log.i("result", Urls.register);
                 Log.i("result", jsonObject.toString());*/
                List<NameValuePair> params= new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", name.getText().toString()));
                params.add(new BasicNameValuePair("age", ""+(selectedAge+1)));
                params.add(new BasicNameValuePair("gender", ""+(gender==0?1:gender)));
                params.add(new BasicNameValuePair("mobile", mobile.getText().toString()));
                params.add(new BasicNameValuePair("country", ""+arrayListCountry.get(selectedCountry).getId()));
                params.add(new BasicNameValuePair("city", ""+arrayListCity.get(selectedCity).getId()));
                params.add(new BasicNameValuePair("region", ""+arrayListRegion.get(selectedArea).getId()));
                params.add(new BasicNameValuePair("device_token", yourPreference.getData("fcmtoken")));
                params.add(new BasicNameValuePair("device_type",""+ 2));
                getsAllOfferData(Urls.register, params);
                /*new LongOperationC(MainActivity.this, Urls.register, jsonObject.toString(), new LongOperationC.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        try {Log.i("result", output);
                            JSONObject jsonObject1 = new JSONObject(output);
                            yourPreference.saveDataInt("user_id", jsonObject1.getInt("id"));
                            yourPreference.saveDataString("user_name", jsonObject1.getString("name"));
                            yourPreference.saveDataString("user_age", jsonObject1.getString("age"));
                            yourPreference.saveDataString("user_gender", jsonObject1.getString("gender"));
                            yourPreference.saveDataString("user_mobile", jsonObject1.getString("mobile"));
                            yourPreference.saveDataInt("user_country", jsonObject1.getInt("country"));
                            yourPreference.saveDataInt("user_city", jsonObject1.getInt("city"));
                            yourPreference.saveDataInt("user_region", jsonObject1.getInt("region"));
                             yourPreference.saveDataboolean("notif_all", true);
                             yourPreference.saveDataboolean("notif_supermarket", true);
                             yourPreference.saveDataboolean("notif_restaurants", true);
                             yourPreference.saveDataboolean("notif_collections", true);
                            Log.i("result", jsonObject.toString() + "===========/n" + output);
                            Intent intent= new Intent(MainActivity.this, PagerActivity.class);
                            intent.putExtra("registerednow", "true");
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).execute();*/
            }
        }catch (Exception e){e.printStackTrace();}
    }
    private void getsAllOfferData(final String url, List<NameValuePair> params)  {
        try {
            final ACProgressPie dialog;
            dialog = new ACProgressPie.Builder(MainActivity.this)
                    .ringColor(Color.WHITE)
                    .pieColor(getResources().getColor(R.color.colorPrimaryDark))
                    .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                    .build();
            dialog.show();
            new LongOperationE(MainActivity.this, url, params, new LongOperationE.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {YourPreference yourPreference= YourPreference.getInstance(MainActivity.this);
                        Log.i("result", output);
                        dialog.dismiss();
                        JSONObject jsonObject1 = new JSONObject(output);
                        yourPreference.saveDataInt("user_id", jsonObject1.getInt("id"));
                        yourPreference.saveDataString("user_name", jsonObject1.getString("name"));
                        yourPreference.saveDataString("user_age", jsonObject1.getString("age"));
                        yourPreference.saveDataString("user_gender", jsonObject1.getString("gender"));
                        yourPreference.saveDataString("user_mobile", jsonObject1.getString("mobile"));
                        yourPreference.saveDataInt("user_country", jsonObject1.getInt("country"));
                        yourPreference.saveDataInt("user_city", jsonObject1.getInt("city"));
                        yourPreference.saveDataInt("user_region", jsonObject1.getInt("region"));
                        yourPreference.saveDataboolean("notif_all", true);
                        yourPreference.saveDataboolean("notif_supermarket", true);
                        yourPreference.saveDataboolean("notif_restaurants", true);
                        yourPreference.saveDataboolean("notif_collections", true);
                        Intent intent= new Intent(MainActivity.this, PagerActivity.class);
                        intent.putExtra("registerednow", "true");
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).execute();
        }catch (Exception e){e.printStackTrace();}


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
