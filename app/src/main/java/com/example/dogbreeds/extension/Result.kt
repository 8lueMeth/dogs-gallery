package com.example.dogbreeds.extension

import com.example.dogbreeds.R
import com.example.dogbreeds.network.error.BaseError

val <T> Result<T>.errorResId: Int
    get() = if (isSuccess)
        throw IllegalStateException("No Error happened!")
    else if (exceptionOrNull() is BaseError) {
        (exceptionOrNull() as BaseError).messageIdRes()
    } else R.string.unknown_error