package com.nick.patternlocklibrary.pattern;

import android.animation.Animator;
import android.os.Handler;
import android.util.Log;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.widget.LockPatternView;

import java.util.List;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public class PatternSettingsFragment extends PatternLockFragment {

    private static final String TAG = PatternSettingsFragment.class.getName();

    private static final int REDRAW_DELAY = 1000;

    private boolean mIsContinuing;

    private String mTmpPatternStr;

    private Handler mHandler;

    private class PatterListener implements LockPatternView.OnPatternListener {

        @Override
        public void onPatternStart() {

        }

        @Override
        public void onPatternCleared() {

        }

        @Override
        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        @Override
        public void onPatternDetected(List<LockPatternView.Cell> pattern) {

            Log.d(TAG, "onPatternDetected");

            if (mIsContinuing) {
                getPatternView().disableInput();
                String patStr = getPatternUtils().patternToString(pattern);
                if (patStr.equals(mTmpPatternStr)) {
                    getPreferenceHelper().savePattern(patStr);
                    if (patStr.equals(getPreferenceHelper().getSavedPattern())) {
                        Log.d(TAG, "pattern saved correct! ");
                    }
                    getPatternView().setDisplayMode(LockPatternView.DisplayMode.Correct);
                    getAnimUtils().runFlipHorizonAnimation(getLockLogoView(), new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            getLockCallback().onPatternMatch();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                } else {
                    mTmpPatternStr = null;
                    mIsContinuing = false;
                    getPatternView().setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    getAnimUtils().animateTextView(getTipView(), R.string.tips_not_match);
                    if (mHandler == null) {
                        mHandler = new Handler();
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getAnimUtils().animateTextView(getTipView(), R.string.draw_pattern);
                            getPatternView().enableInput();
                            getPatternView().clearPattern();
                        }
                    }, REDRAW_DELAY);

                }
                return;
            }


            if (pattern.size() < MIN_POINT_COUNT) {
                getPatternView().disableInput();
                getAnimUtils().animateTextView(getTipView(), R.string.tips_point_count);
                getPatternView().setDisplayMode(LockPatternView.DisplayMode.Wrong);
                if (mHandler == null) {
                    mHandler = new Handler();
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPatternView().clearPattern();
                        getAnimUtils().animateTextView(getTipView(), R.string.draw_pattern);
                        mTmpPatternStr = null;
                        mIsContinuing = false;
                        getPatternView().enableInput();
                    }
                }, REDRAW_DELAY);
                return;
            }
            getPatternView().setDisplayMode(LockPatternView.DisplayMode.Correct);
            getPatternView().disableInput();
            mTmpPatternStr = getPatternUtils().patternToString(pattern);
            getAnimUtils().animateTextView(getTipView(), R.string.tips_pattern_record);
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getPatternView().clearPattern();
                    getPatternView().enableInput();
                    mIsContinuing = true;
                }
            }, REDRAW_DELAY);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        getPatternView().setOnPatternListener(new PatterListener());
        getAnimUtils().animateTextView(getTipView(), R.string.draw_pattern);
    }
}
