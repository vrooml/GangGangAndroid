package com.example.huge.fzugang.Utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.RetrofitStuff.DeletePostRequest;
import razerdp.basepopup.BasePopupWindow;

import static com.example.huge.fzugang.MyApplication.getContext;

public class MyPopup extends BasePopupWindow{
    public TextView deleteButton;

    public MyPopup(Context context) {
        super(context);
    }

    // 必须实现，这里返回您的contentView
    // 为了让库更加准确的做出适配，强烈建议使用createPopupById()进行inflate
    @Override
    public View onCreateContentView() {
        View view=createPopupById(R.layout.popup_layout);
        deleteButton=view.findViewById(R.id.delete_item);
        return view;
    }

    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }
}
