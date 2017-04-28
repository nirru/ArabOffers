package arab_offers.lue.com.Utils;

import android.content.Context;

/**
 * Created by Fujitsu on 09-01-2017.
 */
public class Constants {
    Context context;
    int user_id;
    YourPreference yourPreference=null;
    public Constants(Context context) {
        yourPreference=YourPreference.getInstance(context);
        this.context=context;
        setUser_id();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id() {
        this.user_id = yourPreference.getDataInteger("user_id");
    }
}
