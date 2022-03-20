package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Field
import com.example.myapplication.databinding.ListItemBinding
import com.example.myapplication.databinding.NumberItemBinding
import com.example.myapplication.databinding.TextItemBinding

class RecyclerAdapter( private val fields: List<Field>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val response = mutableMapOf<String, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            TEXT -> TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.text_item, parent, false))
            NUMERIC -> NumberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.number_item, parent, false))
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
            TYPE_TEXT -> TEXT
            TYPE_NUMERIC -> NUMERIC
            else -> LIST
        }
    }

    private inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TextItemBinding.bind(view)
        fun bind(field : Field) {
            binding.textTextView.text = field.title
            binding.textEditText.addTextChangedListener {
                response[field.name.toString()] = binding.textEditText.text.toString()
            }
        }
    }

    private inner class NumberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = NumberItemBinding.bind(view)
        fun bind(field: Field) {
            binding.numberTextView.text = field.title
            binding.numberEditText.addTextChangedListener {
                response[field.name.toString()] = binding.numberEditText.text.toString()
            }
        }
    }

    private inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        fun bind(field: Field) {
            binding.listTextView.text = field.title
            var hasNone = false
            field.values?.forEach {
                if (it.key != NONE) {
                    val button = RadioButton(binding.root.context)
                    button.text = it.value
                    button.hint = it.key
                    binding.listGroup.addView(button)
                } else {
                    hasNone = true
                    response[field.name.toString()] = it.key
                }
            }
            if(!hasNone)
                binding.listGroup.check(binding.listGroup.getChildAt(0).id)
            binding.listGroup.setOnCheckedChangeListener { radioGroup, i ->
                response[field.name.toString()] = radioGroup.findViewById<RadioButton>(i).hint.toString()
            }
        }
    }

    companion object {
        const val TYPE_TEXT = "TEXT"
        const val TYPE_NUMERIC = "NUMERIC"
        const val TEXT = 0
        const val NUMERIC = 1
        const val LIST = 2
        const val NONE = "none"
    }
}
