package com.example.fitconnect

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface ExerciseService {
    @GET("/exercise/{id}")
    suspend fun getExerciseById(@retrofit2.http.Path("id") id: String): ExerciseItem
}
//interface DietService{
//    @GET("/diet/")
//    suspend fun getDiet(): DietResponse
//}

interface DietService {
    @GET("/diet/{id}")
    suspend fun getDietById(@retrofit2.http.Path("id") id: String): FoodItem
}

private val retrofit = Retrofit.Builder()
    .baseUrl("https://fitapi-perfect.onrender.com") // replace with your fitapi url
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val exerciseService = retrofit.create(ExerciseService::class.java)
    val dietService = retrofit.create(DietService::class.java)



//pura api ka