package arab_offers.lue.com;

/**
 * Created by Fujitsu on 02-01-2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAdView;

import org.json.JSONObject;

import arab_offers.lue.com.Asyncs.LongOperationD;
import arab_offers.lue.com.Fragments.Tab1;
import arab_offers.lue.com.Fragments.Tab2;
import arab_offers.lue.com.Fragments.Tab3;
import arab_offers.lue.com.Interfaces.Search;
import arab_offers.lue.com.Utils.ConnectionDetector;
import arab_offers.lue.com.Utils.Urls;
import arab_offers.lue.com.Utils.YourPreference;

//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views
public class PagerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
AppCompatTextView appCompatTextView;
    //This is our tablayout
    private TabLayout tabLayout;
    private AppCompatImageView setting;
    //This is our viewPager
    Pager adapter;
    private ViewPager viewPager;
    public  SearchView searchView;
    LinearLayout mainLayout;

    String[] str;
    String[] stH;
    AppCompatImageView noconnection;
    ConnectionDetector cd;
   YourPreference yourPreference=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        str= getResources().getStringArray(R.array.collection);
        stH= getResources().getStringArray(R.array.collection);
        yourPreference=YourPreference.getInstance(PagerActivity.this);
        yourPreference.saveDataboolean("session", true);

        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noconnection= (AppCompatImageView)findViewById(R.id.noconnection);

         cd = new ConnectionDetector(this);
        final Handler handler= new Handler();


        //Initializing the tablayout
        mainLayout=(LinearLayout)findViewById(R.id.main_layout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //searchView=(SearchView)findViewById(R.id.searchView);
        setting=(AppCompatImageView)findViewById(R.id.setting);
        appCompatTextView= new AppCompatTextView(PagerActivity.this);
        appCompatTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        appCompatTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_new_4));
        appCompatTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
        appCompatTextView.setTextSize(12);
        appCompatTextView.setPadding(10, 10, 10, 10);
        appCompatTextView.setSingleLine();
        appCompatTextView.setTypeface(null, Typeface.BOLD);
        //Adding the tabs using addTab() method

        tabLayout.addTab(tabLayout.newTab().setText(str[0]));
        tabLayout.addTab(tabLayout.newTab().setText(str[1]));
        tabLayout.addTab(tabLayout.newTab().setText(str[2]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
         adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);

       /* int position= viewPager.getCurrentItem();
        Fragment fr = adapter.getItem(position);

        if(fr instanceof Tab1)
        {
            Tab1 fr1= (Tab1)adapter.getItem(position);
            fr1.searchable();
        }else if(fr instanceof Tab2)
        {
            Tab2 fr1= (Tab2)adapter.getItem(position);
            fr1.searchable();
        }
        else if(fr instanceof Tab3)
        {
            Tab3 fr1= (Tab3)adapter.getItem(position);
            fr1.searchable();
        }*/
        //Adding onTabSelectedListener to swipe views
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean nettest = cd.isConnectingToInternet();
                if(!nettest){
                    noconnection.setVisibility(View.VISIBLE);
                }else{
                    noconnection.setVisibility(View.GONE);
                }
                handler.postDelayed(this, 2000);
            }
        }).start();
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (yourPreference.getData("user_name").equalsIgnoreCase("")) {
                        Urls.ntifyregisternow(PagerActivity.this);
                    } else {
                        startActivity(new Intent(PagerActivity.this, SettingActivity.class));
                    }
                }catch (Exception e){e.printStackTrace();
                    Urls.ntifyregisternow(PagerActivity.this);}

            }
        });
        /*try{
            if(getIntent().getStringExtra("registerednow").equalsIgnoreCase("true"))
                //Urls.notifystatenice(PagerActivity.this, mainLayout, getResources().getString(R.string.registerednow) );
        }catch (Exception e){e.printStackTrace();}*/

        new LongOperationD(PagerActivity.this, Urls.settings, "", new LongOperationD.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try{
                    YourPreference yourPreference= YourPreference.getInstance(getApplicationContext());
                    JSONObject jsonObject= new JSONObject(output);
                    yourPreference.saveDataString("admob", jsonObject.getString("admob_android"));
                    yourPreference.saveDataString("admin_email", jsonObject.getString("admin_email"));
                    yourPreference.saveDataString("share_message", jsonObject.getString("share_message"));
                }catch (Exception e){e.printStackTrace();}
            }
        }).execute();


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.i("clicked", "clicked");
//                return false;
//            }
//        });


    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        appCompatTextView.setText(stH[tab.getPosition()]);
        tabLayout.getTabAt(tab.getPosition()).setCustomView(appCompatTextView);
        if(tab.getPosition()==0){
            tabLayout.getTabAt(1).setCustomView(null);
            tabLayout.getTabAt(2).setCustomView(null);
            tabLayout.getTabAt(1).setText(str[1]);
            tabLayout.getTabAt(2).setText(str[2]);
        }else if(tab.getPosition()==1){
            tabLayout.getTabAt(0).setCustomView(null);
            tabLayout.getTabAt(2).setCustomView(null);
            tabLayout.getTabAt(0).setText(str[0]);
            tabLayout.getTabAt(2).setText(str[2]);
        }else if(tab.getPosition()==2){
            tabLayout.getTabAt(1).setCustomView(null);
            tabLayout.getTabAt(0).setCustomView(null);
            tabLayout.getTabAt(1).setText(str[1]);
            tabLayout.getTabAt(0).setText(str[0]);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
