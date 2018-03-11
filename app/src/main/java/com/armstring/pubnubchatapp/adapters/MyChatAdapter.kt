package com.armstring.pubnubchatapp.adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.armstring.pubnubchatapp.R
import com.armstring.pubnubchatapp.pojos.Pojo
import com.armstring.pubnubchatapp.pojos.PubSubListRow
import kotlinx.android.synthetic.main.list_row_pubsub1.view.*
import kotlin.concurrent.timer


class MyChatAdapter(context: Context, resource: Int, var userName: String) : ArrayAdapter<Pojo>(context, resource) {

    var values = ArrayList<Pojo>()
    var layoutInflater = LayoutInflater.from(context)

    override fun add(message: Pojo) {
        values.add(message)
        Log.d("Armstring", "element has been added and size: " + this.count.toString())

        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("Armstring", "adapter method getView")
        val msg: Pojo = this.values.get(position)
        val msgView: PubSubListRow = PubSubListRow()
        val tempView: View

        if (msg.sender == userName) {
            tempView = layoutInflater.inflate(R.layout.list_row_pubsub1, parent, false)

            msgView.sender = tempView.findViewById(R.id.sender) as TextView
            msgView.message = tempView.findViewById(R.id.message) as TextView
            msgView.timeStamp = tempView.findViewById(R.id.timestamp) as TextView

        } else {
            tempView = layoutInflater.inflate(R.layout.list_row_pubsub2, parent, false)

            msgView.sender = tempView.findViewById(R.id.sender2) as TextView
            msgView.message = tempView.findViewById(R.id.message2) as TextView
            msgView.timeStamp = tempView.findViewById(R.id.timestamp2) as TextView

        }

        msgView.sender.text = msg.sender
        msgView.message.text = msg.message
        msgView.timeStamp.text = msg.timeStamp

        return tempView
    }


    override fun getCount(): Int {
        Log.d("Armstring", "adapter method getCount size: " + values.size)
        return values.size
    }

    override fun clear() {
        this.values.clear()
        notifyDataSetChanged()
    }
}