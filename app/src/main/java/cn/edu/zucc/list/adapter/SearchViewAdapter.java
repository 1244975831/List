package cn.edu.zucc.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.R;

/**
 * Created by Xu on 2017/5/28.
 */

public class SearchViewAdapter extends BaseAdapter {
    private Context context;
    private List<ListDetialMain> datas;
    int flag = 0;
    public SearchViewAdapter(Context context, List<ListDetialMain> datas) {
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
        SearchViewAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new SearchViewAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_search_item,null);
            holder.listdetail = (TextView)convertView.findViewById(R.id.item_listdetail);
            holder.list_name = (TextView)convertView.findViewById(R.id.item_listname);

            convertView.setTag(holder);
        }else{
            holder = (SearchViewAdapter.ViewHolder) convertView.getTag();
        }

        holder.list_name.setText(data.getOwn());
        holder.listdetail.setText(data.getDetialname());

        return convertView;
    }

    public final class ViewHolder {
        TextView list_name;
        TextView listdetail;
    }

}

