package com.nick.patternlocklibrary.pattern;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.manager.PreferenceHelper;
import com.nick.patternlocklibrary.widget.LockPatternView;
import com.nick.patternlocklibrary.widget.PatternMode;
import com.nick.patternlocklibrary.widget.PatternUtils;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public class PatternLockFragment extends Fragment {

    public interface PatternLockCallback {
        abstract void removePatternLock();

        abstract void onPatternLockShow();

        abstract void onPatternSaved();

        abstract void onPatternError();
    }

    private static final String TAG = "nick.pattern.lib";

    private LockPatternView mPatternView;
    private PatternUtils mPatternUtils;
    private PreferenceHelper mPreferenceHelper;

    private PatternLockCallback mLockCallback;

    private PatternMode mPatternMode;

    private boolean mIsContinuing;

    public static PatternLockFragment getInstance(
            Context context) {
        if (context == null) {
            throw new SecurityException("Should has a token.");
        }
        return new PatternLockFragment();
    }

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
        View rootView = inflater.inflate(R.layout.layout_pattern, container,
                false);
        init(rootView);
        return rootView;
    }

    protected void init(View rootView) {
        mPatternUtils = new PatternUtils();
        mPreferenceHelper = new PreferenceHelper(getActivity());
        mPatternView = (LockPatternView) rootView.findViewById(R.id.pattern);
        mPatternView.setSaveEnabled(false);
    }

    public LockPatternView getPatternView() {
        return mPatternView;
    }

    public PatternLockFragment setPatternMode(PatternMode mode) {
        this.mPatternMode = PatternMode.EDIT;
        return this;
    }

    public boolean isReadyToBeRemoved() {
        return true;
    }

    public void die() {
        mPatternView.disableInput();
    }
}
