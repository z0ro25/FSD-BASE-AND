package com.truongnt.fsd.fsd_base_and.features.lang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.truongnt.fsd.fsd_base_and.R
import com.truongnt.fsd.fsd_base_and.models.LanguageModelStart

class LanguageStartAdapter(
    private val languageModelList: MutableList<LanguageModelStart>?,
    private val context: Context,
    private val iClickItemLanguage: IClickItemLanguage
) : RecyclerView.Adapter<LanguageStartAdapter.LangugeViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LangugeViewHolder {
        val view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_language_start, parent, false)
        return LangugeViewHolder(view)
    }

    override fun onBindViewHolder(holder: LangugeViewHolder, position: Int) {
        val languageModel = languageModelList!!.get(position)
        if (languageModel == null) {
            return
        }
        holder.tvLang.setText(languageModel.languageName)
        if (languageModel.active == true) {
//            holder.rdbCheck.setImageResource(R.drawable.ic_lang_select)
            //            holder.layoutItem.setBackgroundResource(R.drawable.bg_c12_grad);
//            holder.tvLang.setTextColor(context.getColor(R.color.white));
        } else {
//            holder.rdbCheck.setImageResource(R.drawable.ic_lang_unselect)
            //            holder.layoutItem.setBackgroundResource(R.drawable.bg_c12_141b31);
//            holder.tvLang.setTextColor(context.getColor(R.color.color_27272A));
        }
        if (languageModel.code == "en") {
            Glide.with(context).asBitmap().load(R.drawable.ic_en).into(holder.icLang)
        } else if (languageModel.code == "hi") {
            Glide.with(context).asBitmap().load(R.drawable.ic_hi).into(holder.icLang)
        } else if (languageModel.code == "es") {
            Glide.with(context).asBitmap().load(R.drawable.ic_span).into(holder.icLang)
        } else if (languageModel.code == "fr") {
            Glide.with(context).asBitmap().load(R.drawable.ic_fr).into(holder.icLang)
        } else if (languageModel.code == "de") {
            Glide.with(context).asBitmap().load(R.drawable.ic_ger).into(holder.icLang)
        } else if (languageModel.code == "ko") {
            Glide.with(context).asBitmap().load(R.drawable.ic_korea).into(holder.icLang)
        } else if (languageModel.code == "pt") {
            Glide.with(context).asBitmap().load(R.drawable.ic_port).into(holder.icLang)
        } else if (languageModel.code == "ja") {
            Glide.with(context).asBitmap().load(R.drawable.ic_jp).into(holder.icLang)
        } else if (languageModel.code == "vi") {
            Glide.with(context).asBitmap().load(R.drawable.ic_vi).into(holder.icLang)
        }

        holder.layoutItem.setOnClickListener(View.OnClickListener { v: View? ->
            iClickItemLanguage.onClickItemLanguage(languageModel.code)
            setCheck(languageModel.code)
        })
    }

    override fun getItemCount(): Int {
        if (languageModelList != null) {
            return languageModelList.size
        } else {
            return 0
        }
    }

    class LangugeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rdbCheck: ImageView
        val tvLang: TextView
        val layoutItem: LinearLayout
        val icLang: ImageView
        val divider: View? = null

        init {
            icLang = itemView.findViewById<ImageView>(R.id.ic_lang)
            tvLang = itemView.findViewById<TextView>(R.id.txtName)
            layoutItem = itemView.findViewById<LinearLayout>(R.id.layoutItemStart)
            rdbCheck = itemView.findViewById<ImageView>(R.id.imgCheck)
        }
    }

    fun setCheck(code: String?) {
        for (item in languageModelList!!) {
            if (item.code == code) {
                item.active = true
            } else {
                item.active = false
            }
        }
        notifyDataSetChanged()
    }
}

