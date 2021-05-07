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

    // 닉네임 변경
    const val USER_UPDATE_NICK: String = "/user/updatenick"

    // 로그아웃
    const val USER_LOGOUT: String = "/user/logout"

    /* 게시물 관리 */
    // 게시물 목록 조회
    const val POST_LIST: String = "/post/list"

    // 게시물 등록
    const val POST_UPLOAD: String = "/post/upload"

    // 게시물 이미지 등록
    const val POST_UPLOAD_IMAGE: String = "/post/uploadimage"

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

    // 예약내역(빌린장비)
    const val RESERVE_LIST: String = "/reserve/list"

    // 예약내역 상세
    const val RESERVE_VIEW: String = "/reserve/view"

    // 대여
    const val STATE_RENTAL: String = "/state/rental"

    // 반납
    const val STATE_RETURN: String = "/state/return"

    // 승인
    const val STATE_GRANT: String = "/state/grant"

    // 취소
    const val STATE_CANCEL: String = "/state/cancel"

    // 이미지 업로드
    const val UPLOAD_IMAGE: String = "/image/upload"
}

object Preference {

    // Preference 이름
    const val SHARED_PREFERENCE_FILE = "camponog_preference"

    // Preference Key 값
    const val SHARED_PREFERENCE_NAME_COOKIE = "cookies"

}