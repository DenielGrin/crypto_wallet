package com.degrin.bitcoinwallet.core.navigation.utils.browser

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log


private const val TAG = "BrowserUtils"

fun Context.openUrlInBrowser(url: String) {
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    } catch (e: Exception) {
        Log.e(TAG, "Error opening transaction in browser: $url", e)
    }
}
