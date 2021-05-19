package com.basecamp.campong.view

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basecamp.campong.databinding.ActivityReqReserveBinding
import com.basecamp.campong.retrofit.RetrofitManager
import com.basecamp.campong.utils.Constants
import com.basecamp.campong.utils.Keyword
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

        postid = intent.getLongExtra(Keyword.POST_ID, -1)

        setUpDefaultDate()

        setContentView(mBinding.root)
    }

    // 날짜 기본값 설정
    // 대여 - 오늘 날짜, 반납 - 내일 날짜
    private fun setUpDefaultDate() {
        val defaultStart = Calendar.getInstance()
        val defaultEnd = Calendar.getInstance()
        defaultEnd.add(Calendar.DAY_OF_MONTH, 1)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        firstDateStr = sdf.format(defaultStart.time)
        endDateStr = sdf.format(defaultEnd.time)

        mBinding.rentalDateTextView.text = firstDateStr
        mBinding.returnDateTextView.text = endDateStr

    }

    // 날짜 텍스트뷰 클릭하면 DateRangePicker 실행
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

    // 선택가능한 날짜는 오늘로부터 45일까지
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
            TODO("Not yet implemented")
        }

        override fun describeContents(): Int {
            TODO("Not yet implemented")
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

    fun requestReserve(view: View) {
        RetrofitManager.instance.requestReserveRequest(postid!!, firstDateStr!!, endDateStr!!) {
            when (it) {
                0 -> {
                    Log.d(Constants.TAG, "ReqReserveActivity - requestReserve() : 예약 성공")
                    Toast.makeText(this, "예약에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Log.d(Constants.TAG, "ReqReserveActivity - requestReserve() : 예약 실패")
                    Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}