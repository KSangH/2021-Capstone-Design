package com.basecamp.campong.retrofit

import com.basecamp.campong.model.*
import com.basecamp.campong.utils.API
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    /* 회원 관리*/
    // 회원가입
    @Headers("content-type: application/json")
    @POST(API.USER_SIGN_UP)
    fun requestSignup(
        @Body body: ReqSignup
    ): Call<ResultBase>

    // 이메일 중복확인
    @Headers("content-type: application/json")
    @POST(API.USER_CHECK_EMAIL)
    fun requestCheckEmail(
        @Body body: ReqCheckEmail
    ): Call<ResultBase>

    // 닉네임 중복확인
    @Headers("content-type: application/json")
    @POST(API.USER_CHECK_NICK)
    fun requestCheckNick(
        @Body body: ReqCheckNick
    ): Call<ResultBase>

    // 로그인
    @Headers("content-type: application/json")
    @POST(API.USER_LOGIN)
    fun requestLogin(
        @Body body: ReqLogin
    ): Call<ResultBase>

    // 로그아웃
    @Headers("content-type: application/json")
    @POST(API.USER_LOGOUT)
    fun requestLogout(
    ): Call<ResultBase>

    // 프로필 변경
    @Headers("content-type: application/json")
    @POST(API.USER_UPDATE_USER)
    fun requestUpdateUser(
        @Body body: ReqUpdateUser
    ): Call<ResultBase>

    // 회원 정보
    @POST(API.USER_INFO)
    fun requestUserInfo(): Call<ResultUserInfo>

    /* 이미지 */
    // 이미지 업로드
    @Multipart
    @POST(API.UPLOAD_IMAGE)
    fun requestUploadImage(
        @Part image: MultipartBody.Part
    ): Call<ResultUploadImage>

    /* 게시물 목록 관리 */
    // 게시물 목록 조회
    @Headers("content-type: application/json")
    @GET(API.POST_LIST)
    fun requestPostList(
        @Query("pagenum") pagenum: Int,
        @Query("keyword") keyword: String? = null,
        @Query("catename") catename: String? = null,
        @Query("location") location: String? = null
    ): Call<ResultPostList>

    /* 게시물 관리 */
    // 게시물 등록
    @Headers("content-type: application/json")
    @POST(API.POST_UPLOAD)
    fun requestUploadPost(
        @Body body: ReqUploadPost
    ): Call<ResultUploadPost>

    // 게시물 조회
    @Headers("content-type: application/json")
    @POST(API.POST_VIEW)
    fun requestPostView(
        @Body body: ReqPostView
    ): Call<ResultPostView>

    // 게시물 수정
    @Headers("content-type: application/json")
    @POST(API.POST_UPDATE)
    fun requestUpdatePost()

    // 게시물 삭제
    @Headers("content-type: application/json")
    @POST(API.POST_DELETE)
    fun requestDeletePost(): Call<ResultBase>

    /* 예약/대여/반납 */
    // 예약하기 화면 조회
    @Headers("content-type: application/json")
    @POST(API.RESERVE_INIT)
    fun requestReserveInit(
        @Body body: ReqReserveInit
    ): Call<ResultBase>

    // 예약하기
    @Headers("content-type: application/json")
    @POST(API.RESERVE_REQUEST)
    fun requestReserveRequest(
        @Body body: ReqReserveRequest
    ): Call<ResultBase>

    // 예약내역(빌려준장비)
    @Headers("content-type: application/json")
    @POST(API.RESERVE_MYLIST)
    fun requestReserveMyList(
    ): Call<ResultReserveList>

    // 예약내역(빌린장비)
    @Headers("content-type: application/json")
    @POST(API.RESERVE_LIST)
    fun requestReserveList(
        @Body body: ReqReserveList
    ): Call<ResultReserveList>

    // 예약내역 상세
    @Headers("content-type: application/json")
    @POST(API.RESERVE_VIEW)
    fun requestReserveView(
        @Body body: ReqReserveState
    ): Call<ResultBase>

    // 대여하기
    @Headers("content-type: application/json")
    @POST(API.STATE_RENTAL)
    fun requestStateRental(
        @Body body: ReqReserveState
    ): Call<ResultBase>

    // 반납하기
    @Headers("content-type: application/json")
    @POST(API.STATE_RETURN)
    fun requestStateReturn(
        @Body body: ReqReserveState
    ): Call<ResultBase>

    // 승인하기
    @Headers("content-type: application/json")
    @POST(API.STATE_GRANT)
    fun requestStateGrant(
        @Body body: ReqReserveState
    ): Call<ResultBase>

    // 취소하기
    @Headers("content-type: application/json")
    @POST(API.STATE_CANCEL)
    fun requestStateCancel(
        @Body body: ReqReserveState
    ): Call<ResultBase>
}