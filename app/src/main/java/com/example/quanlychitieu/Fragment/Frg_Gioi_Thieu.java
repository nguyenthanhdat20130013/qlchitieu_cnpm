package com.example.quanlychitieu.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.R;


public class Frg_Gioi_Thieu extends Fragment {
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_gioi_thieu, container, false);
        webView = (WebView) view.findViewById(R.id.web_gioi_thieu);
        webView.loadUrl("file:///android_asset/index.html");
        return view;
    }
}
