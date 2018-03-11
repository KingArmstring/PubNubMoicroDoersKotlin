package com.armstring.pubnubchatapp.pojos

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class Pojo constructor(@JsonProperty("sender") sender: String, @JsonProperty("message") message: String, @JsonProperty("timeStamp") timeStamp: String) {

    private val sender: String? =null
    private val message: String? = null
    private val timeStamp: String? = null

    public fun getSender(): String {
        return this.sender!!
    }

    public fun getMessage(): String {
        return this.message!!//means please make sure that this is not null( can't be null)
    }

    public fun getTimeStamp(): String {
        return this.timeStamp!!
    }

    override fun equals(obj: Any?): Boolean {
        if(obj == null) {
            return false
        }
        if(obj == this) {
            return true
        }

        val other: Pojo = obj as Pojo //type cast
        return Objects.equals(this.message, other.message)
                && Objects.equals(this.sender, other.sender)
                && Objects.equals(this.timeStamp, other.timeStamp)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "Sender: " + this.sender +
                "Message: " + this.message +
                "TimeStamp: " + this.timeStamp
    }

}