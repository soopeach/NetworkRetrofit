package com.soopeach.practiceForRetrofit.retrogit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {

    // 객체를 하나만 생성해야하니 싱글턴 패턴을 사용
    companion object{

        private const val BASE_URL = "https://api.github.com"
        private var INSTACE: Retrofit? = null

        fun getInstance(): Retrofit{

            if(INSTACE == null){
                INSTACE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return INSTACE!!
        }
    }
}