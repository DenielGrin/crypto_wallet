package com.degrin.bitcoinwallet.core.navigation.utils.warnings

import android.content.Context
import android.widget.Toast
import com.degrin.bitcoinwallet.R

fun showToastError(
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
