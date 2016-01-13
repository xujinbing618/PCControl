package com.example.pccontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {
    private Button remoteShutdown;
    private Button remotePPT;
    private Button remoteMouse;
    private Button adbWirelessDebug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_menu);
        remoteShutdown = (Button)findViewById(R.id.remote_shutdown_btn);
        remoteShutdown.setOnClickListener(this);
        remotePPT = (Button)findViewById(R.id.remote_ppt_btn);
        remotePPT.setOnClickListener(this);
        remoteMouse = (Button)findViewById(R.id.remote_mouse_btn);
        remoteMouse.setOnClickListener(this);
        adbWirelessDebug = (Button)findViewById(R.id.adb_wireless_debug_btn);
        adbWirelessDebug.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.remote_shutdown_btn:
                intent = new Intent(this,RemoteShutdownAty.class);
                startActivity(intent);
                break;
            case R.id.remote_ppt_btn:
                 intent = new Intent(this,PPTOpenremindAty.class);
                startActivity(intent);
                break;
            case R.id.remote_mouse_btn:
                intent  = new Intent(this,MouseControlAty.class);
                startActivity(intent);
                break;
            case R.id.adb_wireless_debug_btn:
                intent  = new Intent(this,AdbDebugAty.class);
                startActivity(intent);
                break;



        }
    }
}
