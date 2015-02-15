package com.nick.patternlocklibrary.pattern;

import android.support.annotation.NonNull;

import com.nick.patternlocklibrary.widget.PatternMode;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public abstract class PatternUnlockActivity extends PatternLockActivity {

    @NonNull
    @Override
    protected final PatternLockFragment onCreatePattenFragment() {
        return new PatternLockFragment().setPatternMode(PatternMode.UNLOCK);
    }
}
