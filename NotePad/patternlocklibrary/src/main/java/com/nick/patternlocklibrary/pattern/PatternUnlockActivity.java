package com.nick.patternlocklibrary.pattern;

import com.nick.patternlocklibrary.widget.PatternMode;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public abstract class PatternUnlockActivity extends PatternLockActivity {

    @Override
    protected final PatternLockFragment onCreatePattenFragment() {
        return PatternUnLockFragment.getInstance(this).setPatternMode(PatternMode.UNLOCK);
    }
}
