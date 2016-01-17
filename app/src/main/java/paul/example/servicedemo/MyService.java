package paul.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyService extends Service {
    private final String TAG = "Service";
    private int count = 0;
    private boolean threadDisable = false;

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        new Thread(new Runnable() {
            // @Override
            public void run() {
                while (!threadDisable) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    count++;
                    System.out.println("CountService Count is " + count);
                }
            }
        }).start();


        super.onCreate();
    }

    public int getCount() {
        return count;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        this.threadDisable = true;
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        this.threadDisable = true;
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onstartCommand: " + startId);
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.i(TAG, "onbindService");
        return super.bindService(service, conn, flags);
    }
}
