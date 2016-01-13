package com.example.pccontrol;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pccontrol.constants.Constants;
import com.pccontrol.utils.UdpSender;
import com.pccontrol.view.MyRelativeLayout;
import com.pccontrol.view.OnSizeChangedListener;

import java.io.IOException;

/**
 * Created by 张建浩 on 2015/6/20.
 */
public class KeyBoardInputAty extends Activity implements View.OnClickListener{
    private static String TAG = KeyBoardInputAty.class.getName();
    private ImageView keyboardImg;
    private Button pcUp;
    private Button pcDown;
    private Button pcLeft;
    private Button pcRight;
    private Button pcEnter;
    private Button pcEsc;
    private Button pcBackspace;
    private Button pcDelete;
    private String ip;
    private MyRelativeLayout topLayout;
    private EditText inputet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard_control_fragment);
        topLayout = (MyRelativeLayout)findViewById(R.id.keyboard_top_layout);
        topLayout.setOnSizeChangedListener(new OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                Log.v(TAG, w + "," + h + "," + oldw + ", " + oldh);
                if (oldh > 0 && oldh < h)
                    finish();
            }
        });
        keyboardImg = (ImageView)findViewById(R.id.back_mouse_control);
        keyboardImg.setOnClickListener(this);
        pcUp = (Button)findViewById(R.id.pc_up);
        pcUp.setOnClickListener(this);
        pcDown = (Button)findViewById(R.id.pc_down);
        pcDown.setOnClickListener(this);
        pcLeft = (Button)findViewById(R.id.pc_left);
        pcLeft.setOnClickListener(this);
        pcRight = (Button)findViewById(R.id.pc_right);
        pcRight.setOnClickListener(this);
        pcEnter = (Button)findViewById(R.id.pc_enter);
        pcEnter.setOnClickListener(this);
        pcEsc = (Button)findViewById(R.id.pc_esc);
        pcEsc.setOnClickListener(this);
        pcBackspace = (Button)findViewById(R.id.pc_backspace);
        pcBackspace.setOnClickListener(this);
        pcDelete = (Button)findViewById(R.id.pc_delete);
        pcDelete.setOnClickListener(this);
        inputet = (EditText)findViewById(R.id.hide_et);
        inputet.addTextChangedListener(new TextWatcher() {
            private String oldstr;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldstr = s.toString();
                System.out.println("before:"+oldstr);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newstring = s.toString().replace(oldstr,"");
                System.out.println(newstring + "count=" + count);
                if (count>0&&(newstring != null||newstring.length()>0))
                sendOrderSyn(ip,Constants.INPUT_EVENT,newstring);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        AppApplication application  = (AppApplication)getApplication();
        ip  = application.getIp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DEL:
                Log.v(TAG,"del cilck");
                sendOrderSyn(ip,Constants.KEY_EVENT_BACKSPACE);
                break;
            case KeyEvent.KEYCODE_ENTER:
                Log.v(TAG,"enter click");
                break;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pc_up:
                sendOrderSyn(ip, Constants.KEY_EVENT_UP);
                break;
            case R.id.pc_down:
                sendOrderSyn(ip, Constants.KEY_EVENT_DOWN);
                break;
            case R.id.pc_left:
                sendOrderSyn(ip, Constants.KEY_EVENT_LEFT);
                break;
            case R.id.pc_right:
                sendOrderSyn(ip, Constants.KEY_EVENT_RIGHT);
                break;
            case R.id.pc_enter:
                sendOrderSyn(ip, Constants.KEY_EVENT_ENTER);
                break;
            case R.id.pc_esc:
                sendOrderSyn(ip, Constants.KEY_EVENT_ESC);
                break;
            case R.id.pc_backspace:
                sendOrderSyn(ip, Constants.KEY_EVENT_BACKSPACE);
                break;
            case R.id.pc_delete:
                sendOrderSyn(ip, Constants.KEY_EVENT_DELETE);
                break;
            case R.id.back_mouse_control:
                  finish();
                break;

        }
    }
    public void sendOrderSyn(final String ip, final int orderCode){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UdpSender.sendOrder(ip,orderCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void sendOrderSyn(final String ip, final int orderCode, final String info){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    UdpSender.sendOrder(ip,orderCode,info);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
