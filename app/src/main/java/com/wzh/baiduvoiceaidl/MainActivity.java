package com.wzh.baiduvoiceaidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wzh.aidl.IVoiceInterface;

public class MainActivity extends AppCompatActivity {
    private EditText input ;
    private Button button ;
    /**
     * 创建远程服务
     */
    private IVoiceInterface iVoiceInterface;
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iVoiceInterface = IVoiceInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iVoiceInterface = null ;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WaveViewV1 waveView = (WaveViewV1) findViewById(R.id.wave);
        waveView.setRunning();

        /**
         * 绑定、启动所谓的服务端 服务
         */
        final Intent intent = new Intent();
        intent.setAction("android.intent.action.VoiceService");
        intent.setPackage("com.wzh.baiduvoice");
        bindService(intent,conn, Service.BIND_AUTO_CREATE);

        input = (EditText) findViewById(R.id.input);
        button = (Button) findViewById(R.id.speak);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();
                if (iVoiceInterface!=null){
                    try {
                        iVoiceInterface.speak(text);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
