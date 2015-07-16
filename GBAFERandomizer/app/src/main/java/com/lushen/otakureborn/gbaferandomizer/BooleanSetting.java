package com.lushen.otakureborn.gbaferandomizer;

import java.util.ArrayList;

/**
 * Created by lushe_000 on 7/15/2015.
 */

public class BooleanSetting extends RandomizeSetting {

    public static final String BooleanSettingType = "Boolean";

    public BooleanSetting(String title, String description, Boolean enabledByDefault) {
        super(title, description, enabledByDefault);
    }

    public String getType() {
        return BooleanSettingType;
    }

    public String valueDisplayString() {
        return title + ": " + (isEnabled ? "YES" : "NO");
    }
}
