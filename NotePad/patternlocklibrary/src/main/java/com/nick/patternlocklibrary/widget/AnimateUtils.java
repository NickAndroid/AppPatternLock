package com.nick.patternlocklibrary.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.nick.patternlocklibrary.R;

public class AnimateUtils {

    private AlphaAnimation mDismissAnim, mShowAnim;
    private Animation mShakeAnim;

    private Resources mRes;

    public AnimateUtils(Context context) {
        init(context);
    }

    private void init(Context context) {
        mRes = context.getResources();
        mDismissAnim = new AlphaAnimation(1f, 0f);
        mShowAnim = new AlphaAnimation(0f, 1f);
        mDismissAnim.setDuration(250);
        mShowAnim.setDuration(250);
        Interpolator acc = AnimationUtils.loadInterpolator(context, android.R.interpolator.accelerate_decelerate);
        mDismissAnim.setInterpolator(acc);
        mShowAnim.setInterpolator(acc);
        mShakeAnim = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    public void animateTextView(final TextView textView, final int textToSet) {
        animateTextView(textView, mRes.getString(textToSet));
    }

    public void animateTextView(final TextView textView, final String textToSet) {
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

    public void runFlipHorizonAnimation(View view, Animator.AnimatorListener listener) {
        view.setAlpha(0);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view,
                "rotationY", -180f, 0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "alpha",
                0f, 1f);
        set.setDuration(500);
        set.addListener(listener);
        set.playTogether(objectAnimator1, objectAnimator2);
        set.start();
    }

    public void shakeView(View v, Animation.AnimationListener listener) {
        mShakeAnim.setAnimationListener(listener);
        v.startAnimation(mShakeAnim);
    }
}
