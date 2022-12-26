package com.tuly.doodlekong.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher

}