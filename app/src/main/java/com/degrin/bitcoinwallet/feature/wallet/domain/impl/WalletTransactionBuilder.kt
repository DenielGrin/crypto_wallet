package com.degrin.bitcoinwallet.feature.wallet.domain.impl

import android.util.Log
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionParams
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
            val fee = Coin.valueOf(params.feeAmount)
            val utxoValue = Coin.valueOf(params.utxo.value)

            Log.d(
                TAG,
                "buildTransaction: Building transaction. UTXO: txId=${params.utxo.txId}, vOutIndex=${params.utxo.vOutIndex}, value=${params.utxo.value}, sendAmount=$sendAmount, fee=$fee, toAddress=${params.destinationAddress}"
            )

            if (utxoValue.subtract(sendAmount) < fee) {
                throw IllegalArgumentException("buildTransaction: Not enough funds to send transaction with fee")
            }

            val transaction = Transaction()
            transaction.addOutput(sendAmount, toAddress)
            Log.d(TAG, "buildTransaction: Output added: $sendAmount to $toAddress")

            val change = utxoValue.subtract(sendAmount).subtract(fee)
            if (change.isPositive) {
                val changeAddress = key.toAddress(scriptType, network)
                transaction.addOutput(change, changeAddress)
                Log.d(TAG, "buildTransaction: Change output added: $change to $changeAddress")
            }

            val utxoHash = Sha256Hash.wrap(params.utxo.txId)
            val outPoint = TransactionOutPoint(params.utxo.vOutIndex, utxoHash)
            val input = TransactionInput(
                /* parentTransaction = */ transaction,
                /* scriptBytes = */ byteArrayOf(),
                /* outpoint = */ outPoint,
                /* value = */ utxoValue
            )
            transaction.addInput(input)

            val scriptCode = ScriptBuilder.createP2PKHOutputScript(key.pubKeyHash)
            Log.d(TAG, "buildTransaction: Script code created: $scriptCode")

            for (i in 0 until transaction.inputs.size) {
                val txIn = transaction.getInput(i.toLong())
                val signature = transaction.calculateWitnessSignature(
                    /* inputIndex = */ i,
                    /* key = */ key,
                    /* scriptCode = */ scriptCode,
                    /* value = */ utxoValue,
                    /* hashType = */ Transaction.SigHash.ALL,
                    /* anyoneCanPay = */ false
                )
                txIn.witness = TransactionWitness.of(
                    listOf(
                        signature.encodeToBitcoin(),
                        key.pubKey
                    )
                )
                Log.d(TAG, "buildTransaction: Signature and witness added for input $i")
            }

            Result.success(transaction.serialize().toHexString())

        } catch (exception: IllegalArgumentException) {
            Log.e(TAG, "buildTransaction: ${exception.message}")
            Result.failure(exception)
        } catch (exception: Exception) {
            Log.e(TAG, "buildTransaction: Error building transaction: ${exception.message}")
            Result.failure(exception)
        }
    }

    companion object {
        private const val TAG = "TransactionBuilder"
    }
}
