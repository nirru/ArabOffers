package arab_offers.lue.com;

import android.app.Application;

/**
 * Created by Fujitsu on 04-01-2017.
 */
public class Arab_offer extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                //Catch your exception
                // Without System.exit() this will not work.
                System.exit(2);
            }
        });
    }

}
