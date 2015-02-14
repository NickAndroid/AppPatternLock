package dev.nick.apppatternlock;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import dev.nick.apppatternlock.PatternLockFragment.PatternLockCallback;
import dev.nick.apppatternlock.manager.PreferenceHelper;
import dev.nick.apppatternlock.pattern.PatternMode;

public abstract class PatternLockActivity extends ActionBarActivity implements
		PatternLockCallback {

	private boolean mActivityAlive;

	private PatternLockFragment mLockFragment;

	private PreferenceHelper mPreferenceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initInternel();
	}

	private void initInternel() {
		mPreferenceHelper = new PreferenceHelper(this);
	}

	protected void checkLockState() {
		if (mLockFragment == null) {
			mLockFragment = PatternLockFragment.getInstance(this)
					.setPatternMode(PatternMode.UNLOCK);
		}
		if (mActivityAlive && mPreferenceHelper.isPatternLockActivate()) {
			getSupportFragmentManager().beginTransaction()
					.replace(getContainerId(), mLockFragment).commit();
		}
	}

	protected void removeLock() {
		if (mActivityAlive && mLockFragment != null
				&& mLockFragment.isReadyToBeRemoved()) {
			mLockFragment.die();
			getSupportFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.anim.nothing,
							getTransactionAnimation()).remove(mLockFragment)
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
