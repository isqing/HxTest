package com.wonders.hxtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.hx.hxui.ChatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText et;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.et);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录======
                // 登录到聊天服务器
                if ("".equals(et.getText().toString())){
                    Toast.makeText(MainActivity.this, "对方用户名不能为空", Toast.LENGTH_SHORT);
                }else {
                    EMClient.getInstance().login("q12345", "123", new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            Log.i(TAG, "登录聊天服务器成功！");
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Intent chat = new Intent(MainActivity.this, ChatActivity.class);
                                    chat.putExtra(EaseConstant.EXTRA_USER_ID, et.getText().toString());  //对方账号
//                       chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊式
                                    startActivity(chat);
                                }
                            });
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                        }

                        @Override
                        public void onError(int code, final String message) {
                            Log.i(TAG, "登录聊天服务器失败！");
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    EMClient.getInstance().logout(false);
                                    Toast.makeText(MainActivity.this, "登录聊天服务器失败：" + message, Toast.LENGTH_SHORT);
                                }
                            });
                        }
                    });
                    //==========
                }
            }
        });

    }

}
