package com.wonders.hxtest;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.hx.DemoHelper;

/**
 * Created by liyaqing on 2017/9/26.
 */

public class MyApplication extends Application {
    public static MyApplication applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
//初始化
        EMClient.getInstance().init(applicationContext, options);

//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(applicationContext,options);
        DemoHelper.getInstance().init(applicationContext);
    }
}
