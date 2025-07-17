# Bitcoin Wallet Android App

### Technology Stack
- ![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white&labelColor=%237F52FF)
- ![Java](https://img.shields.io/badge/JAVA-1.8-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=%23ED8B00)
- ![Gradle](https://img.shields.io/badge/Gradle-8.10.2-green.svg?style=for-the-badge&logo=Gradle&logoColor=blue)
- ![Android-Gradle](https://img.shields.io/badge/Android_Gradle_Plugin-8.5.2-grey.svg?style=for-the-badge&logo=Gradle&logoColor=white)

### Features:
- Display current wallet balance.
- Input fields for amount and destination address.
- Send bitcoins with a fixed fee of 0.000001 tBTC.
- Show transaction ID after a successful send, with a link to view it in a block explorer.
- Handle errors during transaction creation or sending.
- Refresh balance after sending.

### Tech Stack:
- **Kotlin**: Main language.
- **Jetpack Compose**: UI framework.
- **Material Design 2/3**: UI components.
- **bitcoinj library**: Bitcoin protocol library.
- **Blockstream Esplora REST API**: For UTXO fetching and transaction broadcasting.

### Useful Links:
- [Bitcoin Signet Wiki](https://bitcoincore.org/en/segwit/)
- [Blockstream Esplora API Documentation](https://github.com/Blockstream/esplora)
- [Bitcoin Transaction Decoder](https://blockchain.info/decode_tx)
