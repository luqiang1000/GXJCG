package cn.dlc.guankungongxiangjicunji;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.base.BaseActivity;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.activity.MainActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mode = PrefUtil.getDefault().getString("Type", "");
        if (TextUtils.isEmpty(mode)) {
            shouModeDialog();
        } else {
            if ("FUll".equals(mode) || "NOID".equals(mode)) {
                startActivity(MainActivity.class);
                finish();
            } else if ("HALF".equals(mode)) {
                startActivity(HalfSizeActivty.class);
                finish();
            } else {
                shouModeDialog();
            }
        }

    }

    private void shouModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("请选择模式");
        builder.setPositiveButton("双屏", (dialog, which) -> {
            startActivity(MainActivity.class);
            PrefUtil.getDefault().saveString("Type", "FUll");//双屏
            finish();
        });
        builder.setNegativeButton("半屏", (dialogInterface, i) -> {
            startActivity(HalfSizeActivty.class);
            PrefUtil.getDefault().saveString("Type", "HALF");//半屏
            finish();
        });
        builder.setNeutralButton("无身份证",(dialogInterface, i) -> {
            startActivity(MainActivity.class);
            PrefUtil.getDefault().saveString("Type", "NOID");//无身份证
            finish();
        });
        builder.show();
    }
}
