package com.example.fitconnect

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


fun getCurrentUserId(): String {
    return FirebaseAuth.getInstance().currentUser?.uid ?: ""
}

suspend fun getFitnessPlan(age: Int, bmi: Double, goal: String, dietViewModel: DietViewModel, exerciseViewModel: ExerciseViewModel) {
    val exerciseIds: List<String>
    val dietIds: List<String>

    Log.d("mainViewModel", "Function gets called")

    if (age in 10..18 && bmi < 18.5 && goal == "Gain Weight") {
        exerciseIds = listOf("7", "8", "1", "12", "24", "25", "26")
        dietIds = listOf("4", "10", "5", "6", "7", "1", "2")
    } else if (age in 10..18 && bmi < 18.5 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("18", "16", "20", "17", "26", "41", "32")
        dietIds = listOf("12", "10", "5", "6", "11", "13", "8")
    } else if (age in 10..18 && bmi < 18.5 && goal == "Get Fitter") {
        exerciseIds = listOf("1", "35", "2", "13", "20", "33", "28")
        dietIds = listOf("4", "5", "12", "6", "3", "9", "11")
    } else if (age in 10..18 && bmi < 18.5 && goal == "Learn the Basics") {
        exerciseIds = listOf("26", "32", "31", "22", "38", "34", "15")
        dietIds = listOf("5", "2", "15", "8", "9", "7", "12")
    } else if (age in 10..18 && bmi in 18.6 .. 24.8 && goal == "Lose Weight") {
        exerciseIds = listOf("16", "23", "22", "18", "29", "30", "42")
        dietIds = listOf("6", "5", "8", "11", "16", "12", "1")
    } else if (age in 10..18 && bmi in 18.6 .. 24.8 && goal == "Gain Weight") {
        exerciseIds = listOf("6", "39", "12", "4", "27", "33", "28")
        dietIds = listOf("8", "7", "1", "11", "4", "13", "12")
    } else if (age in 10..18 && bmi in 18.6 .. 24.8 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("22", "16", "17", "23", "27", "41", "32")
        dietIds = listOf("2", "8", "9", "4", "10", "6", "16")
    } else if (age in 10..18 && bmi in 18.6 .. 24.8 && goal == "Get Fitter") {
        exerciseIds = listOf("7", "8", "12", "21", "13", "24", "31")
        dietIds = listOf("13", "8", "16", "15", "11", "1", "16")
    } else if (age in 10..18 && bmi in 18.6 .. 24.8 && goal == "Learn the Basics") {
        exerciseIds = listOf("26", "37", "4", "14", "39", "23", "25")
        dietIds = listOf("11", "16", "12", "6", "13", "4", "9")
    } else if (age in 10..18 && bmi in 24.9 .. 29.9 && goal == "Lose Weight") {
        exerciseIds = listOf("19", "17", "16", "13", "29", "33", "27")
        dietIds = listOf("10", "16", "3", "5", "4", "9", "11")
    } else if (age in 10..18 && bmi in 24.9 .. 29.9 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("16", "18", "22", "13", "25", "32", "27")
        dietIds = listOf("7", "16", "2", "6", "11", "3", "1")
    } else if (age in 10..18 && bmi in 24.9 .. 29.9 && goal == "Get Fitter") {
        exerciseIds = listOf("34", "36", "4", "20", "19", "28", "42")
        dietIds = listOf("12", "3", "16", "13", "4", "2", "5")
    } else if (age in 10..18 && bmi in 24.9 .. 29.9 && goal == "Learn the Basics") {
        exerciseIds = listOf("22", "40", "37", "14", "19", "15", "23")
        dietIds = listOf("16", "2", "16", "13", "11", "7", "3")
    } else if (age in 10..18 && bmi >= 30 && goal == "Lose Weight") {
        exerciseIds = listOf("23", "18", "16", "22", "27", "30", "26")
        dietIds = listOf("2", "6", "15", "12", "4", "16", "1")
    } else if (age in 10..18 && bmi >= 30 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("23", "17", "22", "21", "31", "30", "24")
        dietIds = listOf("10", "2", "3", "11", "9", "8", "16")
    } else if (age in 10..18 && bmi >= 30 && goal == "Get Fitter") {
        exerciseIds = listOf("35", "37", "38", "18", "19", "26", "30")
        dietIds = listOf("16", "6", "1", "7", "13", "16", "11")
    } else if (age in 10..18 && bmi >= 30 && goal == "Learn the Basics") {
        exerciseIds = listOf("13", "16", "17", "23", "31", "26", "24")
        dietIds = listOf("10", "2", "6", "7", "11", "16", "15")
    } else if (age in 18..26 && bmi < 18.5 && goal == "Gain Weight") {
        exerciseIds = listOf("37", "15", "7", "9", "30", "42", "24")
        dietIds = listOf("10", "13", "3", "7", "16", "9", "1")
    } else if (age in 18..26 && bmi < 18.5 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("20", "18", "19", "22", "42", "32", "41")
        dietIds = listOf("10", "3", "7", "6", "11", "4", "12")
    } else if (age in 18..26 && bmi < 18.5 && goal == "Get Fitter") {
        exerciseIds = listOf("35", "1", "37", "23", "20", "28", "41")
        dietIds = listOf("4", "7", "16", "11", "3", "2", "10")
    } else if (age in 18..26 && bmi < 18.5 && goal == "Learn the Basics") {
        exerciseIds = listOf("18", "2", "7", "15", "40", "9", "34")
        dietIds = listOf("1", "9", "6", "12", "4", "10", "2")
    } else if (age in 18..26 && bmi in 18.6 .. 24.8 && goal == "Lose Weight") {
        exerciseIds = listOf("20", "22", "23", "19", "29", "27", "25")
        dietIds = listOf("13", "10", "2", "8", "5", "12", "16")
    } else if (age in 18..26 && bmi in 18.6 .. 24.8 && goal == "Gain Weight") {
        exerciseIds = listOf("9", "39", "11", "36", "30", "29", "25")
        dietIds = listOf("13", "5", "11", "10", "3", "9", "16")
    } else if (age in 18..26 && bmi in 18.6 .. 24.8 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("23", "17", "18", "22", "24", "27", "31")
        dietIds = listOf("16", "3", "10", "15", "13", "6", "4")
        Log.d("mainViewModel","This should fetch")
    } else if (age in 18..26 && bmi in 18.6 .. 24.8 && goal == "Get Fitter") {
        exerciseIds = listOf("3", "36", "5", "20", "16", "27", "32")
        dietIds = listOf("1", "7", "16", "8", "3", "6", "15")
    } else if (age in 18..26 && bmi in 18.6 .. 24.8 && goal == "Learn the Basics") {
        exerciseIds = listOf("31", "14", "28", "34", "42", "36", "1")
        dietIds = listOf("15", "10", "6", "16", "13", "9", "8")
    } else if (age in 18..26 && bmi in 24.9 .. 29.9 && goal == "Lose Weight") {
        exerciseIds = listOf("20", "18", "21", "16", "32", "42", "27")
        dietIds = listOf("6", "7", "15", "13", "1", "16", "11")
    } else if (age in 18..26 && bmi in 24.9 .. 29.9 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("22", "20", "17", "21", "42", "41", "28")
        dietIds = listOf("12", "10", "8", "1", "13", "5", "6")
    } else if (age in 18..26 && bmi in 24.9 .. 29.9 && goal == "Get Fitter") {
        exerciseIds = listOf("2", "10", "4", "21", "22", "28", "25")
        dietIds = listOf("13", "10", "8", "16", "2", "5", "15")
    } else if (age in 18..26 && bmi in 24.9 .. 29.9 && goal == "Learn the Basics") {
        exerciseIds = listOf("24", "33", "32", "7", "29", "31", "35")
        dietIds = listOf("11", "3", "6", "7", "13", "10", "5")
    } else if (age in 18..26 && bmi >= 30 && goal == "Lose Weight") {
        exerciseIds = listOf("17", "18", "16", "20", "25", "33", "28")
        dietIds = listOf("5", "12", "8", "16", "1", "11", "7")
    } else if (age in 18..26 && bmi >= 30 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("18", "13", "16", "21", "25", "28", "30")
        dietIds = listOf("16", "11", "3", "10", "16", "1", "6")
    } else if (age in 18..26 && bmi >= 30 && goal == "Get Fitter") {
        exerciseIds = listOf("1", "3", "36", "17", "19", "42", "29")
        dietIds = listOf("9", "13", "16", "12", "16", "11", "8")
    } else if (age in 18..26 && bmi >= 30 && goal == "Learn the Basics") {
        exerciseIds = listOf("41", "22", "36", "20", "9", "40", "25")
        dietIds = listOf("13", "1", "10", "6", "3", "16", "4")
    } else if (age in 26..32 && bmi < 18.5 && goal == "Gain Weight") {
        exerciseIds = listOf("6", "38", "2", "37", "24", "27", "41")
        dietIds = listOf("9", "13", "1", "11", "16", "8", "4")
    } else if (age in 26..32 && bmi < 18.5 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("22", "18", "21", "16", "41", "42", "32")
        dietIds = listOf("3", "12", "8", "15", "1", "6", "10")
    } else if (age in 26..32 && bmi < 18.5 && goal == "Get Fitter") {
        exerciseIds = listOf("7", "9", "8", "20", "13", "31", "29")
        dietIds = listOf("16", "4", "5", "10", "12", "2", "11")
    } else if (age in 26..32 && bmi < 18.5 && goal == "Learn the Basics") {
        exerciseIds = listOf("13", "34", "4", "10", "14", "28", "19")
        dietIds = listOf("8", "12", "15", "16", "4", "3", "1")
    } else if (age in 26..32 && bmi in 18.6 .. 24.8 && goal == "Lose Weight") {
        exerciseIds = listOf("16", "22", "19", "13", "27", "28", "31")
        dietIds = listOf("1", "2", "8", "16", "3", "16", "6")
    } else if (age in 26..32 && bmi in 18.6 .. 24.8 && goal == "Gain Weight") {
        exerciseIds = listOf("11", "4", "35", "38", "26", "25", "42")
        dietIds = listOf("11", "16", "16", "5", "1", "6", "2")
    } else if (age in 26..32 && bmi in 18.6 .. 24.8 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("20", "22", "18", "16", "30", "33", "25")
        dietIds = listOf("5", "6", "16", "16", "9", "7", "1")
    } else if (age in 26..32 && bmi in 18.6 .. 24.8 && goal == "Get Fitter") {
        exerciseIds = listOf("7", "37", "4", "19", "21", "28", "24")
        dietIds = listOf("7", "13", "10", "9", "11", "4", "8")
    } else if (age in 26..32 && bmi in 18.6 .. 24.8 && goal == "Learn the Basics") {
        exerciseIds = listOf("12", "8", "32", "30", "14", "10", "24")
        dietIds = listOf("16", "10", "4", "16", "5", "3", "12")
    } else if (age in 26..32 && bmi in 24.9 .. 29.9 && goal == "Lose Weight") {
        exerciseIds = listOf("21", "18", "23", "20", "30", "28", "33")
        dietIds = listOf("4", "15", "13", "16", "1", "16", "3")
    } else if (age in 26..32 && bmi in 24.9 .. 29.9 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("23", "18", "16", "22", "30", "27", "25")
        dietIds = listOf("10", "7", "9", "16", "16", "2", "11")
    } else if (age in 26..32 && bmi in 24.9 .. 29.9 && goal == "Get Fitter") {
        exerciseIds = listOf("36", "3", "39", "17", "18", "42", "29")
        dietIds = listOf("10", "4", "2", "5", "13", "12", "3")
    } else if (age in 26..32 && bmi in 24.9 .. 29.9 && goal == "Learn the Basics") {
        exerciseIds = listOf("30", "4", "42", "23", "40", "11", "22")
        dietIds = listOf("10", "3", "9", "12", "16", "1", "8")
    } else if (age in 26..32 && bmi >= 30 && goal == "Lose Weight") {
        exerciseIds = listOf("23", "20", "13", "17", "30", "42", "29")
        dietIds = listOf("11", "3", "7", "2", "6", "4", "15")
    } else if (age in 26..32 && bmi >= 30 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("20", "17", "13", "23", "41", "42", "28")
        dietIds = listOf("14", "1", "9", "5", "3", "2", "4")
    } else if (age in 26..32 && bmi >= 30 && goal == "Get Fitter") {
        exerciseIds = listOf("36", "3", "39", "20", "19", "42", "26")
        dietIds = listOf("12", "10", "7", "9", "1", "16", "3")
    } else if (age in 26..32 && bmi >= 30 && goal == "Learn the Basics") {
        exerciseIds = listOf("2", "28", "12", "31", "35", "18", "17")
        dietIds = listOf("12", "6", "8", "9", "13", "11", "16")
    } else if (age in 32..40 && bmi < 18.5 && goal == "Gain Weight") {
        exerciseIds = listOf("37", "10", "3", "38", "29", "27", "30")
        dietIds = listOf("12", "10", "16", "16", "1", "5", "15")
    } else if (age in 32..40 && bmi < 18.5 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("16", "20", "18", "19", "25", "24", "41")
        dietIds = listOf("6", "8", "9", "11", "12", "10", "3")
    } else if (age in 32..40 && bmi < 18.5 && goal == "Get Fitter") {
        exerciseIds = listOf("40", "38", "5", "17", "19", "32", "24")
        dietIds = listOf("9", "3", "2", "10", "15", "4", "7")
    } else if (age in 32..40 && bmi < 18.5 && goal == "Learn the Basics") {
        exerciseIds = listOf("24", "4", "39", "42", "2", "26", "14")
        dietIds = listOf("2", "6", "12", "1", "3", "5", "16")
    } else if (age in 32..40 && bmi in 18.6 .. 24.8 && goal == "Lose Weight") {
        exerciseIds = listOf("17", "19", "13", "16", "24", "30", "32")
        dietIds = listOf("8", "1", "11", "6", "7", "12", "2")
    } else if (age in 32..40 && bmi in 18.6 .. 24.8 && goal == "Gain Weight") {
        exerciseIds = listOf("15", "39", "5", "40", "25", "28", "41")
        dietIds = listOf("16", "10", "13", "16", "11", "3", "9")
    } else if (age in 32..40 && bmi in 18.6 .. 24.8 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("19", "16", "22", "23", "41", "42", "31")
        dietIds = listOf("10", "4", "1", "6", "9", "16", "16")
    } else if (age in 32..40 && bmi in 18.6 .. 24.8 && goal == "Get Fitter") {
        exerciseIds = listOf("39", "10", "38", "13", "22", "29", "33")
        dietIds = listOf("15", "8", "16", "16", "2", "4", "5")
    } else if (age in 32..40 && bmi in 18.6 .. 24.8 && goal == "Learn the Basics") {
        exerciseIds = listOf("5", "7", "6", "33", "23", "32", "41")
        dietIds = listOf("8", "11", "13", "15", "6", "3", "2")
    } else if (age in 32..40 && bmi in 24.9 .. 29.9 && goal == "Lose Weight") {
        exerciseIds = listOf("23", "13", "17", "22", "28", "25", "24")
        dietIds = listOf("8", "15", "7", "1", "2", "6", "16")
    } else if (age in 32..40 && bmi in 24.9 .. 29.9 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("13", "18", "21", "23", "31", "32", "26")
        dietIds = listOf("3", "15", "4", "16", "16", "6", "9")
    } else if (age in 32..40 && bmi in 24.9 .. 29.9 && goal == "Get Fitter") {
        exerciseIds = listOf("5", "36", "9", "19", "23", "33", "26")
        dietIds = listOf("12", "16", "5", "6", "13", "3", "4")
    } else if (age in 32..40 && bmi in 24.9 .. 29.9 && goal == "Learn the Basics") {
        exerciseIds = listOf("35", "19", "6", "8", "22", "24", "16")
        dietIds = listOf("12", "16", "11", "1", "8", "10", "15")
    } else if (age in 32..40 && bmi >= 30 && goal == "Lose Weight") {
        exerciseIds = listOf("13", "19", "18", "22", "29", "30", "42")
        dietIds = listOf("4", "12", "1", "5", "9", "16", "13")
    } else if (age in 32..40 && bmi >= 30 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("22", "21", "20", "17", "30", "24", "33")
        dietIds = listOf("13", "12", "5", "3", "8", "9", "6")
    } else if (age in 32..40 && bmi >= 30 && goal == "Get Fitter") {
        exerciseIds = listOf("3", "12", "7", "16", "23", "24", "33")
        dietIds = listOf("9", "11", "7", "1", "8", "16", "5")
    } else if (age in 32..40 && bmi >= 30 && goal == "Learn the Basics") {
        exerciseIds = listOf("5", "23", "15", "18", "1", "19", "40")
        dietIds = listOf("7", "11", "4", "6", "9", "10", "8")
    } else if (age in 40..50 && bmi < 18.5 && goal == "Gain Weight") {
        exerciseIds = listOf("39", "3", "5", "40", "30", "27", "31")
        dietIds = listOf("16", "16", "10", "7", "9", "4", "12")
    } else if (age in 40..50 && bmi < 18.5 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("20", "18", "23", "13", "33", "42", "29")
        dietIds = listOf("8", "3", "11", "6", "15", "4", "10")
    } else if (age in 40..50 && bmi < 18.5 && goal == "Get Fitter") {
        exerciseIds = listOf("3", "9", "14", "21", "22", "26", "31")
        dietIds = listOf("14", "1", "13", "2", "6", "16", "11")
    } else if (age in 40..50 && bmi < 18.5 && goal == "Learn the Basics") {
        exerciseIds = listOf("41", "39", "18", "27", "8", "11", "23")
        dietIds = listOf("16", "10", "5", "1", "9", "11", "7")
    } else if (age in 40..50 && bmi in 18.6 .. 24.8 && goal == "Lose Weight") {
        exerciseIds = listOf("13", "23", "17", "19", "28", "24", "27")
        dietIds = listOf("12", "15", "16", "5", "16", "4", "13")
    } else if (age in 40..50 && bmi in 18.6 .. 24.8 && goal == "Gain Weight") {
        exerciseIds = listOf("14", "1", "12", "3", "42", "29", "31")
        dietIds = listOf("12", "2", "7", "4", "5", "9", "11")
    } else if (age in 40..50 && bmi in 18.6 .. 24.8 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("18", "17", "19", "21", "41", "42", "33")
        dietIds = listOf("5", "12", "10", "11", "9", "7", "16")
    } else if (age in 40..50 && bmi in 18.6 .. 24.8 && goal == "Get Fitter") {
        exerciseIds = listOf("6", "38", "8", "22", "13", "32", "41")
        dietIds = listOf("13", "10", "6", "8", "2", "16", "3")
    } else if (age in 40..50 && bmi in 18.6 .. 24.8 && goal == "Learn the Basics") {
        exerciseIds = listOf("41", "12", "36", "33", "37", "28", "4")
        dietIds = listOf("14", "13", "9", "2", "16", "6", "4")
    } else if (age in 40..50 && bmi in 24.9 .. 29.9 && goal == "Lose Weight") {
        exerciseIds = listOf("22", "20", "21", "17", "32", "28", "41")
        dietIds = listOf("14", "1", "13", "6", "2", "16", "5")
    } else if (age in 40..50 && bmi in 24.9 .. 29.9 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("21", "17", "19", "13", "31", "27", "28")
        dietIds = listOf("12", "2", "8", "13", "3", "11", "5")
    } else if (age in 40..50 && bmi in 24.9 .. 29.9 && goal == "Get Fitter") {
        exerciseIds = listOf("37", "34", "10", "22", "17", "28", "27")
        dietIds = listOf("6", "9", "12", "11", "5", "3", "7")
    } else if (age in 40..50 && bmi in 24.9 .. 29.9 && goal == "Learn the Basics") {
        exerciseIds = listOf("17", "23", "7", "1", "32", "37", "13")
        dietIds = listOf("5", "11", "16", "3", "16", "1", "12")
    } else if (age in 40..50 && bmi >= 30 && goal == "Lose Weight") {
        exerciseIds = listOf("19", "20", "16", "17", "41", "42", "32")
        dietIds = listOf("14", "10", "13", "4", "8", "15", "11")
    } else if (age in 40..50 && bmi >= 30 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("13", "23", "21", "17", "28", "25", "24")
        dietIds = listOf("3", "6", "11", "4", "12", "13", "8")
    } else if (age in 40..50 && bmi >= 30 && goal == "Get Fitter") {
        exerciseIds = listOf("37", "39", "9", "16", "19", "27", "30")
        dietIds = listOf("1", "12", "13", "10", "9", "6", "8")
    } else if (age in 40..50 && bmi >= 30 && goal == "Learn the Basics") {
        exerciseIds = listOf("18", "32", "41", "11", "36", "10", "19")
        dietIds = listOf("8", "7", "15", "1", "6", "16", "2")
    } else if (age in 50..70 && bmi < 18.5 && goal == "Gain Weight") {
        exerciseIds = listOf("4", "39", "15", "8", "27", "33", "30")
        dietIds = listOf("12", "7", "16", "11", "4", "3", "6")
    } else if (age in 50..70 && bmi < 18.5 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("23", "17", "20", "21", "32", "29", "24")
        dietIds = listOf("16", "9", "15", "5", "16", "10", "8")
    } else if (age in 50..70 && bmi < 18.5 && goal == "Get Fitter") {
        exerciseIds = listOf("4", "3", "5", "21", "18", "31", "32")
        dietIds = listOf("9", "4", "3", "2", "13", "16", "16")
    } else if (age in 50..70 && bmi < 18.5 && goal == "Learn the Basics") {
        exerciseIds = listOf("1", "4", "36", "21", "7", "25", "3")
        dietIds = listOf("13", "16", "11", "3", "4", "2", "7")
    } else if (age in 50..70 && bmi in 18.6 .. 24.8 && goal == "Lose Weight") {
        exerciseIds = listOf("18", "22", "13", "23", "28", "24", "33")
        dietIds = listOf("11", "4", "15", "12", "6", "13", "16")
    } else if (age in 50..70 && bmi in 18.6 .. 24.8 && goal == "Gain Weight") {
        exerciseIds = listOf("9", "14", "36", "12", "28", "30", "24")
        dietIds = listOf("8", "3", "4", "10", "15", "13", "2")
    } else if (age in 50..70 && bmi in 18.6 .. 24.8 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("35", "38", "4", "15", "29", "42", "31")
        dietIds = listOf("10", "3", "7", "11", "5", "8", "13")
    } else if (age in 50..70 && bmi in 18.6 .. 24.8 && goal == "Get Fitter") {
        exerciseIds = listOf("15", "11", "5", "18", "16", "30", "33")
        dietIds = listOf("1", "13", "5", "15", "10", "4", "9")
    } else if (age in 50..70 && bmi in 18.6 .. 24.8 && goal == "Learn the Basics") {
        exerciseIds = listOf("15", "21", "32", "17", "6", "39", "26")
        dietIds = listOf("5", "16", "2", "15", "4", "1", "10")
    } else if (age in 50..70 && bmi in 24.9 .. 29.9 && goal == "Lose Weight") {
        exerciseIds = listOf("18", "23", "16", "13", "28", "31", "29")
        dietIds = listOf("14", "5", "1", "10", "9", "7", "15")
    } else if (age in 50..70 && bmi in 24.9 .. 29.9 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("22", "16", "23", "18", "32", "30", "41")
        dietIds = listOf("1", "5", "15", "16", "4", "2", "9")
    } else if (age in 50..70 && bmi in 24.9 .. 29.9 && goal == "Get Fitter") {
        exerciseIds = listOf("2", "5", "40", "13", "19", "28", "32")
        dietIds = listOf("7", "10", "13", "12", "15", "6", "3")
    } else if (age in 50..70 && bmi in 24.9 .. 29.9 && goal == "Learn the Basics") {
        exerciseIds = listOf("38", "36", "42", "3", "18", "35", "40")
        dietIds = listOf("6", "1", "4", "8", "12", "15", "16")
    } else if (age in 50..70 && bmi >= 30 && goal == "Lose Weight") {
        exerciseIds = listOf("16", "21", "23", "20", "27", "25", "41")
        dietIds = listOf("5", "9", "4", "16", "3", "1", "8")
    } else if (age in 50..70 && bmi >= 30 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("23", "20", "19", "17", "26", "24", "29")
        dietIds = listOf("6", "15", "12", "2", "9", "3", "4")
    } else if (age in 50..70 && bmi >= 30 && goal == "Get Fitter") {
        exerciseIds = listOf("37", "9", "40", "23", "18", "41", "29")
        dietIds = listOf("4", "2", "13", "8", "16", "16", "12")
    } else if (age in 50..70 && bmi >= 30 && goal == "Learn the Basics") {
        exerciseIds = listOf("36", "5", "8", "12", "2", "32", "21")
        dietIds = listOf("4", "7", "13", "9", "15", "1", "16")
    } else if (age >= 70 && bmi < 18.5 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("23", "21", "13", "18", "26", "41", "31")
        dietIds = listOf("7", "16", "10", "13", "16", "11", "5")
    } else if (age >= 70 && bmi < 18.5 && goal == "Get Fitter") {
        exerciseIds = listOf("4", "8", "7", "19", "23", "24", "41")
        dietIds = listOf("15", "11", "9", "5", "6", "16", "13")
    } else if (age >= 70 && bmi < 18.5 && goal == "Learn the Basics") {
        exerciseIds = listOf("4", "23", "8", "18", "34", "7", "42")
        dietIds = listOf("16", "15", "16", "9", "8", "12", "10")
    } else if (age >= 70 && bmi in 18.6 .. 24.8 && goal == "Lose Weight") {
        exerciseIds = listOf("16", "22", "18", "20", "26", "42", "41")
        dietIds = listOf("12", "15", "13", "16", "6", "3", "2")
    } else if (age >= 70 && bmi in 18.6 .. 24.8 && goal == "Gain Weight") {
        exerciseIds = listOf("8", "40", "36", "37", "31", "42", "33")
        dietIds = listOf("14", "1", "2", "7", "8", "12", "15")
    } else if (age >= 70 && bmi in 18.6 .. 24.8 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("20", "22", "18", "21", "27", "31", "33")
        dietIds = listOf("5", "13", "7", "9", "15", "6", "4")
    } else if (age >= 70 && bmi in 18.6 .. 24.8 && goal == "Get Fitter") {
        exerciseIds = listOf("3", "15", "37", "20", "21", "28", "27")
        dietIds = listOf("1", "3", "8", "16", "12", "9", "4")
    } else if (age >= 70 && bmi in 18.6 .. 24.8 && goal == "Learn the Basics") {
        exerciseIds = listOf("32", "33", "26", "37", "35", "34", "15")
        dietIds = listOf("15", "4", "1", "13", "12", "8", "2")
    } else if (age >= 70 && bmi in 24.9 .. 29.9 && goal == "Lose Weight") {
        exerciseIds = listOf("23", "18", "20", "22", "28", "24", "26")
        dietIds = listOf("8", "11", "4", "3", "2", "5", "16")
    } else if (age >= 70 && bmi in 24.9 .. 29.9 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("16", "18", "22", "21", "33", "41", "26")
        dietIds = listOf("3", "15", "16", "9", "2", "13", "11")
    } else if (age >= 70 && bmi in 24.9 .. 29.9 && goal == "Get Fitter") {
        exerciseIds = listOf("9", "2", "10", "23", "19", "27", "28")
        dietIds = listOf("2", "8", "4", "11", "10", "6", "13")
    } else if (age >= 70 && bmi in 24.9 .. 29.9 && goal == "Learn the Basics") {
        exerciseIds = listOf("31", "11", "9", "38", "15", "19", "24")
        dietIds = listOf("5", "10", "9", "1", "2", "7", "6")
    } else if (age >= 70 && bmi >= 30 && goal == "Lose Weight") {
        exerciseIds = listOf("17", "20", "13", "21", "31", "26", "33")
        dietIds = listOf("10", "8", "3", "1", "16", "16", "9")
    } else if (age >= 70 && bmi >= 30 && goal == "Gain More Flexibility") {
        exerciseIds = listOf("16", "19", "21", "23", "28", "25", "29")
        dietIds = listOf("15", "5", "10", "7", "16", "16", "12")
    } else if (age >= 70 && bmi >= 30 && goal == "Get Fitter") {
        exerciseIds = listOf("38", "36", "2", "22", "18", "26", "29")
        dietIds = listOf("9", "8", "3", "6", "2", "11", "4")
    } else if (age >= 70 && bmi >= 30 && goal == "Learn the Basics") {
        exerciseIds = listOf("9", "10", "39", "41", "27", "31", "22")
        dietIds = listOf("1", "10", "9", "16", "15", "4", "5")
    } else {
        Log.d("FitnessPlan", "No matching fitness plan found for age: $age, bmi: $bmi, goal: $goal")
        return
    }
    Log.d("FitnessPlan", "Matching fitness plan found for age: $age, bmi: $bmi, goal: $goal")


    delay(300)

    exerciseIds.forEach { id ->
        exerciseViewModel.fetchExercise(id)
        delay(100)
    }
    dietIds.forEach { ids ->
        dietViewModel.fetchDiet(ids)
        delay(100)
    }

    Log.d("FitnessPlan", "Fetched Exercise IDs: $exerciseIds")
    Log.d("FitnessPlan", "Fetched Diet IDs: $dietIds")
}

