package cn.edu.zucc.list;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Fragment.ListdetilFragment;
import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.Item.ListMain;
import cn.edu.zucc.list.adapter.ListAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ListAdapter listAdapter;
    private List<ListMain> datas;
    ArrayList<ListMain> listdata = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private DBManager dbManager;
    ListView list;
    Toolbar toolbar;
    String uesname;
    String spinnercontent;
    int newlistnum;
    int deleteflag = 0;
    int listordetial = 0;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //侧边栏操作
        draweroperate();
        //加载数据
        initdata();
        //列表操作
        Listoperate();
        //浮动按钮操作
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }
    //创建listdetial的对话框
    private   void   showCustomizeDialog() {

        AlertDialog.Builder customizeDialog =  new   AlertDialog.Builder(MainActivity.  this  );

        final   View dialogView = LayoutInflater.from(MainActivity.  this  ) .inflate(R.layout.dialog_layout,  null  );

        final Spinner spinner = (Spinner)dialogView.findViewById(R.id.list_select);

        String[] d =new String[ listdata.size()];
        for(int i = 0 ; i < listdata.size() ; i ++ ){

            d[i]=listdata.get(i).getListname();

        }

        //参数第二个是layout 第三个是控件
        ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,android.R.id.text1,d);

        spinner.setAdapter(adapter);

        spinnercontent = (String) spinner.getSelectedItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnercontent = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        customizeDialog.setTitle(  "设定目标"  );
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton(  "确定"  , new   DialogInterface.OnClickListener() {
                    @Override

                    public   void   onClick(DialogInterface dialog,   int   which) {
                        if(spinnercontent==""||spinnercontent==null){
                            Toast.makeText(MainActivity.this,"请选择要添加到的清单",Toast.LENGTH_SHORT).show();
                        }else{
                            EditText editText = (EditText)dialogView.findViewById(R.id.message);
                            String newlistname = editText.getText().toString();
                            if(newlistname==""||newlistname.isEmpty()||newlistname==null){
                                Toast.makeText(MainActivity.this,"目标名不能为空,创建失败",Toast.LENGTH_SHORT).show();
                            }else{
                                for(int i = 0 ;i<listdata.size();i++){
                                    if(spinnercontent.equals(listdata.get(i).getListname())){
                                        newlistnum = listdata.get(i).getListnum();
                                    }
                                }
                                dbManager.addListdetial(newlistname,newlistnum);
                                Toast.makeText(MainActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                                listAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                });

        customizeDialog.show();

    }

    public void initdata(){
        Intent intent = getIntent();
        uesname= intent.getStringExtra("uesname");

        dbManager = new DBManager(getBaseContext());
        listdata.clear();
        listdata.addAll(dbManager.selectList(uesname));

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
                listdetilFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
            case R.id.action_settings:
                break;
            case R.id.action_search:
                Intent intent = new Intent(this,SearchActivity.class);
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
        int id = item.getItemId();
        if(id==R.id.delete&&listordetial==1){
            ListMain Itemdata = datas.get(deleteflag);
            dbManager.deleteList(Itemdata.getListnum());
            initdata();
            //列表操作
            Listoperate();
            listAdapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.userdetial) {
            // Handle the camera action
        } else if (id == R.id.loginout) {
            Intent intent = new Intent(this, LoginActivity.class);
            dbManager.deleteSaveuser();
            startActivity(intent);
            finish();
        }
//        else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
