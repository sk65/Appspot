package com.yefimoyevhen.appspot.ui.userdatails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yefimoyevhen.appspot.R
import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.databinding.FragmentUserDetailsBinding
import com.yefimoyevhen.appspot.model.UserDTO
import com.yefimoyevhen.appspot.ui.users.USER_ID_KEY
import com.yefimoyevhen.appspot.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private val binding: FragmentUserDetailsBinding by viewBinding()
    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.findUserById(requireArguments().getString(USER_ID_KEY)!!)
        viewModel.dataState.observe(viewLifecycleOwner) { datastate ->
            when (datastate) {
                is DataState.Error -> Toast.makeText(
                    context,
                    datastate.message ?: getString(R.string.something_goes_wrong),
                    Toast.LENGTH_SHORT
                ).show()
                is DataState.Loading -> {
                    //IGNORE
                }
                is DataState.Success -> onSuccess(datastate.data)
            }
        }
    }

    private fun onSuccess(user: UserDTO) {
        binding.apply {
            firstName.text = user.firstName
            lastName.text = user.lastName
            age.text = user.age.toString()
            gender.text = user.gender
            country.text = user.country
        }
    }
}