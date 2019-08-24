package com.example.huge.fzugang.Utils;

import android.content.Context;
import com.example.huge.fzugang.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

public class LoadingdialogUtil{
    public static ZLoadingDialog getZLoadingDialog(Context context){
        ZLoadingDialog dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(Z_TYPE.CIRCLE)//设置类型
                .setLoadingColor(context.getResources().getColor(R.color.qmui_config_color_black))//颜色
                .setHintText("加载中")
                .setCanceledOnTouchOutside(false)
                .show();
        return dialog;
    }
}
