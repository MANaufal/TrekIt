package org.d3if3112.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import org.d3if3112.model.Food
import org.d3if3112.model.FoodApiData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

private const val BASE_URL = "https://api.api-ninjas.com/v1/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface NutritionApi {
    @GET("nutrition")
    suspend fun getFood(
        @Header("X-Api-Key") apiKey: String,
        @Query("query") query: String
    ): List<FoodApiData>
}

object NutApi {
    val service: NutritionApi by lazy {
        retrofit.create(NutritionApi::class.java)
    }
}