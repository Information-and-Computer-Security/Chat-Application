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
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ArrayList users;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        final ListView chats = findViewById(R.id.list);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser.getDisplayName();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        System.out.println(mFirebaseDatabaseReference.getKey());
        DatabaseReference userNameRef = mFirebaseDatabaseReference;//child("name");

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();



        userNameRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //super.onDataChange(dataSnapshot);
                users = new ArrayList<String>();
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    users.add(String.valueOf(snap.getKey()));}}

            public void onCancelled(DatabaseError databaseError){}});
        ArrayList<String> chatList = new ArrayList<>();
        //TESTING
       // chatList.add("Group Chat");
        for(String i : users)
            chatList.add(i);
        chatList.add("Group Chat");
        chatList.remove(mUsername);
        //chatList.add("USER 1");
        //chatList.add("messages");

        //chatList.add(users.get(0));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chatList);
        chats.setAdapter(arrayAdapter);
        chats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) chats.getItemAtPosition(position);
                if (clickedItem == "Group Chat") clickedItem = "group_messages";
                else{
                    ArrayList<String> messageID = new ArrayList<String>();
                    messageID.add(clickedItem);
                    messageID.add(mUsername);
                    Collections.sort(messageID);
                    clickedItem = messageID.get(0) + "_" + messageID.get(1);
                }



                MESSAGES_CHILD = clickedItem;
                System.out.println(MESSAGES_CHILD);
                Intent intent = new Intent(SelectChat.this, MainActivity.class);
                //System.out.println(clickedItem);
                intent.putExtra("user", clickedItem);
                startActivity(intent);
                setResult(57,intent);
                finish();
            }
        });
    }
}

