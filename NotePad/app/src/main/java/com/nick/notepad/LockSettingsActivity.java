package com.nick.notepad;

import com.nick.patternlocklibrary.pattern.PatternSettingsActivity;

public class LockSettingsActivity extends PatternSettingsActivity {

    @Override
    protected int getThemeOverlay() {
        return super.getThemeOverlay();
    }

    @Override
    protected boolean hideActionBar() {
        return true;
    }

    @Override
    protected int getContainerId() {
        return R.id.container;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_note_pad;
    }

    @Override
    protected void doOnCreate() {

    }
}
