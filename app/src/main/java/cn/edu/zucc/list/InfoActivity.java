package cn.edu.zucc.list;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Item.UserMain;

/**
 * Created by dell on 2017/5/25.
 */

public class InfoActivity extends Activity {
/*    private DBManager dbManager;
    private UserMain userMain;*/
    private TextView tv_editName;
    private DBManager dbManager;
    String username;
    ArrayList<UserMain> data = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        username = getIntent().getStringExtra("username");
        tv_editName = (TextView)findViewById(R.id.tv_userName);
        intidata();
        tv_editName.setText(data.get(0).getName());
    }
    public void intidata(){
        dbManager = new DBManager(getBaseContext());
        data.clear();
        data.add(dbManager.selectUsers(username));
    }
    public void editName(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.info_editname,null);
        final EditText editText = (EditText)linearLayout.findViewById(R.id.editUserName);
        editText.setText(data.get(0).getName());
        builder.setView(linearLayout);
        builder.setTitle("修改名字");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //写入数据库
                String newname = editText.getText().toString();
                if(newname.equals(null)||newname.equals("")||newname==""){
                    Toast.makeText(InfoActivity.this,"新昵称为空，修改失败",Toast.LENGTH_SHORT).show();
                }else {
                    dbManager.updateusername(username,newname);
                    Toast.makeText(InfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    intidata();
                    tv_editName.setText(data.get(0).getName());
                }
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void infoback(View view){
        finish();
    }
}
