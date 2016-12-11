package com.tacademy.sadajo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tacademy.sadajo.chatting.ChattingDetailActivity;
import com.tacademy.sadajo.home.HomeActivity;
import com.tacademy.sadajo.network.chatting.ChatListDB;

import java.net.URLDecoder;
import java.util.Map;

/*
  실제 FCM을 통한 푸쉬메세지를 받는 곳
 */
public class FCMPushMessageService extends FirebaseMessagingService {

    private static final String TAG = "FCMPushMessageService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        //실제 푸쉬로 넘어온 데이터
        Map<String, String> receiveData = remoteMessage.getData();   //실제 보낸 메세지가 들어가 있음
        //실제 보낸 메세지가 들어가 있음
        try {
            //한글은 반드시 디코딩 해준다.
            //키 맞춰주기
//            sendPushNotification(URLDecoder.decode(receiveData.get("message"), "UTF-8"),
//                    URLDecoder.decode(receiveData.get("room"), "UTF-8"));
            ChatListDB chatListDB = new ChatListDB();
            chatListDB.receiverCode = Integer.parseInt(receiveData.get("partner"));
            chatListDB.receiverImg = receiveData.get("img");
            chatListDB.receiverName = URLDecoder.decode(receiveData.get("nick"), "UTF-8");
            chatListDB.roomNum = Integer.parseInt(receiveData.get("room"));


            // sendPushNotification(URLDecoder.decode(receiveData.get("partner"), "UTF-8"));
            sendPushNotification(chatListDB);

        } catch (Exception e) {

        }
    }

    private void sendPushNotification(String pushMessage) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("fcmExtra", pushMessage);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);//한번 실행하면 스테이터스바에서 없어지게

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //사운드

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)//스테이터스바 아이콘
                .setContentTitle("사다조")
                .setContentText(pushMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    //메세지 푸쉬
    private void sendPushNotification(ChatListDB chatListDB) {
        Intent intent = new Intent(this, ChattingDetailActivity.class);
        //   intent.putExtra("fcmExtra", pushMessage);
        intent.putExtra("roomNum", chatListDB.roomNum);
        intent.putExtra("receiver", chatListDB.receiverCode); //상대방
        intent.putExtra("receiverName", chatListDB.receiverName);
        intent.putExtra("receiverImg", chatListDB.receiverImg);
        intent.putExtra("type", 2);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);//한번 실행하면 스테이터스바에서 없어지게

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //사운드

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)//스테이터스바 아이콘
                .setContentTitle("사다조")
                .setContentText("새로운 메세지")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
