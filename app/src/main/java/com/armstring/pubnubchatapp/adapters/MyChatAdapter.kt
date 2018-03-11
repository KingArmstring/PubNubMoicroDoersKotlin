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


class MyChatAdapter constructor(context: Context, resource: Int, userName: String): ArrayAdapter<Pojo>(context, resource){

    var resource: Int? = null
    var layoutInflater: LayoutInflater? = null
    var values: MutableList<Pojo>? = null
    var userName: String? = null
    var myContext: Context = context

    init {
        Log.d("Armstring", "init in the constructor")
        this.resource = resource
        this.layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.userName = userName
        this.values = ArrayList<Pojo>()
    }

    override fun add(message : Pojo) {
        values!!.add(0, message)
        Log.d("Armstring", "element has been added and size: " + this.count.toString())
        (myContext as Activity).runOnUiThread(object : Runnable{
            override fun run() {
                notifyDataSetChanged()
            }
        })
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("Armstring", "adapter method getView")
        val msg: Pojo = this.values!!.get(position)
        val msgView: PubSubListRow = PubSubListRow()
        val tempView: View
        if(msg.getSender().equals(userName)) {//this means that this message is sent from the owner of the phone.
            tempView = layoutInflater!!.inflate(R.layout.list_row_pubsub1, parent, false)
            msgView.sender = tempView.findViewById(R.id.sender) as TextView
            msgView.message = tempView.findViewById(R.id.message) as TextView
            msgView.timeStamp = tempView.findViewById(R.id.timestamp) as TextView
        }else {//this means that this message is received by the owner of the phone
            tempView = layoutInflater!!.inflate(R.layout.list_row_pubsub2, parent, false)
            msgView.sender = tempView.findViewById(R.id.sender2) as TextView
            msgView.message = tempView.findViewById(R.id.message2) as TextView
            msgView.timeStamp = tempView.findViewById(R.id.timestamp2) as TextView
        }

        msgView.sender!!.text = msg.getSender()
        msgView.message!!.text = msg.getMessage()
        msgView.timeStamp!!.text = msg.getTimeStamp()

        return tempView
    }


    override fun getCount(): Int {
        Log.d("Armstring", "adapter method getCount size: " + values!!.size)
        return values!!.size
    }

    override fun clear() {
        this.values!!.clear()
        notifyDataSetChanged()
    }
}












