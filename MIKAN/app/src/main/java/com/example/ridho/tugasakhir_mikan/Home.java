package com.example.ridho.tugasakhir_mikan;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.ridho.tugasakhir_mikan.Adapter.ViewPagerAdapter;
import com.example.ridho.tugasakhir_mikan.Fragment.HomeFragment;
import com.example.ridho.tugasakhir_mikan.Fragment.ProfileFragment;
import com.example.ridho.tugasakhir_mikan.Fragment.RiwayatFragment;

public class Home extends AppCompatActivity {

    private SwipeDisableViewPager viewPager;
    HomeFragment homeFragment;
    RiwayatFragment riwayatFragment;
    ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom);
        viewPager = (SwipeDisableViewPager) findViewById(R.id.main_view);
        viewPager.setPagingEnable(false);//agar tidak bisa swipe

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navhome:
                        viewPager.setCurrentItem(0,false);
                        return true;
                    case R.id.navRiwayat:
                        viewPager.setCurrentItem(1,false);
                        return true;
                    case R.id.navprofile:
                        viewPager.setCurrentItem(2,false);
                        return true;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                bottomNavigationView.getMenu().getItem(i).setChecked(false);
            }

            @Override
            public void onPageSelected(int i) {
                Log.d("page", "onPageSelected: " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setupViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        riwayatFragment = new RiwayatFragment();
        profileFragment = new ProfileFragment();
        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(riwayatFragment);
        viewPagerAdapter.addFragment(profileFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }

}
