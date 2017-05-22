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

import cn.edu.zucc.list.R;
import cn.edu.zucc.list.adapter.ListDetialAdapter;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ListdetilSet extends Fragment {
    private List data=new ArrayList();
    TextView title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v2 = inflater.inflate(R.layout.listdetil_set, container, false);
        title = (TextView)v2.findViewById(R.id.set_title);
        Bundle bundle = getArguments();
        title.setText(bundle.getString("title"));
        return v2;
    }

}