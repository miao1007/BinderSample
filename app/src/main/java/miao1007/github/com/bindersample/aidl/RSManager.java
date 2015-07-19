package miao1007.github.com.bindersample.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import miao1007.github.com.bindersample.IRemoteService;

/**
 * Created by leon on 7/19/15.
 * A RemoteServiceManager proxy for { @link RemoteService}
 */
public class RSManager {

  IRemoteService iRemoteService;
  ServiceConnection connection;
  Context context;

  public RSManager(Context context) {
    connection = new ServiceConnection() {
      @Override public void onServiceConnected(ComponentName name, IBinder service) {
        iRemoteService = IRemoteService.Stub.asInterface(service);
      }

      @Override public void onServiceDisconnected(ComponentName name) {
        iRemoteService = null;
      }
    };
    this.context = context;
    Intent intent = new Intent(context, RemoteService.class);
    context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
  }

  public int get() throws RemoteException {
    checkConnection();
    return iRemoteService.get();
  }

  public void set(int val) throws RemoteException {
    checkConnection();
    iRemoteService.set(val);
  }

  private void checkConnection() {
    if (iRemoteService == null) {
      throw new IllegalStateException("Service lost connection!");
    }
  }

  public void shutDown() {
    try {
      this.context.unbindService(this.connection);
    } catch (IllegalArgumentException donoting) {
      //do noting
    }
    this.iRemoteService = null;
  }
}
