package com.yefimoyevhen.appspot.util

class FakeInternetChecker() : InternetChecker {
    var isConnected = true
    override fun hasInternetConnection(): Boolean = isConnected

}