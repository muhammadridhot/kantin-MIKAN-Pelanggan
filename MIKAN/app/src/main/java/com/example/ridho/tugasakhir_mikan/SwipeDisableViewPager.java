package com.example.ridho.tugasakhir_mikan;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SwipeDisableViewPager extends ViewPager {

    private boolean enabled;

    public SwipeDisableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(this.enabled){
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(this.enabled){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public void setPagingEnable(boolean enabled){
        this.enabled = enabled;
    }
}
