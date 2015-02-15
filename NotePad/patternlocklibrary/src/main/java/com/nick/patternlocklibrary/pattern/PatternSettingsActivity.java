package com.nick.patternlocklibrary.pattern;

import android.support.annotation.NonNull;

import com.nick.patternlocklibrary.widget.PatternMode;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public abstract class PatternSettingsActivity extends PatternLockActivity {

    @NonNull
    @Override
    protected PatternLockFragment onCreatePattenFragment() {
        return new PatternSettingsFragment().setPatternMode(PatternMode.EDIT);
    }

    @Override
    public void onPatternMatch() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void removePatternLock() {
        super.removePatternLock();
        finish();
    }
}
