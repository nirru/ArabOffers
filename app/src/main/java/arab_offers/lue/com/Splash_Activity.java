package arab_offers.lue.com;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import java.util.Locale;

import arab_offers.lue.com.Utils.YourPreference;


public class Splash_Activity extends Activity {
    public boolean isActive = false;
    Animation zoomin;
    ImageView mainsplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Locale locale = new Locale("us"); //US English Locale
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        zoomin = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoomin);
        mainsplash=(ImageView)findViewById(R.id.mainsplash);
        mainsplash.startAnimation(zoomin);
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                YourPreference yourPreference=YourPreference.getInstance(Splash_Activity.this);
                boolean login=yourPreference.getDataboolean("session");
                if (login){
                    Intent intent = new Intent(Splash_Activity.this, PagerActivity.class);
                    startActivity(intent);
                    finish();
                }else
                {
                    Intent intent=new Intent(Splash_Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, secondsDelayed * 6000);
    }
}





