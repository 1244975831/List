package cn.edu.zucc.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        ImageView welcomeImg = (ImageView) this.findViewById(R.id.welcome);
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(1000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
        welcomeImg.setBackgroundResource(R.drawable.hello);
        anima.setAnimationListener(new AnimationImpl());
    }
    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
//           welcomeImg.setBackgroundResource(R.drawable.welcome);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip(); // 动画结束后跳转到别的页面
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    private void skip() {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
    }
}
