package com.example.socketiochat.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import com.example.socketiochat.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject


//fun View.snackbar(message: String, action: (() -> Unit)? = null) {
//    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG).setAction("ok") {}
//        .setActionTextColor(ContextCompat.getColor(context, R.color.red))
//    action?.let {
//        snackbar.setAction("Retry") {
//            it()
//        }
//    }
//    val snackBarView = snackbar.view
//    snackBarView.setTranslationY(-(160f))
//    snackbar.show()
//}

//fun Fragment.error500AlertDialogMessage() {
//
//    val alertView = LayoutInflater.from(requireContext()).inflate(R.layout.error500alert_dialog, null)
//    val alert = AlertDialog.Builder(requireContext())
//    alert.setView(alertView)
//    val alertDialog = alert.create()
//    alertDialog.window?.setBackgroundDrawable(
//        ContextCompat.getDrawable(
//            requireContext(),
//            R.drawable.round_corner
//        )
//    )
//    alertDialog.show()
//    val pos = alertView.findViewById<MaterialButton>(R.id.button_positive)
//    val neg = alertView.findViewById<MaterialButton>(R.id.button_negative)
//
//    pos.setOnClickListener { alertDialog.dismiss() }
//    neg.setOnClickListener {
//        alertDialog.dismiss()
//        findNavController().navigate(R.id.supportFragment)
//    }
//
//    val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//    alertDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//
//}
//
//fun Fragment.connectionErrorAlertDialogMessage() {
//
//    val alertView = LayoutInflater.from(requireContext()).inflate(R.layout.connection_error_alert_dialog, null)
//    val alert = AlertDialog.Builder(requireContext())
//    alert.setView(alertView)
//    val alertDialog = alert.create()
//    alertDialog.window?.setBackgroundDrawable(
//        ContextCompat.getDrawable(
//            requireContext(),
//            R.drawable.round_corner
//        )
//    )
//    alertDialog.show()
//    val pos = alertView.findViewById<MaterialButton>(R.id.button_positive)
//
//    pos.setOnClickListener { alertDialog.dismiss() }
//
//
//    val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//    alertDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//}
//
//fun Fragment.alertDialogMessage(
//    title: String,
//    message: String,
//    posButtonText: String,
//    negButtonText: String?,
//    action: NavDirections? = null,
//    backPressed: Boolean? = null,
//) {
//
//    val alertView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_message, null)
//    val alert = AlertDialog.Builder(requireContext())
//    alert.setView(alertView)
//    val alertDialog = alert.create()
//    alertDialog.window?.setBackgroundDrawable(
//        ContextCompat.getDrawable(
//            requireContext(),
//            R.drawable.round_corner
//        )
//    )
//    alertDialog.show()
//    alertView.findViewById<TextView>(R.id.txt_title).text = title
//    alertView.findViewById<TextView>(R.id.txt_body).text = message
//    val pos = alertView.findViewById<MaterialButton>(R.id.button_positive)
//    pos.text = posButtonText
//    val neg = alertView.findViewById<MaterialButton>(R.id.button_negative)
//    neg.text = negButtonText
//
//    if (negButtonText == null)
//        neg.visibility = View.GONE
//
//    neg.setOnClickListener {
//        alertDialog.dismiss()
//    }
//    pos.setOnClickListener {
//        alertDialog.dismiss()
//        if (action != null){
//            findNavController().navigate(action)
//
//        }else if (backPressed == true){
////            val queue = findNavController().backQueue
////            if (findNavController().previousBackStackEntry?.destination?.id == R.id.openPositionsFragment){
////                findNavController().popBackStack(queue[1].destination.id,false)
////            }else{
////                findNavController().popBackStack(queue[2].destination.id,false)
////            }
//            findNavController().popBackStack()
//        }else if(backPressed == false){
////            val queue = findNavController().backQueue
////            findNavController().popBackStack(queue[1].destination.id,false)
//            findNavController().popBackStack()
//        }
//
//
//
//
//    }
//
//    val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//    alertDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//
//}
//
//fun Fragment.logOutMessage() {
//    val alertView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_message, null)
//    val alert = AlertDialog.Builder(requireContext())
//    alert.setView(alertView)
//    val alertDialog = alert.create()
//    alertDialog.window?.setBackgroundDrawable(
//        ContextCompat.getDrawable(
//            requireContext(), R.drawable.round_corner
//        )
//    )
//    alertDialog.show()
//    alertView.findViewById<TextView>(R.id.txt_title).text = "Authenticate required"
//    alertView.findViewById<TextView>(R.id.txt_body).text =
//        "your session has expired! please log in to continue"
//    val pos = alertView.findViewById<MaterialButton>(R.id.button_positive)
//    pos.text = "Log in"
//    val neg = alertView.findViewById<MaterialButton>(R.id.button_negative)
//
//    neg.visibility = View.GONE
//
//    pos.setOnClickListener {
//        (activity as MainActivity).performLogout()
//    }
//
//    alertDialog.setOnCancelListener {
//        (activity as MainActivity).performLogout()
//    }
//
//    val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//    alertDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//}
//
//fun Fragment.cantFindAccountDialog() {
//    val alertView = LayoutInflater.from(requireContext()).inflate(R.layout.incorrect_password_alert_dialog, null)
//    val alert = AlertDialog.Builder(requireContext())
//    alert.setView(alertView)
//    val alertDialog = alert.create()
//    alertDialog.window?.setBackgroundDrawable(
//        ContextCompat.getDrawable(
//            requireContext(),
//            R.drawable.round_corner
//        )
//    )
//    alertDialog.show()
//    val pos = alertView.findViewById<MaterialButton>(R.id.button_positive)
//    val neg = alertView.findViewById<MaterialButton>(R.id.button_negative)
//
//    pos.setOnClickListener { alertDialog.dismiss() }
//    neg.setOnClickListener {
//        alertDialog.dismiss()
//        findNavController().navigate(R.id.registerFragment1)
//    }
//
//    val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//    alertDialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//}
//
//// just call this fun in main activity fragments
//fun Fragment.logout() = lifecycleScope.launch {
//    (activity as MainActivity).performLogout()
//}
//
//
//fun Fragment.handleApiError(
//    failure: Resource.Failure, retry: (() -> Unit)? = null
//) {
//    when {
//        failure.isNetworkError -> requireView().snackbar(
//            "Please check your internet connection", retry
//        )
//        failure.errorCode == 401 -> {
//
//            if (this is LoginFragment) {
//                requireView().snackbar("You've entered incorrect email or password")
//            } else if (this is VerifyEmailFragment) {
//                requireView().snackbar("You've entered incorrect code")
//            } else if (this !is RegisterFragment) {
//                logout()
//            }
//        }
//        failure.errorCode == 404 -> {
//
//        }
//        failure.errorCode == 500 -> {
//
//        }
//        else -> {
//            val error = failure.errorBody?.string().toString()
//            requireView().snackbar(error)
//        }
//    }
//}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

//fun Fragment.showMessageErrorFromServer(errorBody: ResponseBody?){
//    if (errorBody != null){
//        val json = JSONObject(errorBody.string())
//        val status = json.getString("message")
//        alertDialogMessage("Error!", status, "Ok", null, null)
//    }
//}