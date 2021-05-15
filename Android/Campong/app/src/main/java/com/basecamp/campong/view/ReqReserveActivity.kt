package com.basecamp.campong.view

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityReqReserveBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class ReqReserveActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityReqReserveBinding
    private var postid: Long? = null
    private var firstDateStr: String? = null
    private var endDateStr: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityReqReserveBinding.inflate(layoutInflater)

        postid = intent.getLongExtra("postid", -1)

        setUpDefaultDate()

        setContentView(mBinding.root)
    }

    fun setUpDefaultDate() {
        val defaultStart = Calendar.getInstance()
        val defaultEnd = Calendar.getInstance()
        defaultEnd.add(Calendar.DAY_OF_MONTH, 1)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        mBinding.rentalDateTextView.text = sdf.format(defaultStart.time)
        mBinding.returnDateTextView.text = sdf.format(defaultEnd.time)

    }

    fun setUpRangePickerDialog(view: View) {
        val builderRange = MaterialDatePicker.Builder.dateRangePicker()

        builderRange.setCalendarConstraints(limitRange().build())

        val pickerRange = builderRange.build()
        pickerRange.show(supportFragmentManager, pickerRange.toString())
        pickerRange.addOnPositiveButtonClickListener {
            val firstDateLong = it.first
            val firstDate = Date(firstDateLong!!)
            val endDateLong = it.second
            val endDate = Date(endDateLong!!)

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            firstDateStr = sdf.format(firstDate)
            endDateStr = sdf.format(endDate)

            mBinding.rentalDateTextView.text = firstDateStr
            mBinding.returnDateTextView.text = endDateStr

            pickerRange.dismiss()
        }
    }

    private fun limitRange(): CalendarConstraints.Builder {
        val constraintsBuilderRange = CalendarConstraints.Builder()

        val calendarStart: Calendar = GregorianCalendar.getInstance() // 현재 날짜
        val calendarEnd: Calendar = GregorianCalendar.getInstance()

        calendarEnd.add(Calendar.DAY_OF_MONTH, 45)

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)

        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))

        return constraintsBuilderRange
    }

    class RangeValidator(private val minDate: Long, private val maxDate: Long) :
        CalendarConstraints.DateValidator {

        constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong()
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            TODO("not implemented")
        }

        override fun describeContents(): Int {
            TODO("not implemented")
        }

        override fun isValid(date: Long): Boolean {
            return !(minDate > date || maxDate < date)

        }

        companion object CREATOR : Parcelable.Creator<RangeValidator> {
            override fun createFromParcel(parcel: Parcel): RangeValidator {
                return RangeValidator(parcel)
            }

            override fun newArray(size: Int): Array<RangeValidator?> {
                return arrayOfNulls(size)
            }
        }

    }

}