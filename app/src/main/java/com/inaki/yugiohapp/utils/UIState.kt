package com.inaki.yugiohapp.utils

import com.inaki.yugiohapp.model.CardData

sealed class UIState {
    class LOADING(val isLoading: Boolean = true) : UIState()
    class SUCCESS(val data: CardData): UIState()
    class ERROR(val error: Throwable): UIState()
}