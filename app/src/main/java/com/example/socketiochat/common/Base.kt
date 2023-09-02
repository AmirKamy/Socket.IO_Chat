package com.example.socketiochat.common

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.ischeck.network.SafeApiCall

abstract class BaseViewModel(): ViewModel(){

}

interface BaseRepository: SafeApiCall