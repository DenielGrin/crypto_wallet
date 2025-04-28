package com.cordobalegal.core.ui.component.inputField

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cordobalegal.R
import com.cordobalegal.core.ui.accessibility.Accessibility
import com.cordobalegal.core.ui.component.button.view.BaseButton
import com.cordobalegal.core.ui.component.button.viewModel.ButtonImageGravity
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.cordobalegal.core.ui.theme.AppTheme
import com.cordobalegal.core.ui.theme.getAppThemeTypography
import com.cordobalegal.core.ui.theme.robotoRegular
import com.cordobalegal.core.utils.compose.colorFullClick
import com.degrin.bitcoinwallet.ui.components.inputField.getTextFieldColors

@Composable
fun BaseTextFieldWithLabel(
    modifier: Modifier = Modifier,
    value: String,
    isShowTrailingButton: Boolean = false,
    labelId: Int?,
    vararg: String? = null,
    readOnly: Boolean = true,
    isError: Boolean = false,
    errorText: String? = null,
    isShowClearingButton: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = getTextFieldColors(),
    onUpdateValueClick: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val effectiveVisualTransformation = remember(passwordVisible) {
        if (visualTransformation == PasswordVisualTransformation('•') && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(if (isError) Sizes.Paddings.dp16 else 0.dp)
    ) {
        OutlinedTextField(
            value = TextFieldValue(value, selection = TextRange(value.length)),
            onValueChange = {
                onValueChange(it.text)
            },
            readOnly = readOnly,
            label = {
                if (labelId != null) {
                    Text(
                        text = when {
                            vararg != null -> stringResource(labelId, vararg)
                            else -> stringResource(labelId)
                        },
                        style = TextStyle(
                            fontFamily = robotoRegular,
                            color = when {
                                isError -> MaterialTheme.colorScheme.onError
                                else -> MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f)
                            },
                            fontWeight = FontWeight.W400,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                    )
                }
            },
            isError = isError,
            textStyle = TextStyle(
                fontFamily = robotoRegular,
                color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            modifier = modifier.fillMaxWidth(),
            trailingIcon = {
                if (isShowTrailingButton) {
                    when {
                        visualTransformation == PasswordVisualTransformation('•') -> {
                            Icon(
                                modifier = Modifier.colorFullClick(
                                    onClick = { passwordVisible = !passwordVisible }
                                ),
                                painter = painterResource(
                                    id = if (passwordVisible) R.drawable.ic_close_eye else R.drawable.ic_eye
                                ),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }

                        isShowClearingButton -> Icon(
                            modifier = Modifier.colorFullClick(
                                onClick = { onValueChange("") }
                            ),
                            painter = painterResource(id = R.drawable.ic_clear_field),
                            tint = when {
                                isError -> MaterialTheme.colorScheme.onError
                                else -> MaterialTheme.colorScheme.onSecondaryContainer
                            },
                            contentDescription = Accessibility.CLEAR_BUTTON
                        )

                        else -> {
                            BaseButton(
                                modifier = Modifier
                                    .padding(end = Sizes.Paddings.dp12)
                                    .height(Sizes.Size.dp30)
                                    .width(Sizes.Size.dp70),
                                buttonTextId = R.string.default_update_title,
                                buttonImageGravity = ButtonImageGravity.IMAGE_IS_EMPTY,
                                textColor = MaterialTheme.colorScheme.primaryContainer,
                                containerColor = Color.Transparent,
                                radius = Sizes.CornerShape.dp10,
                                border = BorderStroke(
                                    width = Sizes.BorderSizes.dp1,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ),
                                contentPadding = PaddingValues(0.dp),
                                onClick = onUpdateValueClick
                            )
                        }
                    }
                }
            },
            visualTransformation = effectiveVisualTransformation,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(Sizes.CornerShape.dp16),
            colors = colors,
        )
        if (isError) {
            Text(
                modifier = Modifier.padding(start = Sizes.Paddings.dp8),
                text = errorText.orEmpty(),
                style = getAppThemeTypography().displayMedium.copy(
                    color = MaterialTheme.colorScheme.onError
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileTextField_Preview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            BaseTextFieldWithLabel(
                value = "Some value",
                labelId = R.string.profile_email,
                isShowTrailingButton = true
            )
            BaseTextFieldWithLabel(
                value = "Some value",
                labelId = R.string.profile_full_name,
                isShowTrailingButton = false
            )
            BaseTextFieldWithLabel(
                value = "Some value",
                labelId = R.string.profile_password_title,
                isShowTrailingButton = true,
                visualTransformation = PasswordVisualTransformation('•')
            )

            BaseTextFieldWithLabel(
                value = "Some value",
                labelId = R.string.profile_bank_name,
                isShowTrailingButton = true,
                isShowClearingButton = true
            )
            BaseTextFieldWithLabel(
                value = "Some value",
                labelId = R.string.profile_bank_name,
                isShowTrailingButton = true,
                isShowClearingButton = true,
                isError = true
            )
        }
    }
}