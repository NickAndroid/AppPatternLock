package dev.nick.apppatternlock;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.nick.apppatternlock.manager.PreferenceHelper;
import dev.nick.apppatternlock.pattern.LockPatternView;
import dev.nick.apppatternlock.pattern.LockPatternView.Cell;
import dev.nick.apppatternlock.pattern.LockPatternView.DisplayMode;
import dev.nick.apppatternlock.pattern.LockPatternView.OnPatternListener;
import dev.nick.apppatternlock.pattern.PatternMode;
import dev.nick.apppatternlock.pattern.PatternUtils;

public class PatternLockFragment extends Fragment {

	public interface PatternLockCallback {
		abstract void removePatternLock();

		abstract void onPatternLockShow();

		abstract void onPatternSaved();

		abstract void onPatternError();
	}

	private static final String TAG = "nick.pattern.lib";

	private LockPatternView mPatternView;
	private PatternUtils mPatternUtils;
	private PreferenceHelper mPreferenceHelper;

	private PatternLockCallback mLockCallback;

	private PatternMode mPatternMode;

	private boolean mIsContinuing;

	private PatternLockFragment() {
	}

	public static PatternLockFragment getInstance(
			Context context) {
		if (context == null) {
			throw new SecurityException("Should has a token.");
		}
		return new PatternLockFragment();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof PatternLockCallback) {
			mLockCallback = (PatternLockCallback) activity;
		} else {
			throw new IllegalArgumentException(
					"The calling activity should implement the PatternLockCallback interface.");
		}
		mLockCallback.onPatternLockShow();
	}

	public boolean onBackPressed() {
		if (isReadyToBeRemoved()) {
			mLockCallback.removePatternLock();
			return true;
		}
		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout_pattern, container,
				false);
		return rootView;
	}

	protected void init(View rootView) {
		mPatternUtils = new PatternUtils();
		mPreferenceHelper = new PreferenceHelper(getActivity());
		mPatternView = (LockPatternView) rootView.findViewById(R.id.pattern);
		mPatternView.setSaveEnabled(false);
		mPatternView.setOnPatternListener(new PatternListener());
	}

	public PatternLockFragment setPatternMode(PatternMode mode) {
		this.mPatternMode = PatternMode.EDIT;
		return this;
	}

	public boolean isReadyToBeRemoved() {
		return true;
	}

	public void die() {
		mPatternView.disableInput();
	}

	private class PatternListener implements OnPatternListener {

		@Override
		public void onPatternStart() {

		}

		@Override
		public void onPatternCleared() {
			Log.i(TAG, "onPatternCleared");
		}

		@Override
		public void onPatternCellAdded(List<Cell> pattern) {
			Log.i(TAG, "onPatternCellAdded");
		}

		@Override
		public void onPatternDetected(List<Cell> pattern) {
			mPatternView.setDisplayMode(DisplayMode.Wrong);
		}

	}
}
