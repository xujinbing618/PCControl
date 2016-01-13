package com.pccontrol.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 张建浩 on 2015/6/22.
 */
public class SharePrefereceUtils {

    public static void saveString(Context context,String key,String value,String filename){

        SharedPreferences share = context.getApplicationContext().getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context,String filename,String key){

            SharedPreferences share = context.getApplicationContext().getSharedPreferences(filename,Context.MODE_PRIVATE);
            return share.getString(key, null);

    }


}
