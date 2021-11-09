package com.yefimoyevhen.appspot.ui.userdatails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yefimoyevhen.appspot.database.model.User
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

    private val _dataState = MutableLiveData<DataState<User>>()
    val dataState: LiveData<DataState<User>>
        get() = _dataState

    fun findUserById(userId: String) {
        viewModelScope.launch {
            appspotRepository.findUserById(userId)
                .onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

}