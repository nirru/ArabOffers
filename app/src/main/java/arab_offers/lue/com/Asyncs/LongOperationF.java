package arab_offers.lue.com.Asyncs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.Form;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;

import org.json.JSONObject;

import arab_offers.lue.com.R;
import arab_offers.lue.com.Utils.WebHandler;
import arab_offers.lue.com.Utils.YourPreference;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;


/**
 * Created by Fujitsu on 10-11-2016.
 */
public class LongOperationF extends AsyncTask<String, Void, String> {
    Context context;
    YourPreference yourPreference;
    private String responses="", jsonEntity, url;
    String id="";

    String msg = null;
    Dialog d;
    public AsyncResponse delegate = null;
    ACProgressPie dialog;

    public interface AsyncResponse {
        void processFinish(String output);
    }
    public LongOperationF(Context context, String url, String jsonEntity, AsyncResponse delegate) {
        this.id= id;
        this.url=url;
        this.jsonEntity=jsonEntity;
        this.context=context;
        this.delegate=delegate;
        this.url=url;
    }

    @Override
    protected void onPreExecute() {
        yourPreference=YourPreference.getInstance(context);
        dialog = new ACProgressPie.Builder(context)
                .ringColor(Color.WHITE)
                .pieColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .build();
        dialog.show();
    }
    @Override
    protected String doInBackground(String... params){
        String jsonReq;
        JSONObject json = new JSONObject();


        try {

            Form form = new Form()
                    .add("user_id", yourPreference.getDataInteger("user_id"));

            Request request = Bridge
                    .post(url).body(form)
                    .request();
            Response response = request.response();
            json = response.asJsonObject();
            return  responses=json.toString();
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
            return responses= null;
        }
//        Log.i("result", response.toString());


    }

    @Override
    protected void onPostExecute(String result) {

        delegate.processFinish(result);
        dialog.dismiss();
    }
}

