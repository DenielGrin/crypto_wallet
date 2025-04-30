package com.degrin.bitcoinwallet.ui.components.alerts

import android.content.Context
import android.widget.Toast
import com.degrin.bitcoinwallet.R

fun showToast(
    context: Context,
    message: String?,
    duration: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(
        /* context = */ context,
        /* text = */ message ?: context.getString(R.string.default_unknown_error_message),
        /* duration = */ duration
    ).apply { show() }
}
