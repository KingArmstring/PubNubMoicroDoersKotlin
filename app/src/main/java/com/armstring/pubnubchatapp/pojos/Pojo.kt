package com.armstring.pubnubchatapp.pojos

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class Pojo(@JsonProperty("sender") var sender: String = "",
           @JsonProperty("message") var message: String = "",
           @JsonProperty("timeStamp") var timeStamp: String = "") {

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