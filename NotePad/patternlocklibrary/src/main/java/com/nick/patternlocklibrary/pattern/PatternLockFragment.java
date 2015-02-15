package com.nick.patternlocklibrary.pattern;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.manager.PreferenceHelper;
import com.nick.patternlocklibrary.widget.AnimateUtils;
import com.nick.patternlocklibrary.widget.LockPatternView;
import com.nick.patternlocklibrary.widget.PatternMode;
import com.nick.patternlocklibrary.widget.PatternUtils;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public class PatternLockFragment extends Fragment {

    public static final int MIN_POINT_COUNT = 3;
    public static final int MAX_POINT_COUNT = 9;
    public static final int MAX_RETTY_COUNT = 5;

    public interface PatternLockCallback {
        abstract void removePatternLock();

        abstract void onPatternLockShow();

        abstract void onPatternSaved();

        abstract void onPatternError();

        abstract void onPatternMatch();
    }

    private static final String TAG = "nick.pattern.lib";

    private Resources mRes;

    private View mRootView;
    private TextView mTipView;
    private ImageView mLockLogoView;
    private LockPatternView mPatternView;
    private PatternUtils mPatternUtils;
    private AnimateUtils mAnimUtils;
    private PreferenceHelper mPreferenceHelper;

    private PatternLockCallback mLockCallback;

    private PatternMode mPatternMode;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PatternLockCallback) {
            mLockCallback = (PatternLockCallback) activity;
        } else {
            throw new IllegalArgumentException(
                    "The calling activity should implement the PatternLockCallback interface.");
        }
        mLockCallback.onPatternLockShow();
        mRes = activity.getResources();
    }

    public boolean onBackPressed() {
        if (isReadyToBeRemoved()) {
            mLockCallback.removePatternLock();
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_pattern, container,
                false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    protected void initView() {
        mPatternUtils = new PatternUtils();
        mAnimUtils = new AnimateUtils(getActivity());
        mPreferenceHelper = new PreferenceHelper(getActivity());
        mPatternView = (LockPatternView) mRootView.findViewById(R.id.pattern);
        mPatternView.setSaveEnabled(false);
        mTipView = (TextView) mRootView.findViewById(R.id.tips);
        mLockLogoView = (ImageView) mRootView.findViewById(R.id.lock);
    }

    public Resources getRes() {
        return mRes;
    }

    public LockPatternView getPatternView() {
        return mPatternView;
    }

    public TextView getTipView() {
        return mTipView;
    }

    public ImageView getLockLogoView() {
        return mLockLogoView;
    }

    public View getRootView() {
        return mRootView;
    }

    public PatternLockCallback getLockCallback() {
        return mLockCallback;
    }

    public PreferenceHelper getPreferenceHelper() {
        return mPreferenceHelper;
    }

    public PatternUtils getPatternUtils() {
        return mPatternUtils;
    }

    public PatternMode getPatternMode() {
        return mPatternMode;
    }

    public PatternLockFragment setPatternMode(PatternMode mode) {
        this.mPatternMode = PatternMode.EDIT;
        return this;
    }

    public AnimateUtils getAnimUtils() {
        return mAnimUtils;
    }

    public boolean isReadyToBeRemoved() {
        return false;
    }

    public void die() {
        mPatternView.disableInput();
    }
}
