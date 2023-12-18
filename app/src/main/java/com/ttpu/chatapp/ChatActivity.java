package com.ttpu.chatapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText messageEditText;

    private Button sendButton;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private Message recipient;
    private FirebaseUser currentUser;
    private DatabaseReference messagesRef;
    private Query messagesQuery;
    private ChildEventListener messagesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get the current user from Firebase Auth
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        messagesRef = database.getReference("messages");

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Get recipient from previous activity
        recipient = getIntent().getParcelableExtra("recipient");

        // Initialize message list and adapter
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        // Set up send button click listener
        sendButton.setOnClickListener(view -> {
            String messageText = messageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                // Check if recipient is not null
                if (recipient != null) {
                    // Create new message object and add to Firebase
                    String sender = currentUser.getEmail();
                    TimeStamp timeStamp1 = new TimeStamp();
                    String timestamp = timeStamp1.getCurrentTimestamp();
                    Message message = new Message(sender, messageText, timestamp);

                    messagesRef.push().setValue(message);

                    // Clear message edit text
                    messageEditText.setText("");
                } else {
                    // Handle null recipient object
                    Toast.makeText(ChatActivity.this, "Recipient is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up child event listener for new messages in Firebase
        messagesQuery = messagesRef.orderByChild("recipientId").equalTo(currentUser.getUid());
        messagesListener = new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Message message = dataSnapshot.getValue(Message.class);
                messageList.add(message);
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // ... other child event listener methods, like onChildChanged, onChildRemoved, etc.
        };

        messagesQuery.addChildEventListener(messagesListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messagesQuery.removeEventListener(messagesListener);
    }

}
