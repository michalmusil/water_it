package cz.mendelu.xmusil5.waterit.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ViewImagePickBinding

class ImagePickView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    private lateinit var binding: ViewImagePickBinding

    var buttonText: String
        get() = binding.pickImageButton.text.toString()
        set(text) {
            binding.pickImageButton.text = text
        }

    var image: Drawable
        get() = binding.imageContainer.drawable
        set(drawable) {
            binding.imageContainer.setImageDrawable(drawable)
        }

    init {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewImagePickBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.ImagePickView)
        val buttonText = attributeValues.getString(R.styleable.ImagePickView_imagePickButtonText)
        val initialImage = attributeValues.getDrawable(R.styleable.ImagePickView_imagePickInitialPicture)

        buttonText?.let { this.buttonText = buttonText }
        initialImage?.let { this.image = initialImage }

        attributeValues.recycle()
    }

    fun setOnPickImageButtonListener(listener: View.OnClickListener){
        binding.pickImageButton.setOnClickListener(listener)
    }
}