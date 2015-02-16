package com.nick.patternlocklibrary.pattern;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.widget.LockPatternView;
import com.nick.patternlocklibrary.widget.PatternMode;
import com.nick.patternlocklibrary.widget.PatternUtils;
import com.nick.patternlocklibrary.widget.ViewAnimator;

import java.util.List;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public class PatternLockFragment extends Fragment {

    public static final int MIN_POINT_COUNT = 3;
    public static final int MAX_POINT_COUNT = 9;
    public static final int MAX_RETRY_COUNT = 5;

    public interface PatternLockCallback {

        /**
         * Called when pattern view shown.
         */
        abstract void onPatternLockShow();

        /**
         * Called when pattern view removed.
         */
        abstract void onPatternLockRemoved();

        /**
         * Called every time pattern draw error times over MIN.
         */
        abstract void onPatternError();

        /**
         * Called when the pattern from user valid.
         *
         * @see #isPatternValid(java.util.List)
         */
        abstract void onPatternMatch();

        /**
         * Called when in pattern settings activity, that user has successfully
         * created a pattern, We can save this pattern.
         *
         * @param pattern Created pattern.
         */
        abstract void onPatternCreated(List<LockPatternView.Cell> pattern);

        /**
         * @param pattern The pattern from user.
         * @return If the pattern from user is valid or correct, if true,
         * we will unlock it, and call the
         * @see #onPatternMatch()
         */
        abstract boolean isPatternValid(List<LockPatternView.Cell> pattern);
    }

    private static final String TAG = "nick.pattern.lib";

    private Resources mRes;

    private View mRootView;
    private FrameLayout mMaskView;
    private TextView mTipView;
    private ImageView mLockLogoView;
    private LockPatternView mPatternView;
    private PatternUtils mPatternUtils;
    private ViewAnimator mAnimUtils;

    private PatternLockCallback mLockCallback;

    private PatternMode mPatternMode;

    private int mUnlockLogoId, mLockLogoId;

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

    @Override
    public void onDetach() {
        super.onDetach();
        if (mLockCallback != null) {
            mLockCallback.onPatternLockRemoved();
        }
    }

    public boolean onBackPressed() {
        return isReadyToBeRemoved();
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
        mAnimUtils = new ViewAnimator(getActivity());
        mPatternView = (LockPatternView) mRootView.findViewById(R.id.pattern);
        mPatternView.setSaveEnabled(false);
        mTipView = (TextView) mRootView.findViewById(R.id.tips);
        mLockLogoView = (ImageView) mRootView.findViewById(R.id.lock);
        mLockLogoView.setImageResource(mLockLogoId);
        mMaskView = (FrameLayout) mRootView.findViewById(R.id.mask);
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

    public FrameLayout getMaskView() {
        return mMaskView;
    }

    public PatternLockCallback getLockCallback() {
        return mLockCallback;
    }

    public PatternUtils getPatternUtils() {
        return mPatternUtils;
    }

    public PatternMode getPatternMode() {
        return mPatternMode;
    }

    public ViewAnimator getAnimUtils() {
        return mAnimUtils;
    }

    public boolean isReadyToBeRemoved() {
        return true;
    }

    public void die() {
        mPatternView.disableInput();
    }

    public PatternLockFragment setPatternMode(PatternMode mode) {
        this.mPatternMode = PatternMode.EDIT;
        return this;
    }

    public PatternLockFragment setLockLogoId(int lockLogoId) {
        this.mLockLogoId = lockLogoId;
        return this;
    }

    public PatternLockFragment setUnlockLogoId(int unLockLogoId) {
        this.mUnlockLogoId = unLockLogoId;
        return this;
    }

    public int getLockLogoId() {
        return mLockLogoId;
    }

    public int getUnlockLogoId() {
        return mUnlockLogoId;
    }
}
