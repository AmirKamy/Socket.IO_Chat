package com.example.socketiochat

import android.os.Bundle
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.socketiochat.common.EventObserver
import com.example.socketiochat.common.alertDialogMessage
import com.example.socketiochat.common.showMessageErrorFromServer
import com.example.socketiochat.databinding.FragmentMainAuthBinding
import com.example.socketiochat.model.User
import com.example.socketiochat.network.SessionManager
import com.example.socketiochat.viewmodel.UserViewModel
import com.ischeck.network.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAuthFragment : Fragment() {

    private var _binding: FragmentMainAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())


        binding.textInputUsername.editText?.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.textInputUsername.error = null
            else binding.textInputUsername.error = getString(R.string.required)
        }
        binding.textInputPassword.editText?.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.length >= 8) binding.textInputPassword.error = null
            else if (text != null && text.isEmpty()) binding.textInputPassword.error = getString(R.string.required)
            else if (text != null && text.length < 8) binding.textInputPassword.error = "you have to enter at least 8 character!"
        }

        binding.buttonLogin.setOnClickListener {
            validateDataAndLogin()
        }

        viewModel.loginEvent.observe(viewLifecycleOwner, EventObserver{

            Log.i("gggg", it.toString())

            if (it is Resource.Loading){
                binding.buttonLogin.startAnimation()
            }

            if (it is Resource.Success) {
                sessionManager.saveAuthToken(it.value.token)
                sessionManager.saveUserProfile(it.value.user)
                binding.buttonLogin.stopAnimation()
                startMainActivity()

            } else if (it is Resource.Failure) {
                binding.buttonLogin.stopAnimation()
                when {
                    it.isNetworkError -> alertDialogMessage("Connection Error", "Please check your internet connection", "Ok")
                    it.errorCode == 500 -> alertDialogMessage("Server Error", "we have problem in our server. please try again later", "Ok")
                    it.errorCode == 422 -> alertDialogMessage("Username", "The username has already been taken", "Ok")
                    else -> {
                       showMessageErrorFromServer(it.errorBody)
                    }
                }
            }
        })

    }

    private fun validateDataAndLogin() {
        val username = binding.textInputUsername.editText?.text.toString().trim()
        val password = binding.textInputPassword.editText?.text.toString().trim()

        if (username.isEmpty()) {
            binding.textInputUsername.error = getString(R.string.required)
            binding.textInputUsername.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.textInputPassword.error = getString(R.string.required)
            binding.textInputPassword.requestFocus()
            return
        }

        if (password.length < 8) {
            binding.textInputPassword.error = "you have to enter at least 8 character!"
            binding.textInputPassword.requestFocus()
            return
        }

        login(username,password)

    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        requireContext().startActivity(intent)
        activity?.finish()
    }

    private fun login(username: String, password: String) {
        viewModel.login(username, password)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}