package com.ttpu.chatapp;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class FileActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<File> fileList;
    private FileAdapter fileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        listView = findViewById(R.id.file_list_view);
        fileList = new ArrayList<>();
        fileAdapter = new FileAdapter(this, fileList);
        listView.setAdapter(fileAdapter);

        // TODO: Add code to populate fileList with files and update fileAdapter

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // TODO: Add code to handle file item click
        });
    }
}

