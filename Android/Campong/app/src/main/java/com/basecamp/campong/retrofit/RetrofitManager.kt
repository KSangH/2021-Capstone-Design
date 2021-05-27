package com.basecamp.campong.retrofit

import android.util.Log
import com.basecamp.campong.model.*
import com.basecamp.campong.utils.API
import com.basecamp.campong.utils.Constants.TAG
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val service: RetrofitService? =
        RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitService::class.java)

    // 회원가입
    fun requestSignup(
        email: String, password: String, usernick: String, phone: String, username: String,
        completion: (Int) -> Unit
    ) {
        val req = ReqSignup(email, password, usernick, phone, username)
        val call = service?.requestSignup(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {  // 통신 성공
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                completion(1)
                            }
                        }
                        else -> { // 통신 실패
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }

            }
        )
    }

    // 이메일 중복확인
    fun requestCheckEmail(email: String, completion: (Int) -> Unit) {
        val req = ReqCheckEmail(email)
        val call = service?.requestCheckEmail(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                if (response.body()?.errCode == 1002) {
                                    completion(1) // 중복 이메일
                                } else {
                                    completion(2)
                                }
                            }
                        }
                        else -> {
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }

            }
        )
    }

    // 닉네임 중복확인
    fun requestCheckNick(usernick: String, completion: (Int) -> Unit) {
        val req = ReqCheckNick(usernick)
        val call = service?.requestCheckNick(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                if (response.body()?.errCode == 1003) {
                                    completion(1) // 중복 닉네임
                                } else {
                                    completion(2)
                                }
                            }
                        }
                        else -> {
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }
            }
        )
    }

    // 로그인
    fun requestLogin(email: String, password: String, completion: (Int) -> Unit) {
        val req = ReqLogin(email, password)
        val call = service?.requestLogin(
            req
        ) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1005) { // 로그인 오류
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 로그아웃
    fun requestLogout(completion: (Int) -> Unit) {
        val call = service?.requestLogout(
        ) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1005) { // 로그인 오류
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 사용자 정보 요청
    fun requestUserInfo(completion: (Int, usernick: String?, imageid: Long?) -> Unit) {
        val call = service?.requestUserInfo() ?: return

        call.enqueue(object : Callback<ResultUserInfo> {
            override fun onResponse(
                call: Call<ResultUserInfo>,
                response: Response<ResultUserInfo>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.usernick, response.body()!!.image_id)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null, null)
                            } else {
                                completion(2, null, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUserInfo>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null, null)
            }

        })
    }

    // 프로필 변경
    fun requestUpdateUser(usernick: String, image_id: Long?, completion: (Int) -> Unit) {
        val req = ReqUpdateUser(usernick, image_id)
        val call = service?.requestUpdateUser(
            req
        ) ?: return

        call.enqueue(
            object : Callback<ResultBase> {
                override fun onResponse(
                    call: Call<ResultBase>,
                    response: Response<ResultBase>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.d(TAG, response.raw().toString())
                            if (response.body()?.error == false) { // 성공
                                completion(0)
                            } else {
                                if (response.body()?.errCode == 1007) {
                                    completion(1) // 닉네임 변경 중 오류
                                } else {
                                    completion(2)
                                }
                            }
                        }
                        else -> {
                            Log.d(TAG, response.code().toString())
                            completion(-1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    completion(-1)
                }
            }
        )
    }

    // 이미지 업로드
    fun requestUploadImage(file: File, completion: (Int, image_id: Long?) -> Unit) {

        val fileBody = MultipartBody.Part.createFormData(
            "image", file.name, file.asRequestBody("image/jpeg".toMediaType())
        )

        val call = service?.requestUploadImage(
            fileBody
        ) ?: return

        call.enqueue(object : Callback<ResultUploadImage> {
            override fun onResponse(
                call: Call<ResultUploadImage>,
                response: Response<ResultUploadImage>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.imageid)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUploadImage>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }
        })
    }

    // 게시물 목록 조회
    fun requestPostList(
        pagenum: Int, location: String?,
        completion: (Int, postList: List<Post>?) -> Unit
    ) {
        var call = service?.requestPostList(pagenum) ?: return

        if (location != null) {
            call = service.requestPostList(pagenum, location)
        }

        call.enqueue(object : Callback<ResultPostList> {

            override fun onResponse(
                call: Call<ResultPostList>,
                response: Response<ResultPostList>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.item)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultPostList>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }

        })
    }

    // 게시물 검색
    fun requestSearchPostList(
        pagenum: Int, keyword: String?, catename: String?,
        completion: (Int, postList: List<Post>?) -> Unit
    ) {
        var call = service?.requestPostList(pagenum) ?: return

        if (keyword != null && catename != null) {
            call = service.requestPostList(pagenum, keyword, catename)
        } else if (keyword != null) {
            call = service.requestPostList(pagenum, keyword)
        } else if (catename != null) {
            call = service.requestPostList(pagenum, catename)
        }

        call.enqueue(object : Callback<ResultPostList> {

            override fun onResponse(
                call: Call<ResultPostList>,
                response: Response<ResultPostList>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.item)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultPostList>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }

        })
    }

    // 게시물 등록
    fun requestUploadPost(
        catename: String,
        title: String,
        contents: String,
        fee: String,
        lat: String,
        lon: String,
        location: String,
        imageid: Long?, completion: (Int, postid: Long?) -> Unit
    ) {
        val req = ReqUploadPost(catename, title, contents, fee, lat, lon, location, imageid)
        val call = service?.requestUploadPost(req) ?: return

        call.enqueue(object : Callback<ResultUploadPost> {
            override fun onResponse(
                call: Call<ResultUploadPost>,
                response: Response<ResultUploadPost>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.postid) // 성공시 업로드한 게시물을 확인하기 위한 postid
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUploadPost>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }

        })
    }

    // 게시물 조회
    fun requestPostView(
        postid: Long,
        completion: (Int, mypost: Boolean?, post: Post?) -> Unit
    ) {
        val req = ReqPostView(postid)
        val call = service?.requestPostView(req) ?: return

        call.enqueue(object : Callback<ResultPostView> {
            override fun onResponse(
                call: Call<ResultPostView>,
                response: Response<ResultPostView>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.mypost, response.body()!!.post)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null, null)
                            } else {
                                completion(2, null, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultPostView>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null, null)
            }
        })
    }

    // 게시물 수정
    // postid, catename, title, contents, fee, lat, lon, location, imageid
    fun requestUpdatePost() {
        // TODO
    }

    // 게시물 삭제
    // postid
    fun requestDeletePost() {
        // TODO
    }

    /* 예약/대여/반납 */
    // 예약하기 화면 조회
    fun requestReserveInit(postid: Long, completion: (Int) -> Unit) {
        val req = ReqReserveInit(postid)
        val call = service?.requestReserveInit(req) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 예약하기
    fun requestReserveRequest(
        postid: Long,
        rentaldate: String,
        returndate: String,
        completion: (Int) -> Unit
    ) {
        val req = ReqReserveRequest(postid, rentaldate, returndate)
        val call = service?.requestReserveRequest(req) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 예약내역(빌려준장비)
    fun requestReserveMyList(
        state: Int, pagenum: Int,
        completion: (Int, reservationList: List<ReserveItem>?) -> Unit
    ) {
        val req = ReqReserveList(state, pagenum)
        val call = service?.requestReserveMyList(req) ?: return

        call.enqueue(object : Callback<ResultReserveList> {
            override fun onResponse(
                call: Call<ResultReserveList>,
                response: Response<ResultReserveList>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.data)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultReserveList>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }

        })
    }

    // 예약내역(빌린장비)
    fun requestReserveList(
        state: Int, pagenum: Int,
        completion: (Int, reservationList: List<ReserveItem>?) -> Unit
    ) {
        val req = ReqReserveList(state, pagenum)
        val call = service?.requestReserveList(req) ?: return

        call.enqueue(object : Callback<ResultReserveList> {
            override fun onResponse(
                call: Call<ResultReserveList>,
                response: Response<ResultReserveList>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.data)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultReserveList>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }

        })
    }

    // 예약내역 상세
    fun requestReserveView(reserveid: Long, completion: (Int, ReserveItem?) -> Unit) {

        val req = ReqReserveState(reserveid)
        val call = service?.requestReserveView(req) ?: return

        call.enqueue(object : Callback<ResultReserveView> {
            override fun onResponse(
                call: Call<ResultReserveView>,
                response: Response<ResultReserveView>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0, response.body()!!.reserveItem)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1, null)
                            } else {
                                completion(2, null)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultReserveView>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1, null)
            }

        })
    }

    // 대여하기
    fun requestReserveStateRental(reserveid: Long, completion: (Int) -> Unit) {

        val req = ReqReserveState(reserveid)
        val call = service?.requestStateRental(req) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 반납하기
    fun requestReserveStateReturn(reserveid: Long, completion: (Int) -> Unit) {

        val req = ReqReserveState(reserveid)
        val call = service?.requestStateReturn(req) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 승인하기
    fun requestReserveStateGrant(reserveid: Long, completion: (Int) -> Unit) {
        val req = ReqReserveState(reserveid)
        val call = service?.requestStateGrant(req) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    // 취소하기
    fun requestReserveStateCancel(reserveid: Long, completion: (Int) -> Unit) {

        val req = ReqReserveState(reserveid)
        val call = service?.requestStateCancel(req) ?: return

        call.enqueue(object : Callback<ResultBase> {
            override fun onResponse(call: Call<ResultBase>, response: Response<ResultBase>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) { // 성공
                            completion(0)
                        } else {
                            if (response.body()?.errCode == 1007) {
                                completion(1)
                            } else {
                                completion(2)
                            }
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1)
                    }
                }
            }

            override fun onFailure(call: Call<ResultBase>, t: Throwable) {
                Log.d(TAG, t.toString())
                completion(-1)
            }

        })
    }

    fun requestReverseGeocoding(
        lat: Double,
        lon: Double,
        completion: (Int, String?, String?, String?) -> Unit
    ) {
        val coords = "$lon,$lat"
        val call = service?.requestReverseGeocoding(coords) ?: return

        call.enqueue(object : Callback<ResultReverseGeocoding> {
            override fun onResponse(
                call: Call<ResultReverseGeocoding>,
                response: Response<ResultReverseGeocoding>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body() != null) {

                            var addr: String? = null
                            var roadaddr: String? = null
                            var baseaddr: String? = null

                            for (item in response.body()!!.results) {
                                when (item.name) {
                                    "addr" -> { // 지번주소
                                        if (item.region.area1.name != "") {
                                            addr = item.region.area1.name
                                        }
                                        if (item.region.area2.name != "") {
                                            addr += " ${item.region.area2.name}"
                                        }
                                        if (item.region.area3.name != "") {
                                            addr += " ${item.region.area3.name}"
                                        }
                                        if (item.region.area4.name != "") {
                                            addr += " ${item.region.area4.name}"
                                        }

                                        baseaddr = addr

                                        if (item.land.number1 != "") {
                                            addr += " ${item.land.number1}"
                                        }
                                        if (item.land.number2 != "") {
                                            addr += "-${item.land.number2}"
                                        }
                                    }
                                    "roadaddr" -> { // 도로명 주소
                                        if (item.region.area1.name != "") {
                                            roadaddr = item.region.area1.name
                                        }
                                        if (item.region.area2.name != "") {
                                            roadaddr += " ${item.region.area2.name}"
                                        }
                                        if (item.region.area3.name != "") {
                                            roadaddr += " ${item.region.area3.name}"
                                        }
                                        if (item.region.area4.name != "") {
                                            roadaddr += " ${item.region.area4.name}"
                                        }

                                        if (item.land.name != "") {
                                            roadaddr += " ${item.land.name}"
                                        }
                                        if (item.land.number1 != "") {
                                            roadaddr += " ${item.land.number1}"
                                        }
                                    }
                                }
                            }
                            completion(0, baseaddr, addr, roadaddr)
                        }
                    }
                    else -> {
                        Log.d(TAG, response.code().toString())
                        completion(-1, null, null, null)
                    }
                }
            }

            override fun onFailure(call: Call<ResultReverseGeocoding>, t: Throwable) {
                completion(-1, null, null, null)
            }

        })

    }
}