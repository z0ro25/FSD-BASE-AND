package com.ezt.ai.story.maker.features.onboarding.adapters

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.truongnt.fsd.fsd_base_and.databinding.VhdOnbBinding
import com.truongnt.fsd.fsd_base_and.databinding.ViewholderNativeFullBinding

class OnbAdapter(val context: AppCompatActivity, val items: ArrayList<Triple<Int, Int, Int>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == 0) NativeViewHolder(
            ViewholderNativeFullBinding.inflate(
                context.layoutInflater,
                parent,
                false
            )
        )
        else OnbViewHolder(VhdOnbBinding.inflate(context.layoutInflater, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].third == 0) 0 else 1
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is NativeViewHolder) {
            (holder as NativeViewHolder).binding.apply {

            }
        } else {
            val image = items[position]
            val viewHolder = holder as OnbViewHolder
            viewHolder.binding.apply {
                ivOnb.setImageResource(image.third)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class OnbViewHolder(val binding: VhdOnbBinding) : RecyclerView.ViewHolder(binding.root)
    class NativeViewHolder(val binding: ViewholderNativeFullBinding) :
        RecyclerView.ViewHolder(binding.root)
}