package com.miniproject.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private final static int MSG_TYPE_LEFT = 0;
    private final static int MSG_TYPE_RIGHT = 1;
    private final static int GALLERY_PICK = 2;
    private final static int VIDEO_PICK = 3;

    private TextView mUsername;

    private List<ChatMessage> mChatMessages;
    private RecyclerView mShowMessageFromUser;
    private EditText mMessage;
    private FloatingActionButton mSendMessage;
    private ChatAdapter adapter;
    private ImageButton mSendImage;

    StorageReference mImageStorage;
    String userId;
    ValueEventListener seenListener;
    FirebaseUser fUser;
    DatabaseReference reference;


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
                    Snackbar.make(findViewById(android.R.id.content), "You have been logout", Snackbar.LENGTH_SHORT).show();
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
    protected void onPause() {
        super.onPause();
//        reference.removeEventListener(seenListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mUsername = findViewById(R.id.user_name_toolbar);
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        final String userName = bundle.getString("userName");
        mUsername.setText(userName);

        Log.d("MessageActivity", userId);

        mShowMessageFromUser = findViewById(R.id.show_messages_from_user);
        mShowMessageFromUser.setHasFixedSize(true);
        mShowMessageFromUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        readMessages(fUser.getUid(), userId);

        mMessage = findViewById(R.id.input);
        mSendMessage = findViewById(R.id.fab);

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = mMessage.getText().toString();
                if(!msg.equals("")) {
                    sendMessage(fUser.getUid(), userId, msg);
                    mMessage.setText("");
                } else {
                    Toast.makeText(MessageActivity.this, "Enter Message", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        seenMessage(userId);

        mImageStorage = FirebaseStorage.getInstance().getReference();
        mSendImage = findViewById(R.id.select_image);
        mSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String options[] = {"image", "video"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
                builder.setTitle("What you want to send: ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Intent galleryIntent = new Intent();
                            galleryIntent.setType("image/*");
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_PICK);
                        } else {
                            Intent videoIntent = new Intent();
                            videoIntent.setType("video/*");
                            videoIntent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(videoIntent, "Select Video"), VIDEO_PICK);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {

            final Uri imageUri = data.getData();

            fUser = FirebaseAuth.getInstance().getCurrentUser();

            final DatabaseReference user_message_push = FirebaseDatabase.getInstance().getReference().child("chats").push();

            final String push_id = user_message_push.getKey();

            StorageReference filePath = mImageStorage.child("message_images").child(push_id + ".jpg");

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String download_url = uri.toString();
                                    user_message_push.setValue(new ChatMessage(download_url, userId, fUser.getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "image", false));
                                }
                            });
                        }
                    }
                }
            });

        } else if (requestCode == VIDEO_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            final Uri videoUri = data.getData();

            fUser = FirebaseAuth.getInstance().getCurrentUser();
            final String current_user_ref = "messages/" + fUser.getUid() + "/" + userId;
            final String chat_user_ref = "messages/" + userId + "/" + fUser.getUid();

            final DatabaseReference user_message_push = FirebaseDatabase.getInstance().getReference().child("chats").push();

            final String push_id = user_message_push.getKey();

            StorageReference filePath = mImageStorage.child("message_videos").child(push_id + ".mpeg");

            filePath.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String download_url = uri.toString();
                                    user_message_push.setValue(new ChatMessage(download_url, userId, fUser.getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "video", false));
                                }
                            });
                        }
                    }
                }
            });

        }

    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("chats").push().setValue(new ChatMessage(message, receiver, sender, FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "text", false));
    }

    private class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

        private List<ChatMessage> chats;

        private ChatAdapter(List<ChatMessage> chats) {
            this.chats = chats;
        }

        @Override
        public int getItemViewType(int position) {
            fUser = FirebaseAuth.getInstance().getCurrentUser();
            if (chats.get(position).getSender().equals(fUser.getUid())) {
                return MSG_TYPE_RIGHT;
            } else {
                return MSG_TYPE_LEFT;
            }
        }

        @NonNull
        @Override
        public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (viewType == MSG_TYPE_RIGHT) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            }
            return new ChatHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ChatHolder holder, int position) {
            final ChatMessage chatMessage = chats.get(position);

            if (chatMessage.getType().equals("text")) {
                holder.mMessageText.setVisibility(View.VISIBLE);
                holder.mMessageText.setText(chatMessage.getMessage());
                holder.mSentImage.setVisibility(View.GONE);
                holder.mSentVideo.setVisibility(View.GONE);

            } else if (chatMessage.getType().equals("image")){
                holder.mSentImage.setVisibility(View.VISIBLE);
                holder.mMessageText.setVisibility(View.GONE);
                holder.mSentVideo.setVisibility(View.GONE);
                Picasso.get().load(chatMessage.getMessage()).resize(150, 200).centerCrop().into(holder.mSentImage);
                holder.mSentImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), PlayVideoActivity.class);
                        intent.putExtra("link", chatMessage.getMessage());
                        intent.putExtra("type", "image");
                        startActivity(intent);
                    }
                });

            } else {

                holder.mSentImage.setVisibility(View.GONE);
                holder.mMessageText.setVisibility(View.GONE);
                holder.mSentVideo.setVisibility(View.VISIBLE);

                holder.mSentVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), PlayVideoActivity.class);
                        intent.putExtra("uri", chatMessage.getMessage());
                        intent.putExtra("type", "video");
                        startActivity(intent);
                    }
                });
            }

            holder.mMessageTime.setText(DateFormat.format("dd-MM-yyyy HH:mm", chatMessage.getMessageTime()));

            if (position == chats.size() - 1) {

                holder.mIsSeen.setVisibility(View.VISIBLE);
                if (chatMessage.isIsseen()) {
                    holder.mIsSeen.setText("Seen");
                } else {
                    holder.mIsSeen.setText("Delivered");
                }

            } else {
                holder.mIsSeen.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return chats.size();
        }
    }

    private class ChatHolder extends RecyclerView.ViewHolder {

        final private TextView mMessageText;
        final private TextView mIsSeen;
        final private ImageView mSentImage;
        final private ImageView mSentVideo;
        final private TextView mMessageTime;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.show_message);
            mIsSeen = itemView.findViewById(R.id.txt_seen);
            mSentImage = itemView.findViewById(R.id.sent_image);
            mSentVideo = itemView.findViewById(R.id.sent_video);
            mMessageTime = itemView.findViewById(R.id.time_of_message);
        }
    }

    private void readMessages(final String myId, final String userId) {
        mChatMessages = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChatMessages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // LOAD MESSAGES
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if (chatMessage.getReceiver().equals(myId) && chatMessage.getSender().equals(userId) ||
                            chatMessage.getReceiver().equals(userId) && chatMessage.getSender().equals(myId)) {
                        mChatMessages.add(chatMessage);
                    }

                    // SEEN MESSAGE
                    if(chatMessage.getReceiver().equals(fUser.getUid()) && chatMessage.getSender().equals(userId)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }

                adapter = new ChatAdapter(mChatMessages);
                mShowMessageFromUser.setAdapter(adapter);

                if (!mChatMessages.isEmpty()) {
                    mShowMessageFromUser.scrollToPosition(mChatMessages.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void seenMessage(final String userId) {
        reference = FirebaseDatabase.getInstance().getReference("chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if(chatMessage.getReceiver().equals(fUser.getUid()) && chatMessage.getSender().equals(userId)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}