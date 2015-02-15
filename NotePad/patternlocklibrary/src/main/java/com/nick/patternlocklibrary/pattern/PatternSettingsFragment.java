package com.nick.patternlocklibrary.pattern;

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

    private static final int REDRAW_DELAY = 1500;

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
                    getPatternView().setDisplayMode(LockPatternView.DisplayMode.Correct);
                    getLockCallback().removePatternLock();
                } else {
                    mTmpPatternStr = null;
                    mIsContinuing = false;
                    getPatternView().setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    getAnimUtils().animateTextView(getTipView(), R.string.draw_to_unlock);
                    getPatternView().enableInput();
                    getPatternView().clearPattern();
                }
                return;
            }


            if (pattern.size() < MIN_POINT_COUNT) {
                getPatternView().disableInput();
                getAnimUtils().animateTextView(getTipView(), R.string.tips_point_count);
                getPatternView().setDisplayMode(LockPatternView.DisplayMode.Wrong);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPatternView().clearPattern();
                        getAnimUtils().animateTextView(getTipView(), R.string.draw_to_unlock);
                        mTmpPatternStr = null;
                        mIsContinuing = false;
                        getPatternView().enableInput();
                    }
                }, REDRAW_DELAY);
                return;
            }

            getPatternView().disableInput();
            mTmpPatternStr = getPatternUtils().patternToString(pattern);
            getAnimUtils().animateTextView(getTipView(), R.string.tips_pattern_record);
            getPatternView().setDisplayMode(LockPatternView.DisplayMode.Correct);
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getPatternView().clearPattern();
                    getPatternView().enableInput();
                    getAnimUtils().animateTextView(getTipView(), R.string.tips_draw_again);
                    mIsContinuing = true;
                }
            }, REDRAW_DELAY);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        getPatternView().setOnPatternListener(new PatterListener());
    }
}
