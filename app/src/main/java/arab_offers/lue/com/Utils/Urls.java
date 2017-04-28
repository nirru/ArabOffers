package arab_offers.lue.com.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import arab_offers.lue.com.MainActivity;
import arab_offers.lue.com.R;


/**
 * Created by Fujitsu on 10-11-2016.
 */
public class Urls {
    YourPreference  yourPreference=null;
    public static String IP= "http://85.195.75.115";
    public static String getallOffers="/offers/";
    public static String getallOffer="/offers";
    public static String getAllCountries="http://85.195.75.115/country";
    public static String getAllCities="http://85.195.75.115/city/";
    public static String getAllRegions="http://85.195.75.115/region/";
    public static String register="http://85.195.75.115/users/";
    public static String likes="http://85.195.75.115/like/";
    public static String comments="http://85.195.75.115/comments/";
    public static String settings="http://85.195.75.115/appsettings/";
    public static String settings_notif="http://85.195.75.115/users/settings/";

    public static String getGetallOffers( Context context) {
        YourPreference yourPreference= YourPreference.getInstance(context);
        try {
            if (yourPreference.getData("user_name").equalsIgnoreCase("")) {
                return getallOffer;
            } else {
                return getallOffers;
            }
        }catch (Exception e){e.printStackTrace();
        return  getallOffer;}
    }

    public static void setGetallOffers(String getallOffers) {
        Urls.getallOffers = getallOffers;
    }

    public static void notifystate(Context context, View view, String msg){
        Snackbar snackbar;
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.RED);
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        snackbar.show();
    }
    public static void notifystatenice(Context context,View view, String msg){
        Snackbar snackbar;
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.BLUE);
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        snackbar.show();
    }
    public  static  void ntifyregisternow(final Context context){
        new MaterialDialog.Builder(context)
                .title(R.string.alert).titleColor(context.getResources().getColor(R.color.colorPrimaryDark)).
                content(context.getResources().getString(R.string.notregistered)).contentColor(context.getResources().getColor(R.color.colorPrimary))
                .backgroundColor(context.getResources().getColor(android.R.color.white))
                .positiveText(R.string.registerNow).positiveColor(context.getResources().getColor(android.R.color.holo_green_dark)).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }).negativeText(R.string.cancel).negativeColor(context.getResources().getColor(android.R.color.holo_red_dark)).cancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

            }
        })
                .show();
    }

}
