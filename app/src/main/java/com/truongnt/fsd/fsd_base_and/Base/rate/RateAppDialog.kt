package com.truongnt.fsd.fsd_base_and.Base.rate

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.truongnt.fsd.fsd_base_and.R
import com.truongnt.fsd.fsd_base_and.databinding.DialogRateAppBinding

class RateAppDialog(context: Context,showNotNow : Boolean) : Dialog(context) {

    var binding: DialogRateAppBinding? = null

    var onRate: (() -> Unit)? = null
    var onSend: (() -> Unit)? = null

    init {
        binding = DialogRateAppBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(root)
            window?.apply {
                setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.CENTER)
            }

            tvNotNow.isVisible = showNotNow

            ratingbar.setOnRatingChangeListener { view, rate, fromUser ->
                when (rate) {
                    1f -> {
                        ivEmo.setAnimation(R.raw.rate1)
                    }

                    2f -> {
                        ivEmo.setAnimation(R.raw.rate2)
                    }

                    3f -> {
                        ivEmo.setAnimation(R.raw.rate3)
                    }

                    4f -> {
                        ivEmo.setAnimation(R.raw.rate4)
                    }

                    5f -> {
                        ivEmo.setAnimation(R.raw.rate5)
                    }
                }
            }

            btnRateApp.setOnClickListener {
                if(ratingbar.rating <= 3f){
                    onSend?.invoke()
                }else{
                    onRate?.invoke()
                }
                dismiss()
            }

            tvNotNow.setOnClickListener {
                dismiss()
            }
        }
    }

}