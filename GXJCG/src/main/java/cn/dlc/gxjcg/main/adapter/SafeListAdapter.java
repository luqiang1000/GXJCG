package cn.dlc.guankungongxiangjicunji.main.adapter;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.main.bean.SafeBean;


/**
 * 页面:wuyufeng  on  2018/3/30 0030.
 * 对接口:
 * 作用:
 */

public class SafeListAdapter extends BaseRecyclerAdapter<SafeBean> {

    
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_safe;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        SafeBean item = getItem(position);
        holder.getText(R.id.tv_content).setText(position+1+"."+item.content);
        
    }
}
