package com.google.firebase.codelab.friendlychat;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.getValue;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ChildEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectChat extends AppCompatActivity {
    public static ArrayList<String> users;
    public static String MESSAGES_CHILD = "group_messages";
    public static String getData(){return MESSAGES_CHILD;}
    private DatabaseReference mFirebaseDatabaseReference1;
    public ArrayList<String> chatList = new ArrayList<>();
    private FirebaseAuth mFirebaseAuth1;
    private FirebaseUser mFirebaseUser1;
    private String mUsername1;
    public CountDownLatch done = new CountDownLatch(1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ArrayList users;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        final ListView chats = findViewById(R.id.list);

        mFirebaseAuth1 = FirebaseAuth.getInstance();
        mFirebaseUser1 = mFirebaseAuth1.getCurrentUser();
        mUsername1 = mFirebaseUser1.getDisplayName();
        mFirebaseDatabaseReference1 = FirebaseDatabase.getInstance().getReference("users");
        System.out.println(mFirebaseDatabaseReference1.getKey());
        DatabaseReference userNameRef = mFirebaseDatabaseReference1;

        mFirebaseDatabaseReference1 = FirebaseDatabase.getInstance().getReference();

        //Add Group Chatroom
        chatList.add("Group Chat");

        //Get a list of all registered users, add to chat list
        userNameRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<String>();
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    System.out.println(snap.getKey());
                    users.add(String.valueOf(snap.getKey()));}
                for(String i : users)
                    chatList.add(i);

                //Remove the username of the current user from the chat list
                chatList.remove(mUsername1);

                //Set view to select which chatroom to use, store as either group_messages
                //Or as the two users as user1_user2 sorted alphabetically
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SelectChat.this, android.R.layout.simple_list_item_1, chatList);
                chats.setAdapter(arrayAdapter);
                chats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String clickedItem=(String) chats.getItemAtPosition(position);
                        if (clickedItem == "Group Chat") clickedItem = "group_messages";
                        else{
                            ArrayList<String> messageID = new ArrayList<String>();
                            messageID.add(clickedItem);
                            messageID.add(mUsername1);
                            Collections.sort(messageID);
                            clickedItem = messageID.get(0) + "_" + messageID.get(1);
                        }

                        //Return back to main activity
                        MESSAGES_CHILD = clickedItem;
                        System.out.println(MESSAGES_CHILD);
                        Intent intent = new Intent(SelectChat.this, MainActivity.class);
                        intent.putExtra("user", clickedItem);
                        startActivity(intent);
                        setResult(57,intent);
                        finish();
                    }
                });
            }
            public void onCancelled(DatabaseError databaseError){}});

    }
}

