package com.degrin.bitcoinwallet.feature.wallet.domain.impl

import com.degrin.bitcoinwallet.feature.wallet.data.model.TransactionParams
import org.bitcoinj.base.AddressParser
import org.bitcoinj.base.BitcoinNetwork
import org.bitcoinj.base.Coin
import org.bitcoinj.base.ScriptType
import org.bitcoinj.base.Sha256Hash
import org.bitcoinj.core.Context
import org.bitcoinj.core.Transaction
import org.bitcoinj.core.TransactionInput
import org.bitcoinj.core.TransactionOutPoint
import org.bitcoinj.core.TransactionWitness
import org.bitcoinj.crypto.DumpedPrivateKey
import org.bitcoinj.script.ScriptBuilder

class WalletTransactionBuilder {

    @OptIn(ExperimentalStdlibApi::class)
    fun buildTransaction(params: TransactionParams): Result<String> {
        return try {
            Context.propagate(Context())

            val scriptType = ScriptType.P2WPKH
            val network = BitcoinNetwork.SIGNET

            val cleanKey = params.privateKey.substringAfter(':')
            val key = DumpedPrivateKey.fromBase58(network, cleanKey).key

            val addressParser = AddressParser.getDefault()
            val toAddress = addressParser.parseAddress(params.destinationAddress)

            val sendAmount = Coin.valueOf(params.amount)

            val totalInput = Coin.valueOf(params.utxo.value)
            val fee = Coin.valueOf(params.feeAmount)

            if (totalInput.subtract(sendAmount) < fee) {
                throw IllegalArgumentException("Not enough funds to send transaction with fee")
            }

            val transaction = Transaction()
            transaction.addOutput(sendAmount, toAddress)

            val change = totalInput.subtract(sendAmount).subtract(fee)
            if (change.isPositive) {
                transaction.addOutput(change, key.toAddress(scriptType, network))
            }

            val utxo = Sha256Hash.wrap(params.utxo.txId)
            val outPoint = TransactionOutPoint(params.utxo.vOutIndex, utxo)
            val input =
                TransactionInput(transaction, byteArrayOf(), outPoint, Coin.valueOf(params.utxo.value))

            transaction.addInput(input)

            val scriptCode = ScriptBuilder.createP2PKHOutputScript(key.pubKeyHash)

            for (i in 0 until transaction.inputs.size) {
                val txIn = transaction.getInput(i.toLong())
                val signature = transaction.calculateWitnessSignature(
                    i,
                    key,
                    scriptCode,
                    Coin.valueOf(params.utxo.value),
                    Transaction.SigHash.ALL,
                    false
                )
                txIn.witness = TransactionWitness.of(listOf(signature.encodeToBitcoin(), key.pubKey))
            }

            Result.success(transaction.serialize().toHexString())

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
