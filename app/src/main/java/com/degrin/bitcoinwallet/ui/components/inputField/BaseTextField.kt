package com.degrin.bitcoinwallet.ui.components.inputField

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography
import com.degrin.bitcoinwallet.ui.theme.robotoRegular

@Composable
fun InputFieldCompositionLocalProvider(
    content: @Composable () -> Unit
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.surfaceVariant,
        backgroundColor = MaterialTheme.colorScheme.surfaceContainer
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        content()
    }
}

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    isError: Boolean = false,
    errorText: String? = "",
    counterText: Boolean = false,
    maxLength: Int? = null,
    maxLines: Int = 1,
    label: @Composable (() -> Unit)? = null,
    shape: CornerBasedShape = ShapeDefaults.Medium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {

    InputFieldCompositionLocalProvider {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            maxLines = maxLines,
            label = label,
            onValueChange = {
                val text = it
                if (maxLength != null && text.length <= maxLength) {
                    onValueChange(text)
                } else if (maxLength == null) {
                    onValueChange(text)
                }
            },
            placeholder = {
                Text(
                    text = hint,
                    style = getAppThemeTypography().titleMedium.copy(
                        MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
            },
            shape = shape,
            textStyle = getAppThemeTypography().titleMedium.copy(
                MaterialTheme.colorScheme.onSecondaryContainer
            ),
            isError = isError,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            supportingText = {
                SupportingText(
                    errorText = errorText,
                    counterText = counterText,
                    value = value,
                    maxLength = maxLength
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                focusedIndicatorColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                errorCursorColor = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.5f),
                errorContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                errorSupportingTextColor = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.5f),
            ),
            keyboardActions = keyboardActions,
        )
    }
}

@Composable
fun SupportingText(
    errorText: String?,
    counterText: Boolean,
    value: String,
    maxLength: Int?
) {
    Row {
        if (!errorText.isNullOrBlank()) {
            Text(
                text = errorText,
                style = TextStyle(
                    fontFamily = robotoRegular,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 13.sp,
                    color = MaterialTheme.colorScheme.onError
                )
            )
        }
        if (counterText) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${value.count()}/${maxLength}",
                style = TextStyle(
                    fontFamily = robotoRegular,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 13.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BaseTextField_Preview() {
    AppTheme {
        BaseTextField(
            value = "text field value",
            onValueChange = {},
            hint = "text field hint"
        )
    }
}