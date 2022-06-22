package com.example.chatapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.repositories.ConversationRepo;
import com.example.chatapp.viewmodels.ContactsViewModel;
import com.example.chatapp.viewmodels.ConversationViewModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class firebaseService extends FirebaseMessagingService {
    static ConversationViewModel conversationViewModel;
    static ContactsViewModel contactsViewModel;

    public firebaseService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            createNotificationChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_notific).setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(1, builder.build());

            Content content = new Content(
                    ConversationRepo.getLoggedUser().getId(),
                    remoteMessage.getData().get("fromUser"),
                    remoteMessage.getNotification().getBody(),
                    remoteMessage.getData().get("time"), false);

            Contact contact = new Contact(
                    remoteMessage.getData().get("fromUser"),
                    remoteMessage.getData().get("fromUser"),
                    remoteMessage.getData().get("server"),
                    null, null, ConversationRepo.getLoggedUser().getId());


            if (Objects.equals(remoteMessage.getData().get("type"), "0")) {
                //type == 0 --> new message received
                if (conversationViewModel != null) {
                    //update the conversation view model only when the conversation is active
                    conversationViewModel.receiveMessage(content);
                } else {
                    //the user is the contacts page
                    contactsViewModel.updateContact(content);
                }
            } else {
                // type == 1 --> user has been added
                if (conversationViewModel == null) {
                    //update the contact list only when no conversion is active
                    contactsViewModel.updateFromServer(contact);
                }
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "My channel", importance);
            channel.setDescription("chat app notification channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public static ContactsViewModel getContactsViewModel() {
        return contactsViewModel;
    }

    public static void setContactsViewModel(ContactsViewModel contactsViewModel) {
        firebaseService.contactsViewModel = contactsViewModel;
    }

    public static ConversationViewModel getConversationViewModel() {
        return conversationViewModel;
    }

    public static void setConversationViewModel(ConversationViewModel conversationViewModel) {
        firebaseService.conversationViewModel = conversationViewModel;
    }


}