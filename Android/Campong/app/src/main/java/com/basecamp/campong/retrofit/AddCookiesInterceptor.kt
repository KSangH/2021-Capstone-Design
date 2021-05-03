package com.basecamp.campong.retrofit

import com.basecamp.campong.utils.Preference
import com.basecamp.campong.utils.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class AddCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        // Preference에서 쿠키 가져오기
        val getCookies: Set<String> =
            SharedPreferenceManager.instance.getStringSet(
                Preference.SHARED_PREFERENCE_NAME_COOKIE,
                HashSet<String>()
            ) as Set<String>

        // header에 쿠키 넣어주기
        for (cookie in getCookies) {
            builder.addHeader("Cookie", cookie)
        }

//        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android")

        return chain.proceed(builder.build())
    }
}