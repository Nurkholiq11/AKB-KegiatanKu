package com.nurkholiq.kegiatanku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(view -> {
            if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    private void setOnboardingItems() {
        List<com.nurkholiq.kegiatanku.OnboardingItem> onboardingItems = new ArrayList<>();

        com.nurkholiq.kegiatanku.OnboardingItem onboarding1 = new com.nurkholiq.kegiatanku.OnboardingItem();
        onboarding1.setTitle("Buat jadwal Anda");
        onboarding1.setDescription("Atur jadwal penting Anda dengan baik di tempat kerja untuk mempermudah pekerjaan Anda nantinya.");
        onboarding1.setImage(R.drawable.vector_schedule);

        com.nurkholiq.kegiatanku.OnboardingItem onboarding2 = new com.nurkholiq.kegiatanku.OnboardingItem();
        onboarding2.setTitle("Kelola tugas dengan mudah");
        onboarding2.setDescription("Anda dapat dengan mudah mengatur pekerjaan dan jadwal Anda dengan baik sehingga Anda lebih nyaman saat melakukan pekerjaan.");
        onboarding2.setImage(R.drawable.vector_manage);

        com.nurkholiq.kegiatanku.OnboardingItem onboarding3 = new com.nurkholiq.kegiatanku.OnboardingItem();
        onboarding3.setTitle("Siap mulai harimu");
        onboarding3.setDescription("Dan setelah semua jadwal Anda sudah tertata dengan baik dan rapi, kini Anda siap untuk memulai hari dengan menyenangkan.\n\n`" +
                "Nikmati harimu.");
        onboarding3.setImage(R.drawable.vector_start);

        onboardingItems.add(onboarding1);
        onboardingItems.add(onboarding2);
        onboardingItems.add(onboarding3);

        onboardingAdapter = new com.nurkholiq.kegiatanku.OnboardingAdapter(onboardingItems);

    }

    private void setOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index) {
        int childcount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childcount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }

        if (index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("Mulai");
        } else {
            buttonOnboardingAction.setText("Lanjutkan");
        }

    }

}