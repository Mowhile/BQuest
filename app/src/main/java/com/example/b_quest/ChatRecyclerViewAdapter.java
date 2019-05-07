package com.example.b_quest;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//CODE BY JUAN MARTIN


//this adapter takes information form the ChatActivity class in order to display messages on the screen
//and its started from the ChatActivity class
public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    //declaring the array that contain the information to be displayed
    private ArrayList<Message> messages = new ArrayList<>();

    //this arrayList contains the name of the user writing the message, this will help at the time of choosing how to display the messages
    private String userName;

    //getting the context
    private Context context;

    //constructor
    public ChatRecyclerViewAdapter(ArrayList<Message> messages, String userName, ChatActivity context) {
        this.messages = messages;
        this.userName = userName;
        this.context = context;

    }

    //the onCreateViewHolder builds the place where each individual message will be shown based on who is writing the message
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new ViewHolder(view);
    }

    //the onBindViewHolder method adds the information to the layout created by the onCreateViewHolder method
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.message.setText(messages.get(position).getMessage());
        holder.name.setText(messages.get(position).getUser());
    }

    //this method defines how many times the adapter will be creating layouts
    @Override
    public int getItemCount() {
        return messages.size();
    }

    //this class define the information to be attach to the holder, the holder will be an instance of one of this class

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user);
            message = itemView.findViewById(R.id.message);

        }
    }
}

