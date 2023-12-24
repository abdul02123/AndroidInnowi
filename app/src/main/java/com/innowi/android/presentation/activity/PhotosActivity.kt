package com.innowi.android.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.innowi.android.R
import com.innowi.android.base.BaseCompatVBActivity
import com.innowi.android.databinding.ActivityMainBinding
import com.innowi.android.domain.model.photo.response.PhotoResponse
import com.innowi.android.presentation.adapter.SectionAdapter
import com.innowi.android.presentation.viewmodel.PhotoViewModel
import com.innowi.android.util.makeGone
import com.innowi.android.util.makeVisible
import com.innowi.android.util.network.ErrorResponse
import com.innowi.android.util.network.NetworkResult
import com.innowi.android.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosActivity : BaseCompatVBActivity<ActivityMainBinding>() {

    private val viewModel: PhotoViewModel by viewModels()

    override fun setUpViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun init() {
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.result.observe(this) {
            mBinding?.progressBar?.makeGone()
            when (it) {
                is NetworkResult.Success -> {
                    setAdapter(it.result as ArrayList<PhotoResponse>)
                }
                is NetworkResult.Error -> {
                    val error = it.errorResponse as ErrorResponse
                    showToast(error.message)
                }
                is NetworkResult.Loading -> {
                mBinding?.progressBar?.makeVisible()
            }
            }
        }

        viewModel.noInternetConnection.observe(this){
            val data = it as Boolean
            if (data){
                viewModel.messageStatus(false)
                showToast(getString(R.string.network_error_message_internet))
            }
        }
    }

    private fun setAdapter(photoArray: ArrayList<PhotoResponse>?) {
        if (photoArray?.isNotEmpty() == true) {
            val groupByAlbum = photoArray.groupBy { it.albumId }
            val sectionAdapter = SectionAdapter(groupByAlbum)
            mBinding?.recyclerView?.adapter = sectionAdapter
        }

    }
}