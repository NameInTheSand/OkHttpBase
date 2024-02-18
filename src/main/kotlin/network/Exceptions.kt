package org.example.network

open class NetworkException(
    msg: String = ""
) : Exception(msg)

class PageNotFoundException() : NetworkException(msg = "Page not found")