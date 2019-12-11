package com.example.ridho.tugasakhir_mikan;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class slide_intro extends AppCompatActivity {

    private LinearLayout mDotLayout;

    private Button login,skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_intro);

        ViewPager mSlideViewPage = (ViewPager) findViewById(R.id.ViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        login = (Button) findViewById(R.id.btnLogin_Intro);
        skip = (Button) findViewById(R.id.btnSkip_Intro);

        com.example.ridho.tugasakhir_mikan.slide_intro_adapter slide_intro_adapter = new slide_intro_adapter(this);

        mSlideViewPage.setAdapter(slide_intro_adapter);

        addDotsIndicator(0);

        mSlideViewPage.addOnPageChangeListener(viewListener);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(slide_intro.this, com.example.ridho.tugasakhir_mikan.login.class));
                finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(slide_intro.this,login.class));
                finish();
            }
        });
    }

    private void addDotsIndicator(int position) {
        TextView[] mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.text_color_secondary));

            mDotLayout.addView(mDots[i]);
        }

        mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        mDots[position].setTextSize(50);
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            if(i == 2){
                login.setVisibility(View.VISIBLE);
                skip.setVisibility(View.INVISIBLE);
            }
            else {
                login.setVisibility(View.INVISIBLE);
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
