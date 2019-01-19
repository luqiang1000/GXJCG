package cn.dlc.guankungongxiangjicunji.main.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.commonlibrary.utils.rv_tool.RecyclerViewUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.main.adapter.KeyboardAdapter;
import cn.dlc.guankungongxiangjicunji.main.adapter.KeyboardAdapterHs;

/**
 * Created by wuyufeng    on  2018/6/21 0021.
 * interface by
 */

public class CustomKeyboardViewHs extends FrameLayout {

    private RecyclerView mRecyclerView;
    private List<String> mData;
    private KeyboardAdapterHs mKeyboardAdapter;
    private OnClickKeyboardItemListenerHs mListener;

    public CustomKeyboardViewHs(Context context) {
        this(context, null);
    }

    public CustomKeyboardViewHs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyboardViewHs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_key_board, this);
        mRecyclerView = findViewById(R.id.recyclerView);
        initData();
        initView();
    }


    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i < 9) {
                mData.add(String.valueOf(i + 1));
            } else if (i == 9) {
                mData.add("退格");
            } else if (i == 10) {
                mData.add("0");
            } else {
                mData.add("确认");
            }
        }
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mKeyboardAdapter = new KeyboardAdapterHs();
        RecyclerViewUtil.addSpaceByRes(mRecyclerView, gridLayoutManager, R.dimen.normal_1dp, R.dimen.normal_1dp);
        mRecyclerView.setAdapter(mKeyboardAdapter);
        mKeyboardAdapter.setNewData(mData);
        mKeyboardAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseRecyclerAdapter.CommonHolder holder,
                                    int position) {
                String item = mKeyboardAdapter.getItem(position);
                Log.e("))))))))))))))", position + "===" + (mListener == null));
                if (position < 9 || position == 10) {
                    if (mListener != null) {
                        mListener.onClickNumber(item);
                    }
                } else if (position == 9) {//删除键
                    if (mListener != null) {
                        mListener.onClickDelete();
                    }
                } else {//确认键
                    if (mListener != null) {
                        mListener.onClickConfirm();
                    }
                }
            }
        });
    }

    public interface OnClickKeyboardItemListenerHs {
        void onClickNumber(String num);

        void onClickDelete();

        void onClickConfirm();
    }

    public void setOnClickKeyboardItemListener(OnClickKeyboardItemListenerHs l) {
        this.mListener = l;
        Log.e("))))))))))))))", (mListener == null) + "==");
    }
}
