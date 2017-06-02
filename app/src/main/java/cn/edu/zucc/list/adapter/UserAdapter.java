package cn.edu.zucc.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.zucc.list.Item.UserMain;
import cn.edu.zucc.list.R;

/**
 * Created by dell on 2017/5/27.
 */

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<UserMain> datas;

    public UserAdapter(Context context,List<UserMain> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        UserMain data = datas.get(position);
        TextView editName;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_info,null);
        }
        editName = (TextView)convertView.findViewById(R.id.tv_userName);
        editName.setText(data.getUsername());
        return convertView;
    }
}
