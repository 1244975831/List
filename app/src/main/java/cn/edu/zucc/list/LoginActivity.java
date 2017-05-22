package cn.edu.zucc.list;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.Item.ListDetialMain;
import cn.edu.zucc.list.Item.ListMain;
import cn.edu.zucc.list.Item.UserMain;

public class LoginActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    private String username;
    private String password;
    private RelativeLayout loginlayout;
    private RelativeLayout ueslogin;
    private RelativeLayout uessign;
    private TranslateAnimation translateAnimation;
    private TranslateAnimation translateAnimation2;
    private ImageButton signback;
    private ImageButton loginback;
    private Button sign;
    private Button login;
    private Button si;
    private Button lo;
    private DBManager dbManager;
    private EditText edituesname;
    private EditText editpassword;
    private EditText signuesname;
    private EditText signpassword;
    ArrayList<UserMain> data = new ArrayList<>();
    ArrayList<ListMain> listdata = new ArrayList<>();
    ArrayList<ListDetialMain> detildata = new ArrayList<>();
    float x1 =0;
    float y1 =0;
    float x = 0;
    float y = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewFlipper = (ViewFlipper)findViewById(R.id.ViewFlippers);
        loginlayout = (RelativeLayout)findViewById(R.id.login);
        ueslogin  = (RelativeLayout)findViewById(R.id.ueslogin);
        uessign  = (RelativeLayout)findViewById(R.id.uessign);
        signback =(ImageButton)findViewById(R.id.signback);
        loginback = (ImageButton)findViewById(R.id.loginback);
        login = (Button)findViewById(R.id.loginbutton);
        sign = (Button)findViewById(R.id.sign);
        edituesname = (EditText)findViewById(R.id.username);
        editpassword = (EditText)findViewById(R.id.userpassword) ;
        signuesname = (EditText)findViewById(R.id.signname) ;
        signpassword = (EditText)findViewById(R.id.signpassword);
        si = (Button) findViewById(R.id.sign_insign);
        lo = (Button) findViewById(R.id.login_inlogin);
        dbManager = new DBManager(getBaseContext());
