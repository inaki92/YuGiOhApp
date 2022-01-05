package com.inaki.yugiohapp.rest

import com.inaki.yugiohapp.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CardsRepository {
    val cardList: StateFlow<UIState>
    suspend fun getCardsList()
}

class CardsRepositoryImpl(
    private val cardApi: CardApi
) : CardsRepository {

    private val _cardListFlow: MutableStateFlow<UIState> = MutableStateFlow(UIState.LOADING())

    override val cardList: StateFlow<UIState>
        get() = _cardListFlow

    override suspend fun getCardsList() {
        try {
            val response = cardApi.getCardList()

            if (response.isSuccessful) {
                response.body()?.let {
                    _cardListFlow.value = UIState.SUCCESS(it)
                } ?: run {
                    _cardListFlow.value = UIState.ERROR(IllegalStateException("Cards are coming as null!"))
                }

            } else {
                _cardListFlow.value = UIState.ERROR(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            _cardListFlow.value = UIState.ERROR(e)
        }
    }


}