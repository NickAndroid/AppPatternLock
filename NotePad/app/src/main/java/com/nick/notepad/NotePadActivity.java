package com.nick.notepad;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.nick.patternlocklibrary.pattern.PatternUnlockActivity;
import com.nick.patternlocklibrary.widget.LockPatternView;

import java.util.List;

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
        return getIsLockEnabled();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_note_pad;
    }

    @Override
    protected void doOnCreate() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!getIsLockEnabled()) {
            finish();
        }
    }

    /**
     * @param pattern The pattern from user.
     * @return If the pattern from user is valid or correct, if true,
     * we will unlock it, and call the
     * @see #onPatternMatch()
     */
    @Override
    public boolean isPatternValid(List<LockPatternView.Cell> pattern) {
        return getPatternUtils().patternToString(pattern)
                .equals(new PreferenceHelper(this).getSavedPattern());
    }

    @Override
    public void onPatternMatch() {
        removeLock();
    }

    @Override
    protected boolean getIsLockEnabled() {
        return new PreferenceHelper(this).isPatternLockActivate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(this, LockSettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}