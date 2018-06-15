package block.com.blockchain.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import block.com.blockchain.utils.IsNetwork;

/**
 * Created by ts on 2017/10/12.
 */

public class NetBroadCastReceiver extends BroadcastReceiver {
    public long time = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = IsNetwork.getNetWorkState(context);
            // 接口回调传过去状态的类型
            if (System.currentTimeMillis() - time > 500) {
                time = System.currentTimeMillis();
                Intent mIntent = new Intent("com.blockchain.net");
                mIntent.putExtra("netWorkState", netWorkState);
                context.sendBroadcast(mIntent);
            }

        }
    }
}
