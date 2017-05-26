package cn.edu.zucc.list.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

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
    int detialnum;
    int flag = 0;
    private DBManager dbManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v2 = inflater.inflate(R.layout.listdetil_set, container, false);
        title = (TextView)v2.findViewById(R.id.set_title);
        aSwitch = (Switch)v2.findViewById(R.id.choosefinish);
        ps = (EditText)v2.findViewById(R.id.ps);
        sure = (Button)v2.findViewById(R.id.sure) ;
        Bundle bundle = getArguments();
        detialnum = bundle.getInt("detialnum");
        final boolean ischeck = bundle.getBoolean("finish");
        title.setText(bundle.getString("title"));
        ps.setText(bundle.getString("ps"));
        if(ischeck==true)
            aSwitch.setChecked(true);
        else
            aSwitch.setChecked(false);
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
                        dbManager.updatedetialps(detialnum,"cc");
                        dbManager.selectDetial(1);
                    }
                }
            }
        });
        return v2;
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


}