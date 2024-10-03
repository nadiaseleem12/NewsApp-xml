package com.example.news.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.data.connectivity.NetworkHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
open class BaseViewModel @Inject constructor(val networkHandler: NetworkHandler):ViewModel() {

    private val _isConnected=MutableLiveData<Boolean>()
    val isConnected:LiveData<Boolean> get() = _isConnected

    private var wasDisconnected = false
    fun updateConnectivity(){

        _isConnected.value = networkHandler.isNetworkAvailable()

        if(_isConnected.value==false){
            wasDisconnected = true
        }
    }

    fun shouldShowBackOnline():Boolean{
        return _isConnected.value== true && wasDisconnected
    }
}