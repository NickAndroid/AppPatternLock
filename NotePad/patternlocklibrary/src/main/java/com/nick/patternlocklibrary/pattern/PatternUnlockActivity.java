package com.nick.patternlocklibrary.pattern;

import android.support.annotation.NonNull;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.widget.LockPatternView;
import com.nick.patternlocklibrary.widget.PatternMode;

import java.util.List;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public abstract class PatternUnlockActivity extends PatternLockActivity {

    @NonNull
    @Override
    protected PatternLockFragment onCreatePattenFragment() {
        return new PatternUnLockFragment().setPatternMode(PatternMode.UNLOCK);
    }

    @Override
    public void onPatternMatch() {
        finish();
    }

    @Override
    public void onPatternError() {
        super.onPatternError();
    }

    @Override
    protected boolean getIsLockEnabled() {
        return true;
    }

    @Override
    protected int getThemeOverlay() {
        return getIsLockEnabled()
                ? R.style.Theme_AppCompat_Light_NoActionBar
                : R.style.Theme_AppCompat_Light;
    }

    @Override
    public final void onPatternCreated(List<LockPatternView.Cell> pattern) {
        // EMPTY.
    }
}
