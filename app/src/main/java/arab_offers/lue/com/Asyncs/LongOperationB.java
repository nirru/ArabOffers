package arab_offers.lue.com.Asyncs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import arab_offers.lue.com.Utils.Urls;
import arab_offers.lue.com.Utils.WebHandler;
import arab_offers.lue.com.Utils.YourPreference;


/**
 * Created by Fujitsu on 27-09-2016.
 */


    public class LongOperationB extends AsyncTask<String, Void, String> {
    Activity context;
    // String url = "http://192.168.1.185:80/location/register.php";
    String url = Urls.IP+Urls.getallOffers;
    YourPreference yourPreference;
    private String response="", name, pass, mail, phone;
    String id="";
    private ProgressDialog dialog;
    String msg = null;
    Dialog d;

    public LongOperationB(Activity context,String id, String url, String name, String pass, String mail, String phone) {
        this.id= id;
        this.url=url;
        this.name=name;
        this.pass=pass;
        this.mail=mail;
        this.phone=phone;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {


    }
    @Override
    protected String doInBackground(String... params){
        String jsonReq;
        JSONObject json = new JSONObject();

        try {/*urlParameters =
                "user_name=" + URLEncoder.encode(name, "UTF-8") +
                        "&password=" + URLEncoder.encode(pass, "UTF-8")+
                        "&email=" + URLEncoder.encode(mail, "UTF-8")+
                        "&phone=" + URLEncoder.encode(phone, "UTF-8");*/
            json.accumulate("user_name",name);
            json.accumulate("password", pass);
            json.accumulate("email",mail);
            json.accumulate("phone",phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonReq = json.toString();
        Log.i("result", jsonReq.toString());
        try {

            response = WebHandler.callByPost(jsonReq,url);

        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
            return response= null;
        }
        Log.i("result", response.toString());
        return response;
    }

    @Override
    protected void onPostExecute(String result) {

        yourPreference=YourPreference.getInstance(context);
        try {

        }catch (Exception e){e.printStackTrace();}
    }
}
