package cn.com.nxyunzhineng.smart_parking_lock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import cn.com.nxyunzhineng.smart_parking_lock.R;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private WelcomeItem adapter;
    private int imgs[] = {R.mipmap.wel1,R.mipmap.wel2,R.mipmap.ic_launcher,R.drawable.gps_point};
    private ImageView imgView[]  = new ImageView[4];
    private Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //android 设置全屏,不用也可以
        viewPager = (ViewPager)findViewById(R.id.wel_viewpager);
        adapter = new WelcomeItem(this,imgs);
        imgView[0] = (ImageView) findViewById(R.id.frist);
        imgView[1] = (ImageView) findViewById(R.id.second);
        imgView[2] = (ImageView) findViewById(R.id.thrid);
        imgView[3] = (ImageView) findViewById(R.id.four);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoMain();
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    reset();
                imgView[position].setImageResource(R.drawable.ic_select);
                if(position == 3)
                {
                    start.setVisibility(Button.VISIBLE);
                }else
                    start.setVisibility(Button.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void reset(){
        imgView[0].setImageResource(R.drawable.ic_unselect);
        imgView[2].setImageResource(R.drawable.ic_unselect);
        imgView[3].setImageResource(R.drawable.ic_unselect);
        imgView[1].setImageResource(R.drawable.ic_unselect);
    }
    class WelcomeItem extends PagerAdapter {
        private int[] arry;
        private LayoutInflater layoutInflater;
        public WelcomeItem(Context context, int[] arry) {
            layoutInflater = LayoutInflater.from(context);
            this.arry = arry;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            View view = layoutInflater.inflate(R.layout.wel_item, null);

            view.setBackgroundResource(arry[position]);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return arry.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = layoutInflater.inflate(R.layout.wel_item, null);
            container.removeView(view);
//            super.destroyItem(container, position, object);
        }
    }
    public void GoMain(){
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }
}
