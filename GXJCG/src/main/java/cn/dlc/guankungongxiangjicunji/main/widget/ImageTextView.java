package cn.dlc.guankungongxiangjicunji.main.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dlc.guankungongxiangjicunji.R;

/**
 * Created by wuyufeng    on  2018/6/22 0022.
 * interface by
 */

public class ImageTextView extends LinearLayout {
    public ImageTextView(@NonNull Context context) {
        this(context,null);
    }

    public ImageTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_image_text, this, true);
        ImageView img = view.findViewById(R.id.iv_img);
        TextView tvOrder = view.findViewById(R.id.tv_order);
        TextView tvContent = view.findViewById(R.id.tv_content);

        int defaultContentColor = ContextCompat.getColor(context, R.color.item_content_color);
        float defaultTextSize = getResources().getDimension(R.dimen.normal_28sp);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);

        // 图片
        int labelImageRes = ta.getResourceId(R.styleable.ImageTextView_image_text_view_image, 0);
        if (labelImageRes != 0) {
            img.setImageResource(labelImageRes);
        }

        // 文本
        String text;
        text = ta.getString(R.styleable.ImageTextView_image_text_view_order);
        tvOrder.setText(text);
        text = ta.getString(R.styleable.ImageTextView_image_text_view_content);
        tvContent.setText(text);
        
        ta.recycle();

    }
}
