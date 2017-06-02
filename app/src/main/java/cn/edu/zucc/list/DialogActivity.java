package cn.edu.zucc.list;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import cn.edu.zucc.list.DB.DBManager;
import cn.edu.zucc.list.FabAnimation.MorphDialogToFab;
import cn.edu.zucc.list.FabAnimation.MorphFabToDialog;
import cn.edu.zucc.list.FabAnimation.MorphTransition;

public class DialogActivity extends AppCompatActivity {

    private ViewGroup container;
    private FrameLayout frameLayout;
    EditText editText;
    private DBManager dbManager;
    private Button sure;
    String uesname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        dbManager = new DBManager(getBaseContext());
        container = (ViewGroup) findViewById(R.id.container);
        editText = (EditText)findViewById(R.id.addlist);
        frameLayout = (FrameLayout)findViewById(R.id.dialog);
        editText.setOnKeyListener(onKey);
        sure = (Button) container.findViewById(R.id.close);
        //方式一
//        setupSharedEelementTransitions1();
        //方式二
        setupSharedEelementTransitions2();
        Intent intent = getIntent();
        uesname= intent.getStringExtra("uesname");
        View.OnClickListener sureListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equals(null)||editText.getText().toString().isEmpty()||editText.getText().toString()==""){
                    Toast.makeText(DialogActivity.this,"清单名不能为空,创建失败",Toast.LENGTH_SHORT).show();
                }else {
                    dbManager.addList(editText.getText().toString(),uesname);
                    Intent intent = new Intent();
                    intent.setAction("action.addlist");
                    sendBroadcast(intent);
                    Toast.makeText(DialogActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        };
        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        View.OnClickListener nothing = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        frameLayout.setOnClickListener(dismissListener);
        container.setOnClickListener(nothing);
        container.findViewById(R.id.close).setOnClickListener(sureListener);
    }

    /**
     * 使用方式一：调用setupSharedEelementTransitions1方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphFabToDialog, MorphDialogToFab
     */
    public void setupSharedEelementTransitions1() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphFabToDialog sharedEnter = new MorphFabToDialog();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToFab sharedReturn = new MorphDialogToFab();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    /**
     * 使用方式二：调用setupSharedEelementTransitions2方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphTransition
     */
    public void setupSharedEelementTransitions2() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        //hujiawei 100是随意给的一个数字，可以修改，需要注意的是这里调用container.getHeight()结果为0
        MorphTransition sharedEnter = new MorphTransition(ContextCompat.getColor(this, R.color.fab_background_color),
                ContextCompat.getColor(this, R.color.dialog_background_color), 100, getResources().getDimensionPixelSize(R.dimen.dialog_corners), true);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphTransition sharedReturn = new MorphTransition(ContextCompat.getColor(this, R.color.dialog_background_color),
                ContextCompat.getColor(this, R.color.fab_background_color), getResources().getDimensionPixelSize(R.dimen.dialog_corners), 100,  false);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
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
