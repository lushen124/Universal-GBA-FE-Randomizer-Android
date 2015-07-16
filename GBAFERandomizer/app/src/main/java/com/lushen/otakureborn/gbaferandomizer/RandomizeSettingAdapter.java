package com.lushen.otakureborn.gbaferandomizer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lushe_000 on 7/15/2015.
 */
public class RandomizeSettingAdapter extends ArrayAdapter<RandomizeSetting> {

    private ArrayList<RandomizeSetting> settingsArray;
    private Activity context;

    static class ViewHolder {
        public TextView titleLabel;
        public TextView descriptionLabel;

        public CheckBox enabledCheckbox;
    }

    public RandomizeSettingAdapter(Activity context, ArrayList<RandomizeSetting> objects) {
        super(context, R.layout.randomize_setting_cell, objects);

        this.context = context;
        this.settingsArray = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;

        if (cell == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            cell = inflater.inflate(R.layout.randomize_setting_cell, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleLabel = (TextView)cell.findViewById(R.id.title_label);
            viewHolder.descriptionLabel = (TextView)cell.findViewById(R.id.description_label);

            viewHolder.enabledCheckbox = (CheckBox)cell.findViewById(R.id.enabled_checkbox);

            cell.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)cell.getTag();

        RandomizeSetting currentSetting = settingsArray.get(position);
        holder.titleLabel.setText(currentSetting.getSettingTitle());

        String descriptionString = currentSetting.getSettingDescription();

        if (currentSetting.isSettingEnabled()) {
            if (currentSetting.subSettings().size() > 0) {
                descriptionString += "\n\n";
                Iterator<RandomizeSetting> iterator = currentSetting.subSettings().iterator();
                while (iterator.hasNext()) {
                    descriptionString += iterator.next().valueDisplayString() + "\n";
                }
            }
        }
        holder.descriptionLabel.setText(descriptionString);

        holder.enabledCheckbox.setChecked(currentSetting.isSettingEnabled());

        return cell;
    }
}
