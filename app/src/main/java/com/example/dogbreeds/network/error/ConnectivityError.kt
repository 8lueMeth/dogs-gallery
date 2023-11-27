package com.example.dogbreeds.network.error

import com.example.dogbreeds.R

class ConnectivityError : BaseError() {
    override fun messageIdRes() = R.string.connectivity_error
}