package cn.dlc.guankungongxiangjicunji.main.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.dlc.guankungongxiangjicunji.WelcomeActivity;
import cn.dlc.guankungongxiangjicunji.main.activity.MainActivity;

/**
 * Created by Administrator on 2018/7/27.
 */


public class SlptClientInstalledReceiver extends BroadcastReceiver {
    String TAG = "slptClient";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                        /* 应用开机自启动 */
            Intent intent_n = new Intent(context,
                    WelcomeActivity.class);

            intent_n.setAction("android.intent.action.MAIN");
            intent_n.addCategory("android.intent.category.LAUNCHER");
            intent_n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent_n);
        }
    }
}

