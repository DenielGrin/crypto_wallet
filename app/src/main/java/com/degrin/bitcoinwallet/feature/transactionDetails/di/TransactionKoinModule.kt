package com.degrin.bitcoinwallet.feature.transactionDetails.di


import com.degrin.bitcoinwallet.feature.transactionDetails.domain.impl.TransactionDetailsRepositoryImpl
import com.degrin.bitcoinwallet.feature.transactionDetails.domain.repository.TransactionDetailsRepository
import com.degrin.bitcoinwallet.feature.transactionDetails.domain.useCase.TransactionDetailsUseCase
import com.degrin.bitcoinwallet.feature.transactionDetails.presentation.viewModel.TransactionDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionDetailsModule = module {

    single<TransactionDetailsRepository> {
        TransactionDetailsRepositoryImpl(
//            restApiClient = get()
        )
    }

    single {
        TransactionDetailsUseCase(
            repository = get(),
        )
    }

    viewModel {
        TransactionDetailsViewModel(
            useCase = get()
        )
    }
}