//        dbManager.initData();
        edituesname.setOnKeyListener(onKey);
        editpassword.setOnKeyListener(onKey);
        signuesname.setOnKeyListener(onKey);
        signpassword.setOnKeyListener(onKey);
        UserMain save = dbManager.selectSaveuser();
        if(save.getUsername()!=null){
            username = save.getUsername();
            password = save.getPassword();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("uesname",username);
            startActivity(intent);
            finish();
        }

    }

    View.OnKeyListener onKey=new View.OnKeyListener() {

        @Override

        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(keyCode == KeyEvent.KEYCODE_ENTER){

                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(imm.isActive()){

                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );

                }

                return true;

            }

            return false;

        }

    };


    public void loginview(View view){
        //起始x值 结束x值 起始y值 结束y值

        translateAnimation = new TranslateAnimation(0,-1600,0,0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        loginlayout.setAnimation(translateAnimation);
        loginlayout.startAnimation(translateAnimation);


        translateAnimation2 = new TranslateAnimation(1600,0,0,0);
        translateAnimation2.setDuration(200);
        translateAnimation2.setFillAfter(true);
        ueslogin.setAnimation(translateAnimation2);
        ueslogin.startAnimation(translateAnimation2);

        uessign.setVisibility(View.GONE);
        ueslogin.setVisibility(View.VISIBLE);
        loginlayout.setVisibility(View.GONE);

        edituesname .setVisibility(View.VISIBLE);
        editpassword .setVisibility(View.VISIBLE);
        lo.setVisibility(View.VISIBLE);
        signuesname .setVisibility(View.GONE);
        signpassword.setVisibility(View.GONE);
        si.setVisibility(View.GONE);

        loginback.setClickable(true);
        login.setClickable(false);
        sign.setClickable(false);

        edituesname.hasFocus();
    }

    public void signview(View view){
        //起始x值 结束x值 起始y值 结束y值

        translateAnimation = new TranslateAnimation(0,1600,0,0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        loginlayout.setAnimation(translateAnimation);
        loginlayout.startAnimation(translateAnimation);


        translateAnimation2 = new TranslateAnimation(-1600,0,0,0);
        translateAnimation2.setDuration(200);
        translateAnimation2.setFillAfter(true);
        uessign.setAnimation(translateAnimation2);
        uessign.startAnimation(translateAnimation2);

        ueslogin.setVisibility(View.GONE);
        uessign.setVisibility(View.VISIBLE);
        loginlayout.setVisibility(View.GONE);
        signback.setClickable(true);
        login.setClickable(false);
        sign.setClickable(false);

        edituesname .setVisibility(View.GONE);
        editpassword .setVisibility(View.GONE);
        lo.setVisibility(View.GONE);
        signuesname .setVisibility(View.VISIBLE);
        signpassword.setVisibility(View.VISIBLE);
        si.setVisibility(View.VISIBLE);
    }

    public void signback(View view){
        //起始x值 结束x值 起始y值 结束y值
        translateAnimation2 = new TranslateAnimation(0,-1600,0,0);
        translateAnimation2.setDuration(200);
        translateAnimation2.setFillAfter(true);
        uessign.setAnimation(translateAnimation2);
        uessign.startAnimation(translateAnimation2);
        uessign.setVisibility(View.GONE);

        translateAnimation = new TranslateAnimation(1600,0,0,0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        loginlayout.setAnimation(translateAnimation);
        loginlayout.startAnimation(translateAnimation);

        ueslogin.setVisibility(View.GONE);
        loginlayout.setVisibility(View.VISIBLE);

        signuesname.setText("");
        signpassword.setText("");
        signuesname.clearFocus();
        signpassword.clearFocus();

        login.setClickable(true);
        sign.setClickable(true);
        signback.setClickable(false);

        edituesname .setVisibility(View.GONE);
        editpassword .setVisibility(View.GONE);
        lo.setVisibility(View.GONE);
        signuesname .setVisibility(View.GONE);
        signpassword.setVisibility(View.GONE);
        si.setVisibility(View.GONE);
    }

    public void  loginback(View view){
        //起始x值 结束x值 起始y值 结束y值
        translateAnimation2 = new TranslateAnimation(0,1600,0,0);
        translateAnimation2.setDuration(200);
        translateAnimation2.setFillAfter(true);
        ueslogin.setAnimation(translateAnimation2);
        ueslogin.startAnimation(translateAnimation2);
        uessign.setVisibility(View.GONE);

        translateAnimation = new TranslateAnimation(-1600,0,0,0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        loginlayout.setAnimation(translateAnimation);
        loginlayout.startAnimation(translateAnimation);


        loginlayout.setVisibility(View.VISIBLE);
        ueslogin.setVisibility(View.GONE);
        loginback.setClickable(false);
        login.setClickable(true);
        sign.setClickable(true);

        edituesname .setVisibility(View.GONE);
        editpassword .setVisibility(View.GONE);
        lo.setVisibility(View.GONE);
        signuesname .setVisibility(View.GONE);
        signpassword.setVisibility(View.GONE);
        si.setVisibility(View.GONE);
    }

    public  void sign(View view){
        username = signuesname.getText().toString();
        password = signpassword.getText().toString();
        data.clear();
        data.add(dbManager.selectUsers(username));
        String d = data.get(0).getUsername();
        if(username==null||password==null||username.equals("")||password.equals("")){
            Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }
        else if (d==null) {
           dbManager.signData(username,password);
            Toast.makeText(LoginActivity.this,"恭喜您注册成功",Toast.LENGTH_SHORT).show();
            signback(view);
        }
        else{
            Toast.makeText(LoginActivity.this,"已存在相同用户名",Toast.LENGTH_SHORT).show();
        }
    }

    public  void login(View view){
        username = edituesname.getText().toString();
        password = editpassword.getText().toString();
        data.clear();detildata.clear();listdata.clear();
        data.add(dbManager.selectUsers(username));
        String d = data.get(0).getPassword();
        //用户登录判断
        if(data.isEmpty()||data.get(0).getUsername()==null){
            Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
        }else{
            if(username==null||password==null||username.equals("")||password.equals("")){
                Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            }
            else if (d.equals(password)&&data.get(0).getPassword()!=null) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("uesname",username);
                dbManager.Saveuser(username,password);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x=event.getX();
            y=event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            x1=event.getX();
            y1=event.getY();

            if(x1-x>20){
                viewFlipper.showPrevious();
            }
            if(x-x1>20){
                viewFlipper.showNext();
            }
        }
        return super.onTouchEvent(event);
    }
}
