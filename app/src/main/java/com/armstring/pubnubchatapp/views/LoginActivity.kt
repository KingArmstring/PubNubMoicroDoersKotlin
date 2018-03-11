package com.armstring.pubnubchatapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.armstring.pubnubchatapp.util.Constants
import com.armstring.pubnubchatapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences(Constants.DATASTREAM_PEERS, Context.MODE_PRIVATE)

        if(!(sharedPreferences!!.getString(Constants.DATASTREAM_UUID, "") == "")) {
            var intent: Intent = Intent(this@LoginActivity, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    fun btnLogin(view: View) {
        var userName: String = userNameEdit.text.toString()

        editor = sharedPreferences!!.edit()
        editor!!.putString(Constants.DATASTREAM_UUID, userName)
        editor!!.apply()

        var intent: Intent = Intent(this@LoginActivity, ChatActivity::class.java)
        startActivity(intent)
        finish()
    }
}
