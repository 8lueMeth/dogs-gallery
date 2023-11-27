package com.example.dogbreeds.network.error

abstract class BaseError(error: String? = null) : Exception(error) {
    abstract fun messageIdRes(): Int
}