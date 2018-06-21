package sohama.com.bidorbuysearchengine.networkmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import sohama.com.bidorbuysearchengine.singleton.BaseGenericModel;
import sohama.com.bidorbuysearchengine.singleton.SharedClass;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private SharedClass sharedClass;
    private BaseGenericModel baseGenericModel;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        try {
            sharedClass = SharedClass.getInstance(context);
            baseGenericModel = BaseGenericModel.getInstance();
            int status = NetworkUtil.getConnectivityStatus(context);

            if (status == NetworkUtil.TYPE_NOT_CONNECTED) {
                sharedClass.setOffline(true);
                baseGenericModel.manageOfflineTextView();
            }

            if ((status == NetworkUtil.TYPE_WIFI || status == NetworkUtil.TYPE_MOBILE)) {
                //Service may not be available (flag not set) at the time WiFi is just switched on
                sharedClass.setOffline(false);
                baseGenericModel.manageOfflineTextView();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
