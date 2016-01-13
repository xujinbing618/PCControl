package com.example.pccontrol;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by 张建浩 on 2015/6/19.
 */
public class MouseControlAty extends Activity {
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mouse_control_main);
        mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        MouseFragment mouseFragment = new MouseFragment();
        transaction.replace(R.id.mouse_control_container,mouseFragment);
        transaction.commit();

    }
}
