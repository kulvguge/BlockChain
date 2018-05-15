package block.com.blockchain.app;

import android.app.Application;

/**
 * Created by ts on 2018/5/15.
 */

public class MyApp extends Application {
    public static MyApp mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
