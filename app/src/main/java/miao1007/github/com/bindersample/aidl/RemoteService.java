package miao1007.github.com.bindersample.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import miao1007.github.com.bindersample.IRemoteService;
import miao1007.github.com.bindersample.LogUtils;

/**
 * Created by leon on 7/19/15.
 * Only used for override and Manifest , NEVER called in Activity.
 */
public class RemoteService extends Service {

  public static final String TAG = LogUtils.makeLogTag(RemoteService.class);

  private int value = 0;

  //get a proxy of remote service
  private final IRemoteService.Stub mInstance = new IRemoteService.Stub() {
    @Override public int get() throws RemoteException {
      Log.d(TAG, "on get remote value: " + value);
      LogUtils.logPidAndThread(TAG);
      return value;
    }

    @Override public void set(int val) throws RemoteException {
      Log.d(TAG, "on set remote service value: " + value);
      LogUtils.logPidAndThread(TAG);
      value = val;
    }
  };

  @Nullable @Override public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind");
    LogUtils.logPidAndThread(TAG);
    return mInstance;
  }
}
