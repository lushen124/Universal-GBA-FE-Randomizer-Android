package com.lushen.otakureborn.gbaferandomizer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.RandomAccess;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView directoryListingView;
    private FileArrayAdapter directoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        directoryListingView = (ListView) findViewById(R.id.directory_list_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");

        ArrayList<File> files = new ArrayList<File>();

        for (int i = 0; i < file.listFiles().length; i++) {
            Log.d("MAIN_ACTIVITY", "File: " + file.listFiles()[i].getName());
            files.add(file.listFiles()[i]);
        }
        Log.d("MAIN_ACTIVITY", "list: end");

        directoryListAdapter = new FileArrayAdapter(this, files);

        directoryListingView.setAdapter(directoryListAdapter);

        directoryListingView.setOnItemClickListener(this);
    }

    private Integer readLittleEndianWordFromFile(RandomAccessFile fileReader, Boolean isAddress) throws IOException {
        Integer value = fileReader.readInt();
        // The value read here is big-endian. We need to convert it to the value in little endian.
        Integer realValue = 0;

        realValue = value & 0xFF;
        realValue <<= 8;
        value >>= 8;

        realValue |= value & 0xFF;
        realValue <<= 8;
        value >>= 8;

        realValue |= value & 0xFF;
        realValue <<= 8;
        value >>= 8;

        realValue |= value & 0xFF;

        if (isAddress) {
            realValue -= 0x8000000;
        }

        return realValue;
    }

    // AdapterView.OnClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("MAIN_ACTIVITY", "Selected file: " + directoryListAdapter.getItem(position));

        File file = directoryListAdapter.getItem(position);
        if (file.exists()) {
            try {
                RandomAccessFile fileReader = new RandomAccessFile(file, "r");

                fileReader.seek(0xAC);

                Integer value = readLittleEndianWordFromFile(fileReader, false);

                if (value == 0x45374541) {
                    Log.d("MAIN_ACTIVITY", "Detected FE7!");
                    Intent intent = new Intent(this, RandomizerActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d("MAIN_ACTIVITY", "Unknown game!");
                }

            } catch (FileNotFoundException e) {
                Log.d("MAIN_ACTIVITY", "Failed to initialize reader for file: " + file.getAbsolutePath().toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("MAIN_ACTIVITY", "IO Failure!");
                e.printStackTrace();
            }

        }
    }
}
