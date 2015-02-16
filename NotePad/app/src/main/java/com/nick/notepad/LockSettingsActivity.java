package com.nick.notepad;

import com.nick.patternlocklibrary.pattern.PatternSettingsActivity;
import com.nick.patternlocklibrary.widget.LockPatternView;

import java.util.List;

public class LockSettingsActivity extends PatternSettingsActivity {

    @Override
    protected int getThemeOverlay() {
        return super.getThemeOverlay();
    }

    @Override
    protected boolean hideActionBar() {
        return false;
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

    /**
     * Called when in pattern settings activity, that user has successfully
     * created a pattern, We can save this pattern.
     *
     * @param pattern Created pattern.
     */
    @Override
    public void onPatternCreated(List<LockPatternView.Cell> pattern) {
        String patternStr = getPatternUtils().patternToString(pattern);
        new PreferenceHelper(this).savePattern(patternStr);
        new PreferenceHelper(this).setPatternLockActivate(true);
    }
}
