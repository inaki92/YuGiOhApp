package com.inaki.yugiohapp.rest

import com.inaki.yugiohapp.model.CardData
import retrofit2.Response
import retrofit2.http.GET

interface CardApi {

    @GET(CARD_LIST)
    suspend fun getCardList(): Response<CardData>

    companion object {
        const val BASE_URL = "https://db.ygoprodeck.com/api/v7/"
        private const val CARD_LIST = "cardinfo.php"
    }
}