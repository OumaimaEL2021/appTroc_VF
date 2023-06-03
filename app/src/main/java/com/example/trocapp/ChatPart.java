package com.example.trocapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocapp.messages.MessageAdapter;
import com.example.trocapp.messages.MessageList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatPart extends AppCompatActivity {
    private final List<MessageList> messageLists = new ArrayList<>();
    private String mobile;
    private String email;
    private String name;
    private TextView nom;
    private RecyclerView messageRecycl;
    private MessageAdapter messageAdapter;
    private CircleImageView maphoto;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_part);

        messageRecycl = findViewById(R.id.message);
        maphoto = findViewById(R.id.photo);
        // get intent data
        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        messageRecycl.setHasFixedSize(true);
        messageRecycl.setLayoutManager(new LinearLayoutManager(this));

        // set adapter to recycle view
        messageAdapter = new MessageAdapter(messageLists, ChatPart.this);
        messageRecycl.setAdapter(messageAdapter);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Log.e("ChatPart", "Error retrieving data", error.toException());
            }
        });

        databaseReference.child("Registered Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                messageLists.clear();

                for (DataSnapshot user : userSnapshot.getChildren()) {
                    final String otherUserId = user.getKey();
                    if (!otherUserId.equals(userId)) {
                        final String nomComplet = user.child("nomComplet").getValue(String.class);
                        final String getImage = user.child("profileImageUrl").getValue(String.class);
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot chatSnapshot) {
                                String lastMessage = "";
                                int unseenMessageCount = 0;
                                String chatKey = "";

                                for (DataSnapshot chat : chatSnapshot.getChildren()) {
                                    if (chat.hasChild("user_1") && chat.hasChild("user_2") && chat.hasChild("messages")) {
                                        String userOne = chat.child("user_1").getValue(String.class);
                                        String userTwo = chat.child("user_2").getValue(String.class);

                                        if ((userOne.equals(userId) && userTwo.equals(otherUserId)) || (userOne.equals(otherUserId) && userTwo.equals(userId))) {
                                            chatKey = chat.getKey();
                                            DataSnapshot messagesSnapshot = chat.child("messages");
                                            for (DataSnapshot messageSnapshot : messagesSnapshot.getChildren()) {
                                                lastMessage = messageSnapshot.child("msg").getValue(String.class);
                                                long messageKey = Long.parseLong(messageSnapshot.getKey());
                                                long lastSeenMessageKey = Long.parseLong(MemoryData.getLastMessage(ChatPart.this, chatKey));
                                                if (messageKey > lastSeenMessageKey) {
                                                    unseenMessageCount++;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }

                                MessageList messageList = new MessageList(nomComplet, otherUserId, lastMessage, unseenMessageCount, getImage, chatKey);
                                messageLists.add(messageList);
                                messageAdapter.updateData(messageLists);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("ChatPart", "Error retrieving chat data", error.toException());
                            }
                        });
                    }else{
                        final String getPhoto = user.child("profileImageUrl").getValue(String.class);
                        if (!getPhoto.isEmpty()) {
                            Picasso.get().load(getPhoto).into(maphoto);}

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChatPart", "Error retrieving user data", error.toException());
            }
        });
    }
}
