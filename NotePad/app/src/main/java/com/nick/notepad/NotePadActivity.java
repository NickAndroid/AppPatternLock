package com.nick.notepad;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowId;

import com.nick.patternlocklibrary.pattern.PatternUnlockActivity;

public class NotePadActivity extends PatternUnlockActivity {

    @Override
    protected int getContainerId() {
        return R.id.container;
    }

    @Override
    protected int getThemeOverlay() {
        return super.getThemeOverlay();
    }

    @Override
    protected boolean hideActionBar() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_note_pad;
    }

    @Override
    protected void doOnCreate() {

    }

}