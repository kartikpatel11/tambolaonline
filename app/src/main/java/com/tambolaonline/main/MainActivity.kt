package com.tambolaonline.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startgameBtn = findViewById<Button>(R.id.startGameButton)
        startgameBtn.setOnClickListener{
            Toast.makeText(this@MainActivity,"Add your code here to go to next screen",Toast.LENGTH_SHORT).show()
            var contactListActivity: ContactListActivity = ContactListActivity()
            contactListActivity

            var intent: Intent = Intent(applicationContext, ContactListActivity::class.java)
            startActivity(intent)
        }
    }
}
