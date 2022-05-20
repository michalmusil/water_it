package cz.mendelu.xmusil5.waterit.views

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.FrameLayout
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ViewDatePickerBinding
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import java.util.*

class DatePickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    private lateinit var binding: ViewDatePickerBinding
    private var onDateChangedListener: CustomOnDateChangedListener? = null


    private var labelText: String
        get() = binding.label.text.toString()
        set(text) {
            binding.label.setText(text)
        }

    private var datePickText: String
        get() = binding.datePickTextView.text.toString()
        set(text) {
            binding.datePickTextView.setText(text)
        }

    init {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewDatePickerBinding.inflate(LayoutInflater.from(context), this, true)

        binding.cancelChoiceButton.setOnClickListener(View.OnClickListener {
            datePickText = ""
            onDateChangedListener?.let { onDateChangedListener!!.onDateChanged(null) }
        })


        binding.datePickButton.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            var month = calendar.get(Calendar.MONTH)
            var year = calendar.get(Calendar.YEAR)

            var datePickerDialog = DatePickerDialog(context, object:
                DatePickerDialog.OnDateSetListener {
                override fun onDateSet(picker: DatePicker?, year: Int, month: Int, day: Int) {
                    val unixTime = DateUtils.getUnixTime(year, month, day)
                    val dateString = DateUtils.getDateString(unixTime)
                    datePickText = dateString
                    onDateChangedListener?.let { onDateChangedListener!!.onDateChanged(unixTime) }
                }
            }, year, month, day)

            datePickerDialog.show()
        })

        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.DatePickerView)
        val labelText: String? = attributeValues.getString(R.styleable.DatePickerView_dateLabel)

        labelText?.let { this.labelText = labelText }
    }

    fun setOnDateChangedListener(onDateChangedListener: CustomOnDateChangedListener){
        this.onDateChangedListener = onDateChangedListener
    }





    interface CustomOnDateChangedListener{
        fun onDateChanged(unixTime: Long?)
    }
}