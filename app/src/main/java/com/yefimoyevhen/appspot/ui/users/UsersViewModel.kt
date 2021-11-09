package com.yefimoyevhen.appspot.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.repository.Repository
import com.yefimoyevhen.appspot.util.DataState
import com.yefimoyevhen.appspot.util.InternetChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val internetChecker: InternetChecker,
    private val repository: Repository
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<User>>>()
    val dataState: LiveData<DataState<List<User>>>
        get() = _dataState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            if (internetChecker.hasInternetConnection()) {
                repository.fetchData()
                    .onEach { dataState ->
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
            } else {
                repository.findAllUsers()
                    .onEach { dataState ->
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
            }
        }
    }
}