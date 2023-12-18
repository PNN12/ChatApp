package com.ttpu.chatapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class FileAdapter extends ArrayAdapter<File> {

    public FileAdapter(Context context, ArrayList<File> files) {
        super(context, 0, files);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        File file = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_file, parent, false);
        }

        // Lookup view for data population
        TextView fileNameTextView = convertView.findViewById(R.id.file_name_text_view);

        // Populate the data into the template view using the data object
        fileNameTextView.setText(file.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}
