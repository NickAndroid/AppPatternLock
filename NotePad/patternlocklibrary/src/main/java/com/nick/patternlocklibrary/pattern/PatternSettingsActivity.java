package com.nick.patternlocklibrary.pattern;

import com.nick.patternlocklibrary.widget.PatternMode;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public abstract class PatternSettingsActivity extends PatternLockActivity {

    @Override
    protected PatternLockFragment onCreatePattenFragment() {
        return PatternSettingsFragment.getInstance(this).setPatternMode(PatternMode.EDIT);
    }
}
