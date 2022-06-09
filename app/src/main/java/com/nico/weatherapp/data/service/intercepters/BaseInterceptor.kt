package com.nico.weatherapp.data.service.intercepters

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.UnknownHostException

class BaseInterceptor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        val request = original.newBuilder()
                .addHeader("Application-Name", "WeatherApp")
                .addHeader("Application-Platform", "Android")
                .build()


        val response = try {
            chain.proceed(request)
        } catch (e: UnknownHostException){
            Response.Builder()
                .request(request)
                .code(204)
                .protocol(Protocol.HTTP_1_1)
                .message("response 204")
                .body("".toResponseBody(null))
                .build()
        }

        return response
    }
}