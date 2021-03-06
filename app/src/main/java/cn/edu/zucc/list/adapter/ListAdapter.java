package cn.edu.zucc.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.zucc.list.Item.ListMain;
import cn.edu.zucc.list.R;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ListAdapter extends BaseAdapter{
    private Context context;
    private List<ListMain> datas;

    public ListAdapter(Context context,List<ListMain> datas) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListMain data = datas.get(position);
        TextView list_name;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_content,null);
        }
        list_name = (TextView)convertView.findViewById(R.id.list_name);
        list_name.setText(data.getListname());
        return convertView;
    }


}
