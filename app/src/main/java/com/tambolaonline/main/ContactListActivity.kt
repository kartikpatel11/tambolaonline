package com.tambolaonline.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.Manifest
import android.provider.ContactsContract
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.tambolaonline.data.Game
import com.tambolaonline.data.Participant
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.util.TambolaTicketGenerator
import com.tambolaonline.variations.VariationTypes
import kotlinx.android.synthetic.main.activity_contact_list.*

class ContactListActivity : AppCompatActivity() {

    val TAG = "ContactListActivity::"
    lateinit var game: Game
    var contactList = arrayListOf<Participant>()
    var participantList = arrayListOf<Participant>()

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
        var adapter = ArrayAdapter<Participant>(this, android.R.layout.simple_list_item_multiple_choice, contactList)

        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null)

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                    if(cursorPhone != null && cursorPhone.count > 0) {
                        while (cursorPhone.moveToNext()) {

                            val phoneNumValue = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            var participant = Participant(participantID = cursor.position, phone = phoneNumValue , name = name, ticket = TambolaTicketGenerator.generateTicket(), prize = HashSet<VariationTypes>())
                            contactList.add(participant)
                            // itemList.add(name)
                            // Log.e("Name ===>",phoneNumValue);
                            Log.e("position =", ""+cursor.position)
                        }
                        cursorPhone.close()
                    }

                }
            }
            cursor.close()
        } else {
            var participant = Participant(participantID = 1, phone = "6474083574" , name = "Nisha", ticket = TambolaTicketGenerator.generateTicket(), prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 2, phone = "6474083574" , name = "Kartik", ticket = TambolaTicketGenerator.generateTicket(), prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 3, phone = "6474083574" , name = "Ruchi", ticket = TambolaTicketGenerator.generateTicket(), prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 4, phone = "6474083574" , name = "Darsh", ticket = TambolaTicketGenerator.generateTicket(), prize = HashSet<VariationTypes>())
            contactList.add(participant)
            participant = Participant(participantID = 5, phone = "6474083574" , name = "Kavish", ticket = TambolaTicketGenerator.generateTicket(), prize = HashSet<VariationTypes>())
            contactList.add(participant)
        }
        contactListView.adapter = adapter

        contactListView.setOnItemClickListener { adapterView, view, i, l ->
            if (participantList.contains(contactList[i])) {
                participantList.remove(contactList[i])
            } else {
                participantList.add(contactList[i])
            }
            // android.widget.Toast.makeText(this, "You Selected the item --> "+itemList[i], android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    fun selectParticipants(view: View) {
        Log.e("select participants", "" + participantList.count())

        participantList.forEach {
            game.participants.add(it)
        }
        // Store updated Game object
        TambolaSharedPreferencesManager.put(game, TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)
        var intent: Intent = Intent(applicationContext, SelectionSummeryActivity::class.java)
        startActivity(intent)
    }

}