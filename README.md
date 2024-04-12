# Retrofitless Android Project with HTTPUrlConnection

This is a simple Android project demonstrating how to interact with a remote API using `HttpURLConnection` instead of Retrofit. The project follows the principles of Clean Architecture and Repository Pattern.

## Features

- Login to the API
- Fetch user profile data

## Used

- Dagger Hilt for dependency injection
- HTTPUrlConnection for network requests
- Lottie for loading
- Coroutine Lifecycle Scopes

## Architecture

The project is structured using Clean Architecture principles:

- **Presentation Layer:** Contains Activities/Fragments and ViewModels for handling UI logic.
- **Domain Layer:** Contains UseCases that represent the business logic.
- **Data Layer:** Contains repositories responsible for interacting with the remote API.

## Usage

To use this project, follow these steps:

1. Clone the repository: `https://github.com/Getir-Android-Kotlin-Bootcamp/getir-android-kotlin-bootcamp-w4-assignment-erenkaraboga.git`
2. Open the project in Android Studio.
3. Build and run the project on an emulator or device.

## Configuration

Before running the project, make sure to configure the following:

- Replace the login informations in `MainViewModel` with your actual informations
- Make sure your informations for login is correct
- 
https://github.com/Getir-Android-Kotlin-Bootcamp/getir-android-kotlin-bootcamp-w4-assignment-erenkaraboga/assets/74095539/a7785db7-7e8b-43c2-b595-101f9a33f85a
