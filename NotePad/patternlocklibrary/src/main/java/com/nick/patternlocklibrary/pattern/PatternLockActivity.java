package com.nick.patternlocklibrary.pattern;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.widget.PatternUtils;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public abstract class PatternLockActivity extends ActionBarActivity implements
        PatternLockFragment.PatternLockCallback {

    private boolean mActivityAlive;

    private boolean mLockShowing;

    private PatternLockFragment mLockFragment;

    private PatternUtils mPatternUtils;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        if (getThemeOverlay() > 0) {
            setTheme(getThemeOverlay());
        } else if (hideActionBar()) {
            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        } else {
            setTheme(R.style.Theme_AppCompat_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initInternal();
        doOnCreate();
    }

    protected abstract void doOnCreate();

    protected abstract int getContentViewId();

    private void initInternal() {
        mActivityAlive = true;
    }

    protected void checkLockState() {
        if (mActivityAlive && getIsLockEnabled() && !mLockShowing) {
            if (mLockFragment == null) {
                mLockFragment = onCreatePattenFragment()
                        .setLockLogoId(getLockedLogoId())
                        .setUnlockLogoId(getUnLockLogId());
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(getContainerId(), mLockFragment).commit();
        }
    }

    protected abstract
    @NonNull
    PatternLockFragment onCreatePattenFragment();

    protected void removeLock() {
        if (mActivityAlive && mLockFragment != null
                && mLockFragment.isReadyToBeRemoved()) {
            mLockFragment.die();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .setCustomAnimations(0,
                            getTransactionAnimation())
                    .remove(mLockFragment)
                    .commit();
        }
    }

    protected abstract int getContainerId();

    protected int getTransactionAnimation() {
        return R.anim.slide_down;
    }

    protected boolean hideActionBar() {
        return false;
    }

    protected int getThemeOverlay() {
        return -1;
    }

    protected int getUnLockLogId() {
        return R.drawable.unlocked;
    }

    protected int getLockedLogoId() {
        return R.drawable.unlocked;
    }

    @Override
    public void onBackPressed() {
        if (mLockFragment != null && mLockFragment.isVisible()) {
            mLockFragment.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityAlive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityAlive = true;
        checkLockState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityAlive = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityAlive = false;
    }

    @Override
    public void onPatternLockShow() {
        mLockShowing = true;
    }

    @Override
    public void onPatternLockRemoved() {
        mLockShowing = false;
    }

    @Override
    public void onPatternError() {

    }

    protected boolean getIsLockEnabled() {
        return true;
    }

    public PatternUtils getPatternUtils() {
        if (mPatternUtils == null) {
            mPatternUtils = new PatternUtils();
        }
        return mPatternUtils;
    }
}
