package cz.mendelu.xmusil5.waterit.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ViewInputShortTextBinding

class ShortTextInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    private lateinit var binding: ViewInputShortTextBinding

    var text: String
        get() = binding.textInputEditText.text.toString()
        set(text) {
            binding.textInputEditText.setText(text)
        }

    init {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewInputShortTextBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.ShortTextInputView)
        val hint = attributeValues.getString(R.styleable.ShortTextInputView_hint)
        if (hint != null){
            binding.textInputLayout.setHint(hint)
        }
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