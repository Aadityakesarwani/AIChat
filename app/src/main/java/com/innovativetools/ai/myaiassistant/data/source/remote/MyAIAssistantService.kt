package com.innovativetools.ai.myaiassistant.data.source.remote

import android.util.Log
import com.google.gson.JsonObject
import com.innovativetools.ai.myaiassistant.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface MyAIAssistantService {

    @POST(Constants.Endpoints.TEXT_COMPLETIONS)
    @Streaming
    fun textCompletionsWithStream(@Body body: JsonObject): Call<ResponseBody>

    @POST(Constants.Endpoints.TEXT_COMPLETIONS_TURBO)
    @Streaming
    fun textCompletionsTurboWithStream(@Body body: JsonObject): Call<ResponseBody>

}