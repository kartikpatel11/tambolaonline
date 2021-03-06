package com.tambolaonline.services


import com.tambolaonline.data.ActiveGames
import com.tambolaonline.data.Game
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface TambolaEndPoints {

    @GET("/getmyticket")
    fun getMyTicket(@Query("gid") gameid: String,
                    @Query("phno") participantNo:String): Call<Game>

    @GET("/getmyactivegames")
    fun getMyGames(@Query("phno") phone: String): Call<List<ActiveGames>>

    @POST("/storegamestatus")
    @FormUrlEncoded
    fun storeGameStatus(@Field("gid") gameid: String,
                        @Field("currnum") currentNum: Int): Call<String>


    @GET("/getmyhostedgame")
    fun getMyHostedGame(@Query("gid") gameid:String): Call<Game>
}