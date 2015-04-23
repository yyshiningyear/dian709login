package com.yy.dian709login;

import android.util.Log;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by Administrator on 2015/4/18.
 */
public class ClientHandler extends Thread {

    private String responseMessage = "0";   //防止null 测试
    public static boolean THREAD_STATUS = false;
    private static final String TAG = "test.websocket.client";
    //private static final String hosturi = "ws://192.168.9.57:8887";
    private static final String hosturi = "ws://192.168.9.222:8887";
    private static final WebSocketConnection mConnection = new WebSocketConnection();

    @Override
    public void run() {
        Log.v(TAG, "Thread begin run");
        startConnection();
        try {
            Thread.sleep(3000);     //等待连接成功再发送心跳
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendHeartbeat();
        THREAD_STATUS = Thread.currentThread().isAlive();
    }

    /*
    向服务器发送消息
     */
    public void sendMessage(String message) {
        if ( ( mConnection.isConnected() )) {
            Log.d(TAG,"sending: " + message);
            mConnection.sendTextMessage(message);
        }
    }

    /**建立连接
     * 处理open与close动作
     * 处理服务器传过来的数据*/
    private void startConnection() {
        Log.d(TAG, "startConnection to " + hosturi);
        Log.d(TAG,"the connection thread id: " + Thread.currentThread().getId());

        try {
            mConnection.connect(hosturi, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + hosturi);

                }

                /*如果是第一次登录成功则显示消息*/
                @Override
                public void onTextMessage(String payload) {

                    Log.d(TAG, "Got echo: " + payload);
                    responseMessage = payload;
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connection lost." + reason);
                    responseMessage = "Connection closed";
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    /*
    *在子线程中向服务器发送心跳
    */
    public void sendHeartbeat() {

        Log.v(TAG, "sendHeartBeat");

        new Thread(new Runnable() {
            @Override
            public void run() {
                //连接时一直发送心跳
                while (mConnection.isConnected()) {
                    Log.d(TAG, "sending heartbeat threadID: " + Thread.currentThread().getId());

                    mConnection.sendTextMessage("0x9");    //发送心跳
                    try {
                        Thread.sleep(120000);  //每2min发送一次心跳 ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /*
    返回连接状态
     */
    public boolean getThreadStatus() {
        return THREAD_STATUS;
    }

    /*
    返回服务器回复的信息
     */
    public String getResponseMessage() {
        return responseMessage;
    }
}
