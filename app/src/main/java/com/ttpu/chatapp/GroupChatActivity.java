package com.ttpu.chatapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GroupChatActivity extends AppCompatActivity {

    private EditText messageEditText;
    private ImageButton sendButton;
    private Button profileButton;

    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        RecyclerView messageRecyclerView = findViewById(R.id.group_chat_list);
        messageEditText = findViewById(R.id.group_chat_message_edittext);
        sendButton = findViewById(R.id.group_chat_send_button);
        profileButton = findViewById(R.id.profile_button);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(messageAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("messages");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String currentUserId = firebaseUser.getUid();

        DatabaseReference usersDatabaseReference = database.getReference().child("users").child(currentUserId);

        usersDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                Log.d("GroupChatActivity", "currentUser: " + currentUser.toString());

                sendButton.setOnClickListener(view -> {
                    String messageText = messageEditText.getText().toString();
                    if (!messageText.isEmpty()) {
                        TimeStamp timeStamp = new TimeStamp();
                        String messageTime = String.valueOf(timeStamp.getCurrentTimestamp());

                        Message message = new Message(
                                currentUser.getUsername(),
                                "group",
                                messageText,
                                messageTime
                        );
                        // Add the message to the database
                        databaseReference.push().setValue(message);

                        messageEditText.setText("");
                    }
                });

                profileButton.setOnClickListener(view -> {
                    Intent intent = new Intent(GroupChatActivity.this, ProfileActivity.class);
                    startActivity(intent);
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GroupChatActivity.this, "Failed to load username.", Toast.LENGTH_SHORT).show();
            }
        });

        childEventListener = new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                messageList.add(message);
                messageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GroupChatActivity.this, "Failed to load messages.", Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseReference != null && childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
        }}}
