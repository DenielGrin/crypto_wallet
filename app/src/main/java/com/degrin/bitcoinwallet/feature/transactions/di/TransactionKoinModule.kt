package com.degrin.bitcoinwallet.feature.transactions.di


import com.degrin.bitcoinwallet.feature.transactions.domain.impl.TransactionRepositoryImpl
import com.degrin.bitcoinwallet.feature.transactions.domain.repository.TransactionRepository
import com.degrin.bitcoinwallet.feature.transactions.domain.useCase.TransactionUseCase
import com.degrin.bitcoinwallet.feature.transactions.presentation.viewModel.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule = module {

    single<TransactionRepository> {
        TransactionRepositoryImpl(
//            restApiClient = get()
        )
    }

    single {
        TransactionUseCase(
            repository = get(),
        )
    }

    viewModel {
        TransactionsViewModel(
            useCase = get()
        )
    }
}
