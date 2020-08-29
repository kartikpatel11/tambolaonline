package com.tambolaonline.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tambolaonline.adapters.MyActiveGamesRecyclerAdapter
import com.tambolaonline.data.ActiveGames
import com.tambolaonline.data.Host
import com.tambolaonline.services.ServiceBuilder
import com.tambolaonline.services.TambolaEndPoints
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import kotlinx.android.synthetic.main.activity_tambola_landing_page.*
import retrofit2.Call
import retrofit2.Response

class TambolaLandingPage : AppCompatActivity() {

    lateinit var host: Host

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambola_landing_page)


        TambolaSharedPreferencesManager.with(this.application)

        host = TambolaSharedPreferencesManager.get<Host>(TambolaConstants.HOST_KEY_NAME)!!

        val request = ServiceBuilder.buildService(TambolaEndPoints::class.java)
        val call = request.getMyGames(host.hostPhNo)


        //Populate games as host
        call.enqueue(object: retrofit2.Callback<List<ActiveGames>> {
            override fun onFailure(call: Call<List<ActiveGames>>, t: Throwable) {
                Toast.makeText(this@TambolaLandingPage, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<ActiveGames>>, response: Response<List<ActiveGames>>) {
                if (response.isSuccessful) {
                    myactivegames_recycler.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@TambolaLandingPage,
                            LinearLayoutManager.VERTICAL,false)
                        adapter =
                            MyActiveGamesRecyclerAdapter(response.body()!!, host, this.context)
                    }

                }
            }
        })
    }

    fun hostNewGame(view: View) {
        var intent: Intent = Intent(applicationContext, VariationsListActivity::class.java)
        startActivity(intent)
    }
}