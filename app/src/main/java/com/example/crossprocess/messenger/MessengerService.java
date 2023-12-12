package com.example.crossprocess.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
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
                    break;
            }
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
