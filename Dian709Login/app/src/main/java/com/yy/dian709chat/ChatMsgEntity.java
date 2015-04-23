package com.yy.dian709chat;

/**
 * Created by YY on 2015/4/22.
 */
public class ChatMsgEntity {

    private final static String TAG = ChatMsgEntity.class.getSimpleName();

    private String name;    //发信姓名
    private String time;    //发信时间
    private String msg;     //发信内容
    private boolean isComMsg = true;    //是否为来信

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getMsgType() {
        return isComMsg;
    }

    public void setMsgType(boolean isComMsg) {
        this.isComMsg = isComMsg;
    }

    public ChatMsgEntity() {

    }
    public ChatMsgEntity(String name, String time, String msg, boolean isComMsg) {
        this.name = name;
        this.time = time;
        this.msg = msg;
        this.isComMsg = isComMsg;
    }
}
