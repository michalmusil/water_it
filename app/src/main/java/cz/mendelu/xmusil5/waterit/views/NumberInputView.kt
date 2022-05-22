package cz.mendelu.xmusil5.waterit.views

import android.content.Context
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ViewInputTextBinding
import cz.mendelu.xmusil5.waterit.databinding.ViewNumberInputBinding

class NumberInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    private lateinit var binding: ViewNumberInputBinding

    var number: Int
        get() = Integer.parseInt(binding.numberInputEditText.text.toString())
        set(number) {
            binding.numberInputEditText.setText(number.toString())
        }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewNumberInputBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.NumberInputView)
        val hint = attributeValues.getString(R.styleable.NumberInputView_numberInputHint)

        hint?.let { binding.numberInputLayout.setHint(hint) }

        attributeValues.recycle()
    }

    fun setError(error: String?){
        //nemusim osetrovat - pokud je null, textInputLayout proste nic neudela
        binding.numberInputLayout.error = error
    }

    fun addTextChangeListener(watcher: TextWatcher){
        binding.numberInputEditText.addTextChangedListener(watcher)
    }
}