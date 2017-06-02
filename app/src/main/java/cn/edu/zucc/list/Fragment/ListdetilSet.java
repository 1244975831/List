package cn.edu.zucc.list.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.R;
import cn.edu.zucc.list.adapter.ListDetialAdapter;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ListdetilSet extends Fragment {
    private List data=new ArrayList();
    TextView title;
    Switch aSwitch;
    EditText  ps;
    Button sure;
    TextView owner;
    int detialnum;
    RelativeLayout rl1;
    RelativeLayout rl2;
    TextView tv_date;
    TextView tv_time;
    String name;
    String deadline="";
    private DatePicker datePicker;
    private TimePicker timePicker;
    private String  date, time, dateTime;
    int flag = 0,Yearflag=0;
    String settime;
    String Y,M,D,H,MIN;
    private DBManager dbManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v2 = inflater.inflate(R.layout.listdetil_set, container, false);
        title = (TextView)v2.findViewById(R.id.set_title);
        aSwitch = (Switch)v2.findViewById(R.id.choosefinish);
        ps = (EditText)v2.findViewById(R.id.ps);
        sure = (Button)v2.findViewById(R.id.sure) ;
        tv_date = (TextView)v2.findViewById(R.id.set_finishdate);
        tv_time = (TextView)v2.findViewById(R.id.set_finishtime);
        owner = (TextView)v2.findViewById(R.id.owner);
        Bundle bundle = getArguments();
        detialnum = bundle.getInt("detialnum");
        final boolean ischeck = bundle.getBoolean("finish");
        title.setText(bundle.getString("title"));
        ps.setText(bundle.getString("ps"));
        settime = bundle.getString("deadline");
        name = bundle.getString("name");
        owner.setText(name);

        if(ischeck==true)
            aSwitch.setChecked(true);
        else
            aSwitch.setChecked(false);

        dateoperate();
        dbManager = new DBManager(getActivity());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dbManager.updatedetial(detialnum,true);
                }
                else{
                    dbManager.updatedetial(detialnum,false);
                }
            }
        });
        ps.setOnKeyListener(onKey);
        ps.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(ps.hasFocus()){
                    flag++;
                }else {
                    if(flag>0){
                        String PS = ps.getText().toString();
                        dbManager.updatedetialps(detialnum,PS);
                    }
                }
            }
        });
        return v2;
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        rl1 = (RelativeLayout)getActivity().findViewById(R.id.rl_date);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActionBar tabsActionBar = getActivity().getActionBar();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.listdetil_setdate, null);
                datePicker = (DatePicker) linearLayout.findViewById(R.id.datePicker);
                builder.setView(linearLayout);
                builder.setTitle("请选择日期");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  DatePicker datepicker1= (DatePicker)linearLayout.findViewById(R.id.datePicker);
                        int y = datePicker.getYear();
                        int m = datePicker.getMonth()+1;
                        int d = datePicker.getDayOfMonth();
                        System.out.println("y:"+y+" m:"+m+" d:"+d);//获取时间
                        tv_date.setText(y+"年"+m+"月"+d+"日");
                        date = tv_date.getText().toString();
                        deadline=y+":"+m+":"+d+":";
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        rl2 = (RelativeLayout)getActivity().findViewById(R.id.rl_time);
        rl2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.listdetil_settime, null);
                builder.setView(linearLayout);
                builder.setTitle("请选择时间");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timePicker= (TimePicker)linearLayout.findViewById(R.id.timePicker);
                        timePicker.setIs24HourView(false);
                        int h = timePicker.getHour();
                        int min = timePicker.getMinute();
                        System.out.println("h:"+h+" min:"+min);//获取时间
                        tv_time.setText(h+":"+min);
                        if(deadline==""){
                            deadline=Y+":"+M+":"+D+":";
                        }
                        time = tv_time.getText().toString();
                        deadline = deadline+h+":"+min+":";
                        dbManager.updatedetialtime(detialnum,deadline);
                        deadline="";
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        //dateTime =
    }
    View.OnKeyListener onKey=new View.OnKeyListener() {

        @Override

        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(keyCode == KeyEvent.KEYCODE_ENTER){

                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(imm.isActive()){
                    ps.clearFocus();
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );

                }

                return true;

            }

            return false;

        }

    };

    public void dateoperate(){
        Yearflag=0;Y=M=D=H=MIN="";
        if(settime == null){
            tv_date.setText(Y);
            tv_time.setText(H);
        }else{
            for(int i =0;i<settime.length();i++){
                String c = settime.charAt(i)+"";
                if(c.equals(":")) {
                    Yearflag++;
                    c="";
                }
                if(Yearflag==0) {
                    Y = Y+c;
                }else if(Yearflag==1){
                    M = M+c;
                }else if(Yearflag==2){
                    D = D+c;
                }else if(Yearflag==3){
                    H = H+c;
                }else if(Yearflag==4){
                    MIN = MIN + c;
                }
            }
            if(Y==""){
                tv_date.setText(Y);
                tv_time.setText(H);
            }else{
                tv_date.setText(Y+"年"+M+"月"+D+"日");
                tv_time.setText(H+":"+MIN);
            }
        }
    }

}