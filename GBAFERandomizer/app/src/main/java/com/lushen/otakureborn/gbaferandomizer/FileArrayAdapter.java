package com.lushen.otakureborn.gbaferandomizer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lushe_000 on 7/11/2015.
 */
public class FileArrayAdapter extends ArrayAdapter<File> {

    private ArrayList<File> filesArray;
    private Activity context;

    static class ViewHolder {
        public TextView nameLabel;
        public TextView sizeLabel;
    }

    FileArrayAdapter(Activity context, ArrayList<File> fileArray) {
        super(context, R.layout.filename_cell, fileArray);

        this.context = context;
        this.filesArray = fileArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;

        if (cell == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            cell = inflater.inflate(R.layout.filename_cell, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.nameLabel = (TextView)cell.findViewById(R.id.filename_label);
            viewHolder.sizeLabel = (TextView)cell.findViewById(R.id.filesize_label);

            cell.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)cell.getTag();

        File currentFile = filesArray.get(position);
        holder.nameLabel.setText(currentFile.getName());
        holder.sizeLabel.setText("Size: " + String.valueOf(currentFile.length()) + " bytes");

        return cell;
    }
}
