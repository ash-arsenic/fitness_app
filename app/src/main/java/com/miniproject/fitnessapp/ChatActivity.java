package com.miniproject.fitnessapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ChatActivity extends AppCompatActivity {

    private ArrayList<ChatMessage> mUsers;
    private UserAdapter mUserAdapter;
    private RecyclerView mUsersList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    finish();
                }
            });
        } else if (item.getItemId() == R.id.menu_change_password) {
            Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_acitvity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mUsersList = findViewById(R.id.list_of_message);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mUsers = new ArrayList<>();
        DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("chats");
        chatReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);

                    if (chatMessage.getReceiver().equals(firebaseUser.getUid())) {
                        mUsers.add(chatMessage);
                    }

                }

                ChatMessage temp;
                for(int i=0; i<mUsers.size(); i++) {
                    for(int j=1; j<mUsers.size() - 1; j++) {
                        if (mUsers.get(j-1).getMessageTime() > mUsers.get(0).getMessageTime()) {
                            temp = mUsers.get(j-1);
                            mUsers.set(j-1, mUsers.get(j));
                            mUsers.set(j, temp);
                        }
                    }
                }
                Collections.reverse(mUsers);
                ArrayList<ChatMessage> unique = new ArrayList<>();
                unique.add(mUsers.get(0));
                boolean flag = false;
                for (int i=0; i<mUsers.size(); i++) {
                    flag = false;
                    for (int j=0; j<unique.size(); j++) {
                        if (mUsers.get(i).getSender().equals(unique.get(j).getSender())) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        unique.add(mUsers.get(i));
                    }
                }

                mUserAdapter = new UserAdapter(unique);
                mUsersList.setAdapter(mUserAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class UserHolder extends RecyclerView.ViewHolder {

        private TextView mUserNameList;
        private ImageView mNewMessages;
        private RelativeLayout mRelativeLayout;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mUserNameList = itemView.findViewById(R.id.user_list_holder_name);
            mRelativeLayout = itemView.findViewById(R.id.show_selected_user);
            mNewMessages = itemView.findViewById(R.id.new_messages);
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        private ArrayList<ChatMessage> mUserArrayList;

        public UserAdapter(ArrayList<ChatMessage> userArrayList) {
            mUserArrayList = userArrayList;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View listView = inflater.inflate(R.layout.holder_user_list, parent, false);
            UserHolder holder = new UserHolder(listView);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final UserHolder holder, final int position) {

            ChatMessage chatMessage = mUserArrayList.get(position);
            holder.mUserNameList.setText(mUserArrayList.get(position).getSenderName());
            holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                    intent.putExtra("userId", mUserArrayList.get(position).getSender());
                    intent.putExtra("userName", mUserArrayList.get(position).getSenderName());
                    startActivity(intent);
                }
            });
            if (!chatMessage.isIsseen()) {
                holder.mNewMessages.setVisibility(View.VISIBLE);
            } else {
                holder.mNewMessages.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return mUserArrayList.size();
        }
    }
}