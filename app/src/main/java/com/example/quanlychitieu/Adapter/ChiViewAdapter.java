package com.example.quanlychitieu.Adapter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quanlychitieu.Fragment.Fragment_Khoan_Chi;
import com.example.quanlychitieu.Fragment.Fragment_Loai_Chi;


public class ChiViewAdapter extends FragmentStatePagerAdapter {
    int soLuongTab = 2;

    public ChiViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment_Loai_Chi();
            case 1:
                return new Fragment_Khoan_Chi();
            default:
                return new Fragment_Loai_Chi();
        }
    }

    @Override
    public int getCount() {
        return soLuongTab;
    }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title="";
            switch (position) {
                case 0:
                    title = "Loại Chi";
                    break;
                case 1:
                    title = "Khoản Chi";
                    break;
            }
            return title;
        }
}
