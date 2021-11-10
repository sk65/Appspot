package com.yefimoyevhen.appspot.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.database.toUserDTOList
import com.yefimoyevhen.appspot.model.UserDTO
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

    private val _dataState = MutableLiveData<DataState<List<UserDTO>>>()
    val dataState: LiveData<DataState<List<UserDTO>>>
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
                        mapDataState(dataState)
                    }.launchIn(viewModelScope)
            } else {
                appspotRepository.findAllUsers()
                    .onEach { dataState ->
                        setIsListEmpty(dataState)
                        mapDataState(dataState)
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun mapDataState(dataState: DataState<List<User>>) {
        when (dataState) {
            is DataState.Error -> _dataState.value =
                DataState.Error(dataState.message)
            is DataState.Loading -> _dataState.value =
                DataState.Loading
            is DataState.Success -> _dataState.value =
                DataState.Success(dataState.data.toUserDTOList())
        }
    }

    private fun setIsListEmpty(dataState: DataState<List<User>>) {
        _isListEmpty.value = dataState is DataState.Success && dataState.data.isEmpty()
    }
}