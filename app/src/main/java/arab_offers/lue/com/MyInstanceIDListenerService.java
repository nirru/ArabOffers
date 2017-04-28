package arab_offers.lue.com;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import arab_offers.lue.com.Utils.YourPreference;

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {
    YourPreference yourPreference;

    private static final String TAG = "MyAndroidFCMIIDService";

    @Override
    public void onTokenRefresh() {
        yourPreference= YourPreference.getInstance(getApplicationContext());
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        if (refreshedToken!=null)
        sendRegistrationToServer(refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        Log.i("fcmtoken", token);
        yourPreference.saveDataString("fcmtoken", token);
        //Implement this method if you want to store the token on your server
    }
}