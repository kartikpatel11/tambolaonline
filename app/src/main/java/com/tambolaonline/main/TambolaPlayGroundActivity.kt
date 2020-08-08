package com.tambolaonline.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tambolaonline.data.Game
import com.tambolaonline.data.Participant
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.util.TambolaTicketGenerator
import kotlinx.android.synthetic.main.activity_tambola_play_ground.*


class TambolaPlayGroundActivity : AppCompatActivity() {

    private var homeFragment = TambolaPlaygroundHomeFragment()
    private var winnersFragment = TambolaWinnersFragment()
    private var ticketFragment = TambolaParticipantTicketViewFragment()
    private var activeFragment:Fragment = homeFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambola_play_ground)


        //TODO: To be removed when game object comes with populated values of participants
        var game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)
        ////////
        val dataList = ArrayList<Participant>()
        dataList.add(Participant(1,"9820742767","Kartik", TambolaTicketGenerator.generateTicket()))
        dataList.add(Participant(2,"9820742766","Ruchi", TambolaTicketGenerator.generateTicket()))
        dataList.add(Participant(3,"9820742765","Nisha", TambolaTicketGenerator.generateTicket()))
        dataList.add(Participant(4,"9820742764","Me", TambolaTicketGenerator.generateTicket()))
        game?.participants?.addAll(dataList)

        TambolaSharedPreferencesManager.put(game,TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)
        ///////////

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