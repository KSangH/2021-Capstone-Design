package com.basecamp.campong.retrofit

import android.content.SharedPreferences
import com.basecamp.campong.utils.Preference
import com.basecamp.campong.utils.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies: HashSet<String> = HashSet()

            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            // Preference에 쿠키 저장
            val preferences = SharedPreferenceManager.instance
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putStringSet(Preference.SHARED_PREFERENCE_NAME_COOKIE, cookies)
            editor.apply()
        }
        return originalResponse
    }
}