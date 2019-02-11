package cn.dlc.guankungongxiangjicunji.main.adapter;

import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.R;

/**
 * Created by wuyufeng    on  2018/6/21 0021.
 * interface by
 */

public class KeyboardAdapterHs extends BaseRecyclerAdapter<String> {

  

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_keyboard;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        String item = getItem(position);
        LinearLayout llBg = holder.getView(R.id.ll_bg);
        TextView text = holder.getText(R.id.tv_content);
        text.setText(item);
        if(position == getData().size()-1){
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX,ResUtil.getPx(R.dimen.normal_30sp));
            llBg.setBackgroundResource(R.drawable.selector_main_click);
        }else if(position == getData().size()-3){
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX,ResUtil.getPx(R.dimen.normal_30sp));
            llBg.setBackgroundResource(R.drawable.selector_white_click);
        }else {
            llBg.setBackgroundResource(R.drawable.selector_white_click);
        }
    }
}
