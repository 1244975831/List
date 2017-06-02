package cn.edu.zucc.list;

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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Fragment.ListdetilFragment;
import cn.edu.zucc.list.Fragment.ListdetilSet;
import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.Item.ListMain;
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
    private ArrayList<ListDetialMain> result;
    ArrayList<ListDetialMain> detialdata = new ArrayList<>();
    private LinearLayout noResult;
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
        username = getIntent().getStringExtra("uesname");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                svStrs = mSearchView.getQuery().toString();
                if(dbManager.searchDetailName(svStrs,username)==null){
                }else{
                    noResult.setVisibility(GONE);
                    result=dbManager.searchDetailName(svStrs,username);
                    initView();
                }
                return true;
            }

            public boolean onQueryTextChange(String newText){
                return false;
            }
        });
    }

    private void initView(){
        mAdapter= new SearchViewAdapter(this,result);
        mListView.setAdapter(mAdapter);
    }
}