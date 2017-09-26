package com.hyphenate.easeui.ui.hx.domain;

import android.app.Application;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojicon.Type;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;

import java.util.Arrays;

public class EmojiconExampleGroupData {
    private static  Application application;
    private static int[] icons = new int[]{
        R.drawable.icon_002_cover,
    };
    
    private static int[] bigIcons = new int[]{
        R.drawable.icon_002,
    };
    
    
    private static final EaseEmojiconGroupEntity DATA = createData();
    
    private static EaseEmojiconGroupEntity createData(){
        EaseEmojiconGroupEntity emojiconGroupEntity = new EaseEmojiconGroupEntity();
        EaseEmojicon[] datas = new EaseEmojicon[icons.length];
        try {
            application = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < icons.length; i++){
            datas[i] = new EaseEmojicon(icons[i], null, Type.BIG_EXPRESSION);
            datas[i].setBigIcon(bigIcons[i]);
            //you can replace this to any you want
            datas[i].setName(application.getString(R.string.emojicon_test_name)+ (i+1));
            datas[i].setIdentityCode("em"+ (1000+i+1));
        }
        emojiconGroupEntity.setEmojiconList(Arrays.asList(datas));
        emojiconGroupEntity.setIcon(R.drawable.ee_2);
        emojiconGroupEntity.setType(Type.BIG_EXPRESSION);
        return emojiconGroupEntity;
    }
    
    
    public static EaseEmojiconGroupEntity getData(){
        return DATA;
    }
}
