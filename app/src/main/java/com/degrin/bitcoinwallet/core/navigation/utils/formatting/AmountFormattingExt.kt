package com.degrin.bitcoinwallet.core.navigation.utils.formatting

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formatBalance(balance: BigDecimal): String {
    val symbols = DecimalFormatSymbols(Locale.US)
    val decimalFormat = DecimalFormat("#.########", symbols)
    decimalFormat.minimumFractionDigits = 0
    decimalFormat.maximumFractionDigits = 8
    return decimalFormat.format(balance)
}
