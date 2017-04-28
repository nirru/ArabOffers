package arab_offers.lue.com.Asyncs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

import arab_offers.lue.com.R;
import arab_offers.lue.com.Utils.WebHandler;
import arab_offers.lue.com.Utils.YourPreference;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;


/**
 * Created by Fujitsu on 10-11-2016.
 */
public class LongOperationE extends AsyncTask<String, Void, String> {
    Context context;
    YourPreference yourPreference;
    private String response="", jsonEntity, url;
    String id="";
    List<NameValuePair> paramx;
    String msg = null;
    Dialog d;
    public AsyncResponse delegate = null;
    ACProgressPie dialog;

    public interface AsyncResponse {
        void processFinish(String output);
    }
    String user_id, type, offset;
    public LongOperationE(Context context, String url, List<NameValuePair> paramx,  AsyncResponse delegate) {
        this.id= id;
        this.url=url;
        this.jsonEntity=jsonEntity;
        this.context=context;
        this.delegate=delegate;
        this.url=url;
        this.paramx=paramx;
        this.user_id=user_id;
        this.type=type;
        this.offset=offset;
    }

    @Override
    protected void onPreExecute() {
        try {
            dialog = new ACProgressPie.Builder(context)
                    .ringColor(Color.WHITE)
                    .pieColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                    .build();
            dialog.show();
        }catch (Exception e){}
    }
    @Override
    protected String doInBackground(String... params){
        String jsonReq;
        JSONObject json = new JSONObject();


        try {

            response = WebHandler.getHttpPutContent(url,paramx);

        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
            return response= null;
        }
//        Log.i("result", response.toString());
        return response==null?"": response;

    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
        try {
            dialog.dismiss();
        }catch (Exception e){e.printStackTrace();}
    }
}

