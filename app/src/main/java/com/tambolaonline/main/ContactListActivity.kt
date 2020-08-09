package com.tambolaonline.main

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tambolaonline.adapters.ContactListRecyclerAdapter
import com.tambolaonline.data.Game
import com.tambolaonline.data.Participant
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.util.TambolaTicketGenerator
import com.tambolaonline.variations.VariationTypes

class ContactListActivity : AppCompatActivity() {

    val TAG = "ContactListActivity::"
    lateinit var game: Game
    var contactList = arrayListOf<Participant>()
    var participantList = arrayListOf<Participant>()


    lateinit var contactListRecyclerAdapter: ContactListRecyclerAdapter

    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        //Get Game object from shared preferences
        TambolaSharedPreferencesManager.with(this.application)
        game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!

        loadContacts()
    }

    private fun loadContacts() {
        var builder = StringBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            getContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                Log.i(TAG,"Permission must be granted in order to display contacts information")
            }
        }
    }


    private fun getContacts() {

        //Initialize recyclerview
        var contactListRecyclerView = findViewById<RecyclerView>(R.id.contactListRecyclerView)
        contactListRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)


        val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

         var cr = getContentResolver();
         var cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                var nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                var numberIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                while (cursor.moveToNext()) {
                    var name = cursor.getString(nameIndex);
                    var phoneNumValue = cursor.getString(numberIndex);

                    var participant = Participant(
                        participantID = cursor.position,
                        phone = phoneNumValue,
                        name = name,
                        prize = HashSet<VariationTypes>()
                    )

                    contactList.add(participant)

                }
            } finally {
                cursor.close();
            }

            if(contactList.size ==0){
            var participant = Participant(participantID = 1, phone = "6474083574" , name = "Nisha", prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 2, phone = "6474083574" , name = "Kartik", prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 3, phone = "6474083574" , name = "Ruchi",  prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 4, phone = "6474083574" , name = "Darsh",  prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 5, phone = "6474083574" , name = "Kavish",  prize = HashSet<VariationTypes>())
            contactList.add(participant)
        }

            contactListRecyclerAdapter =
                ContactListRecyclerAdapter(contactList, participantList, this)
            contactListRecyclerView.adapter = contactListRecyclerAdapter
        }

    }

    fun selectParticipants(view: View) {
            participantList.forEach() {

                //Generate ticket for selected contact
                it.ticket=TambolaTicketGenerator.generateTicket()

                //Add participant to game object
                game.participants.add(it)

            }
        // Store updated Game object
        TambolaSharedPreferencesManager.put(game, TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)

        var intent: Intent = Intent(applicationContext, SelectionSummeryActivity::class.java)
        startActivity(intent)
    }

}