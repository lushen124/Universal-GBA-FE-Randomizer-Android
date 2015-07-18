package com.lushen.otakureborn.gbaferandomizer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class RandomizerActivity extends ActionBarActivity {

    private ListView settingsListView;
    private RandomizeSettingAdapter adapter;

    private Button randomizeButton;

    private static BooleanSetting growths;
    private static NumericSetting growthVariance;
    private static BooleanSetting minimumGrowth;

    private static BooleanSetting bases;
    private static NumericSetting baseVariance;

    private static BooleanSetting constitution;
    private static NumericSetting conVariance;
    private static NumericSetting minimumCon;

    private static BooleanSetting movement;
    private static RangedNumericSetting movementRange;

    private static BooleanSetting affinity;

    private static BooleanSetting items;
    private static NumericSetting mightVariance;
    private static RangedNumericSetting mightRange;
    private static NumericSetting hitVariance;
    private static RangedNumericSetting hitRange;
    private static NumericSetting criticalVariance;
    private static RangedNumericSetting criticalRange;
    private static NumericSetting weightVariance;
    private static RangedNumericSetting weightRange;
    private static NumericSetting durabilityVariance;
    private static RangedNumericSetting durabilityRange;
    private static BooleanSetting assignRandomTraits;

    private MainActivity.GameType gameType;
    private String filePath;

    public static ArrayList<RandomizeSetting> randomizingSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizer);

        this.gameType = (MainActivity.GameType)getIntent().getSerializableExtra("gameType");
        this.filePath = getIntent().getStringExtra("filePath");

        settingsListView = (ListView) findViewById(R.id.options_list);

        randomizeButton = (Button)findViewById(R.id.randomize_button);

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
        weightVariance = new NumericSetting("Weight Variance", "The maximum amount to add to or subtract from a weapon's weight.", 0, 0, 20, 1);
        weightRange = new RangedNumericSetting("Minimum/Maximum Weight", "The range of allowed weights for any given weapon.", 0, 40, 0, 40);
        durabilityVariance = new NumericSetting("Durability Variance", "The maximum amount to add to or subtract from a weapon's Durability.", 0, 0, 50, 1);
        durabilityRange = new RangedNumericSetting("Minimum/Maximum Durability", "The range of allowed durability values for any given weapon.", 1, 99, 1, 99);
        assignRandomTraits = new BooleanSetting("Assign Random Traits", "For every weapon, adds one of the following traits to it:\n\nDevil Effect\nEclipse Effect\nPoison Effect\nReverse Triangle\nUnbreakable\nBrave Effect\nMagic Damage\nNegates Defense\nRandom Stat Bonus\nRandom Effectiveness", false);

        items.addSubsetting(mightVariance);
        items.addSubsetting(mightRange);
        items.addSubsetting(hitVariance);
        items.addSubsetting(hitRange);
        items.addSubsetting(criticalVariance);
        items.addSubsetting(criticalRange);
        items.addSubsetting(weightVariance);
        items.addSubsetting(weightRange);
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

        randomizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, RandomizingActivity.class);
                intent.putExtra("source", filePath);

                File sourceFile = new File(filePath);
                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "FE Randomized");
                directory.mkdir();

                Calendar calendar = Calendar.getInstance();
                DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

                File destination = new File(directory.getAbsolutePath(), "[Rand " + formatter.format(calendar.getTime()) + "]" + sourceFile.getName());
                intent.putExtra("destination", destination.getAbsolutePath());

                intent.putExtra("gameType", gameType);

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

    public static Boolean shouldRandomizeGrowths() {
        return growths.isSettingEnabled();
    }

    public static Integer growthsVariance() {
        return growthVariance.getValue();
    }

    public static Boolean shouldUseMinimumGrowths() {
        return minimumGrowth.isSettingEnabled();
    }

    public static Boolean shouldRandomizeBases() {
        return bases.isSettingEnabled();
    }

    public static Integer basesVariance() {
        return baseVariance.getValue();
    }

    public static Boolean shouldRandomizeCON() {
        return constitution.isSettingEnabled();
    }

    public static Integer conVariance() {
        return conVariance.getValue();
    }

    public static Integer minimumCON() {
        return minimumCon.getValue();
    }
}
