package cn.edu.zucc.list;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Fragment.ListdetilFragment;
import cn.edu.zucc.list.Fragment.ListdetilSet;
import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.Item.ListMain;
import cn.edu.zucc.list.Item.UserMain;
import cn.edu.zucc.list.adapter.ListAdapter;
import cn.edu.zucc.list.adapter.ListDetialAdapter;
import cn.edu.zucc.list.adapter.SearchViewAdapter;

import static android.view.View.GONE;
import static cn.edu.zucc.list.R.id.toolbar;
import static cn.edu.zucc.list.R.string.finished;

/**
 * Created by Xu on 2017/5/24.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchView mSearchView;;
    private ListView mListView;
    private SearchViewAdapter mAdapter;
    private DBManager dbManager;
    List<ListMain> datas;
    private ArrayList<ListDetialMain> result;
    ArrayList<ListDetialMain> detialdata = new ArrayList<>();
    ArrayList<ListMain> listdata = new ArrayList<>();
    ArrayList<UserMain> userinfo = new ArrayList<>();
    String name;
    ImageButton back;
    private LinearLayout noResult;
    private LinearLayout search;
    private String[] mStrs;
    private String svStrs;//searchViewString
    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dbManager =new DBManager(this);
        mSearchView = (SearchView)findViewById(R.id.search_view);
        noResult = (LinearLayout)findViewById(R.id.ll_noResult);
        mListView = (ListView)findViewById(R.id.list_detail);
        search = (LinearLayout)findViewById(R.id.search);

        username = getIntent().getStringExtra("uesname");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                svStrs = mSearchView.getQuery().toString();
                if(dbManager.searchDetailName(svStrs,username)==null){
                    noResult.setVisibility(View.VISIBLE);
                }else{
                    noResult.setVisibility(GONE);
                    result=dbManager.searchDetailName(svStrs,username);
                    initView();
                }
                if(result.size()==0){
                    noResult.setVisibility(View.VISIBLE);
                }
                return true;
            }

            public boolean onQueryTextChange(String newText){
                return false;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search.setVisibility(View.GONE);
                dbManager = new DBManager(getBaseContext());
                listdata.clear();
                listdata.addAll(dbManager.selectList(username));
                userinfo.clear();
                userinfo.add(dbManager.selectUsers(username));
                name = userinfo.get(0).getName();
                int i = 0;
                for(i=0;i<listdata.size();i++){
                    if(listdata.get(i).getListnum()==result.get(position).get_id())
                        break;
                }
                datas=listdata;
                ListMain Itemdata = datas.get(i);
                ListdetilFragment listdetilFragment = new ListdetilFragment();
                //传
                Bundle bundle = new Bundle();
                bundle.putString("title",Itemdata.getListname());
                bundle.putInt("listnum",  Itemdata.getListnum());
                bundle.putString("name",name);
                listdetilFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(R.id.fragment,listdetilFragment);
                fragmentTransaction.commit();
            }
        });

    }


    public  void detialback(View view){
        finish();
    }
    public void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }
    private void initView(){
        mAdapter= new SearchViewAdapter(this,result);
        mListView.setAdapter(mAdapter);
    }
}