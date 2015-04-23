package com.yy.dian709login;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by YY on 2015/4/11.
 */
public class getWorkTime {
    private static final String TAG = "test.websocket.client";
    //private long loginTime = new MainActivity().getLoginTime();

    /*将时间转化为字符串*/
    public String timeCount(long time) {
        //Log.d(TAG,"time=" + time);
        String timeCount = null;

        if (time > 3600000) {
            timeCount = "00:00:00";
            return timeCount;
        }

        long hourc = time/3600000;
        String hour = "0" + hourc;      //?直接转化？
        //Log.d(TAG,"hour= " + hour);
        hour = hour.substring(hour.length()-2,hour.length());   //只取两个字符
        //Log.d(TAG,"hour2=" + hour);

        long minutec = (time - hourc*3600000)/60000;
        String minute = "0" + minutec;
        minute = minute.substring(minute.length()-2, minute.length());

        long secondc = (time - hourc*3600000 - minutec*60000 )/1000;
        String second = "0" + secondc;
        second = second.substring(second.length()-2, second.length());

        timeCount = hour + ":" + minute + ":" + second;
        return timeCount;
    }

    /*显示在线时间*/
    public void showTime() {
        new Thread() {
            @Override
            public void run() {

                Log.d(TAG, "run begin");
                Looper.prepare();       //??
                do {
                    Message message = new Message();
                    mHandler.sendMessage(message);
                    /*mHandler.handleMessage(message);      子线程sendMessage主线程会自动处理消息，
                    如果在子线程中调用handleMessage则会在子线程中执行handleMessage*/

                    /*等待一秒，每秒触发一次handle，刷新一次显示时间*/
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!Thread.currentThread().isInterrupted());
            }
        }.start();
    }

    //long startTime = System.currentTimeMillis();

    //String timeString = timeCount(1000);
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.d(TAG, String.valueOf((System.currentTimeMillis() - loginTime)));
            MainActivity.workTime.setText(timeCount(System.currentTimeMillis() - MainActivity.loginTime));
        }
    };
}
