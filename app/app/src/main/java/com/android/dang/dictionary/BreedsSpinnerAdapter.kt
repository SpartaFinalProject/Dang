package com.android.dang.dictionary

import com.android.dang.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class BreedsSpinnerAdapter(context: Context, private val data: ArrayList<BreedsSpinnerData>) :
    ArrayAdapter<BreedsSpinnerData>(context, android.R.layout.simple_spinner_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_dictionary_spinner,
            parent,
            false
        )
        val item = data[position]
        val nameTextView = view.findViewById<TextView>(R.id.tv_dictionary_spinner)
        nameTextView.text = item.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_dictionary_spinner,
            parent,
            false
        )
        val item = data[position]
        val nameTextView = view.findViewById<TextView>(R.id.tv_dictionary_spinner)
        nameTextView.text = item.name
        return view
    }
}