package com.example.news.util

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.news.R
import com.google.gson.Gson

fun Fragment.showAlertDialog(
    message: String,
    posActionName: String? = null,
    posAction: DialogInterface.OnClickListener? = null,
    negActionName: String? = null,
    negAction: DialogInterface.OnClickListener? = null,
    isCancelable: Boolean = true
): AlertDialog {

    val alertDialogBuilder = AlertDialog.Builder(requireContext())
    alertDialogBuilder.setMessage(message)
    alertDialogBuilder.setPositiveButton(posActionName, posAction)
    alertDialogBuilder.setNegativeButton(negActionName, negAction)
    alertDialogBuilder.setCancelable(isCancelable)
    return alertDialogBuilder.show()
}

fun Fragment.recreateActivity() {
    activity?.let {
        val intent = Intent(it, it::class.java)
        it.startActivity(intent)
        it.finish()
    }
}

fun View.hideKeyboard(context: Context) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}


fun <T> String.fromJson(className: Class<T>): T {
    return Gson().fromJson(this, className)

}

fun Fragment.handleError(
    message: String? = null,
    onTryAgainClick: () -> Unit
) {
    showAlertDialog(message
        ?: getString(R.string.something_went_wrong),
        posActionName = getString(R.string.try_again),
        posAction = { dialog, _ ->
            dialog.dismiss()
            onTryAgainClick()
        },
        negActionName = getString(R.string.cancel),
        negAction = { dialog, _ ->
            dialog.dismiss()
        })
}

