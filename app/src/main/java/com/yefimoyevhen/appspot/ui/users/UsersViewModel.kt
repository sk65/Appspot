package com.yefimoyevhen.appspot.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.repository.AppspotRepository
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
    private val appspotRepository: AppspotRepository
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<User>>>()
    val dataState: LiveData<DataState<List<User>>>
        get() = _dataState

    private val _isListEmpty = MutableLiveData(false)
    val isListEmpty: LiveData<Boolean>
        get() = _isListEmpty

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            if (internetChecker.hasInternetConnection()) {
                appspotRepository.fetchData()
                    .onEach { dataState ->
                        setIsListEmpty(dataState)
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
            } else {
                appspotRepository.findAllUsers()
                    .onEach { dataState ->
                        setIsListEmpty(dataState)
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun setIsListEmpty(dataState: DataState<List<User>>) {
        _isListEmpty.value = dataState is DataState.Success && dataState.data.isEmpty()
    }
}