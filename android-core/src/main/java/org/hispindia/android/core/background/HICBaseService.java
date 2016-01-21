package org.hispindia.android.core.background;

/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import org.hispindia.android.core.utils.HICUtils;

public abstract class HICBaseService extends Service {

    ServiceReceiver myReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        myReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(HICUtils.SERVICE_RECEIVE);
        registerReceiver(myReceiver, filter);
        onStartCommand(intent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    protected abstract void onReceive(Context context, Intent intent);

    protected abstract void onStartCommand(Intent intent);

    class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            HICBaseService.this.onReceive(context, intent);
        }
    }
}
