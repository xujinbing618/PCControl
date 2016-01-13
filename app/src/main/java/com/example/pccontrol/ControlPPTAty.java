package com.example.pccontrol;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.pccontrol.constants.Constants;
import com.pccontrol.utils.UdpSender;

import java.io.IOException;

/**
 * Created by 张建浩 on 2015/6/18.
 */
public class ControlPPTAty extends Activity implements View.OnClickListener{
    private static String TAG = ControlPPTAty.class.getName();
    private Button clickUp;
    private Button clickDown;
    private Button startFullScreen;
    private Button exitFullScreen;
    private String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ppt_control);
        clickUp = (Button)findViewById(R.id.click_up);
        clickUp.setOnClickListener(this);
        clickDown = (Button)findViewById(R.id.click_down);
        clickDown.setOnClickListener(this);
        startFullScreen = (Button)findViewById(R.id.start_ppt_fullscreen);
        startFullScreen.setOnClickListener(this);
        exitFullScreen = (Button)findViewById(R.id.exit_ppt_fullscreen);
        exitFullScreen.setOnClickListener(this);

        AppApplication application  = (AppApplication)getApplication();
        ip  = application.getIp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_up:
                Log.v(TAG,"向上");
                sendOrderSyn(ip,Constants.KEY_EVENT_UP);
                break;
            case R.id.click_down:
                Log.v(TAG,"向下");
                sendOrderSyn(ip, Constants.KEY_EVENT_DOWN);
                break;
            case R.id.start_ppt_fullscreen:
               sendOrderSyn(ip,Constants.KEY_EVENT_F5);
                break;
            case R.id.exit_ppt_fullscreen:
                sendOrderSyn(ip,Constants.KEY_EVENT_ESC);
                break;
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                sendOrderSyn(ip,Constants.KEY_EVENT_UP);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                sendOrderSyn(ip,Constants.KEY_EVENT_DOWN);
                break;
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return true;
    }
}
