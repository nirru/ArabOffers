package arab_offers.lue.com.Asyncs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import arab_offers.lue.com.Utils.WebHandler;
import arab_offers.lue.com.Utils.YourPreference;


/**
 * Created by Fujitsu on 10-11-2016.
 */
public class LongOperationD extends AsyncTask<String, Void, String> {
    Activity context;
    YourPreference yourPreference;
    private String response="", jsonEntity, url;
    String id="";
    private ProgressDialog dialog;
    String msg = null;
    Dialog d;
    public AsyncResponse delegate = null;


    public interface AsyncResponse {
        void processFinish(String output);
    }
    public LongOperationD(Activity context, String url, String jsonEntity, AsyncResponse delegate) {
        this.id= id;
        this.url=url;
        this.jsonEntity=jsonEntity;
        this.context=context;
        this.delegate=delegate;
        this.url=url;
    }

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... params){
        String jsonReq;
        JSONObject json = new JSONObject();


        try {

            response = WebHandler.callByGet(url);

        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
            return response= null;
        }
      //Log.i("result", response.toString());
        return response==null?"": response;

    }

    @Override
    protected void onPostExecute(String result) {

        delegate.processFinish(result);

    }
}

