package com.lushen.otakureborn.gbaferandomizer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;


public class EditSettingActivity extends ActionBarActivity {

    private RandomizeSetting parentSetting;

    private TextView parentSettingTitleLabel;
    private TextView parentSettingDescriptionLabel;

    private Switch parentSettingEnableSwitch;

    private ListView subsettingsList;

    private EditableSettingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting);

        subsettingsList = (ListView)findViewById(R.id.subsetting_list);

        parentSettingTitleLabel = (TextView)findViewById(R.id.main_setting_title_label);
        parentSettingDescriptionLabel = (TextView)findViewById(R.id.main_setting_description_label);

        parentSettingEnableSwitch = (Switch)findViewById(R.id.main_setting_enable_switch);

        this.parentSetting = RandomizerActivity.randomizingSettings.get(getIntent().getIntExtra("settingIndex", 0));

        parentSettingTitleLabel.setText(this.parentSetting.getSettingTitle());
        parentSettingDescriptionLabel.setText(this.parentSetting.getSettingDescription());

        parentSettingEnableSwitch.setChecked(this.parentSetting.isSettingEnabled());
        parentSettingEnableSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                parentSetting.setSettingEnabled(isChecked);
                subsettingsList.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });

        subsettingsList.setVisibility(this.parentSetting.isSettingEnabled() ? View.VISIBLE : View.INVISIBLE);

        adapter = new EditableSettingAdapter(this, this.parentSetting);

        subsettingsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.setTitle(parentSetting.getSettingTitle());
    }
}
