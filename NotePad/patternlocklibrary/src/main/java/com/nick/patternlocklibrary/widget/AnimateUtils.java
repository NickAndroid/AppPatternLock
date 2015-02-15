package com.nick.patternlocklibrary.widget;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class AnimateUtils {

    private ScaleAnimation mDismissAnim, mShowAnim;

    public AnimateUtils(Context context) {
        init(context);
    }

    private void init(Context context) {
        mDismissAnim = new ScaleAnimation(1f, 0f, 1f, 0f);
        mShowAnim = new ScaleAnimation(0f, 1f, 0f, 1f);
        mDismissAnim.setDuration(300);
        mShowAnim.setDuration(300);
        Interpolator acc = AnimationUtils.loadInterpolator(context, android.R.interpolator.accelerate_decelerate);
        mDismissAnim.setInterpolator(acc);
        mShowAnim.setInterpolator(acc);
    }

    public void animateTextView(final TextView textView, final int textToSet) {
        mDismissAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.startAnimation(mShowAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mShowAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.setText(textToSet);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(mDismissAnim);
    }
}
