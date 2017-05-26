package cn.edu.zucc.list;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

/**
 * Created by Xu on 2017/5/24.
 */

public class SearchActivity extends Activity {
    private SearchView searchView;
    private Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}
