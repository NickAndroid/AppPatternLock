package com.nick.patternlocklibrary.pattern;

import com.nick.patternlocklibrary.widget.LockPatternView;

import java.util.List;

/**
 * Email: nick_guo@foxmail.com
 * Created by nick on 15-2-14.
 */
public class PatternSettingsFragment extends PatternLockFragment {

    private class PatterListener implements LockPatternView.OnPatternListener{

        @Override
        public void onPatternStart() {

        }

        @Override
        public void onPatternCleared() {

        }

        @Override
        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        @Override
        public void onPatternDetected(List<LockPatternView.Cell> pattern) {

        }
    }
}
