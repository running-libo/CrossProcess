package com.example.crossprocess.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {

    /**
     * 处理客户端消息，并用于构建Messenger
     */
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 100:
                    System.out.println(msg.getData().getString("data"));

                    sendToClient(msg);
                    break;
            }
        }
    }

    /**
     * 给客户度发送消息
     */
    private static void sendToClient(Message message) {
        Messenger client = message.replyTo;  //通过 message拿到客户传递过来的 Messenger，通过该对象发送message
        //当然，回传消息还是要通过message
        Message msg = Message.obtain(null, 100);
        Bundle bundle = new Bundle();
        bundle.putString("data", "客户端, 我收到你的消息了");
        msg.setData(bundle);
        try {
            client.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建Messenger对象
     */
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //将Messenger对象的Binder返回给客户端
        return mMessenger.getBinder();
    }
}
