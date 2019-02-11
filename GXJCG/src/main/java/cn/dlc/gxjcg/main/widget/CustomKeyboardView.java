package cn.dlc.guankungongxiangjicunji.main.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.rv_tool.RecyclerViewUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.main.adapter.KeyboardAdapter;
import cn.dlc.guankungongxiangjicunji.main.adapter.KeyboardAdapterHs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyufeng    on  2018/6/21 0021.
 * interface by
 */

public class CustomKeyboardView extends FrameLayout {

    private RecyclerView mRecyclerView;
    private List<String> mData;
    private KeyboardAdapter mKeyboardAdapter;
    private KeyboardAdapterHs mKeyboardAdapterHs;
    private OnClickKeyboardItemListener mListener;

    public CustomKeyboardView(Context context) {
        this(context, null);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_key_board, this);
        initData();
        mRecyclerView = findViewById(R.id.recyclerView);
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            initView();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            initViewHs();
        }else{
            initView();
        }
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

        mKeyboardAdapter = new KeyboardAdapter();

        RecyclerViewUtil.addSpaceByRes(mRecyclerView, gridLayoutManager, R.dimen.normal_1dp, R.dimen.normal_1dp);
        mRecyclerView.setAdapter(mKeyboardAdapter);
        mKeyboardAdapter.setNewData(mData);
        mKeyboardAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseRecyclerAdapter.CommonHolder holder,
                                    int position) {
                String item = mKeyboardAdapter.getItem(position);
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

    private void initViewHs() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mKeyboardAdapterHs = new KeyboardAdapterHs();

        RecyclerViewUtil.addSpaceByRes(mRecyclerView, gridLayoutManager, R.dimen.normal_1dp, R.dimen.normal_1dp);
        mRecyclerView.setAdapter(mKeyboardAdapterHs);
        mKeyboardAdapterHs.setNewData(mData);
        mKeyboardAdapterHs.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseRecyclerAdapter.CommonHolder holder,
                                    int position) {
                String item = mKeyboardAdapterHs.getItem(position);
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

    public interface OnClickKeyboardItemListener {
        void onClickNumber(String num);

        void onClickDelete();

        void onClickConfirm();
    }

    public void setOnClickKeyboardItemListener(OnClickKeyboardItemListener l) {
        mListener = l;
    }
}
