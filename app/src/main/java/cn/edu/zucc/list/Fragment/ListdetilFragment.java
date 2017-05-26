package cn.edu.zucc.list.Fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.Item.ListMain;
import cn.edu.zucc.list.MainActivity;
import cn.edu.zucc.list.R;
import cn.edu.zucc.list.adapter.ListDetialAdapter;

public class ListdetilFragment extends Fragment{
    private List<ListDetialMain> datas;
    ListView listdetial;
    ListView detialfinish;
    TextView title;
    EditText task ;
    int listnum;
    int flag=0;
    int deleteflag = 0;
    int listordetial = 0;
    ListDetialAdapter listDetialAdapter;
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
        task = (EditText)v2.findViewById(R.id.task);


//        fab = (FloatingActionButton) v.findViewById(R.id.fab);
//        list = (ListView)v.findViewById(R.id.list);
        task.setOnKeyListener(onKey);
        task.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(task.hasFocus()){
                    flag++;
                }else {
                    if(flag>0){
                        String PS = task.getText().toString();
                        dbManager.addListdetial(PS,listnum);
                    }
                }
            }
        });
        //数据准备
        initdata();
        //设置行高一抵消滑动布局与list的冲突
        Listoperate();

        return v2;
    }
    public void initdata(){
        dbManager = new DBManager(getActivity());

        Bundle bundle = getArguments();
        title.setText(bundle.getString("title"));
        listnum = bundle.getInt("listnum");
        detildata.clear(); finished.clear();notfinish.clear();
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

    public void Listoperate(){
        ViewGroup.LayoutParams layoutParams = this.listdetial.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
        // 行高194px
        layoutParams.height = 194*notfinish.size();
        listdetial.setLayoutParams(layoutParams);
        listDetialAdapter = new ListDetialAdapter(getActivity(),notfinish);
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
//                dbManager.updatedetial(detialdata.getListnum());
                ListdetilSet listdetilSet = new ListdetilSet();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("detialnum",detialdata.getListnum());
                bundle.putBoolean("finish",detialdata.ischeck());
                bundle.putString("title",detialdata.getDetialname());
                bundle.putString("ps",detialdata.getPs());
                listdetilSet.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment,listdetilSet);
                fragmentTransaction.commit();
            }
        });

        detialfinish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datas=detildata;
                ListDetialMain detialdata = datas.get(position);
//                dbManager.updatedetial(detialdata.getListnum());
                ListdetilSet listdetilSet = new ListdetilSet();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("detialnum",detialdata.getListnum());
                bundle.putBoolean("finish",detialdata.ischeck());
                bundle.putString("title",detialdata.getDetialname());
                listdetilSet.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,listdetilSet);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        listdetial.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(listdetial);
                deleteflag = position;
                listordetial=2;
                return false;
            }
        });
        detialfinish.setAdapter(listDetialAdapter);
    }

    View.OnKeyListener onKey=new View.OnKeyListener() {

        @Override

        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(keyCode == KeyEvent.KEYCODE_ENTER){

                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(imm.isActive()){

                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );
                    String PS = task.getText().toString();
                    if(PS.isEmpty()||PS.equals("")||PS==""){
//                        Toast.makeText(getActivity(),"目标名不能为空,创建失败",Toast.LENGTH_SHORT).show();
                    }else{
                        dbManager.addListdetial(PS,listnum);
                        task.setText("");
                        initdata();
                        Listoperate();
                        listdetial.deferNotifyDataSetChanged();
                        detialfinish.deferNotifyDataSetChanged();
                    }
                }

                return true;

            }

            return false;

        }

    };


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.delete&&listordetial==2){
            ListDetialMain data = detildata.get(deleteflag);
            dbManager.deletedetial(data.getListnum());
            initdata();
            //列表操作
            Listoperate();
            listdetial.deferNotifyDataSetChanged();
            detialfinish.deferNotifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

}
