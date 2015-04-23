package com.yy.dian709chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yy.dian709login.R;

import java.util.List;

public class ChatMsgViewAdapter extends BaseAdapter {

    private final static String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private List<ChatMsgEntity> data;
    private Context context;
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    //获取listview的个数
    @Override
    public int getCount() {
        return data.size();
    }

    //获取项
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    //获取项的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //获取view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMsgEntity entity = data.get(position);
        boolean isComMsg = entity.getMsgType();

        ViewHolder viewHolder = null;
        if (convertView == null) {

            if (isComMsg ) {
                //如果是接收的信息 显示为左边
                convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
            } else {
                //如果是发出的信息 显示为右边
                convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.tvUsrName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.isComMsg = isComMsg;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.tvUsrName.setText(entity.getName());
        viewHolder.tvSendTime.setText(entity.getTime());
        viewHolder.tvContent.setText(entity.getMsg());
        return convertView;
    }

    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUsrName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
}
