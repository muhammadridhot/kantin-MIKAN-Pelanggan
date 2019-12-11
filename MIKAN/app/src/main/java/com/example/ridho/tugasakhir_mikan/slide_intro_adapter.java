package com.example.ridho.tugasakhir_mikan;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class slide_intro_adapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public slide_intro_adapter (Context context){
        this.context = context;
    }

    public int[] slideImg_Intro={
            R.drawable.undraw_order_confirmed_1m3v,
            R.drawable.undraw_time_management_30iu,
            R.drawable.undraw_add_to_cart_vkjp
    };

    public String[] slideHeading_Intro ={
            "Simple",
            "Fast",
            "Saving"
    };

    public String[] slideDesc_Intro ={
            "Delivery",
            "Time",
            "Energy"
    };


    @Override
    public int getCount() {
        return slideHeading_Intro.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.tampilan_slide_intro,container,false);

        ImageView slideImgV =(ImageView)view.findViewById(R.id.img_slide_Intro);
        TextView slideHeadingV = (TextView)view.findViewById(R.id.txtJudul_Intro);
        TextView slideDecsV= (TextView)view.findViewById(R.id.txtDesc_Intro);

        slideImgV.setImageResource(slideImg_Intro[position]);
        slideHeadingV.setText(slideHeading_Intro[position]);
        slideDecsV.setText(slideDesc_Intro[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
