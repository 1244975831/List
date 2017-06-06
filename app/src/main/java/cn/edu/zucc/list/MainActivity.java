package cn.edu.zucc.list;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Fragment.ListdetilFragment;
import cn.edu.zucc.list.Item.ListMain;
import cn.edu.zucc.list.Item.UserMain;
import cn.edu.zucc.list.adapter.ListAdapter;

public class MainActivity extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener{
    ListAdapter listAdapter;
    List<ListMain> datas;
    ArrayList<ListMain> listdata = new ArrayList<>();
    ArrayList<UserMain> userinfo = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private DBManager dbManager;
    ListView list;
    Toolbar toolbar;
    String uesname;
    String name;
    String spinnercontent;
    int newlistnum;
    int deleteflag = 0;
    int listordetial = 0;
    private int RC_LOGIN = 100;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.addlist");
        intentFilter.addAction("action.search");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        //加载数据
        initdata();
        //侧边栏操作
        draweroperate();

        //列表操作
        Listoperate();

        //浮动按钮操作
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showInputDialog();
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                intent.putExtra("uesname",uesname);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab, getString(R.string.transition_dialog));
                startActivityForResult(intent, RC_LOGIN, options.toBundle());
            }
        });
    }

    public void initdata(){
        Intent intent = getIntent();
        uesname= intent.getStringExtra("uesname");

        dbManager = new DBManager(getBaseContext());
        listdata.clear();
        listdata.addAll(dbManager.selectList(uesname));
        userinfo.clear();
        userinfo.add(dbManager.selectUsers(uesname));
        name = userinfo.get(0).getName();
    }

    public void Listoperate(){
        list = (ListView)findViewById(R.id.list);
        listAdapter = new ListAdapter(this,listdata);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datas=listdata;
                ListMain Itemdata = datas.get(position);
                list.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                ListdetilFragment listdetilFragment = new ListdetilFragment();

                //传值
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
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(list);
                deleteflag = position;
                 listordetial = 1;
                return false;
            }
        });

    }

    public  void detialback(View view){
        list.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);

    }

    public  void back (){
        list.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }
    public void infoback(View view){
        list.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

    public  void createlist(View view){
        showInputDialog();
    }

    //创建list的对话框
    private   void   showInputDialog() {

        final   EditText editText =   new   EditText(MainActivity.  this  );

        AlertDialog.Builder inputDialog = new   AlertDialog.Builder(MainActivity.  this  );

        inputDialog.setTitle(  "创建新的清单"  ).setView(editText);

        inputDialog.setPositiveButton(  "确定"  ,

                new   DialogInterface.OnClickListener() {

                    @Override

                    public   void   onClick(DialogInterface dialog,   int   which) {

                        if(editText.getText().toString().equals(null)||editText.getText().toString().isEmpty()||editText.getText().toString()==""){
                            Toast.makeText(MainActivity.this,"清单名不能为空,创建失败",Toast.LENGTH_SHORT).show();
                        }else {
                            dbManager.addList(editText.getText().toString(),uesname);
                            initdata();
                            Listoperate();
                        }
                        listAdapter.notifyDataSetChanged();
                    }

                }).show();

    }

    public void setdetial(View view){
      getFragmentManager().popBackStack();
    }

    public void draweroperate(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.timg1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initdata();
                //侧边栏内容修改
                NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
                View headerView=nav_view.getHeaderView(0);
                TextView text_name=(TextView)headerView.findViewById(R.id.drawername);
                text_name.setText(name);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            /*case R.id.action_settings:
                break;*/
            case R.id.action_search:
                Intent intent = new Intent(this,SearchActivity.class);
                intent.putExtra("uesname",uesname);
                intent.putExtra("name",name);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_main, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        dbManager = new DBManager(getBaseContext());
        int id = item.getItemId();
        if(id==R.id.delete&&listordetial==1){
            ListMain Itemdata = listdata.get(deleteflag);
            dbManager.deleteList(Itemdata.getListnum());
            initdata();
            //列表操作
            Listoperate();
            listAdapter.notifyDataSetChanged();
            listordetial=0;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.userdetial) {
            Intent intent = new Intent(this,InfoActivity.class);
            intent.putExtra("username",uesname);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.loginout) {
            Intent intent = new Intent(this, LoginActivity.class);
            dbManager.deleteSaveuser();
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //接收广播刷新列表
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.addlist"))
            {
                initdata();
                Listoperate();
                listAdapter.notifyDataSetChanged();
            }if(action.equals("action.search")){
//                AdapterView.OnItemClickListener onItemClickListener = list.getOnItemClickListener();
//                if(onItemClickListener!=null){
//                    onItemClickListener.onItemClick(list,null,0,0);
//                }
//                list.performItemClick(list.getChildAt(1),1,list.getItemIdAtPosition(1));
            }
        }
    };
}
