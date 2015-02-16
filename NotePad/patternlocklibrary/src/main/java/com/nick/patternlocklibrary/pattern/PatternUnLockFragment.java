package com.nick.patternlocklibrary.pattern;

import android.animation.Animator;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.widget.LockPatternView;

import java.util.List;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public class PatternUnLockFragment extends PatternLockFragment {
    private static final String TAG = PatternSettingsFragment.class.getName();

    private static final int REDRAW_DELAY = 1000;
    private static int PER_RETRY_DELAY = 60 * 1000;//ms
    private static int WAIT_TIME_FACTOR = 2;

    private int mTriedCount = 0;
    private int mErrorTimes = 0;
    private int mPendingWaitTimes = 0;

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
            getPatternView().disableInput();
            if (getLockCallback().isPatternValid(pattern)) {
                getPatternView().setDisplayMode(LockPatternView.DisplayMode.Correct);
                getAnimUtils().runFlipHorizonAnimation(getLockLogoView(), new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        getLockLogoView().setImageResource(getUnlockLogoId());
                        getPatternView().clearPattern();
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
                mTriedCount++;
                if (mTriedCount <= MAX_RETRY_COUNT) {
                    getPatternView().setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    getAnimUtils().animateTextView(getTipView(), R.string.tips_not_match);
                    getAnimUtils().shakeView(getLockLogoView(), new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            getAnimUtils().animateTextView(getTipView(), R.string.draw_to_unlock);
                            getPatternView().clearPattern();
                            getPatternView().enableInput();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    maskInterface();
                    getLockCallback().onPatternError();
                    mErrorTimes++;
                    mPendingWaitTimes = mErrorTimes * WAIT_TIME_FACTOR;
                    getPatternView().clearPattern();
                    if (mHandler == null) {
                        mHandler = new Handler();
                    }
                    getAnimUtils().animateTextView(getTipView(), retrieveWaitStr());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPendingWaitTimes--;
                            if (mPendingWaitTimes == 0) {
                                onTik();
                                return;
                            }
                            getAnimUtils().animateTextView(getTipView(), retrieveWaitStr());
                            mHandler.postDelayed(this, PER_RETRY_DELAY);
                        }
                    }, PER_RETRY_DELAY);
                }
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        getPatternView().setOnPatternListener(new PatterListener());
        getTipView().setText(R.string.draw_to_unlock);
    }

    private void onTik() {
        mTriedCount = 0;
        mPendingWaitTimes = 0;
        getPatternView().enableInput();
        getAnimUtils().animateTextView(getTipView(), R.string.draw_to_unlock);
        removeMask();
    }

    private void maskInterface() {
        getMaskView().startAnimation(getAnimUtils().getShowAnim());
        getMaskView().setBackgroundColor(getRes().getColor(R.color.trans_dark));
    }

    private void removeMask() {
        getMaskView().startAnimation(getAnimUtils().getDismissAnim());
        getMaskView().setBackgroundColor(getRes().getColor(android.R.color.transparent));
    }

    private String retrieveWaitStr() {
        String prefix = getRes().getString(R.string.tips_retry_after);
        String endfix = getRes().getString(R.string.retry_time_unit);
        String minCount = String.valueOf(mPendingWaitTimes);
        return prefix + minCount + endfix;
    }
}
