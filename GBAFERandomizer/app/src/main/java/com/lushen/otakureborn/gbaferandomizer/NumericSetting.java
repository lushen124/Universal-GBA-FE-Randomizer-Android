package com.lushen.otakureborn.gbaferandomizer;

/**
 * Created by lushe_000 on 7/15/2015.
 */
public class NumericSetting extends RandomizeSetting {

    public static final String NumericSettingType = "Numeric";

    private Integer minimum;
    private Integer maximum;
    private Integer stepSize;

    private Integer value;

    public NumericSetting(String title, String description, Integer defaultValue, Integer minimumValue, Integer maximumValue, Integer stepSize) {
        super(title, description, true);

        this.minimum = minimumValue;
        this.maximum = maximumValue;

        this.stepSize = stepSize;

        if (defaultValue > maximumValue) {
            setValue(maximumValue);
        }
        else if (defaultValue < minimumValue) {
            setValue(minimumValue);
        }
        else {
            setValue(defaultValue);
        }
    }

    public void setValue(Integer value) {
        if (value >= minimum && value <= maximum) {
            this.value = value;
        }
    }

    @Override
    public void setSettingEnabled(Boolean enabled) {
        super.setSettingEnabled(true);
    }

    public Integer getMinimumValue() {
        return minimum;
    }

    public Integer getMaximumValue() {
        return maximum;
    }

    public Integer getStepSize() {
        return stepSize;
    }

    public Integer getValue() { return value; }

    public String getType() {
        return NumericSettingType;
    }

    public String valueDisplayString() {
        return title + ": " + String.valueOf(value);
    }
}
