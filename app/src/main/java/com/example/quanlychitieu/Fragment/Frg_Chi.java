package com.example.quanlychitieu.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.quanlychitieu.Adapter.ChiViewAdapter;
import com.example.quanlychitieu.R;
import com.google.android.material.tabs.TabLayout;



public class Frg_Chi extends Fragment{
    TabLayout tabLayout;
    ViewPager viewPager;
    ChiViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi, container, false);
        viewPager = view.findViewById(R.id.viewpager_chi);
        tabLayout = view.findViewById(R.id.tablayout_chi);
        adapter = new ChiViewAdapter(getActivity().getSupportFragmentManager());
        //set adapter cho viewpager
        viewPager.setAdapter(adapter);
        //set viewpager vao tablayout
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
