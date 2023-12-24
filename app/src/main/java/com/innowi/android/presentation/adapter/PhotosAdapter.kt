package com.innowi.android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innowi.android.databinding.ItemPhotosBinding
import com.innowi.android.domain.model.photo.response.PhotoResponse

class PhotosAdapter(private val photoList: List<PhotoResponse>?) :
    RecyclerView.Adapter<PhotosAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photoList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(photoList?.get(position))
    }

    inner class MyViewHolder(private val binding: ItemPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoItem: PhotoResponse?) {
            photoItem?.apply {
                binding.tvTitle.text = title
                binding.tvUrl.text = thumbnailUrl
            }
        }
    }
}