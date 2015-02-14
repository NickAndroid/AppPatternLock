package com.nick.patternlocklibrary.pattern;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.nick.patternlocklibrary.R;
import com.nick.patternlocklibrary.manager.PreferenceHelper;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public abstract class PatternLockActivity extends ActionBarActivity implements
        PatternLockFragment.PatternLockCallback {

    private boolean mActivityAlive;

    private PatternLockFragment mLockFragment;

    private PreferenceHelper mPreferenceHelper;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light);
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initInternal();
        checkLockState();
        doOnCreate();
    }

    protected abstract void doOnCreate();

    protected abstract int getContentViewId();

    private void initInternal() {
        mPreferenceHelper = new PreferenceHelper(this);
        mActivityAlive = true;
    }

    protected void checkLockState() {
        if (mLockFragment == null) {
            mLockFragment = onCreatePattenFragment();
        }
        if (mActivityAlive && mPreferenceHelper.isPatternLockActivate()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getContainerId(), mLockFragment).commit();
        }
    }

    protected abstract PatternLockFragment onCreatePattenFragment();

    protected void removeLock() {
        if (mActivityAlive && mLockFragment != null
                && mLockFragment.isReadyToBeRemoved()) {
            mLockFragment.die();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .setCustomAnimations(R.anim.slide_down,
                            getTransactionAnimation())
                    .remove(mLockFragment)
                    .commit();
        }
    }

    protected abstract int getContainerId();

    protected int getTransactionAnimation() {
        return R.anim.slide_down;
    }

    @Override
    public void onBackPressed() {
        if (mLockFragment != null && mLockFragment.isVisible()) {
            if (mLockFragment.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
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
    public void removePatternLock() {
        removeLock();
    }

    @Override
    public void onPatternLockShow() {

    }

    @Override
    public void onPatternSaved() {

    }

    @Override
    public void onPatternError() {

    }

}
