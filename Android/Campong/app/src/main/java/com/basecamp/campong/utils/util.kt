package com.basecamp.campong.utils

enum class TimeValue(val value: Int, val maximum: Int, val msg: String) {
    SEC(60, 60, "분 전"),
    MIN(60, 24, "시간 전"),
    HOUR(24, 30, "일 전"),
    DAY(30, 12, "달 전"),
    MONTH(12, Int.MAX_VALUE, "년 전")
}

fun timeDiff(regTime: Long): String? {
    val curTime = System.currentTimeMillis()
    var diffTime = (curTime - regTime) / 1000
    var msg: String? = null

    if (diffTime < TimeValue.SEC.value) {
        msg = "방금전"
    } else {
        for (i in TimeValue.values()) {
            diffTime /= i.value

            if (diffTime < i.maximum) {
                msg = i.msg
            }
        }
    }

    return msg
}

