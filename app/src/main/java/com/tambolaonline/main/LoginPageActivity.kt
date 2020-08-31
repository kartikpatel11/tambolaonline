package com.tambolaonline.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.tambolaonline.data.Host
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.google.firebase.messaging.FirebaseMessaging

class LoginPageActivity : AppCompatActivity() {

     private var host: Host? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        TambolaSharedPreferencesManager.with(this.application)

        //Only use this when you want login page to appear
        //TambolaSharedPreferencesManager.removeAll()

        host = TambolaSharedPreferencesManager.get<Host>(TambolaConstants.HOST_KEY_NAME)

        if(host != null) {
            var intent: Intent = Intent(applicationContext, TambolaLandingPage::class.java)
            startActivity(intent)
        }

        /*FirebaseMessaging.getInstance().subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.i("Test::", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
            }*/

    }

    fun openLandingPage(view: View) {



        var hostphNumber = findViewById<EditText>(R.id.txt_mobilenumber).text.toString()
        var hostNickname = findViewById<EditText>(R.id.txt_nickname).text.toString()

        if(hostphNumber.isNullOrEmpty() || hostNickname.isNullOrEmpty()) {
            var errorText = "Ooops!! Missing mobile number or Nickname"

            Toast.makeText(this,errorText,Toast.LENGTH_SHORT).show()
            return

        }
        var host =  Host(hostNickname, hostphNumber)

        TambolaSharedPreferencesManager.put(host, TambolaConstants.HOST_KEY_NAME)

        var intent: Intent = Intent(applicationContext, TambolaLandingPage::class.java)

        startActivity(intent)

    }
}