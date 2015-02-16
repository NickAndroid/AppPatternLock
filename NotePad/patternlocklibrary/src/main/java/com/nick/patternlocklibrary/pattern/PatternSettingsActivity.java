package com.nick.patternlocklibrary.pattern;

import android.support.annotation.NonNull;

import com.nick.patternlocklibrary.widget.LockPatternView;
import com.nick.patternlocklibrary.widget.PatternMode;

import java.util.List;

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
    public final boolean isPatternValid(List<LockPatternView.Cell> pattern) {
        return false;
    }
}
