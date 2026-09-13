package ru.social.core.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object NetworkUtils {

    suspend fun <T> makeCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        call: suspend () -> T
    ): Result<T> = runCatching {
        withContext(dispatcher) {
            call.invoke()
        }
    }

    fun <T> makeCallIO(call: () -> T) {
        CoroutineScope(Dispatchers.IO).launch {
            call.invoke()
        }
    }

}