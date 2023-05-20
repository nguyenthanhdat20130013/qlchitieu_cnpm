package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.quanlychitieu.R;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getPaperOnboardingPageData());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_farme, paperOnboardingFragment);
        fragmentTransaction.commit();

        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList<PaperOnboardingPage> getPaperOnboardingPageData(){
        PaperOnboardingPage scr1 = new PaperOnboardingPage("",
                "Chào mừng bạn đến với ứng dụng quản lý chi tiêu. Mong bạn có trải nghiệm tốt",
                Color.parseColor("#a7cbd7"), R.drawable.step0, R.drawable.icnext);


        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);

        return elements;
    }
}