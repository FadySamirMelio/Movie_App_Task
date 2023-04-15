package com.movie.network.network

import com.google.gson.JsonElement
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET
    @JvmSuppressWildcards
    suspend fun getRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?
    ): Response<JsonElement>

    @POST
    @JvmSuppressWildcards
    suspend fun postRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @Body body: Map<String, Any>?
    ): Response<JsonElement>

    @PUT
    @JvmSuppressWildcards
    suspend fun putRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @Body body: Map<String, Any>?
    ): Response<JsonElement>

    @DELETE
    @JvmSuppressWildcards
    suspend fun deleteRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?
    ): Response<JsonElement>

    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun postMultiPart(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, Any>?,
        @Part file: MultipartBody.Part?
    ): Response<JsonElement>
}