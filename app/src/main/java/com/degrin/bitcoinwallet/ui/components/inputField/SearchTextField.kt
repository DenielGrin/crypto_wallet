package com.cordobalegal.core.ui.component.inputField

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import com.cordobalegal.R
import com.cordobalegal.core.ui.accessibility.Accessibility
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.components.inputField.InputFieldCompositionLocalProvider
import com.degrin.bitcoinwallet.ui.components.inputField.PlaceholderText
import com.degrin.bitcoinwallet.ui.components.inputField.editTextStyle
import com.degrin.bitcoinwallet.ui.components.inputField.getInputFieldColors

@Composable
fun SearchTextField(
    modifier: Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    InputFieldCompositionLocalProvider {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            maxLines = 1,
            singleLine = true,
            onValueChange = onValueChange,
            placeholder = { PlaceholderText(hint) },
            shape = RoundedCornerShape(Sizes.CornerShape.dp20),
            textStyle = editTextStyle(),
            trailingIcon = {
                when {
                    value.isNotEmpty() -> IconButton(onClick = { onValueChange("") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clear_field),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = Accessibility.CLEAR_BUTTON
                        )
                    }

                    else -> Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        contentDescription = Accessibility.CLEAR_BUTTON
                    )
                }
            },
            colors = getInputFieldColors(),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                },
                onNext = {
                    keyboardController?.hide()
                }
            ),
        )
    }
}
