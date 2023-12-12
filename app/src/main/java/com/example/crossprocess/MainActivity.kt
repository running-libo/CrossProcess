package com.example.crossprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.crossprocess.messenger.MessengerService

class MainActivity : ComponentActivity() {

    private var mService: Messenger?= null
    private lateinit var btnMsg: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMsg = findViewById(R.id.btn_msg)

        //绑定service
        bindService(Intent(this, MessengerService::class.java),
            connection, Context.BIND_AUTO_CREATE)

        btnMsg.setOnClickListener {
            mService?.let {
                //创建消息，通过Bundle传递数据
                var message = Message.obtain(null, 100)
                message.data = Bundle().apply {
                    putString("data", "服务端，我给你发送消息了")
                }

                //将客户端的Messenger对象传递给服务端，才能让双方互相通信
                message.replyTo = mClientMessenger

                //像服务端进程发送消息
                it.send(message)
            }
        }
    }

    /**
     * 客户端Messenger对象
     */
    private val mClientMessenger: Messenger = Messenger(MessengerHandler())

    /**
     * 用于构建客户端的Messenger对象，并处理服务端的消息
     */
    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                100 -> {
                    println(msg.data.getString("data"))
                }
            }
        }
    }

    private val connection: ServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, ibinder: IBinder?) {
            mService = Messenger(ibinder)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    override fun onDestroy() {
        //解绑service
        unbindService(connection)

        super.onDestroy()
    }
}