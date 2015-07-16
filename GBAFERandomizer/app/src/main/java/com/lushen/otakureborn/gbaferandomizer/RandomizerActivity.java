package com.lushen.otakureborn.gbaferandomizer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class RandomizerActivity extends ActionBarActivity {

    private ListView settingsListView;
    private RandomizeSettingAdapter adapter;

    private BooleanSetting growths;
    private NumericSetting growthVariance;
    private BooleanSetting minimumGrowth;

    private BooleanSetting bases;
    private NumericSetting baseVariance;

    private BooleanSetting constitution;
    private NumericSetting conVariance;
    private NumericSetting minimumCon;

    private BooleanSetting movement;
    private RangedNumericSetting movementRange;

    private BooleanSetting affinity;

    private BooleanSetting items;
    private NumericSetting mightVariance;
    private RangedNumericSetting mightRange;
    private NumericSetting hitVariance;
    private RangedNumericSetting hitRange;
    private NumericSetting criticalVariance;
    private RangedNumericSetting criticalRange;
    private NumericSetting weightVariance;
    private RangedNumericSetting weightRange;
    private NumericSetting durabilityVariance;
    private RangedNumericSetting durabilityRange;
    private BooleanSetting assignRandomTraits;

    public static ArrayList<RandomizeSetting> randomizingSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizer);

        settingsListView = (ListView) findViewById(R.id.options_list);

        growths = new BooleanSetting("Randomize Growths", "Adds up growth rates for all characters and then re-distributes them randomly across all growth areas.", false);
        growthVariance = new NumericSetting("Variance", "Before re-distribution, adds or subtracts a random amount up to this value to or from the total.", 0, 0, 100, 1);
        minimumGrowth = new BooleanSetting("Use Minimum Growth (5%)", "Before re-distribution, automatically applies 5% growth to all areas.", false);

        growths.addSubsetting(growthVariance);
        growths.addSubsetting(minimumGrowth);

        bases = new BooleanSetting("Randomize Bases", "Adds up personal bases for all characters and then re-distributes them randomly across all stats.", false);
        baseVariance = new NumericSetting("Variance", "Before re-distribution, adds or subtracts a random amount up to this value to or from the total.", 0, 0, 10, 1);

        bases.addSubsetting(baseVariance);

        constitution = new BooleanSetting("Randomize CON", "Adds or subtracts a random amount up to the amount specified to a character's CON.", false);
        conVariance = new NumericSetting("Variance", "The maximum amount to add to or subtract from the character's CON.", 0, 0, 10, 1);
        minimumCon = new NumericSetting("Minimum CON", "The minimum CON a character can have. A character ending with a lower CON will have it brought up to this value.", 0, 0, 10, 1);

        constitution.addSubsetting(conVariance);
        constitution.addSubsetting(minimumCon);

        movement = new BooleanSetting("Randomize MOV", "For every class, randomly assigns a movement range between the minimum and maximum value specified.", false);
        movementRange = new RangedNumericSetting("Minimum/Maximum MOV", "The range of allowed movement ranges allowed for any class.", 1, 9, 1, 9);

        movement.addSubsetting(movementRange);

        affinity = new BooleanSetting("Randomize Affinity", "Assigns a random support affinity to all characters.", false);

        items = new BooleanSetting("Randomize Items", "Applies random deltas to weapon stats.", false);

        mightVariance = new NumericSetting("Might Variance", "The maximum amount to add to or subtract from a weapon's Might.", 0, 0, 10, 1);
        mightRange = new RangedNumericSetting("Minimum/Maximum Might", "The range of allowed power for any given weapon.", 0, 30, 0, 30);
        hitVariance = new NumericSetting("Hit Variance", "The maximum amount to add to or subtract from a weapon's Hit Rate.", 0, 0, 100, 1);
        hitRange = new RangedNumericSetting("Minimum/Maximum Hit Rate", "The range of allowed hit rates for any given weapon.", 0, 255, 0, 255);
        criticalVariance = new NumericSetting("Critical Variance", "The maximum amount to add to or subtract from a weapon's Critical Rate.", 0, 0, 50, 1);
        criticalRange = new RangedNumericSetting("Minimum/Maximum Critical Chance", "The range of allowed critical chances for any given weapon.", 0, 100, 0, 100);
        durabilityVariance = new NumericSetting("Durability Variance", "The maximum amount to add to or subtract from a weapon's Durability.", 0, 0, 50, 1);
        durabilityRange = new RangedNumericSetting("Minimum/Maximum Durability", "The range of allowed durability values for any given weapon.", 1, 99, 1, 99);
        assignRandomTraits = new BooleanSetting("Assign Random Traits", "For every weapon, adds one of the following traits to it:\n\nDevil Effect\nEclipse Effect\nPoison Effect\nReverse Triangle\nUnbreakable\nBrave Effect\nMagic Damage\nNegates Defense\nRandom Stat Bonus\nRandom Effectiveness", false);

        items.addSubsetting(mightVariance);
        items.addSubsetting(mightRange);
        items.addSubsetting(hitVariance);
        items.addSubsetting(hitRange);
        items.addSubsetting(criticalVariance);
        items.addSubsetting(criticalRange);
        items.addSubsetting(durabilityVariance);
        items.addSubsetting(durabilityRange);
        items.addSubsetting(assignRandomTraits);

        randomizingSettings = new ArrayList<RandomizeSetting>();

        randomizingSettings.add(growths);
        randomizingSettings.add(bases);
        randomizingSettings.add(constitution);
        randomizingSettings.add(movement);
        randomizingSettings.add(affinity);
        randomizingSettings.add(items);

        adapter = new RandomizeSettingAdapter(this, randomizingSettings);

        settingsListView.setAdapter(adapter);

        final RandomizerActivity self = this;

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RandomizeSetting setting = randomizingSettings.get(position);

                Intent intent = new Intent(self, EditSettingActivity.class);
                intent.putExtra("settingIndex", position);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_randomizer, menu);
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

        adapter.notifyDataSetChanged();
    }
}
