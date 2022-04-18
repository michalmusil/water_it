package cz.mendelu.xmusil5.waterit.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ViewAttributeWithLabelBinding

class AttributeWithLabelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    private lateinit var binding: ViewAttributeWithLabelBinding

    var labelText: String
        get() = binding.label.text.toString()
        set(text) {
            binding.label.setText(text)
        }

    var attributeText: String
        get() = binding.attribute.text.toString()
        set(text) {
            binding.attribute.setText(text)
        }

    init {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewAttributeWithLabelBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.AttributeWithLabelView)
        val labelText: String? = attributeValues.getString(R.styleable.AttributeWithLabelView_labelText)
        val attributeText: String? = attributeValues.getString(R.styleable.AttributeWithLabelView_attributeText)

        if (labelText != null){
            binding.label.setText(labelText)
        }
        if (attributeText != null){
            binding.attribute.setText(attributeText)
        }

        attributeValues.recycle()
    }

    /*
    fun addTextChangeListener(watcher: TextWatcher){
        binding.textInputEditText.addTextChangedListener(watcher)
    }
     */
}