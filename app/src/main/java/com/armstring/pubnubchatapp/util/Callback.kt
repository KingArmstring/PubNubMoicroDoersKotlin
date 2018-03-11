package com.armstring.pubnubchatapp.util

import android.util.Log
import com.armstring.pubnubchatapp.adapters.MyChatAdapter
import com.armstring.pubnubchatapp.pojos.Pojo
import com.fasterxml.jackson.databind.JsonNode
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult


open class Callback(myChatAdapter: MyChatAdapter): SubscribeCallback() {

    private var adapter: MyChatAdapter = myChatAdapter

    override fun status(pubnub: PubNub?, status: PNStatus?) {
    }

    override fun presence(pubnub: PubNub?, presence: PNPresenceEventResult?) {
    }

    override fun message(pubnub: PubNub?, message: PNMessageResult?) {
        try {
            val jsonMsg: JsonNode = message!!.message
            val dsMsg: Pojo = JsonUtil.convert(jsonMsg, Pojo::class.java)
            Log.d("Armstring", "do we callback the PubNub")
            adapter.add(dsMsg)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
