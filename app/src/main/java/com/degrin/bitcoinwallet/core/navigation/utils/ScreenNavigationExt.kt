package com.degrin.bitcoinwallet.core.navigation.utils

import com.degrin.bitcoinwallet.core.navigation.Screen
import com.google.gson.Gson
import java.io.Serializable

fun Screen.defaultScreenName(): String = this::class.java.simpleName

fun Screen.defaultScreenNameWithParams(vararg params: String): String {
    return defaultScreenName() + params.joinToString { "/{$it}" }
}

fun Screen.defaultScreenNameWithOptionalParams(optionalParams: List<String>): String {
    val optionals = optionalParams.joinToString { "?$it={$it}" }
    return defaultScreenName() + optionals
}

fun Screen.screenNameWithParams(optionalParams: List<Pair<String, String?>>): String {
    val optionals = optionalParams.joinToString { pair ->
        val key = pair.first
        val value = pair.second
        "?${key}=${value}"
    }
    return defaultScreenName() + optionals
}

fun Screen.screenNameWithOptionalParams(optionalParams: List<Pair<String, Any>>): String {
    val optionals = optionalParams.joinToString { "?${it.first}=${it.second}" }
    return defaultScreenName() + optionals
}

fun Screen.screenNameWithOptionalParams(
    params: List<Any>,
    optionalParams: List<Pair<String, Any>>
): String {
    val optionals = optionalParams.joinToString { "?${it.first}=${it.second}" }
    return defaultScreenName() + params.joinToString { "/$it" } + optionals
}

fun Screen.sendSerializableParams(optionalParams: List<Pair<String, Serializable>>): String {
    val optionals = optionalParams.joinToString { pair ->
        val key = pair.first
        val value = pair.second.let { Gson().toJson(it) }
        "?${key}=${value}"
    }
    return defaultScreenName() + optionals
}

inline fun <reified T> getDataFromArgs(json: String?): T? {
    return try {
        Gson().fromJson(json, T::class.java)
    } catch (e: Exception) {
        null
    }
}
