package arab_offers.lue.com.Utils;

import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by nikk on 14/4/17.
 */

public class DeviceUtils {

    public static boolean isTablet(Resources res) {
        int screenLayoutMasked = res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenLayoutMasked == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenLayoutMasked == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
