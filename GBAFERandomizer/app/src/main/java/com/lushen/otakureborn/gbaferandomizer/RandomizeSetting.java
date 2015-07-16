package com.lushen.otakureborn.gbaferandomizer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lushe_000 on 7/15/2015.
 */
public abstract class RandomizeSetting implements Serializable {

    protected String title;
    protected String description;

    protected Boolean isEnabled;

    protected ArrayList<RandomizeSetting> subsettings;

    protected RandomizeSetting(String title, String description, Boolean enabledByDefault) {
        this.title = title;
        this.description = description;

        this.isEnabled = enabledByDefault;

        this.subsettings = new ArrayList<RandomizeSetting>();
    }

    public String getSettingTitle() {
        return title;
    }

    public void setSettingTitle(String title) {
        this.title = title;
    }

    public String getSettingDescription() {
        return description;
    }

    public void setSettingDescription(String description) {
        this.description = description;
    }

    public Boolean isSettingEnabled() {
        return isEnabled;
    }

    public void setSettingEnabled(Boolean enabled) {
        this.isEnabled = enabled;
    }

    public ArrayList<RandomizeSetting> subSettings() {
        return subsettings;
    }

    public void addSubsetting(RandomizeSetting subSetting) {
        if (subSetting != null) {
            subsettings.add(subSetting);
        }
    }

    abstract String getType();
    abstract String valueDisplayString();
}
