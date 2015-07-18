package com.lushen.otakureborn.gbaferandomizer;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lushen.otakureborn.gbaferandomizer.models.FECharacter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class RandomizingActivity extends ActionBarActivity {

    private String sourcePath;
    private String destinationPath;

    private MainActivity.GameType gameType;

    private TextView statusLabel;
    private ProgressBar progressIndicator;

    private class RandomizeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            File inputFile = new File(sourcePath);
            File outputFile = new File(destinationPath);

            byte[] byteArray = null;
            FileInputStream input = null;
            try {
                input = new FileInputStream(inputFile);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024 *8];
                int bytesRead = 0;

                while ((bytesRead = input.read(buffer)) != -1) {
                    byteOutput.write(buffer, 0, bytesRead);
                }

                byteArray = byteOutput.toByteArray();

            } catch (FileNotFoundException exception) {
                Log.d("RANDOMIZING", "Source File Not Found!");
            } catch (IOException exception) {
                Log.d("RANDOMIZING", "Failed to read from Source File!");
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException exception) {
                        Log.d("RANDOMIZING", "Failed to close input stream.");
                    }
                }
            }

            if (gameType == MainActivity.GameType.FE7) {
                Integer characterAddressPointer = FE7GameData.PointerToCharacterTableOffset;
                Integer byte1 = byteArray[characterAddressPointer] & 0xFF;
                Integer byte2 = (byteArray[characterAddressPointer + 1] << 8) & 0xFF00;
                Integer byte3 = (byteArray[characterAddressPointer + 2] << 16) & 0xFF0000;
                Integer byte4 = (byteArray[characterAddressPointer + 3] << 24) & 0xFF000000;
                Log.d("RANDOMIZING", "Components: " + Integer.toHexString(byte1) + " " + Integer.toHexString(byte2) + " " + Integer.toHexString(byte3) + " " + Integer.toHexString(byte4));
                Integer tableOffset = byte1 + byte2 + byte3 + byte4;
                Log.d("RANDOMIZING", "Raw Address: " + Integer.toHexString(tableOffset));
                tableOffset -= 0x08000000;
                if (tableOffset.equals(FE7GameData.DefaultCharacterTableOffset)) {
                    Log.d("RANDOMIZING", "Character Table Found at Offset: " + Integer.toHexString(tableOffset));

                    Integer currentOffset = tableOffset;

                    ArrayList<FECharacter> characterList = new ArrayList<FECharacter>();

                    for (int i = 0; i < FE7GameData.CharacterCount; i++) {
                        byte[] subArray = Arrays.copyOfRange(byteArray, currentOffset, currentOffset + FE7GameData.CharacterEntrySize);

                        FECharacter character = new FECharacter(subArray, gameType);

                        characterList.add(character);

                        currentOffset += FE7GameData.CharacterEntrySize;
                    }

                    Iterator<FECharacter> characterListIterator = characterList.iterator();
                    while (characterListIterator.hasNext()) {
                        FECharacter currentCharacter = characterListIterator.next();
                        if (RandomizerActivity.shouldRandomizeGrowths()) {
                            currentCharacter.randomizeGrowths(RandomizerActivity.growthsVariance(), RandomizerActivity.shouldUseMinimumGrowths());
                        }
                        if (RandomizerActivity.shouldRandomizeBases()) {
                            currentCharacter.randomizeBases(RandomizerActivity.basesVariance());
                        }
                        if (RandomizerActivity.shouldRandomizeCON()) {
                            currentCharacter.randomizeCON(RandomizerActivity.conVariance(), RandomizerActivity.minimumCON(), 0);
                        }
                    }

                    if (gameType == MainActivity.GameType.FE7) {
                        UPSPatcher.patchByteArrayWithUPSFile(byteArray, getApplicationContext().getResources().openRawResource(R.raw.arch_tutorial_slayer));
                    }

                    ByteBuffer outputBuffer = ByteBuffer.wrap(byteArray);

                    currentOffset = tableOffset;

                    outputBuffer.position(currentOffset);

                    for (int i = 0; i < FE7GameData.CharacterCount; i++) {
                        FECharacter currentCharacter = characterList.get(i);
                        Integer originalOffset = outputBuffer.position();

                        outputBuffer.put(currentCharacter.getRawData());

                        outputBuffer.position(originalOffset + FE7GameData.CharacterEntrySize);
                    }

                    byte[] finalByteArray = outputBuffer.array();

                    BufferedOutputStream outputStream = null;
                    try {
                        outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

                        outputStream.write(finalByteArray);

                    } catch (FileNotFoundException exception) {
                        Log.d("RANDOMIZING", "Failed to setup output file stream.");
                    } catch (IOException exception) {
                        Log.d("RANDOMIZING", "Failed to create output file.");
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                                Log.d("RANDOMIZING", "Successfully wrote file!");
                            } catch (IOException exception) {
                                Log.d("RANDOMIZING", "Failed to close output file.");
                            }
                        }
                    }
                } else {
                    Log.d("RANDOMIZING", "Unexpected character table offset: " + Integer.toHexString(tableOffset));
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            statusLabel.setText("Randomized!");
            progressIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizing);

        sourcePath = getIntent().getStringExtra("source");
        destinationPath = getIntent().getStringExtra("destination");

        gameType = (MainActivity.GameType)getIntent().getSerializableExtra("gameType");

        Log.d("RANDOMIZING", "Source Path: " + getIntent().getStringExtra("source"));
        Log.d("RANDOMIZING", "Destination Path: " + getIntent().getStringExtra("destination"));
        Log.d("RANDOMIZING", "Game Type: " + gameType.toString());

        statusLabel = (TextView)findViewById(R.id.status_label);
        progressIndicator = (ProgressBar)findViewById(R.id.progress_indicator);

        RandomizeTask randomizeTask = new RandomizeTask();
        randomizeTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_randomizing, menu);
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
    }
}
