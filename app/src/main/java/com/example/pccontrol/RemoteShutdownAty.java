package com.example.pccontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pccontrol.constants.Constants;
import com.pccontrol.utils.UdpSender;

import java.io.IOException;

/**
 * Created by 张建浩 on 2015/6/21.
 */
public class RemoteShutdownAty extends Activity {
    private Button shutdown;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shutdown_main);
        AppApplication application  = (AppApplication)getApplication();
        ip  = application.getIp();
        shutdown = (Button)findViewById(R.id.shutdown_btn);
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shutdown.setBackgroundResource(R.drawable.shutdown_btn_style_press);

                sendOrderSyn(ip, Constants.SHUTDOWN_COMMAND);
            }
        });
    }
    public  void sendOrderSyn(final String ip, final int orderCode){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UdpSender.sendOrder(ip, orderCode);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
