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
import com.tambolaonline.variations.VariationTypes
import kotlinx.android.synthetic.main.activity_contact_list.*

class ContactListActivity : AppCompatActivity() {

    val TAG = "ContactListActivity::"
    lateinit var game: Game
    var itemList = arrayListOf<String>()
    var participantList = arrayListOf<String>()

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

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemList)

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
                            val phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            itemList.add(name)
                            Log.e("Name ===>",phoneNumValue);
                        }
                        cursorPhone.close()
                    }

                }
            }
            cursor.close()
        } else {
            itemList.add("nisha")
            //   toast("No contacts available!")
        }
        contactListView.adapter = adapter


        contactListView.setOnItemClickListener { adapterView, view, i, l ->
            if (participantList.contains(itemList[i])) {
                participantList.remove(itemList[i])
            } else {
                participantList.add(itemList[i])
            }
            // android.widget.Toast.makeText(this, "You Selected the item --> "+itemList[i], android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    fun selectParticipants(view: View) {
        Log.e("select participants", "")
        participantList.forEachIndexed { index, element ->
            var participant = Participant(participantID = index, name = element, ticket = emptyArray(), prize = HashSet<VariationTypes>())
            game.participants.add(participant)
        }
        var intent: Intent = Intent(applicationContext, TambolaPlayGroundActivity::class.java)
        startActivity(intent)
    }

}