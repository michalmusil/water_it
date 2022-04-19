package cz.mendelu.xmusil5.waterit.views

import android.content.Context
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ViewInputTextBinding

class TextInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    private lateinit var binding: ViewInputTextBinding

    var text: String
        get() = binding.textInputEditText.text.toString()
        set(text) {
            binding.textInputEditText.setText(text)
        }

    init {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewInputTextBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.TextInputView)
        val hint = attributeValues.getString(R.styleable.TextInputView_hint)
        val maxChars = attributeValues.getInteger(R.styleable.TextInputView_maxChars, -1)
        val maxLines = attributeValues.getInteger(R.styleable.TextInputView_maxLines, -1)

        hint?.let { binding.textInputLayout.setHint(hint) }
        if (maxChars > 0) binding.textInputEditText.filters = arrayOf(InputFilter.LengthFilter(maxChars))
        if (maxLines > 0) binding.textInputEditText.maxLines = maxLines

        attributeValues.recycle()
    }

    fun setError(error: String?){
        //nemusim osetrovat - pokud je null, textInputLayout proste nic neudela
        binding.textInputLayout.error = error
    }

    fun addTextChangeListener(watcher: TextWatcher){
        binding.textInputEditText.addTextChangedListener(watcher)
    }
}