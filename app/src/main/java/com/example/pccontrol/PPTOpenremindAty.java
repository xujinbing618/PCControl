package com.example.pccontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 张建浩 on 2015/6/18.
 */
public class PPTOpenremindAty extends ActionBarActivity{
    private Button startControlPPT;
    private Button upBtn;
    private Button downBtn;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_ppt_remind);
        mFragmentManager = getSupportFragmentManager();
        startControlPPT = (Button)findViewById(R.id.start_control_ppt);
        startControlPPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PPTOpenremindAty.this,ControlPPTAty.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
