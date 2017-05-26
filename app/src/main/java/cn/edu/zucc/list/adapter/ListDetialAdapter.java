package cn.edu.zucc.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.R;

/**
 * Created by Administrator on 2017/5/2.
 */
public class ListDetialAdapter extends BaseAdapter {
    private Context context;
    private List<ListDetialMain> datas;
    int flag=0;
    public ListDetialAdapter(Context context,List<ListDetialMain> datas) {
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
        ListDetialMain data = datas.get(position);
        TextView list_name;
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.tasklist_item,null);
            holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkfinish);
            holder.list_name = (TextView)convertView.findViewById(R.id.taskname);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        list_name = (TextView)convertView.findViewById(R.id.taskname);
        holder.list_name.setText(data.getDetialname());
//        checkBox = (CheckBox)convertView.findViewById(R.id.checkfinish);
//        checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
            }
        });
        if(flag==1)
         holder.checkBox.setVisibility(View.GONE);

        return convertView;
    }

    public final class ViewHolder {
        TextView list_name;
        CheckBox checkBox;
    }

}