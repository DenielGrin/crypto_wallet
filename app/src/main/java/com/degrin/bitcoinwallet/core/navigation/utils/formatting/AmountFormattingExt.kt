package com.degrin.bitcoinwallet.core.navigation.utils.formatting

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun formatBalance(balance: BigDecimal): String {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    numberFormat.minimumFractionDigits = 2
    numberFormat.maximumFractionDigits = 8
    return numberFormat.format(balance)
}
