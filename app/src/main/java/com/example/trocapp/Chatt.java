package com.example.trocapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocapp.chat.ChatAdapter;
import com.example.trocapp.chat.ChatList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatt extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private int genechatKey;
    String getUser="";
    private String chatKey;
    private boolean LoadingFist=true;
    private final List<ChatList> chatLists=new ArrayList<>();

    private ChatAdapter chatAdapter;

    private RecyclerView chattingRecyclview;
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatt);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView name = findViewById(R.id.name);
        final EditText editmessage = findViewById(R.id.editmessage);
        final CircleImageView profilepic = findViewById(R.id.profilePic);
        final ImageView sendBtn = findViewById(R.id.send);

        chattingRecyclview =findViewById(R.id.chattingRecyclview);
        //get data from message adapter
        final String getName = getIntent().getStringExtra("name");
        final String getPhoto = getIntent().getStringExtra("photo");
        name.setText(getName);
        Picasso.get().load(getPhoto).into(profilepic);
        chattingRecyclview.setHasFixedSize(true);
        chattingRecyclview.setLayoutManager(new LinearLayoutManager(Chatt.this));
        chatAdapter=new ChatAdapter(chatLists,Chatt.this);
        chattingRecyclview.setAdapter(chatAdapter);

        chatKey=getIntent().getStringExtra("chat_key");
        final String getUserId = getIntent().getStringExtra("userId");
        getUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (chatKey.isEmpty()) {
                    chatKey="1";
                        if(snapshot.hasChild("chat")){
                            chatKey=String.valueOf(snapshot.child("chat").getChildrenCount()+1);
                        }
                    }

                    if(snapshot.hasChild("chat")){
                        if(snapshot.child("chat").child(chatKey).hasChild("messages")){
                            chatLists.clear();
                            for(DataSnapshot messageSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()){

                                if(messageSnapshot.hasChild("msg") && messageSnapshot.hasChild("userId")){
                                    final String messageTimestams =messageSnapshot.getKey();
                                    final String getUserID =messageSnapshot.child("userId").getValue(String.class);
                                    final String getMsg =messageSnapshot.child("msg").getValue(String.class);

                                    // Get the current timestamp
                                    long currentTimestamp = System.currentTimeMillis();
                                    Date currentDate = new Date(currentTimestamp);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                                    String formattedDate = simpleDateFormat.format(currentDate);
                                    String formattedTime = simpleTimeFormat.format(currentDate);
                                    ChatList chatList = new ChatList(getUserID, getName, getMsg, formattedDate, formattedTime);

                                    chatLists.add(chatList);


                                    if(LoadingFist || Long.parseLong(messageTimestams)>Long.parseLong(MemoryData.getLastMessage(Chatt.this,chatKey))){
                                        LoadingFist=false;
                                        MemoryData.saveLastMessage(messageTimestams,chatKey,Chatt.this);
                                        chatAdapter.updateChatList(chatLists);

                                        chattingRecyclview.scrollToPosition(chatLists.size()-1);
                                    }
                                }
                            }

                        }
                    }

                    editmessage.setText("");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        //Picasso.get.load(getProfilePic).into(profilePic);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getTextmessage=editmessage.getText().toString();
                final String currentTimestamp=String.valueOf(System.currentTimeMillis()).substring(0,10);

                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUser);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getUserId);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTextmessage);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("userId").setValue(getUser);

            }
        }
        );
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }
        );


    }
}