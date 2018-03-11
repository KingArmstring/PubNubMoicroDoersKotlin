package com.armstring.pubnubchatapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.armstring.pubnubchatapp.util.Constants
import com.armstring.pubnubchatapp.R
import com.armstring.pubnubchatapp.adapters.MyChatAdapter
import com.armstring.pubnubchatapp.util.Callback
import com.armstring.pubnubchatapp.util.DateTimeUtil
import com.google.common.collect.ImmutableMap
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.presence.PNHereNowResult
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ChatActivity : AppCompatActivity() {

    companion object {
        private val PUBSUB_CHANNEL: List<String> = Arrays.asList(Constants.CHANNEL_NAME)
    }
    private var mPubnub_DataStream: PubNub? = null
    private var mPubSubAdapter: MyChatAdapter? = null
    private var mPubSubPnCallback: Callback? = null
    private var sharedPreferences: SharedPreferences? = null
    private var userName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sharedPreferences = getSharedPreferences(Constants.DATASTREAM_PEERS, Context.MODE_PRIVATE)
        if(!(sharedPreferences!!.contains(Constants.DATASTREAM_UUID))) {
            var toLoginIntent: Intent = Intent(this@ChatActivity, LoginActivity::class.java)
            startActivity(toLoginIntent)
            finish()
            return
        }
        userName = sharedPreferences!!.getString(Constants.DATASTREAM_UUID, "")
        this.mPubSubAdapter = MyChatAdapter(this, 0, userName as String)

        //I think that this line is making a problem which is if you remove the "assert not null it does not work"
        //and if you cast it into MyChatAdapter it stops complaining as well but same, does not work.
        this.mPubSubPnCallback = Callback(this.mPubSubAdapter!!)//


        message_list.adapter = mPubSubAdapter
        initPubNub()
        initChannels()
    }

    private fun initPubNub() {
        var configuration: PNConfiguration = PNConfiguration()
        configuration.publishKey = Constants.PUBNUB_PUBLISH_KEY
        configuration.subscribeKey = Constants.PUBNUB_SUBSCRIBE_KEY
        configuration.uuid = this.userName
        configuration.isSecure = true
        this.mPubnub_DataStream = PubNub(configuration)
    }

    private fun initChannels() {

        this.mPubnub_DataStream!!.addListener(this.mPubSubPnCallback) // adding callback listener to the pubnub connection instance
        this.mPubnub_DataStream!!.subscribe().channels(PUBSUB_CHANNEL).withPresence().execute()//we have to come to that part later.
        this.mPubnub_DataStream!!.hereNow().channels(PUBSUB_CHANNEL).async(object : PNCallback<PNHereNowResult>() {
            override fun onResponse(result: PNHereNowResult?, status: PNStatus?) {

            }

        })
    }

    fun btnSend(view: View) {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        var message: Map<String, String> = ImmutableMap.of<String, String>(
                "sender", this.userName,
                "message", new_message.text.toString(),
                "timestamp", DateTimeUtil.getTimeStampUtc())

        Log.d("Armstring", message.get("sender"))
        Log.d("Armstring", message.get("message"))
        Log.d("Armstring", message.get("timestamp"))

        this.mPubnub_DataStream!!.publish().channel(Constants.CHANNEL_NAME).message(message)
            .async(object : PNCallback<PNPublishResult>() {
                override fun onResponse(result: PNPublishResult?, status: PNStatus?) {
                    try {
                        if (!status!!.isError) {
                            Toast.makeText(this@ChatActivity, "success", Toast.LENGTH_LONG).show()
                            new_message!!.setText("")
                        } else {
                            new_message!!.setText("")
                            Toast.makeText(this@ChatActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )
    }
}



/*
@OnClick(R.id.sendButton)
    fun publish() {
        val message = ImmutableMap.of("sender", this@MainActivity.mUsername!!,
                "message", mMessage!!.text.toString(),
                "timestamp", DateTimeUtil.getTimeStampUtc())

        this.mPubnub_DataStream!!.publish().channel(Constants.CHANNEL_NAME).message(message).async(
                object : PNCallback<PNPublishResult>() {
                    override fun onResponse(result: PNPublishResult, status: PNStatus) {
                        try {
                            if (!status.isError) {
                                mMessage!!.setText("")
                            } else {
                                mMessage!!.setText("")
                                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
        )
    }
 */