package com.example.fitconnect

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class UserInputViewModel(application: Application) : AndroidViewModel(application) {



    // User input data
    val age = mutableStateOf(20)
    val height = mutableStateOf(150)  // in cm
    val weight = mutableStateOf(60)   // in kg
    val gender = mutableStateOf<String?>(null)
    val goal = mutableStateOf<String?>(null)
    val bmi = mutableStateOf(0.0)
    var username = mutableStateOf<String?>(null)
    var imageUri = mutableStateOf<Uri?>(null)

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val userDao = AppDatabase.getDatabase(application).userDao()

    private val profileDao = AppDatabase.getDatabase(application).profileDao()

    fun saveProfile(uid: String) {
        viewModelScope.launch {
            val profile = ProfileEntity(
                uid = uid,
                imageUri = imageUri.value?.toString()
            )
            profileDao.insertProfile(profile)
        }
    }
    fun loadProfile(uid: String, onLoaded: (ProfileEntity?) -> Unit) {
        viewModelScope.launch {
            val profile = profileDao.getProfile(uid)
            profile?.let {
                imageUri.value = it.imageUri?.let { uriString -> Uri.parse(uriString) }
            }
            onLoaded(profile)
        }
    }
    fun clearProfile() {
        viewModelScope.launch {
            profileDao.clearProfile()
        }
    }

    fun calculateBMI() {
        val heightInMeters = height.value / 100.0
        val rawBmi = if (heightInMeters > 0) {
            weight.value / (heightInMeters * heightInMeters)
        } else 0.0
        bmi.value = String.format("%.2f", rawBmi).toDouble()
    }

//    fun updateUser(age: Int, height: Int, weight: Int, goal: String) {
//        this.age.value = age
//        this.height.value = height
//        this.weight.value = weight
//        this.goal.value = goal
//
//        saveToDatabase()
//
//        saveToFirebase()
//    }

    fun getUserFromRoom(userId: String, callback: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUser(userId)
            callback(user)
        }
    }

    fun saveUserData(onComplete: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid
//        val uri = imageUri.value

        if (userId == null) {
            Log.e("Firebase", "User is not logged in. Cannot save data.")
            onComplete(false)
            return
        }

            val userData = mutableMapOf<String, Any>(
                "age" to age.value,
                "height" to height.value,
                "weight" to weight.value,
                "bmi" to bmi.value
            )
            gender.value?.let { userData["gender"] = it }
            username.value?.let { userData["username"] = it }
            goal.value?.let{userData["goal"] =it}

            db.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener {
                    Log.d("Firebase", "User data saved successfully.")
                    onComplete(true)
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Error saving user data", e)
                    onComplete(false)
                }

    }


    suspend fun getUserData() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.e("Firestore", "User not logged in")
            return
        }

        Log.d("Firestore", "Fetching document for user: $userId")

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                Log.d("Firestore", "Document fetched")

                if (document.exists()) {
                    Log.d("Firestore", "Document exists, parsing user data")

                    val age = document.getLong("age")?.toInt() ?: 0
                    val height = document.getLong("height")?.toInt() ?: 0
                    val weight = document.getLong("weight")?.toInt() ?: 0
                    val bmi = document.getDouble("bmi") ?: 0.0
                    val gender = document.getString("gender")
                    val username = document.getString("username")
                    val goal = document.getString("goal")

                    val userEntity = UserEntity(
                        id = userId,
                        age = age,
                        height = height,
                        weight = weight,
                        bmi = bmi,
                        gender = gender,
                        username = username,
                        goal = goal
                    )

                    Log.d("Firestore", "Parsed user: $userEntity")

                    viewModelScope.launch {
                        userDao.insertUser(userEntity)
                        Log.d("Firestore", "User inserted into Room DB")
                    }

                } else {
                    Log.e("Firestore", "User document does not exist")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching user data", exception)
            }
    }
}

class MainViewModel:ViewModel() {

    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.BottomScreen.Exercise)
    val currentScreen: MutableState<Screen>
        get() = _currentScreen
    private val _isFetching = MutableStateFlow(true)
    val isFetching = _isFetching.asStateFlow()

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value = screen

    }
    fun clearData() {
        // Reset all stored data
        _currentScreen.value = Screen.BottomScreen.Exercise
        // Clear any other stored state
    }

    fun runFitnessPlanLogic(
        user: UserEntity,
        dietViewModel: DietViewModel,
        exerciseViewModel: ExerciseViewModel
    ) {
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Running fitness plan logic for you")
                getFitnessPlan(
                    age = user.age,
                    bmi = user.bmi,
                    goal = user.goal.toString(),
                    dietViewModel = dietViewModel,
                    exerciseViewModel = exerciseViewModel
                )
                Log.d("MainViewModel", "Finished fitness plan logic.")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching plan: ${e.localizedMessage}", e)
            } finally {
                _isFetching.value = false
            }
        }
    }
}

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val exerciseDao = db.exerciseDao() // Assuming you have ExerciseDao setup

    private val _exerciseState: MutableState<ExerciseState> = mutableStateOf(ExerciseState())
    val exerciseState: State<ExerciseState> = _exerciseState

    // Fetch a single exercise by ID
    fun fetchExercise(id: String) {
        viewModelScope.launch {
            try {
                val exerciseItem = exerciseService.getExerciseById(id) // returns single ExerciseFromAPI

                // Convert to your ExerciseItem entity if needed, or directly use ExerciseFromAPI
                val exercise = ExerciseItem(
                    id = exerciseItem.id,
                    exercise = exerciseItem.exercise,
                    description = exerciseItem.description,
                    repsTime = exerciseItem.repsTime,
                    gif = exerciseItem.gif,
                    effects = exerciseItem.effects
                )

                // Save to Room
                exerciseDao.insertAll(listOf(exercise))

                _exerciseState.value = _exerciseState.value.copy(
                    loading = false,
                    list = listOf(exercise),
                    error = null
                )

                Log.d("ExerciseViewModel", "Fetched single exercise from API")

            } catch (e: Exception) {
                _exerciseState.value = _exerciseState.value.copy(
                    loading = false,
                    error = "Error fetching exercise: ${e.message}"
                )
            }
        }
    }

    fun clearExercise() {
        viewModelScope.launch {
            exerciseDao.clearAllExercise()
        }
    }

    // Load from Room
    fun loadExercisesFromDao() {
        viewModelScope.launch {
            val items = exerciseDao.getAllExerciseItems()
            Log.d("ExerciseViewModel", "Loaded ${items.size} from DAO")
            _exerciseState.value = _exerciseState.value.copy(
                loading = false,
                list = items,
                error = null
            )
        }
    }
    fun getExerciseItemById(id: String): Flow<ExerciseItem?> {
        return exerciseDao.getExerciseItemById(id)
    }

    suspend fun hasExerciseData(): Boolean {
        return exerciseDao.getAllExerciseItems().isNotEmpty()
    }

    data class ExerciseState(
        val loading: Boolean = true,
        val list: List<ExerciseItem> = emptyList(),
        val error: String? = null
    )
}


class DietViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dietDao = db.dietDao() // Assuming you have a DAO for diet

    private val _dietState: MutableState<DietState> = mutableStateOf(DietState())
    val dietState: State<DietState> = _dietState


    fun fetchDiet(id: String) {
        viewModelScope.launch {
            try {
                // Fetching single FoodItem from API
                val foodItem = dietService.getDietById(id) // Returns FoodItem

                // Convert it into your DietItem (Room entity)
                val dietItem = DietItem(
                    id = foodItem.ID,
                    name = foodItem.Name,
                    image = foodItem.image,
                    amount = foodItem.Amount,
                    recipe1 = foodItem.recipe1,
                    recipe1Description = foodItem.recipe1Description,
                    recipe2 = foodItem.recipe2,
                    recipe2Description = foodItem.recipe2Description,
                    recipe3 = foodItem.recipe3,
                    recipe3Description = foodItem.recipe3Description
                )

                // Save to Room (as a list of one)
                dietDao.insertAll(listOf(dietItem))

                Log.d("DietViewModel", "Fetched single diet item from API")

                _dietState.value = _dietState.value.copy(
                    loading = false,
                    list = listOf(dietItem),
                    error = null
                )

            } catch (e: Exception) {
                _dietState.value = _dietState.value.copy(
                    loading = false,
                    error = "Error fetching diet item: ${e.message}"
                )
            }
        }
    }
    fun clearDiet() {
        viewModelScope.launch {
            dietDao.clearAllDiet()
        }
    }



    // This function will load diet data from the DAO
    fun loadDietFromDao() {
        viewModelScope.launch {
            val dietItems = dietDao.getAllDietItems()
            Log.d("DietViewModel", "Fetched items from DAO")
            _dietState.value = _dietState.value.copy(

                loading = false,
                list = dietItems,
                error = null
            )
        }
    }

    fun getDietItemById(id: String): Flow<DietItem?> {
        return dietDao.getDietItemById(id)
    }

    suspend fun hasDietData(): Boolean {
        return dietDao.getAllDietItems().isNotEmpty()
    }

    data class DietState(
        val loading: Boolean = true,
        val list: List<DietItem> = emptyList(),
        val error: String? = null
    )
}


class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.taskDao()
    private val _tasks = MutableStateFlow<List<TaskItem>>(emptyList())
    val tasks: Flow<List<TaskItem>> = _tasks.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAllTasks().collect { taskList ->
                _tasks.value = taskList // Update UI state
            }
        }
    }

    fun addTask(task: TaskItem) = viewModelScope.launch {
        dao.insertTask(task)
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        dao.getAllTasks().first().find { it.id == id }?.let {
            dao.deleteTask(it)
        }
    }

    fun markTaskComplete(id: Int) = viewModelScope.launch {
        dao.getAllTasks().first().find { it.id == id }?.let {
            dao.updateTask(it.copy(completed = true))
        }
    }

    fun clearTasks() {
        viewModelScope.launch {
            dao.clearAll()
        }
    }
    suspend fun getAllTasks(): List<TaskItem> {
        return dao.getAllTasks().first()
    }

    fun scheduleTaskNotification(context: Context, task: TaskItem) {
         if(task.completed)
             return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskNotificationReceiver::class.java).apply {
            action = "com.example.fitconnect.TASK_NOTIFICATION"
            putExtra("taskId", task.id)
            putExtra("taskName", task.title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, task.id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = task.timeInMillis // your saved time in millis
            add(Calendar.MINUTE, -1) // 1 minute before
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }


    fun cancelTaskNotification(context: Context, taskId: Int, taskName: String) {
        val intent = Intent(context, TaskNotificationReceiver::class.java).apply {
            action = "com.example.fitconnect.TASK_NOTIFICATION" // ✅ Match action
            putExtra("taskId", taskId)                          // ✅ Match extras
            putExtra("taskName", taskName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

}

