package com.lushen.otakureborn.gbaferandomizer;

import android.util.Log;

/**
 * Created by lushe_000 on 7/15/2015.
 */
public class RangedNumericSetting extends RandomizeSetting {
    public static final String RangedNumericSettingType = "RangedNumeric";

    private Integer absoluteMinimum;
    private Integer absoluteMaximum;

    private Integer currentMinimum;
    private Integer currentMaximum;

    public RangedNumericSetting(String title, String description, Integer defaultMinimum, Integer defaultMaximum, Integer minimum, Integer maximum) {
        super(title, description, true);

        this.absoluteMinimum = minimum;
        this.absoluteMaximum = maximum;

        this.currentMinimum = defaultMinimum;
        this.currentMaximum = defaultMaximum;
    }

    public void setMinimum(Integer minimum) {
        if (minimum <= absoluteMaximum) {
            absoluteMinimum = minimum;
        }
        else {
            Log.d("RANGED_NUMERIC_SETTING", "Invalid setting for minimum!");
            return;
        }

        if (currentMinimum < minimum) {
            currentMinimum = minimum;
        }

        if (currentMaximum < currentMinimum) {
            currentMaximum = currentMinimum;
        }
    }

    public void setMaximum(Integer maximum) {
        if (maximum >= absoluteMinimum) {
            absoluteMaximum = maximum;
        }
        else {
            Log.d("RANGED_NUMERIC_SETTING", "Invalid setting for maximum!");
            return;
        }

        if (currentMaximum > maximum) {
            currentMaximum = maximum;
        }

        if (currentMinimum > currentMaximum) {
            currentMinimum = currentMaximum;
        }
    }

    public void setLowerBound(Integer lowerBound) {
        if (lowerBound >= absoluteMinimum && lowerBound <= currentMaximum) {
            currentMinimum = lowerBound;
        }
        else {
            Log.d("RANGED_NUMERIC_SETTING", "Invalid setting for lower bound!");
            return;
        }
    }

    public void setUpperBound(Integer upperBound) {
        if (upperBound <= absoluteMaximum && upperBound >= currentMinimum) {
            currentMaximum = upperBound;
        }
        else {
            Log.d("RANGED_NUMERIC_SETTING", "Invalid setting for upper bound!");
            return;
        }
    }

    public Integer getMinimum() {
        return absoluteMinimum;
    }

    public Integer getMaximum() {
        return absoluteMaximum;
    }

    public Integer getLowerBound() {
        return currentMinimum;
    }

    public Integer getUpperBound() {
        return currentMaximum;
    }

    public String getType() {
        return RangedNumericSettingType;
    }

    public String valueDisplayString() {
        return title + ": From " + String.valueOf(currentMinimum) + " to " + String.valueOf(currentMaximum);
    }
}
