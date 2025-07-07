# 💪 FitConnect – Your Personalized Fitness Companion

**FitConnect** is an Android fitness app built with Kotlin and Jetpack Compose to help users stay healthy, active, and consistent. It includes BMI calculations, dynamic diet and workout plans via a FastAPI backend, and task scheduling with daily reset and notification support.

---

## 📲 Download the APK

👉 [**Download FitConnect.apk**](https://raw.githubusercontent.com/Observer-1729/Fitconnect/main/Apk/FitConnect.apk)

---

## ✨ Features

- 🔢 **BMI Calculator** – Calculates BMI and recommends fitness actions
- 🏋️ **Personalized Exercises** – Pulled from a FastAPI-powered backend
- 🥗 **Daily Diet Plans** – Food suggestions with recipes via API
- 🕒 **Smart Task Scheduling** – Get notified 1 minute before each task
- ♻️ **Auto Reset** – Tasks auto-reset every midnight
- 📱 **Offline Storage** – Uses Room database for local persistence

---

## 🖼️ App Screenshots

<div align="center">
  <img src="https://raw.githubusercontent.com/Observer-1729/Fitconnect/main/Screenshots/Select-Gender.jpg" width="220" />
  <img src="https://raw.githubusercontent.com/Observer-1729/Fitconnect/main/Screenshots/Diet-List.jpg" width="220" />
  <img src="https://raw.githubusercontent.com/Observer-1729/Fitconnect/main/Screenshots/Recipe-Screen.jpg" width="220" />
  <img src="https://raw.githubusercontent.com/Observer-1729/Fitconnect/main/Screenshots/Exercise-List.jpg" width="220" />
  <img src="https://raw.githubusercontent.com/Observer-1729/Fitconnect/main/Screenshots/Exercise-Description.jpg" width="220" />
  <img src="https://raw.githubusercontent.com/Observer-1729/Fitconnect/main/Screenshots/Tasks-Screen.jpg" width="220" />
</div>

---

## 🌐 Website

🔗 [**Live Landing Page**](https://fitconnect-three.vercel.app/)

---

## ⚙️ Backend API – FastAPI (fitapi)

FitConnect fetches diet and exercise data using a custom backend built with **FastAPI**. You need to **deploy this API** and link it in the app.

### 🔧 Step 1: Deploy the FastAPI Project

Use platforms like:

- [Render](https://render.com/)
- [Railway](https://railway.app/)
- [Fly.io](https://fly.io/)
- [Replit](https://replit.com/)

Once deployed, you’ll get a base URL such as:

https://fitapi-perfect.onrender.com


---

### 🧩 Step 2: Update API Base URL in `ApiService.kt`

In your Android project, open:

app/src/main/java/.../network/ApiService.kt

Replace the existing base URL in the Retrofit builder:

```kotlin
private val retrofit = Retrofit.Builder()
    .baseUrl("https://fitapi-perfect.onrender.com") // ← Replace with your own FastAPI URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()

```

## 📥 How to Clone and Run Locally
Want to run FitConnect on your own system? Here's how:

### 1️⃣ Clone the Repository
```
bash

git clone https://github.com/Observer-1729/Fitconnect.git
cd Fitconnect
```
### 2️⃣ Open in Android Studio

Launch Android Studio

Click on "Open"

Select the cloned Fitconnect folder

Let it sync and index

### 3️⃣ Connect Device or Emulator
Plug in your Android phone or start an emulator

Press Run ▶️ in Android Studio

### 4️⃣ (Optional) Change API Endpoint
If you have deployed your own fitapi, update the base URL as shown above.


## 🛠 Tech Stack

| **Layer**    | **Tools**                   |
|--------------|-----------------------------|
| Frontend     | Kotlin + Jetpack Compose    |
| Database     | Room (SQLite) + Firebase    |
| Backend      | FastAPI (fitapi)            |
| Networking   | Retrofit + Gson             |
| Website      | HTML/CSS + Vercel           |
| Hosting      | GitHub, Render, Vercel      |

## 📧 Contact
Developed by SahilKumar Singh