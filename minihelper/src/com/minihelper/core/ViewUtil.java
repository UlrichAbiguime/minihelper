package com.minihelper.core;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.view.View;

/**
 * Utilities for dealing with {@link View} objects and their sub-classes
 */
public class ViewUtil {
    /**
     * Toggles the view's visibility between {@link View#VISIBLE} and
     * {@link View#GONE} depending on the given gone flag
     *
     * @param view
     * @param gone
     */
    public static void setGone(final View view, final boolean gone) {
        if (view == null)
            return;

        final int current = view.getVisibility();
        if (gone && current != GONE)
            view.setVisibility(GONE);
        else if (!gone && current != VISIBLE)
            view.setVisibility(VISIBLE);
    }
}