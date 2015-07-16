package com.lushen.otakureborn.gbaferandomizer;

import android.app.Activity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lushe_000 on 7/16/2015.
 */
public class EditableSettingAdapter extends ArrayAdapter<RandomizeSetting> {

    private enum CellType {
        BOOLEAN, NUMERIC, RANGED_NUMERIC
    }

    private class ViewHolder {
        public TextView titleLabel;
        public TextView descriptionLabel;
    }

    private class BooleanViewHolder extends ViewHolder {
        public CheckBox enabledCheckbox;
    }

    private class NumericViewHolder extends ViewHolder {
        public NumberPicker valuePicker;
    }

    private class RangedNumericViewHolder extends ViewHolder {
        public NumberPicker minimumPicker;
        public NumberPicker maximumPicker;
    }

    private RandomizeSetting parentSetting;
    private Activity context;

    public EditableSettingAdapter(Activity context, RandomizeSetting topLevelSetting) {
        super(context, 0, topLevelSetting.subSettings());

        this.parentSetting = topLevelSetting;
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        RandomizeSetting subsetting = parentSetting.subSettings().get(position);

        if (subsetting.getType().equals(BooleanSetting.BooleanSettingType)) {
            return CellType.BOOLEAN.ordinal();
        }
        else if (subsetting.getType().equals(NumericSetting.NumericSettingType)) {
            return CellType.NUMERIC.ordinal();
        }
        else if (subsetting.getType().equals(RangedNumericSetting.RangedNumericSettingType)) {
            return CellType.RANGED_NUMERIC.ordinal();
        }

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;

        int itemType = getItemViewType(position);

        if (cell == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            ViewHolder viewHolder;
            if (itemType == CellType.BOOLEAN.ordinal()) {
                cell = inflater.inflate(R.layout.boolean_setting_cell, null);
                BooleanViewHolder booleanHolder = new BooleanViewHolder();
                booleanHolder.enabledCheckbox = (CheckBox)cell.findViewById(R.id.enabled_checkbox);
                viewHolder = booleanHolder;
            }
            else if (itemType == CellType.NUMERIC.ordinal()) {
                cell = inflater.inflate(R.layout.numeric_setting_cell, null);
                NumericViewHolder numericHolder = new NumericViewHolder();
                numericHolder.valuePicker = (NumberPicker)cell.findViewById(R.id.value_picker);
                viewHolder = numericHolder;
            }
            else if (itemType == CellType.RANGED_NUMERIC.ordinal()) {
                cell = inflater.inflate(R.layout.ranged_numeric_setting_cell, null);
                RangedNumericViewHolder rangedHolder = new RangedNumericViewHolder();
                rangedHolder.minimumPicker = (NumberPicker)cell.findViewById(R.id.minimum_value_picker);
                rangedHolder.maximumPicker = (NumberPicker)cell.findViewById(R.id.maximum_value_picker);
                viewHolder = rangedHolder;
            }
            else {
                cell = inflater.inflate(R.layout.boolean_setting_cell, null);
                viewHolder = new ViewHolder();
            }

            viewHolder.titleLabel = (TextView)cell.findViewById(R.id.title_label);
            viewHolder.descriptionLabel = (TextView)cell.findViewById(R.id.description_label);

            cell.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)cell.getTag();

        RandomizeSetting subsetting = parentSetting.subSettings().get(position);

        holder.titleLabel.setText(subsetting.getSettingTitle());
        holder.descriptionLabel.setText(subsetting.getSettingDescription());

        if (itemType == CellType.BOOLEAN.ordinal()) {
            final BooleanSetting booleanSetting = (BooleanSetting)subsetting;
            BooleanViewHolder booleanHolder = (BooleanViewHolder)holder;
            booleanHolder.enabledCheckbox.setChecked(booleanSetting.isSettingEnabled());
            booleanHolder.enabledCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    booleanSetting.setSettingEnabled(isChecked);
                }
            });
        }
        else if (itemType == CellType.NUMERIC.ordinal()) {
            final NumericSetting numericSetting = (NumericSetting)subsetting;
            NumericViewHolder numericHolder = (NumericViewHolder)holder;
            numericHolder.valuePicker.setValue(numericSetting.getValue());
            numericHolder.valuePicker.setMaxValue(numericSetting.getMaximumValue());
            numericHolder.valuePicker.setMinValue(numericSetting.getMinimumValue());

            numericHolder.valuePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if (newVal > oldVal) {
                        numericSetting.setValue(oldVal + numericSetting.getStepSize());
                    }
                    else if (newVal < oldVal) {
                        numericSetting.setValue(oldVal - numericSetting.getStepSize());
                    }
                    picker.setValue(numericSetting.getValue());
                }
            });
        }

        else if (itemType == CellType.RANGED_NUMERIC.ordinal()) {
            final RangedNumericSetting rangedSetting = (RangedNumericSetting)subsetting;
            final RangedNumericViewHolder rangedHolder = (RangedNumericViewHolder)holder;
            rangedHolder.minimumPicker.setMinValue(rangedSetting.getMinimum());
            rangedHolder.minimumPicker.setMaxValue(rangedSetting.getUpperBound());
            rangedHolder.maximumPicker.setMinValue(rangedSetting.getLowerBound());
            rangedHolder.maximumPicker.setMaxValue(rangedSetting.getMaximum());
            rangedHolder.minimumPicker.setValue(rangedSetting.getLowerBound());
            rangedHolder.maximumPicker.setValue(rangedSetting.getUpperBound());

            rangedHolder.maximumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    rangedSetting.setUpperBound(newVal);
                    rangedHolder.minimumPicker.setMaxValue(newVal);
                }
            });

            rangedHolder.minimumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    rangedSetting.setLowerBound(newVal);
                    rangedHolder.maximumPicker.setMinValue(newVal);
                }
            });
        }

        return cell;
    }
}
