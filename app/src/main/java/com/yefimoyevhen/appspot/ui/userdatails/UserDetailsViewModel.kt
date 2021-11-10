package com.yefimoyevhen.appspot.ui.userdatails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.database.toUserDTO
import com.yefimoyevhen.appspot.model.UserDTO
import com.yefimoyevhen.appspot.repository.AppspotRepository
import com.yefimoyevhen.appspot.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val appspotRepository: AppspotRepository
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<UserDTO>>()
    val dataState: LiveData<DataState<UserDTO>>
        get() = _dataState

    fun findUserById(userId: String) {
        viewModelScope.launch {
            appspotRepository.findUserById(userId)
                .onEach { mapDataState(it) }.launchIn(viewModelScope)
        }
    }

    private fun mapDataState(dataState: DataState<User>) {
        when (dataState) {
            is DataState.Error -> _dataState.value =
                DataState.Error(dataState.message)
            is DataState.Loading -> _dataState.value =
                DataState.Loading
            is DataState.Success -> _dataState.value =
                DataState.Success(dataState.data.toUserDTO())
        }
    }
}