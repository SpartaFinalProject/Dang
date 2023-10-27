package com.android.dang.dictionary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.android.dang.databinding.ItemDictionarySpinnerBinding
import com.android.dang.databinding.ItemDictionarySpinnerDropBinding
import com.android.dang.dictionary.data.BreedsSpinnerData


class BreedsSpinnerAdapter(context: Context, private val data: ArrayList<BreedsSpinnerData>):
    ArrayAdapter<BreedsSpinnerData>(context, 0, data) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemDictionarySpinnerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val item = data[position]
        val nameTextView = binding.tvDictionarySpinner
        nameTextView.text = item.name
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemDictionarySpinnerDropBinding.inflate(LayoutInflater.from(context),parent,false)
        val item = data[position]
        val nameTextView = binding.tvDictionarySpinner
        nameTextView.text = item.name
        return binding.root
    }
}