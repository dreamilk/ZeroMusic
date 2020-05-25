package com.shanghq.zeromusic.views;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.shanghq.zeromusic.R;

public class MusicInformationDialog extends Dialog {

    private Context mContext;


    public MusicInformationDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;


        setContentView(R.layout.item_music_information);

    }


    @Override
    public void show() {
        super.show();

//        Window window=getWindow();
//        WindowManager.LayoutParams layoutParams=window.getAttributes();
//
//        Log.d("test","width:"+layoutParams.width+"  height:"+layoutParams.height);
//        int with=layoutParams.width;
////        layoutParams.width=(int)(with *0.9);
////        layoutParams.height= (int) (layoutParams.width*1.5);
////        getWindow().setAttributes(layoutParams);




    }

}
