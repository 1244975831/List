package cn.edu.zucc.list.Fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.R;
import cn.edu.zucc.list.adapter.ListDetialAdapter;

public class ListdetilFragment extends Fragment{
    private List<ListDetialMain> datas;
    ListView listdetial;
    ListView detialfinish;
    TextView title;
    private DBManager dbManager;
    ArrayList<ListDetialMain> detildata = new ArrayList<>();
    ArrayList<ListDetialMain> notfinish = new ArrayList<>();
    ArrayList<ListDetialMain> finished = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v2 = inflater.inflate(R.layout.activity_listdetil_fragment, container, false);
        listdetial = (ListView) v2.findViewById(R.id.list_task);
        detialfinish = (ListView) v2.findViewById(R.id.list_task_finish);
        title = (TextView)v2.findViewById(R.id.detial_title);
        //数据准备
        initdata();
        //设置行高一抵消滑动布局与list的冲突
        ViewGroup.LayoutParams layoutParams = this.listdetial.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
        // 行高194px
        layoutParams.height = 194*notfinish.size();
        listdetial.setLayoutParams(layoutParams);
        ListDetialAdapter listDetialAdapter = new ListDetialAdapter(getActivity(),notfinish);
        listdetial.setAdapter(listDetialAdapter);

         layoutParams = this.detialfinish.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
        layoutParams.height = 194*finished.size();
        detialfinish.setLayoutParams(layoutParams);
        listDetialAdapter = new ListDetialAdapter(getActivity(),finished);
        detialfinish.setAdapter(listDetialAdapter);

        listdetial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datas=detildata;
                ListDetialMain detialdata = datas.get(position);

                ListdetilSet listdetilSet = new ListdetilSet();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("title",detialdata.getDetialname());
                listdetilSet.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,listdetilSet);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        detialfinish.setAdapter(listDetialAdapter);
        return v2;
    }
    public void initdata(){
        dbManager = new DBManager(getActivity());

        Bundle bundle = getArguments();
        title.setText(bundle.getString("title"));
        int listnum = bundle.getInt("listnum");
        detildata.clear();
        detildata.addAll(dbManager.selectDetial(listnum));
        for(int i = 0 ;i<detildata.size() ; i ++){
            if(detildata.get(i).ischeck()){
                finished.add( detildata.get(i));
            }
            else {
                notfinish.add(detildata.get(i));
            }
        }
    }
}
