package com.yefimoyevhen.appspot.ui.users

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yefimoyevhen.appspot.R
import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.databinding.FragmentUsersBinding
import com.yefimoyevhen.appspot.util.DataState
import dagger.hilt.android.AndroidEntryPoint

const val USER_ID_KEY = "USER_ID_KEY"

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private var _adapter: UsersAdapter? = null
    private val adapter
        get() = _adapter!!

    private val viewModel: UsersViewModel by viewModels()
    private val binding: FragmentUsersBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.dataState.observe(viewLifecycleOwner) { datastate ->
            when (datastate) {
                is DataState.Error -> onError(
                    datastate.message ?: getString(R.string.something_goes_wrong)
                )
                is DataState.Loading -> showProgressBar()
                is DataState.Success -> onSuccess(datastate.data)
            }
        }
    }

    private fun onSuccess(users: List<User>) {
        hideProgressBar()
        adapter.submitList(users)
    }

    private fun onError(message: String) {
        hideProgressBar()
        Log.i("dev", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView() {
        _adapter = UsersAdapter().apply {
            onItemClickListener = { openUserDetails(it) }
        }
        binding.userList.adapter = adapter
    }

    private fun openUserDetails(userId: String) {
        val bundle = Bundle().apply {
            putString(USER_ID_KEY, userId)
        }
        findNavController().navigate(
            R.id.action_userListFragment_to_userDetailsFragment,
            bundle
        )
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        _adapter = null
        super.onDestroyView()
    }
}