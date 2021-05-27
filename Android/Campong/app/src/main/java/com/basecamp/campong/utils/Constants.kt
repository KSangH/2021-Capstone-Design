package com.basecamp.campong.utils

object Constants {
    const val TAG: String = "로그"
}

object API {
    const val BASE_URL: String = "http://20.41.75.198:8080"

    /* 회원관리 */
    // 회원가입
    const val USER_SIGN_UP: String = "/user/signup"

    // 이메일 중복확인
    const val USER_CHECK_EMAIL: String = "/user/checkemail"

    // 닉네임 중복확인
    const val USER_CHECK_NICK: String = "/user/checknick"

    // 로그인
    const val USER_LOGIN: String = "/user/login"

    // 프로필 변경
    const val USER_UPDATE_USER: String = "/user/updateuser"

    // 유저 정보
    const val USER_INFO: String = "/user/userinfo"

    // 로그아웃
    const val USER_LOGOUT: String = "/user/logout"

    /* 게시물 관리 */
    // 게시물 목록 조회
    const val POST_LIST: String = "/post/list"

    // 게시물 등록
    const val POST_UPLOAD: String = "/post/upload"

    // 게시물 조회
    const val POST_VIEW: String = "/post/view"

    // 게시물 수정
    const val POST_UPDATE: String = "/post/update"

    // 게시물 삭제
    const val POST_DELETE: String = "/post/delete"

    /* 예약-대여-반납 */
    // 예약 폼
    const val RESERVE_INIT: String = "/reserve/init"

    // 최종 예약
    const val RESERVE_REQUEST: String = "/reserve/request"

    // 예약내역(빌려준장비)
    const val RESERVE_MYLIST: String = "/reserve/mylist"

    // 예약내역(빌린장비)
    const val RESERVE_LIST: String = "/reserve/list"

    // 예약내역 상세
    const val RESERVE_VIEW: String = "/reserve/view"

    // 대여
    const val STATE_RENTAL: String = "/reserve/state/rental"

    // 반납
    const val STATE_RETURN: String = "/reserve/state/return"

    // 승인
    const val STATE_GRANT: String = "/reserve/state/grant"

    // 취소
    const val STATE_CANCEL: String = "/reserve/state/cancel"

    // 이미지 업로드
    const val UPLOAD_IMAGE: String = "/image/upload"
}

object Keyword {

    const val EMAIL = "email"

    const val PASSWORD = "password"

    const val USERNICK = "usernick"

    const val PHONE = "phone"

    const val USERNAME = "username"

    const val ERROR = "error"

    const val ERROR_MESSAGE = "message"

    const val ERROR_CODE = "errorcode"

    const val POST_ID = "postid"

    const val CATENAME = "catename"

    const val TITLE = "title"

    const val CONTENTS = "contents"

    const val FEE = "fee"

    const val LAT = "lat"

    const val LON = "lon"

    const val LOCATION = "location"

    const val UPLOAD_DATE = "uploaddate"

    const val RENTAL_DATE = "rentaldate"

    const val RETURN_DATE = "returndate"

    const val STATE = "state"

    const val REQUEST_STATE = "requeststate"

    const val RESERVE_ID = "reserveid"

    const val QRCODE = "qrcode"

    const val IMAGE_ID = "imageid"

    const val QR_TYPE = "qr_type"

    const val QR_TYPE_RENTAL = 1

    const val QR_TYPE_RETURN = 2

    const val RESERVESTATE = "reservestate"

    const val PAGE_NUM = "pagenum"
}

object Preference {

    // Preference 이름
    const val SHARED_PREFERENCE_FILE = "camponog_preference"

    // Preference Key 값
    const val SHARED_PREFERENCE_NAME_COOKIE = "cookies"

}

object RentalState {
    const val RENTAL_STATE_WAIT = 1
    const val RENTAL_STATE_CONFIRM = 2
    const val RENTAL_STATE_RENTAL = 3
    const val RENTAL_STATE_RETURN = 4
    const val RENTAL_STATE_CANCEL = 5
}

object RequestCode {
    const val WRITE_POST = 100
    const val ACCEPT_REQUEST = 300
    const val PICK_PHOTO = 400
    const val SELECT_LOCATION = 500
}

object Map {
    const val BASE_URL: String = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc"

    const val KEY_ID: String = "X-NCP-APIGW-API-KEY-ID"

    const val KEY: String = "X-NCP-APIGW-API-KEY"
}