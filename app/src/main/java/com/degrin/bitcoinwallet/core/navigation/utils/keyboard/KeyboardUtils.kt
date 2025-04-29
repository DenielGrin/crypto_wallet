package com.degrin.bitcoinwallet.core.navigation.utils.keyboard

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Modifier.clearFocusOnClick(): Modifier {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    return this.pointerInteropFilter { event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            focusManager.hideKeyboard(context)
        }
        false
    }
}

fun FocusManager.hideKeyboard(context: Context){
    clearFocus()
    KeyboardUtils.hideKeyboard(View(context))
}

object KeyboardUtils {

    /**
     * Hides the soft keyboard for the given view.
     */
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Clears focus from the given view and hides the keyboard.
     */
    fun clearFocus(view: View) {
        view.clearFocus()
        hideKeyboard(view)
    }
}
