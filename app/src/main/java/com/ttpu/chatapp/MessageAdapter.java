package com.ttpu.chatapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the message item layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // Get the message at the current position
        Message message = messageList.get(position);

        // Set the message text and author name in the message item view
        holder.messageTextView.setText(message.getMessage());
        holder.timeStamp.setText(getFormattedTime(message.getTimestamp()));
        holder.authorTextView.setText(message.getAuthor());
        if (!holder.currentAuthor.equals(message.getAuthor())) {
            holder.currentAuthor = message.getAuthor();
            holder.authorTextView.setText(holder.currentAuthor + ": ");
        }
    }


    private String getFormattedTime(String timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            long timeMillis = sdf.parse(timestamp).getTime();
            return sdf.format(new Date(timeMillis));
        } catch (ParseException e) {
            e.printStackTrace();
            return "Время не указано";
        }
    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageTextView;
        private TextView authorTextView;
        private TextView timeStamp;
        private String currentAuthor;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find the message item view elements by ID
            messageTextView = itemView.findViewById(R.id.message_text);
            authorTextView = itemView.findViewById(R.id.author_text);
            timeStamp = itemView.findViewById(R.id.message_time);
            currentAuthor = "";
        }
    }

}
