package com.example.starwarsinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.starwarsinfo.Fragments.PeopleFragment;
import com.example.starwarsinfo.Fragments.PlanetFragment;
import com.example.starwarsinfo.Fragments.VehicleFragment;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        ViewPager viewPager = findViewById(R.id.vpSW);
        FragmentPagerAdapter vpAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
    }
}

class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PeopleFragment();
            case 1:
                return new PlanetFragment();
            case 2:
                return new VehicleFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Personajes";
            case 1:
                return "Planetas";
            case 2:
                return "Vehiculos";
            default:
                return "";
        }
    }


}
