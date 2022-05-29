package cz.mendelu.xmusil5.waterit.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ViewCustomInputBinding

class CustomInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    private lateinit var binding: ViewCustomInputBinding

    var label: String
        get() = binding.label.text.toString()
        set(value) {
            binding.label.setText(value)
        }

    var value: String
        get() = binding.value.text.toString()
        set(value) {
            binding.value.setText(value)
        }

    init {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewCustomInputBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.CustomInputView)
        val label = attributeValues.getString(R.styleable.CustomInputView_customLabel)
        val image = attributeValues.getDrawable(R.styleable.CustomInputView_customImage)

        label?.let { this.label = label }
        image?.let { binding.image.setImageDrawable(image) }

        attributeValues.recycle()
    }

    fun setCancelButtonVisible(visible: Boolean){
        if (visible) {
            binding.cancelButton.visibility = VISIBLE
        } else{
            binding.cancelButton.visibility = GONE
        }
    }

    fun setOnCancelButtonListener(listener: View.OnClickListener){
        binding.cancelButton.setOnClickListener(listener)
    }

    fun setRootOnClickListener(listener: View.OnClickListener){
        binding.root.setOnClickListener(listener)
    }

    fun setImageDrawable(drawable: Drawable){
        binding.image.setImageDrawable(drawable)
    }
}