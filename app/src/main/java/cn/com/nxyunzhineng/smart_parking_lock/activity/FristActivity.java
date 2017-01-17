package cn.com.nxyunzhineng.smart_parking_lock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import cn.com.nxyunzhineng.smart_parking_lock.R;

public class FristActivity extends Activity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        AlphaAnimation animation = new AlphaAnimation(1.0f,1.0f);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("aniii:","OK");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goWelcome();
                Log.d("aniii:","OK");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView = (TextView) findViewById(R.id.app_name);
        animation.setDuration(1500);
        animation.startNow();
        textView.setAnimation(animation);

    }
    private void goWelcome(){
        Intent intent = new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}
