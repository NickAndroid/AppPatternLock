package dev.nick.apppatternlock.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceHelper {

	private static final String SAVED_PATTERN_LOCK = "dev.nick.pattern.lock";
	private static final String ACTIVATE_PATTERN_LOCK = "dev.nick.lock.activate";

	private Context mContext;

	public PreferenceHelper(Context context) {
		this.mContext = context;
	}

	public String getSavedPattern() {
		SharedPreferences s = mContext.getSharedPreferences(SAVED_PATTERN_LOCK,
				Context.MODE_PRIVATE);
		return s.getString(SAVED_PATTERN_LOCK, null);
	}

	public void savePattern(String pattern) {
		SharedPreferences s = mContext.getSharedPreferences(SAVED_PATTERN_LOCK,
				Context.MODE_PRIVATE);
		Editor editor = s.edit();
		editor.putString(SAVED_PATTERN_LOCK, pattern);
		editor.apply();
	}

	public boolean isPatternLockActivate() {
		SharedPreferences s = mContext.getSharedPreferences(
				ACTIVATE_PATTERN_LOCK, Context.MODE_PRIVATE);
		return s.getBoolean(ACTIVATE_PATTERN_LOCK, false);
	}

	public void setPatternLockActivate(boolean active) {
		SharedPreferences s = mContext.getSharedPreferences(
				ACTIVATE_PATTERN_LOCK, Context.MODE_PRIVATE);
		Editor editor = s.edit();
		editor.putBoolean(SAVED_PATTERN_LOCK, active);
		editor.apply();
	}
}
