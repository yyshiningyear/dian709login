package com.yy.dian709chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.yy.dian709login.ClientHandler;
import com.yy.dian709login.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends Activity implements View.OnClickListener {

    private Button mBtnSend;
    private EditText mEditTextContent;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
    private ClientHandler mClientHandler = new ClientHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mEditTextContent = (EditText) findViewById(R.id.et_sendMsg);

    }

    private void initData() {
        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_send:
                send();
                break;
            default:
                break;
        }
    }

    //发送信息
    private void send() {
        String sendMsg = mEditTextContent.getText().toString();
        if (sendMsg.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setMsg(sendMsg);
            entity.setTime(getTime());
            entity.setName("YY");           //设置发信人姓名
            entity.setMsgType(false);
            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();
            mEditTextContent.setText("");
            mListView.setSelection(mListView.getCount() - 1);
        }
        mClientHandler.sendMessage("msg:" + sendMsg);   //向服务器发送
    }

    //显示接收到的消息
    private void showComMsg(String name, String msg) {
        ChatMsgEntity entity = new ChatMsgEntity(name, getTime(), msg, true);
        mDataArrays.add(entity);
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(mListView.getCount() - 1);
    }

    private String getTime() {
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins);
        return sbBuffer.toString();
    }
}
