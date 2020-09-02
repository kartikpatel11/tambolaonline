package com.tambolaonline.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tambolaonline.data.Game
import com.tambolaonline.data.Participant
import com.tambolaonline.services.ServiceBuilder
import com.tambolaonline.services.TambolaEndPoints
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.util.TambolaTicketGenerator
import kotlinx.android.synthetic.main.activity_tambola_play_ground.*
import retrofit2.Call
import retrofit2.Response


class TambolaPlayGroundActivity : AppCompatActivity() {

    private var homeFragment = TambolaPlaygroundHomeFragment()
    private var winnersFragment = TambolaWinnersFragment()
    private var ticketFragment = TambolaParticipantTicketViewFragment()
    private var activeFragment:Fragment = homeFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambola_play_ground)

        val gameId = intent.getStringExtra(TambolaConstants.GAME_ID)

        val request = ServiceBuilder.buildService(TambolaEndPoints::class.java)
        val call = request.getMyHostedGame(gameId!!)

        call.enqueue(object: retrofit2.Callback<Game> {
            override fun onFailure(call: Call<Game>, t: Throwable) {
                Toast.makeText(this@TambolaPlayGroundActivity, "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.isSuccessful) {
                    TambolaSharedPreferencesManager.put(response.body()!!,TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)
                }
            }
        })



        supportFragmentManager.beginTransaction().add(R.id.tambolaplaygroundfragment, ticketFragment, "3").hide(ticketFragment).commit();
        supportFragmentManager.beginTransaction().add(R.id.tambolaplaygroundfragment, winnersFragment, "2").hide(winnersFragment).commit();
        supportFragmentManager.beginTransaction().add(R.id.tambolaplaygroundfragment,homeFragment, "1").commit();

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
             R.id.menu_navigation_playground -> {
                    swapFragment(homeFragment)
                    true
                }
                R.id.menu_navigation_winners -> {
                    swapFragment(winnersFragment)
                    true

                }
                R.id.menu_navigation_participant_tickets -> {
                    swapFragment(ticketFragment)
                    true
                }

                else -> false

            }

        }

    }

    /**
     * Hide currently active fragment and show the fragment that's clicked on
     */
    private fun swapFragment (fragment: Fragment) {

        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
        activeFragment = fragment

    }
}