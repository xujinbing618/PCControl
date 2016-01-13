package com.example.pccontrol;

import android.app.Application;

/**
 * Created by 张建浩 on 2015/6/19.
 */
public class AppApplication extends Application {
    private String ip;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
