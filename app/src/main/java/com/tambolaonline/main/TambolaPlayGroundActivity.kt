package com.tambolaonline.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_tambola_play_ground.*


class TambolaPlayGroundActivity : AppCompatActivity() {

    private var homeFragment = TambolaPlaygroundHomeFragment()
    private var winnersFragment = TambolaWinnersFragment()
    private var ticketFragment = TambolaParticipantTicketViewFragment()
    private var activeFragment:Fragment = homeFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambola_play_ground)


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