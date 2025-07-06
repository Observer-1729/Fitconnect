package com.example.fitconnect

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

//@Serializable
@Parcelize
data class ExerciseFromAPI(val id:String,
                           val exercise:String,
                           val description:String,
                           val gif:String,
                           @SerializedName("reps/time") val repsTime: String,
                            val effects:String) : Parcelable

data class ExerciseResponse(val exercise: List<ExerciseFromAPI>)

//@Serializable
@Parcelize
data class FoodItem(
    val ID: String,
    val Name: String,
    val image: String,
    val Amount: String,
    @SerializedName("Recipe 1") val recipe1: String,
    @SerializedName("Recipe 1 Description") val recipe1Description: String,
    @SerializedName("Recipe 2") val recipe2: String,
    @SerializedName("Recipe 2 Description") val recipe2Description: String,
    @SerializedName("Recipe 3") val recipe3: String,
    @SerializedName("Recipe 3 Description") val recipe3Description: String
) : Parcelable

data class DietResponse(val diet: List<FoodItem>)

