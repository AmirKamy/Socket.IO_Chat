package com.example.socketiochat

import android.os.Bundle
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.socketiochat.common.EventObserver
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

        viewModel.loginEvent.observe(viewLifecycleOwner, EventObserver{


            if (it is Resource.Loading)
                // loading view

            if (it is Resource.Success) {
                sessionManager.saveAuthToken(it.value.access_token)
                sessionManager.saveUserProfile(User(it.value.uid, it.value.username))

                startMainActivity()

            } else if (it is Resource.Failure) {
               // setProgressIndicator(false)
                when {
                    it.isNetworkError -> {} // connectionErrorAlertDialogMessage()
                    it.errorCode == 500 ->{} // error500AlertDialogMessage()
                    it.errorCode == 401 -> {} // cantFindAccountDialog()
                    else -> {
                       // showMessageErrorFromServer(it.errorBody)
                    }
                }
            }
        })

    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        requireContext().startActivity(intent)
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}