package com.nick.notepad;


import com.nick.patternlocklibrary.pattern.PatternUnlockActivity;

public class NotePadActivity extends PatternUnlockActivity {

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