package com.armstring.pubnubchatapp.views


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.armstring.pubnubchatapp.R
import kotlinx.android.synthetic.main.activity_main.*

class EntranceActivity : AppCompatActivity() {

    var fadeAnimation: Animation? = null
    var appearAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        appearAnimation = AnimationUtils.loadAnimation(this@EntranceActivity, R.anim.appear_animation)
        fadeAnimation = AnimationUtils.loadAnimation(this@EntranceActivity, R.anim.fade_animation)

//        imgLogo.animation = appearAnimation
//        txtLogo.animation = appearAnimation

        entranceActivityId.animation = appearAnimation

        appearAnimation!!.setAnimationListener(object :  Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                imgLogo.clearAnimation()
                //txtLogo.clearAnimation()

                try {
                    Thread.sleep(2000)
                }catch (e: InterruptedException) {

                }
                entranceActivityId.animation = fadeAnimation
                //txtLogo.animation = fadeAnimation
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })

        fadeAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                //imgLogo.visibility = View.GONE
                //txtLogo.visibility = View.GONE

                var intent: Intent = Intent(this@EntranceActivity, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_animation, R.anim.fade_animation)
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })


        setToFullScreen()

    }

    private fun setToFullScreen() {
        entranceActivityId.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
}
