package cn.dlc.guankungongxiangjicunji.main.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import cn.dlc.commonlibrary.utils.DialogUtil;
import cn.dlc.guankungongxiangjicunji.R;

/**
 * Created by wuyufeng on 2018/3/14.
 */

public class GetQrCodeDialog extends Dialog {
    
    /**
     * @param context
     */
    public GetQrCodeDialog(@NonNull Context context) {
        super(context, R.style.CommonDialogStyle);
        setContentView(R.layout.dialog_get_qr_code);
        DialogUtil.adjustDialogLayout(this, false, false);
        DialogUtil.setGravity(this, Gravity.CENTER);
        setCanceledOnTouchOutside(false);
    }
    
    
}
