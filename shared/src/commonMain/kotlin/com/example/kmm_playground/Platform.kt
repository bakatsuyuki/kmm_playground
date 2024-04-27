package com.example.kmm_playground

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform