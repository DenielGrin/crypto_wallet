# Bitcoin Wallet Android App

### Technology Stack
- ![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white&labelColor=%237F52FF)
- ![Java](https://img.shields.io/badge/JAVA-1.8-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=%23ED8B00)
- ![Gradle](https://img.shields.io/badge/Gradle-8.8-green.svg?style=for-the-badge&logo=Gradle&logoColor=blue)
- ![Android-Gradle](https://img.shields.io/badge/Android_Gradle_Plugin-8.5.2-grey.svg?style=for-the-badge&logo=Gradle&logoColor=white)

This minimalist Android app is written in Kotlin and designed for the Bitcoin Signet network. It allows users to send bitcoins to a specified address with basic wallet functionalities.

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

### Requirements:
- **Android Studio**: Giraffe or newer.
- **Min SDK**: 24.
- **Internet Connection**: Required for fetching UTXOs and broadcasting transactions.

### Installation:
1. Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2. Open the project in Android Studio.
3. Run the app on an emulator or physical device.

### Usage:
1. Fund the wallet address using the **Signet Faucet**.
2. Enter the amount (BTC) and the recipient address.
3. Tap **Send**.
4. After sending:
   - View the transaction ID.
   - Tap on the transaction ID to open it in **mempool.space**.
   - Tap **Send more** to return to the main screen.

### Notes:
- The app checks if the balance is sufficient, including the mining fee, before sending.
- Only basic bitcoinj functions are used (no PeerGroup or SPV synchronization).
- UTXO fetching and transaction broadcasting are handled via the **Esplora API**.

### Bonus Features (Optional):
- Dynamic fee calculation based on transaction size (bytes).
- Display transaction history with status, ID, timestamp, and amount.

### Useful Links:
- [Bitcoin Signet Wiki](https://bitcoincore.org/en/segwit/)
- [Blockstream Esplora API Documentation](https://github.com/Blockstream/esplora)
- [Bitcoin Transaction Decoder](https://blockchain.info/decode_tx)
