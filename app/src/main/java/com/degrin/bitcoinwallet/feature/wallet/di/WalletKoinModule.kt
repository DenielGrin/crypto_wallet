package com.degrin.bitcoinwallet.feature.wallet.di


import com.degrin.bitcoinwallet.feature.wallet.domain.impl.WalletRepositoryImpl
import com.degrin.bitcoinwallet.feature.wallet.domain.repository.WalletRepository
import com.degrin.bitcoinwallet.feature.wallet.domain.useCase.WalletUseCase
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.WalletViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val walletModule = module {

    single<WalletRepository> {
        WalletRepositoryImpl(
//            restApiClient = get()
        )
    }

    single {
        WalletUseCase(
            repository = get(),
        )
    }

    viewModel {
        WalletViewModel(
            walletUseCase = get()
        )
    }
}
