package arab_offers.lue.com;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import arab_offers.lue.com.Asyncs.LongOperationE;
import arab_offers.lue.com.Utils.Urls;
import arab_offers.lue.com.Utils.YourPreference;

/**
 * Created by Fujitsu on 03-01-2017.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    YourPreference yourPreference=null;
    AppCompatTextView toolbar_title, wasapp, contactus;
    SwitchCompat radio1, radio2, radio3, radio4;
    LinearLayout linear1, linear2, linear3, linear4;
    RadioGroup group1, group2, group3, group4;
    AppCompatTextView text1, text2, text3, text4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        yourPreference=YourPreference.getInstance(SettingActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title=(AppCompatTextView)toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.setting));
        registerUI();
        registerFunc();
//        yourPreference.saveDataboolean("notif_all", true);
//        yourPreference.saveDataboolean("notif_supermarket", true);
//        yourPreference.saveDataboolean("notif_restaurants", true);
//        yourPreference.saveDataboolean("notif_collections", true);
        try{
            if(yourPreference.getDataboolean("notif_all")){

                radio1.setChecked(true);
                text1.setText(getResources().getString(R.string.allnotoff));
            }else{

                radio1.setChecked(false);
                text1.setText(getResources().getString(R.string.allnoton));

            }
            if(yourPreference.getDataboolean("notif_supermarket")){

                radio2.setChecked(true);
                text2.setText(getResources().getString(R.string.supernotoff));
            }else{
                radio2.setChecked(false);
                text2.setText(getResources().getString(R.string.supernoton));

            }
            if(yourPreference.getDataboolean("notif_restaurants")){
                radio3.setChecked(true);
                text3.setText(getResources().getString(R.string.resnotoff));
            }else{
                radio3.setChecked(false);
                text3.setText(getResources().getString(R.string.resnoton));

            }
            if(yourPreference.getDataboolean("notif_collections")){
                radio4.setChecked(true);
                text4.setText(getResources().getString(R.string.collnotoff));

            }else{
                radio4.setChecked(false);
                text4.setText(getResources().getString(R.string.collnoton));
            }
        }catch (Exception e){e.printStackTrace();}

    }
    private void registerUI(){

        radio1=(SwitchCompat) findViewById(R.id.radio1);
        radio2=(SwitchCompat) findViewById(R.id.radio2);
        radio3=(SwitchCompat) findViewById(R.id.radio3);
        radio4=(SwitchCompat) findViewById(R.id.radio4);
        linear1=(LinearLayout)findViewById(R.id.linear1);
        linear2=(LinearLayout)findViewById(R.id.linear2);
        linear3=(LinearLayout)findViewById(R.id.linear3);
        linear4=(LinearLayout)findViewById(R.id.linear4);
        text1=(AppCompatTextView)findViewById(R.id.text1);
        text2=(AppCompatTextView)findViewById(R.id.text2);
        text3=(AppCompatTextView)findViewById(R.id.text3);
        text4=(AppCompatTextView)findViewById(R.id.text4);
        wasapp=(AppCompatTextView)findViewById(R.id.wasapp);
        contactus=(AppCompatTextView)findViewById(R.id.contactus);

    }
    private void registerFunc(){
        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        linear3.setOnClickListener(this);
        linear4.setOnClickListener(this);
        wasapp.setOnClickListener(this);
        contactus.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();

    }




    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.linear1:
                if(radio1.isChecked()){
                    text1.setText(getResources().getString(R.string.allnoton));
                }else{
                    text1.setText(getResources().getString(R.string.allnotoff));
                }
                radio1.setChecked(!radio1.isChecked());
                List<NameValuePair> nameValuePairs= new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("key", "notif_all"));
                nameValuePairs.add(new BasicNameValuePair("value", radio1.isChecked()?"1":"0"));
                Log.i("output", ""+(radio1.isChecked()?"1":"0"));
                getsAllOfferData(Urls.settings_notif+""+yourPreference.getDataInteger("user_id")+"/", nameValuePairs, "notif_all", radio1.isChecked()?1:0);
                break;
            case R.id.linear2:
                if(radio2.isChecked()){
                    text2.setText(getResources().getString(R.string.supernoton));
                }else{
                    text2.setText(getResources().getString(R.string.supernotoff));
                }
                radio2.setChecked(!radio2.isChecked());
                List<NameValuePair> nameValuePairs1= new ArrayList<NameValuePair>();
                nameValuePairs1.add(new BasicNameValuePair("key", "notif_supermarket"));
                nameValuePairs1.add(new BasicNameValuePair("value", radio2.isChecked()?"1":"0"));
                Log.i("output", ""+(radio2.isChecked()?"1":"0"));
                getsAllOfferData(Urls.settings_notif+yourPreference.getDataInteger("user_id")+"/", nameValuePairs1, "notif_supermarket", radio2.isChecked()?1:0);
                break;
            case R.id.linear3:
                if(radio3.isChecked()){
                    text3.setText(getResources().getString(R.string.resnoton));
                }else{
                    text3.setText(getResources().getString(R.string.resnotoff));
                }
                radio3.setChecked(!radio3.isChecked());
                List<NameValuePair> nameValuePairs2= new ArrayList<NameValuePair>();
                nameValuePairs2.add(new BasicNameValuePair("key", "notif_restaurants"));
                nameValuePairs2.add(new BasicNameValuePair("value", radio3.isChecked()?"1":"0"));
                Log.i("output", ""+(radio3.isChecked()?"1":"0"));
                getsAllOfferData(Urls.settings_notif+""+yourPreference.getDataInteger("user_id")+"/", nameValuePairs2, "notif_restaurants", radio3.isChecked()?1:0);
                break;
            case R.id.linear4:
                if(radio4.isChecked()){
                    text4.setText(getResources().getString(R.string.collnoton));
                }else{
                    text4.setText(getResources().getString(R.string.collnotoff));
                }
                radio4.setChecked(!radio4.isChecked());
                List<NameValuePair> nameValuePairs3= new ArrayList<NameValuePair>();
                nameValuePairs3.add(new BasicNameValuePair("key", "notif_collections"));
                nameValuePairs3.add(new BasicNameValuePair("value", radio4.isChecked()?"1":"0"));
                Log.i("output", ""+(radio4.isChecked()?"1":"0"));
                getsAllOfferData(Urls.settings_notif+yourPreference.getDataInteger("user_id")+"/", nameValuePairs3, "notif_collections",radio4.isChecked()?1:0 );
                break;
            case R.id.wasapp:
                onClickWhatsApp();
                break;
            case R.id.contactus:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{yourPreference.getData("admin_email")});
                i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                i.putExtra(Intent.EXTRA_TEXT   , yourPreference.getData("share_message"));
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SettingActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void getsAllOfferData(final String url, List<NameValuePair> params, final String pref, final int check)  {

        try {
            new LongOperationE(SettingActivity.this, url, params, new LongOperationE.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    boolean ss;
                    try {
                     Log.i("check", check+"");
                        ss=check==1?true:false;
                     Log.i("output", output);
                        if(output.equalsIgnoreCase("true")) {

                            yourPreference.saveDataboolean(pref,ss);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).execute();
        }catch (Exception e){e.printStackTrace();}


    }
    public void onClickWhatsApp() {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = yourPreference.getData("share_message");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, getResources().getString(R.string.share_using)));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
