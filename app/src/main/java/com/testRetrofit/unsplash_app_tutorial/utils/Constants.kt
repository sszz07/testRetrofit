package com.testRetrofit.unsplash_app_tutorial.utils

object Constants {
    const val TAG : String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO,
    USER
}

enum class RESPONSE_STATUS {
    OKAY,
    FAIL,
    NO_CONTENT
}


object API {
    const val BASE_URL : String = "https://api.unsplash.com/"

    const val CLIENT_ID : String = "YS7sdqX2kuYBOifsupK1A-J2S4tkMveczqAQVOEBJMs"

    const val SEARCH_PHOTOS : String = "search/photos"
}
