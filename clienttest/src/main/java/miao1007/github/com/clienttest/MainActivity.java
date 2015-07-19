package miao1007.github.com.clienttest;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Random;
import miao1007.github.com.bindersample.LogUtils;
import miao1007.github.com.bindersample.aidl.RSManager;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = LogUtils.makeLogTag(MainActivity.class);

  @Bind(R.id.start) Button mStart;
  @Bind(R.id.stop) Button mStop;
  @Bind(R.id.remote_call_get) Button mRemoteCallGet;
  @Bind(R.id.remote_call_set) Button mRemoteCallSet;
  @Bind(R.id.result) TextView mResult;

  RSManager manager;

  @OnClick(R.id.start) void start_remote_service() {
    manager = new RSManager(this);
  }

  @OnClick(R.id.stop) void stop_remote_service() {
    manager.shutDown();
  }

  @OnClick(R.id.remote_call_get) void remote_call_get() {
    try {
      mResult.setText(manager.get() + "");
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @OnClick(R.id.remote_call_set) void remote_call_set() {
    try {
      manager.set(new Random().nextInt(10));
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(miao1007.github.com.bindersample.R.layout.activity_main);
    ButterKnife.bind(this);
    LogUtils.logPidAndThread(TAG);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    stop_remote_service();
  }
}
