package com.yy.dian709login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yy.dian709chat.ChatActivity;

public class MainActivity extends Activity {

    private static final String TAG = "test.websocket.client";
    public static TextView workTime;
    private boolean LOGIN_STATUS = false;
    public static boolean RESPONSE = false; //标示是否有信息从服务器返回
    public static long loginTime;
    private getWorkTime mgetWorkTime = new getWorkTime();
    ClientHandler clientHandler = new ClientHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workTime = (TextView) this.findViewById(R.id.workTime);
        Log.d(TAG, "onCreate");

        /*如果没有已存在的子线程 则在子线程中开启连接服务*/
        if (!ClientHandler.THREAD_STATUS) {
            Log.d(TAG, "start client");
            clientHandler.start();
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "Main Thread id: " + Thread.currentThread().getId());
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*显示普通对话框已经工作时间*/
    public void showNormalDia(final String msg) {
        AlertDialog.Builder normalDia = new AlertDialog.Builder(MainActivity.this);
        normalDia.setIcon(R.id.icon);
        normalDia.setTitle("消息");
        normalDia.setMessage(msg);
        normalDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (msg.equals("登录成功")) {
                    Log.d(TAG, "ready to showTime! the thread id: " + Thread.currentThread().getId());
                    mgetWorkTime.showTime();    //显示工作时间
                    startChat();
                }
            }
        });
        normalDia.create().show();
    }

    /*
    发送输入的name和key
     */
    public void sendMessage(View view) throws InterruptedException {
        String inputMessage = "0";  //发送空字符串会让connection关闭 所以初始化改一下。。。
        EditText TextName = (EditText) findViewById(R.id.TextName);
        String inputName = TextName.getText().toString();

        EditText TextKey = (EditText) findViewById(R.id.TextKey);
        String inputKey = TextKey.getText().toString();

        if (!inputName.equals("") && !inputKey.equals("") && !LOGIN_STATUS) {
            inputMessage = inputName + ":" + inputKey;
            clientHandler.sendMessage(inputMessage);        //发送空字符串会让connection关闭。。
            handleMessage();

            /*可能会显示错误，因为从sendmessage到接到回复有延时，此处可加sleep
            if (clientHandler.getLoginStatus()) {
                showNormalDia("登录成功"); 
            }  //bug 如果第一次登录成功后随便输个东西都会显示*/
        }
    }

    /*
    处理服务器返回的消息
     */
    public void handleMessage() {

        if (clientHandler.getResponseMessage().equals("Login Success") && !LOGIN_STATUS) {
            LOGIN_STATUS = true;
            showNormalDia("登录成功");
            loginTime = System.currentTimeMillis();     //记录登录的时间
        } else if (clientHandler.getResponseMessage().equals("Login Failed")) {
            showNormalDia("登录失败");
        } else if (clientHandler.getResponseMessage().equals("Connection closed")) {
            showNormalDia("连接断开");
        }
    }
    
    /*
    返回登录时间
    */
    public void startChat() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}
