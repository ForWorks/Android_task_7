package com.example.myapplication.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.Field
import com.example.myapplication.Constants.TYPE_NUMERIC
import com.example.myapplication.Constants.TYPE_TEXT
import com.example.myapplication.databinding.ListItemBinding
import com.example.myapplication.databinding.NumberItemBinding
import com.example.myapplication.databinding.TextItemBinding

class RecyclerAdapter( private val fields: List<Field>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val response = mutableMapOf<String, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            0 -> TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.text_item, parent, false))
            1 -> NumberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.number_item, parent, false))
            else -> ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (fields[position].type) {
            TYPE_TEXT -> (holder as TextViewHolder).bind(fields[position])
            TYPE_NUMERIC -> (holder as NumberViewHolder).bind(fields[position])
            else -> (holder as ListViewHolder).bind(fields[position])
        }
    }

    override fun getItemCount(): Int {
        return fields.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (fields[position].type){
            TYPE_TEXT -> 0
            TYPE_NUMERIC -> 1
            else -> 2
        }
    }

    private inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TextItemBinding.bind(view)
        fun bind(field : Field) {
            binding.textTextView.text = field.title
            response[field.name.toString()] = ""
            binding.textEditText.addTextChangedListener {
                response[field.name.toString()] = binding.textEditText.text.toString()
            }
        }
    }

    private inner class NumberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = NumberItemBinding.bind(view)
        fun bind(field: Field) {
            binding.numberTextView.text = field.title
            response[field.name.toString()] = ""
            binding.numberEditText.addTextChangedListener {
                response[field.name.toString()] = binding.numberEditText.text.toString()
            }
        }
    }

    private inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        fun bind(field: Field) {
            binding.listTextView.text = field.title
            field.values?.forEach {
                val button = RadioButton(binding.root.context)
                button.text = it.value
                binding.listGroup.addView(button)

            }
            binding.listGroup.check(binding.listGroup.getChildAt(0).id)
            binding.listGroup.setOnCheckedChangeListener { radioGroup, i ->
                response[field.name.toString()] = radioGroup.findViewById<RadioButton>(i).text.toString()
            }
////////////////////////////////////////////////////////////////
        }
    }
}
