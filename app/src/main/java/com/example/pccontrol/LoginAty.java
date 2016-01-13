package com.example.pccontrol;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pccontrol.constants.Constants;
import com.pccontrol.utils.SharePrefereceUtils;
import com.pccontrol.utils.UdpSender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 张建浩 on 2015/6/22.
 */
public class LoginAty extends Activity {
    private Button loginBtn;
    private EditText ipEt;
    private boolean canLogin = false;
    private Timer timer ;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        tv = (TextView)findViewById(R.id.start_adbdebug_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAty.this,AdbDebugAty.class);
                startActivity(intent);
            }
        });
        loginBtn = (Button)findViewById(R.id.connect_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ipEt.getText())){
                    Toast.makeText(LoginAty.this,"请输入pc端给出的ip地址",Toast.LENGTH_SHORT).show();
                    return;
                }

                String ip = ipEt.getText().toString().trim();
                sendOrderSyn(ip, Constants.CONNECT_REQUEST);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (canLogin == false){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginAty.this, "无法连接，请检查ip是否正确，确保手机与电脑在同一局域网内部", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                },500);


            }
        });
        ipEt = (EditText)findViewById(R.id.pc_ip_et);
        String ipstr ;
         if ((ipstr = SharePrefereceUtils.getString(this,"ip","ip")) != null){
             ipEt.setText(ipstr);
         }
        Thread receive = new Thread(new ReceiveThread());
        receive.start();

    }

    private String getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        // 返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
    }

    public void sendOrderSyn(final String ip, final int orderCode){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  String clientip = getLocalIpAddress();
                    UdpSender.sendOrder(ip,orderCode,clientip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private class ReceiveThread implements Runnable{
        private DatagramSocket socket;

        public ReceiveThread() {
            try {
                this.socket = new DatagramSocket(10010);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            byte [] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
          while(true) {
              socket.receive(packet);
              String string = new String(packet.getData(), 0, packet.getLength(), "utf-8");
              int responseCode = Integer.parseInt(string);

              if (canLogin == false&&responseCode == 200) {
                  canLogin = true;
                  socket.close();
                  packet = null;
                  String ip = ipEt.getText().toString().trim();
                  SharePrefereceUtils.saveString(LoginAty.this,"ip",ip,"ip");
                  AppApplication application = (AppApplication) getApplication();
                  application.setIp(ip);
                  Intent intent = new Intent(LoginAty.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              }
          }



            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            finally {
                socket.close();
                packet = null;
            }
        }
    }
}
