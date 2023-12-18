package com.ttpu.chatapp;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GroupActivity extends AppCompatActivity {

    private ListView groupListView;
    private ArrayList<Group> groups;
    private ArrayAdapter<Group> groupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        groupListView = findViewById(R.id.group_list_view);
        groups = new ArrayList<>();
        groupAdapter = new ArrayAdapter<>(this, R.layout.item_group, groups);
        groupListView.setAdapter(groupAdapter);

        groupListView.setOnItemClickListener((parent, view, position, id) -> {
            Group selectedGroup = groups.get(position);
            // TODO: Implement group chat activity
        });

        // TODO: Implement group creation functionality
    }
}

